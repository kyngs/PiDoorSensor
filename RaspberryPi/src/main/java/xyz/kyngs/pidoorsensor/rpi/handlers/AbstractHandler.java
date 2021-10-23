package xyz.kyngs.pidoorsensor.rpi.handlers;

import xyz.kyngs.logger.Logger;
import xyz.kyngs.pidoorsensor.rpi.RaspberryPi;

public abstract class AbstractHandler {
    protected static final Logger LOGGER = RaspberryPi.LOGGER;

    protected final RaspberryPi pi;

    public AbstractHandler(RaspberryPi pi) {
        this.pi = pi;
    }

    public abstract void shutdown();
}