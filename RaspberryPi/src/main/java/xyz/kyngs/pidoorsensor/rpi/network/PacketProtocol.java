package xyz.kyngs.pidoorsensor.rpi.network;

import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.crypt.PacketEncryption;
import com.github.steveice10.packetlib.packet.DefaultPacketHeader;
import com.github.steveice10.packetlib.packet.PacketHeader;
import xyz.kyngs.pidoorsensor.rpi.network.packets.CSPacketLeave;
import xyz.kyngs.pidoorsensor.rpi.network.packets.CSPacketPing;
import xyz.kyngs.pidoorsensor.rpi.network.packets.SPacketDoorStatusChange;

public class PacketProtocol extends com.github.steveice10.packetlib.packet.PacketProtocol {

    private final PacketHeader header;

    public PacketProtocol() {
        header = new DefaultPacketHeader();
        registerPackets();
    }

    @Override
    public String getSRVRecordPrefix() {
        return "_door";
    }

    @Override
    public PacketHeader getPacketHeader() {
        return header;
    }

    @Override
    public PacketEncryption getEncryption() {
        return null;
    }

    private void registerPackets() {
        register(1, CSPacketLeave.class);
        register(2, CSPacketPing.class);
        register(3, SPacketDoorStatusChange.class);
    }

    @Override
    public void newClientSession(Session session) {

    }

    @Override
    public void newServerSession(Server server, Session session) {

    }

}
