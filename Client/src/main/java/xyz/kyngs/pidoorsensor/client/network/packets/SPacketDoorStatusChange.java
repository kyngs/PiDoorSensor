package xyz.kyngs.pidoorsensor.client.network.packets;

import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import xyz.kyngs.pidoorsensor.client.Client;
import xyz.kyngs.pidoorsensor.client.network.Packet;

import java.io.IOException;

public class SPacketDoorStatusChange implements Packet {

    private boolean open;

    @Override
    public void handle(Session sender, Client client) {
        client.onDoorStatusChange(open);
    }

    @Override
    public void read(NetInput in) throws IOException {
        open = in.readBoolean();
    }

    @Override
    public void write(NetOutput out) throws IOException {

    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
