package xyz.kyngs.pidoorsensor.rpi.network.packets;

import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.io.NetInput;
import com.github.steveice10.packetlib.io.NetOutput;
import xyz.kyngs.pidoorsensor.rpi.RaspberryPi;
import xyz.kyngs.pidoorsensor.rpi.network.Client;
import xyz.kyngs.pidoorsensor.rpi.network.Packet;

import java.io.IOException;

public class CSPacketLeave implements Packet {

    private String reason;

    public CSPacketLeave(String reason) {
        this.reason = reason;
    }

    @Override
    public void handle(Session sender, RaspberryPi pi, Client client) {
        client.setLeaveReason(reason);
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
