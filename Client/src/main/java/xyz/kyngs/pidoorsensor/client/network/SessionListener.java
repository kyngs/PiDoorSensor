package xyz.kyngs.pidoorsensor.client.network;

import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.PacketErrorEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.kyngs.pidoorsensor.client.Client;

public class SessionListener extends SessionAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);

    private final Client client;

    public SessionListener(Client client) {
        this.client = client;
    }

    @Override
    public void disconnected(DisconnectedEvent event) {

        var netClient = client.getNetworkHandler().getNetClient();
        if (netClient.isConnecting()) return;
        var reason = netClient.getReasonForDisconnect();

        if (reason == null) reason = event.getReason();

        LOGGER.warn("Got disconnected from the server, attempting reconnect; reason: " + reason);

        netClient.onDisconnect();
    }

    @Override
    public void packetError(PacketErrorEvent event) {
        super.packetError(event);
        event.getSession().disconnect("Error sending packet to server", event.getCause());
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        Packet packet = event.getPacket();
        packet.handle(event.getSession(), client);
    }
}
