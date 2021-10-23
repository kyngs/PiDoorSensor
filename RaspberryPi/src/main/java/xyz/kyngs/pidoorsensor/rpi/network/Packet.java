package xyz.kyngs.pidoorsensor.rpi.network;

import com.github.steveice10.packetlib.Session;
import xyz.kyngs.pidoorsensor.rpi.RaspberryPi;

public interface Packet extends com.github.steveice10.packetlib.packet.Packet {

    default void handle(Session sender, RaspberryPi pi) {
        handle(sender, pi, pi.getNetworkHandler().getClients().get(sender));
    }

    void handle(Session sender, RaspberryPi pi, Client remote);

}
