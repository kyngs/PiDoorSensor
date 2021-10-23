package xyz.kyngs.pidoorsensor.client.handlers;

import xyz.kyngs.logger.Logger;
import xyz.kyngs.pidoorsensor.client.Client;

public abstract class AbstractHandler {

    protected static final Logger LOGGER = Client.LOGGER;
    protected final Client client;

    public AbstractHandler(Client client) {
        this.client = client;
    }

    public abstract void shutdown();
}