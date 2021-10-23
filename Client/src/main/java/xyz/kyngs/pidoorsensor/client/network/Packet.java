package xyz.kyngs.pidoorsensor.client.network;

import com.github.steveice10.packetlib.Session;
import xyz.kyngs.pidoorsensor.client.Client;

public interface Packet extends com.github.steveice10.packetlib.packet.Packet {

    void handle(Session sender, Client client);
}
