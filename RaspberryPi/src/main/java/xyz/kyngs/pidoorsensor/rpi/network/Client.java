package xyz.kyngs.pidoorsensor.rpi.network;

import com.github.steveice10.packetlib.Session;

public class Client {

    private final Session session;
    private String leaveReason;

    public Client(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }
}
