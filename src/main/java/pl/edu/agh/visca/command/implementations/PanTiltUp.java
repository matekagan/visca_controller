package pl.edu.agh.visca.command.implementations;

import pl.edu.agh.visca.command.ViscaCommand;
import pl.edu.agh.visca.command.ViscaResponse;
import pl.edu.agh.visca.connection.ViscaConnection;
import pl.edu.agh.kis.visca.cmd.PanTiltUpCmd;

public class PanTiltUp extends ViscaCommand {

    @Override
    public ViscaResponse execute(ViscaConnection viscaConnection) {
        byte[] commandData = getCommandDataWithPanTiltParameters(new PanTiltUpCmd());
        viscaConnection.writeBytes(commandData);
        return viscaConnection.readResponse();
    }

    @Override
    public String getCode() {
        return "move-up";
    }

    @Override
    protected byte getDefaultSourceAddress() {
        return 0;
    }

    @Override
    protected byte getDefaultDestinationAddress() {
        return 1;
    }

}
