package xyz.kyngs.pidoorsensor.client;

import xyz.kyngs.logger.LogBuilder;
import xyz.kyngs.logger.Logger;
import xyz.kyngs.pidoorsensor.client.handlers.AudioHandler;
import xyz.kyngs.pidoorsensor.client.handlers.NetworkHandler;

public class Client {

    public static final Logger LOGGER = LogBuilder.async().build();
    private final NetworkHandler networkHandler;
    private final String address;
    private final AudioHandler audioHandler;

    public Client(String[] args) {
        address = args.length > 0 ? args[0] : "localhost";

        LOGGER.info("Starting door sensor client on " + System.getProperty("os.name"));

        LOGGER.info("Initializing audio system");

        audioHandler = new AudioHandler(this);

        LOGGER.info("§aAudio handler initialized");

        LOGGER.info("Initializing network system");

        networkHandler = new NetworkHandler(this);

        LOGGER.info("§aNetwork system initialized");

        LOGGER.info("§aDoor sensor client started");

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown, "Shutdown Hook"));
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            LOGGER.error("*** EXCEPTION START ***");

            e.printStackTrace();

            LOGGER.error("*** EXCEPTION END ***");

            LOGGER.error("The application encountered an unrecoverable exception, it will now halt.");

            System.exit(1);

        });
        new Client(args);
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    private void shutdown() {
        LOGGER.info("Shutting down");

        networkHandler.shutdown();

        LOGGER.info("§aShutdown complete, goodbye");

        LOGGER.destroy();
    }

    public void onDoorStatusChange(boolean status) {
        if (status) {
            LOGGER.info("§aDoor opened");
            audioHandler.alert();
        } else {
            LOGGER.info("§cDoor closed");
        }
    }

    public String getAddress() {
        return address;
    }
}
