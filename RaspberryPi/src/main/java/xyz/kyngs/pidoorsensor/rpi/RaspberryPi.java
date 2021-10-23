package xyz.kyngs.pidoorsensor.rpi;

import xyz.kyngs.logger.LogBuilder;
import xyz.kyngs.logger.Logger;
import xyz.kyngs.pidoorsensor.rpi.handlers.NetworkHandler;
import xyz.kyngs.pidoorsensor.rpi.handlers.PiHandler;

public class RaspberryPi {

    public static final Logger LOGGER = LogBuilder.async().build();

    private final PiHandler piHandler;
    private final NetworkHandler networkHandler;
    private final int pin;

    public RaspberryPi(String[] args) {
        pin = args.length > 0 ? Integer.parseInt(args[0]) : 24;
        LOGGER.info("Starting door sensor server on " + System.getProperty("os.name"));

        LOGGER.info("Setting up GPIO");

        piHandler = new PiHandler(this);

        LOGGER.info("§aGPIO initialized");

        LOGGER.info("Initializing network system");

        networkHandler = new NetworkHandler(this);

        LOGGER.info("§aNetwork system initialized");

        LOGGER.info("§aDoor sensor server started");

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown, "Shutdown Hook"));
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            LOGGER.error("§c*** EXCEPTION START ***");

            e.printStackTrace();

            LOGGER.error("§c*** EXCEPTION END ***");

            LOGGER.error("§cThe application encountered an unrecoverable exception, it will now halt.");

            System.exit(1);

        });
        new RaspberryPi(args);
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public PiHandler getPiHandler() {
        return piHandler;
    }

    private void shutdown() {
        LOGGER.info("Shutting down");

        networkHandler.shutdown();
        piHandler.shutdown();

        LOGGER.info("§aShutdown complete, goodbye");

        LOGGER.destroy();
    }

    public int getPin() {
        return pin;
    }
}
