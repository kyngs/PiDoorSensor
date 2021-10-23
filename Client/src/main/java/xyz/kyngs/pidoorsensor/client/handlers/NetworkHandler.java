package xyz.kyngs.pidoorsensor.client.handlers;

import xyz.kyngs.pidoorsensor.client.Client;
import xyz.kyngs.pidoorsensor.client.network.ReconnectableClient;

public class NetworkHandler extends AbstractHandler {

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
