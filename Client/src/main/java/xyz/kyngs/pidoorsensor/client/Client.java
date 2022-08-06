package xyz.kyngs.pidoorsensor.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.kyngs.pidoorsensor.client.handlers.AudioHandler;
import xyz.kyngs.pidoorsensor.client.handlers.NetworkHandler;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private final NetworkHandler networkHandler;
    private final String address;
    private final AudioHandler audioHandler;

    public Client(String[] args) {
        address = args.length > 0 ? args[0] : "localhost";

        LOGGER.info("Starting door sensor client on " + System.getProperty("os.name"));

        LOGGER.info("Initializing audio system");

        audioHandler = new AudioHandler(this);

        LOGGER.info("Audio handler initialized");

        LOGGER.info("Initializing network system");

        networkHandler = new NetworkHandler(this);

        LOGGER.info("Network system initialized");

        LOGGER.info("Door sensor client started");

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

        LOGGER.info("Shutdown complete, goodbye");
    }

    public void onDoorStatusChange(boolean status) {
        if (status) {
            LOGGER.info("Door opened");
            audioHandler.alert();
        } else {
            LOGGER.info("Door closed");
        }
    }

    public String getAddress() {
        return address;
    }
}
