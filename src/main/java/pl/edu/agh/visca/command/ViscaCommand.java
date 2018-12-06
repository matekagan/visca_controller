package pl.edu.agh.visca.command;

import pl.edu.agh.visca.connection.ViscaConnection;
import pl.edu.agh.kis.visca.cmd.Cmd;

import java.util.HashMap;
import java.util.Map;

public abstract class ViscaCommand {

    protected static int PAN_SPEED_INDEX = 4;
    protected static int TILT_SPEED_INDEX = 5;
    protected static int ZOOM_SPEED_INDEX = 4;
    protected byte BROADCAST_ADDR = 88;

    private Map<String, String> params;

    protected Map<String, String> getParams() {
        return params != null ? params : new HashMap<>();
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public abstract ViscaResponse execute(ViscaConnection viscaConnection);

    public abstract String getCode();

    protected byte[] getCommandData(Cmd cmd) {
        byte[] cmdData = cmd.createCommandData();
        pl.edu.agh.kis.visca.cmd.ViscaCommand vCmd = new pl.edu.agh.kis.visca.cmd.ViscaCommand();
        vCmd.commandData = cmdData;
        vCmd.sourceAdr = resolveSourceAddress();
        vCmd.destinationAdr = resolveDestinationAddress();
        return vCmd.getCommandData();
    }

    protected byte[] getCommandDataWithPanTiltParameters(Cmd cmd){
        return applyPanTiltParameters(getCommandData(cmd));
    }

    protected byte[] getCommandDataWithZoomParameters(Cmd cmd){
        return applyZoomParameter(getCommandData(cmd));
    }

    protected String getRequiredParam(String param) {
        String value = getParams().get(param);
        if (value == null) {
            throw new CommandParameterException(String.format("Parameter %s is required", param));
        }
        return value;
    }

    private byte resolveSourceAddress() {
        String srcStr = getParams().get("src");
        return srcStr != null ? Byte.parseByte(srcStr) : getDefaultSourceAddress();
    }

    private byte resolveDestinationAddress() {
        String srcStr = getParams().get("dest");
        // all is an alias for broadcast command
        srcStr = srcStr != null && srcStr.equals("all") ? "8" : srcStr;
        return srcStr != null ? Byte.parseByte(srcStr) : getDefaultDestinationAddress();
    }

    protected abstract byte getDefaultSourceAddress();
    protected abstract byte getDefaultDestinationAddress();

    private byte[] applyPanTiltParameters(byte[] data){
        if (getParams().get("pan_speed") != null){
            data[PAN_SPEED_INDEX] = Byte.parseByte(getParams().get("pan_speed"));
        }
        if (getParams().get("tilt_speed") != null){
            data[TILT_SPEED_INDEX] = Byte.parseByte(getParams().get("tilt_speed"));
        }
        return data;
    }

    private byte[] applyZoomParameter(byte [] data){
        if (getParams().get("zoom_speed") != null){
            data[ZOOM_SPEED_INDEX] = Byte.parseByte(getParams().get("zoom_speed"));
        }
        return data;
    }
}
