package pl.edu.agh.visca.connection;

import pl.edu.agh.visca.command.ViscaResponse;
import pl.edu.agh.visca.Util;

public class StubConnection implements ViscaConnection {

    @Override
    public ViscaResponse readResponse() {
        return new ViscaResponse(new byte[] {0x40, 0x45, -1});
    }

    @Override
    public boolean writeBytes(byte[] data) {
        System.out.println("Writing data: " + Util.byteArrayToString(data));
        return true;
    }
}
