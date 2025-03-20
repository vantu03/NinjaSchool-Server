package com.tgame.ninja.io;

import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import com.tgame.ninja.server.SpamCheck;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Session extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Session.class);

    public boolean connected;

    public Socket socket;

    public Sender sender;

    public int userId;

    public byte type_admin;

    public byte idserver;

    public int width;

    public int height;

    public byte clientType;

    public byte typeHD;

    public byte typeLanguage;

    public boolean isGPRS;

    public boolean isQwerty;

    public boolean isTouch;

    public String deviceName;

    public int provider;

    public int versionIphone;

    public int vetangluong;

    public Vector<Player> listChar;

    public String clientVersion;

    public int version;

    public ISessionHandler messageHandler;

    public final Object LOCK;

    public long connectTime;

    public long lastActiveTime;

    public long timeSend;

    public byte[] key;

    private byte curR;

    private byte curW;

    public String pwd;

    public String username;

    public String playername;

    public String imei;

    public String clientNumber;

    public boolean forceDisconnect;

    public String agentCode;

    public Object LOCKSENDMESS;

    public short messCount;

    public long lastCheck;

    public Session(Socket socket, ISessionHandler handler) {
        connected = false;
        sender = null;
        userId = -1;
        type_admin = 0;
        idserver = 0;
        clientType = -1;
        typeHD = 1;
        provider = 0;
        versionIphone = 1;
        typeLanguage = 0;
        vetangluong = 0;
        listChar = new Vector<>();
        clientVersion = "";
        LOCK = new Object();
        key = null;
        username = "";
        playername = "";
        imei = null;
        agentCode = "0";
        LOCKSENDMESS = new Object();
        messCount = 0;
        lastCheck = System.currentTimeMillis();
        connectTime = System.currentTimeMillis();
        lastActiveTime = System.currentTimeMillis();
        try {
            this.socket = socket;
            messageHandler = handler;
            connected = true;
            SessionManager.instance.add(this);
            if (GameServer.isServerLocal()) {
                LOGGER.info("New socket connection. Client Address: " + getClientAddress());
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void run() {
        if (socket != null) {
            try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
                sender = new Sender(socket.getOutputStream());
                while (connected) {
                    Message message = readMessage(dis);
                    lastActiveTime = System.currentTimeMillis();
                    try {
                        if (message.command != Cmd.NEW_IMAGE && message.command != Cmd.REQUEST_ICON && message.command != Cmd.SKILL_SELECT) {
                            ++messCount;
                            if (messCount > 500) {
                                if (System.currentTimeMillis() - lastCheck < 5000L) {
                                    long timeBan = NJUtil.getMilisByMinute(3);  // time lock
                                    LOGGER.info("User {} send {} message at time, suspicious spam. Last cmd: {}", username, messCount, message.command);
                                    SpamCheck spam = ServerController.usernameSpamMsg.get(username.toLowerCase());
                                    if (spam == null) {
                                        spam = new SpamCheck();
                                        ServerController.usernameSpamMsg.put(username.toLowerCase(), new SpamCheck());
                                    } else {
                                        spam.timeLock = System.currentTimeMillis() + timeBan;
                                    }
                                    ++spam.countSpam;
                                    disconnect("spam server");
                                    break;
                                } else {
                                    lastCheck = System.currentTimeMillis();
                                    messCount = 0;
                                }
                            }
                        }
                        messageHandler.processMessage(this, message);
                    } catch (Exception e) {
                    }
                    if (forceDisconnect) {
                        break;
                    }
                }
            } catch (Exception e) {
            }
            disconnect("");
        }
    }

    public void disconnect(String description) {
        if (connected) {
            try {
                connected = false;
                SessionManager.instance.remove(this);
                messageHandler.onDisconnected(this);
                if (GameServer.isServerLocal() && !description.isEmpty()) {
                    LOGGER.info("Disconnect. Reason: {}", description);
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error("", e);
            }
        }
    }

    public String getClientAddress() {
        InetAddress inetAddress = socket.getInetAddress();
        if (inetAddress != null) {
            return inetAddress.getHostAddress();
        }
        return "-1";
    }

    public void addVeTangLuong(int luong) {
        if (luong < 0) {
            luong = 0;
        }
        if (vetangluong + luong <= 0) {
            return;
        }
        vetangluong += luong;
    }

    public int getVeTangLuong() {
        if (vetangluong < 0) {
            vetangluong = 0;
        }
        return vetangluong;
    }

    public void setVeTangLuong(int ve) {
        if (ve < 0) {
            ve = 0;
        }
        vetangluong = ve;
    }

    public boolean isAndroid() {
        return clientType == GameServer.CLIENT_ANDROID;
    }

    public boolean isIphone() {
        return clientType == GameServer.CLIENT_IPHONE;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isVersionAbove(int Version) {
        return this.version >= Version;
    }

    public boolean isVersion108() {
        return version >= 108;
    }

    public boolean isVersion114() {
        return version >= 114;
    }

    public boolean isVersion116() {
        return version >= 116;
    }

    public boolean isVersion123() {
        return version >= 123;
    }

    public boolean isVersion125() {
        return version >= 125;
    }

    public boolean isVersion130() {
        return version >= 130;
    }

    public boolean isVersion131() {
        return version >= 131;
    }

    public boolean isVersion144() {
        return version >= 144;
    }

    public boolean isVersion145() {
        return version >= 145;
    }

    public boolean isVersion160() {
        return version >= 160;
    }

    public boolean isVersion164() {
        return version >= 164;
    }

    public boolean isVersionPhanThan() {
        return version >= 108;
    }

    public boolean isVersionTet2015() {
        return version >= 105;
    }

    public byte readKey(byte b) {
        byte[] k = key;
        byte c = curR;
        curR = (byte) (c + 1);
        byte i = (byte) ((k[c] & 0xFF) ^ (b & 0xFF));
        if (curR >= key.length) {
            curR %= (byte) key.length;
        }
        return i;
    }

    public Message readMessage(DataInputStream dis) throws IOException {
        byte cmd;
        int size;
        byte[] data;
        if (key == null) {
            cmd = dis.readByte();
            size = dis.readUnsignedShort();
            data = new byte[size];
            for (int i = 0; i < size; ++i) {
                data[i] = dis.readByte();
            }
        } else {
            cmd = readKey(dis.readByte());
            if (cmd == -31) {
                size = dis.readShort();
            } else {
                byte a1 = readKey(dis.readByte());
                byte a2 = readKey(dis.readByte());
                size = ((a1 & 0xFF00) | (a2 & 0xFF));
            }
            data = new byte[size];
            for (int i = 0; i < size; ++i) {
                data[i] = readKey(dis.readByte());
            }
        }
        return new Message(cmd, data);
    }

    public void sendKey(Message m) {
        try {
            sender.sendKey(m);
        } catch (IOException e) {
        }
    }

    public synchronized void sendMessage(Message m) {
        try {
            if (sender != null) {
                sender.sendMessage(m);
            }
        } catch (Exception e) {
        }
    }

    public void setKey(byte[] pKey) {
        key = pKey;
        curW = 0;
        curR = 0;
    }

    public void subVeTangLuong(int ve) {
        if (ve < 0) {
            ve = 0;
        }
        vetangluong -= ve;
    }

    public byte writeKey(byte b) {
        byte[] key = this.key;
        byte curW = this.curW;
        this.curW = (byte) (curW + 1);
        byte i = (byte) ((key[curW] & 0xFF) ^ (b & 0xFF));
        if (this.curW >= this.key.length) {
            this.curW %= (byte) this.key.length;
        }
        return i;
    }

    private class Sender implements Runnable {

        public OutputStream os;

        public LinkedBlockingQueue<byte[]> sendDatas;

        public Sender(OutputStream os) {
            this.os = os;
            sendDatas = new LinkedBlockingQueue<>();
            new Thread(this).start();
        }

        @Override
        public void run() {
            byte[] bytes;
            while (connected) {
                try {
                    bytes = sendDatas.poll(1000L, TimeUnit.MILLISECONDS);
                    if (bytes == null) {
                        continue;
                    }
                    if (key != null) {
                        for (int i = 0; i < bytes.length; ++i) {
                            bytes[i] = writeKey(bytes[i]);
                        }
                    }
                    os.write(bytes);
                    os.flush();
                } catch (Exception e) {
                }
            }
            sendDatas.clear();
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                }
            }
        }

        public void sendKey(Message m) throws IOException {
            byte[] bytes = m.toByteArray();
            os.write(bytes);
            os.flush();
        }

        public void sendMessage(Message m) {
            if (GameServer.isServerLocal() && m.command != -30 && m.command != -29 && m.command != 28) {
                LOGGER.info(">> Send message: {}", m.command);
            }
            if (connected) {
                sendDatas.add(m.toByteArray());
            }
        }
    }
}
