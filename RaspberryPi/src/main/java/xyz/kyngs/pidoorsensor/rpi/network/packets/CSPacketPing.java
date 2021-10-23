package xyz.kyngs.pidoorsensor.rpi.network.packets;

import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import xyz.kyngs.pidoorsensor.rpi.RaspberryPi;
import xyz.kyngs.pidoorsensor.rpi.network.Client;
import xyz.kyngs.pidoorsensor.rpi.network.Packet;

import java.io.IOException;

public class CSPacketPing implements Packet {
    @Override
    public void handle(Session sender, RaspberryPi pi, Client client) {
        sender.send(new CSPacketPing());
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

