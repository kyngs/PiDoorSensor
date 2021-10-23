package xyz.kyngs.pidoorsensor.rpi.network;

import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import xyz.kyngs.pidoorsensor.rpi.RaspberryPi;

import static xyz.kyngs.pidoorsensor.rpi.RaspberryPi.LOGGER;

public class SessionListener extends SessionAdapter {

    private final RaspberryPi pi;

    public SessionListener(RaspberryPi pi) {
        this.pi = pi;
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        Packet packet = event.getPacket();
        packet.handle(event.getSession(), pi);
    }

    @Override
    public void disconnected(DisconnectedEvent event) {
        if (event.getCause() != null) event.getCause().printStackTrace();

        var networkHandler = pi.getNetworkHandler();
        var session = event.getSession();
        var reason = networkHandler.getClients().get(session).getLeaveReason();

        networkHandler.getClients().remove(session);
        if (reason == null) reason = event.getReason();

        LOGGER.info(event.getSession().getHost() + ": disconnected; reason: " + reason);
    }
}