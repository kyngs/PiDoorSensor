package xyz.kyngs.pidoorsensor.client.network.packets;

import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import xyz.kyngs.pidoorsensor.client.Client;
import xyz.kyngs.pidoorsensor.client.network.Packet;

import java.io.IOException;

public class CSPacketPing implements Packet {
    @Override
    public void handle(Session sender, Client client) {
    }

    @Override
    public void read(NetInput in) throws IOException {

    }

    @Override
    public void write(NetOutput out) throws IOException {

    }

    @Override
    public boolean isPriority() {
        return false;
    }
}
