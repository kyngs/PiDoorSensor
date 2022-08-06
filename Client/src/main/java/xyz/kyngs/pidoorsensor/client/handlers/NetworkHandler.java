package xyz.kyngs.pidoorsensor.client.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.kyngs.pidoorsensor.client.Client;
import xyz.kyngs.pidoorsensor.client.network.ReconnectableClient;

public class NetworkHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkHandler.class);

    private final ReconnectableClient netClient;

    public NetworkHandler(Client client) {
        super(client);
        netClient = new ReconnectableClient(client.getAddress(), client);
        netClient.beginConnect();
    }

    public ReconnectableClient getNetClient() {
        return netClient;
    }

    @Override
    public void shutdown() {
        LOGGER.info("Disconnecting from server");

        netClient.disconnect("Client shutting down");

        LOGGER.info("Disconnected from server");
    }
}
