package xyz.kyngs.pidoorsensor.rpi.handlers;

import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpServer;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import xyz.kyngs.pidoorsensor.rpi.RaspberryPi;
import xyz.kyngs.pidoorsensor.rpi.network.Client;
import xyz.kyngs.pidoorsensor.rpi.network.PacketProtocol;
import xyz.kyngs.pidoorsensor.rpi.network.ServerListener;
import xyz.kyngs.pidoorsensor.rpi.network.packets.CSPacketLeave;
import xyz.kyngs.pidoorsensor.rpi.network.packets.SPacketDoorStatusChange;

import java.util.HashMap;
import java.util.Map;

public class NetworkHandler extends AbstractHandler {

    private final Server server;
    private final Map<Session, Client> clients;

    public NetworkHandler(RaspberryPi pi) {
        super(pi);
        clients = new HashMap<>();
        server = new TcpServer("0.0.0.0", 26853, PacketProtocol.class);
        server.addListener(new ServerListener(pi));
        LOGGER.info("Binding server");
        server.bind(true);
        LOGGER.info("§aServer bound");

        pi.getPiHandler().getDoorSwitch().addListener((GpioPinListenerDigital) event -> broadcastPacket(new SPacketDoorStatusChange(event.getState() == PinState.LOW)));
    }

    public Map<Session, Client> getClients() {
        return clients;
    }

    public void broadcastPacket(Packet packet) {
        clients.keySet().forEach(session -> session.send(packet));
    }

    @Override
    public void shutdown() {
        LOGGER.info("Closing server");
        clients.forEach((session, client) -> {
            session.send(new CSPacketLeave("Server closed"));
        });
        server.close(true);
        LOGGER.info("§aServer closed");
    }


}
