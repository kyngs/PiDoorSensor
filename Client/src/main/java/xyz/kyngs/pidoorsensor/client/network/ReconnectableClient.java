package xyz.kyngs.pidoorsensor.client.network;

import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.kyngs.pidoorsensor.client.Client;
import xyz.kyngs.pidoorsensor.client.network.packets.CSPacketLeave;
import xyz.kyngs.pidoorsensor.client.network.packets.CSPacketPing;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

public class ReconnectableClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReconnectableClient.class);

    private final AtomicReference<Session> session;
    private final AtomicReference<NetworkStatus> status;
    private final String address;
    private final Client client;
    private Timer timerForPing;
    private String reasonForDisconnect;
    //Lord forgive me for this one
    private boolean disconnecting;

    public ReconnectableClient(String address, Client client) {
        this.client = client;
        this.address = address;

        session = new AtomicReference<>();
        status = new AtomicReference<>(NetworkStatus.NOT_CONNECTED);
    }

    public String getReasonForDisconnect() {
        return reasonForDisconnect;
    }

    public void setReasonForDisconnect(String reasonForDisconnect) {
        this.reasonForDisconnect = reasonForDisconnect;
    }

    public void beginConnect() {
        synchronized (session) {
            synchronized (status) {
                if (status.get() == NetworkStatus.CONNECTING)
                    throw new IllegalStateException("We are already connecting");
                status.set(NetworkStatus.CONNECTING);
                var attempt = 1;
                while (!attemptConnect()) {
                    if (disconnecting) return;
                    LOGGER.warn("Failed to connect, trying again in 2 seconds");
                    System.gc();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    attempt++;
                }

                LOGGER.info("Successfully connected on " + attempt + " attempt");

                timerForPing = new Timer();
                timerForPing.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        sendPacket(new CSPacketPing());
                    }
                }, 2000, 2000);

                status.set(NetworkStatus.CONNECTED);
            }
        }
    }

    private boolean attemptConnect() {
        LOGGER.info("Connecting to " + address);
        var session = new TcpClientSession(address, 26853, new PacketProtocol(client));
        this.session.set(session);

        session.connect(true);

        var success = session.isConnected();

        if (!success) {
            session.disconnect("Just to be sure");
        }

        return success;
    }

    public void disconnect(String reason) {
        this.disconnecting = true;
        synchronized (session) {
            synchronized (status) {
                if (status.get() == NetworkStatus.DISCONNECTING)
                    throw new IllegalStateException("We are already disconnecting");
                status.set(NetworkStatus.DISCONNECTING);

                var session = this.session.get();
                session.send(new CSPacketLeave(reason));
                session.disconnect(reason);
                status.set(NetworkStatus.NOT_CONNECTED);
            }
        }
    }

    public void onDisconnect() {
        synchronized (session) {
            synchronized (status) {
                if (status.get() == NetworkStatus.NOT_CONNECTED)
                    throw new IllegalStateException("Already disconnected");
                var session = this.session.get();
                if (session.isConnected()) throw new IllegalStateException("Still connected");
                timerForPing.cancel();
                timerForPing = null;
                reasonForDisconnect = null;
                status.set(NetworkStatus.NOT_CONNECTED);
            }
        }
        beginConnect();
    }

    public void sendPacket(Packet packet) {
        synchronized (session) {
            session.get().send(packet);
        }
    }

    public boolean isConnecting() {
        return status.get() == NetworkStatus.CONNECTING;
    }

}
