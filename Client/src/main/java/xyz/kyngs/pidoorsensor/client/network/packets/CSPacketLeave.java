package xyz.kyngs.pidoorsensor.client.network.packets;

import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import xyz.kyngs.pidoorsensor.client.Client;
import xyz.kyngs.pidoorsensor.client.network.Packet;

import java.io.IOException;

public class CSPacketLeave implements Packet {
    private String reason;

    public CSPacketLeave(String reason) {
        this.reason = reason;
    }

    @Override
    public void handle(Session sender, Client client) {
        client.getNetworkHandler().getNetClient().setReasonForDisconnect(reason);
    }

    @Override
    public void read(NetInput in) throws IOException {
        reason = in.readString();
    }

    @Override
    public void write(NetOutput out) throws IOException {
        out.writeString(reason);
    }

    @Override
    public boolean isPriority() {
        return true;
    }
}
