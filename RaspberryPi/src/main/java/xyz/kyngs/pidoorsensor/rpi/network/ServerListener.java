package xyz.kyngs.pidoorsensor.rpi.network;

import com.github.steveice10.packetlib.event.server.*;
import xyz.kyngs.pidoorsensor.rpi.RaspberryPi;

import static xyz.kyngs.pidoorsensor.rpi.RaspberryPi.LOGGER;

public class ServerListener implements com.github.steveice10.packetlib.event.server.ServerListener {

    private final RaspberryPi pi;

    public ServerListener(RaspberryPi bot) {
        this.pi = bot;
    }

    @Override
    public void serverBound(ServerBoundEvent event) {

    }

    @Override
    public void serverClosing(ServerClosingEvent event) {

    }

    @Override
    public void serverClosed(ServerClosedEvent event) {

    }

    @Override
    public void sessionAdded(SessionAddedEvent event) {
        pi.getNetworkHandler().getClients().put(event.getSession(), new Client(event.getSession()));
        event.getSession().addListener(new SessionListener(pi));
        LOGGER.info(event.getSession().getHost() + ": connected");
    }

    @Override
    public void sessionRemoved(SessionRemovedEvent event) {
    }
}
