package com.tgame.ninja.io;

import java.io.*;

public class Message {

    public byte command;

    private ByteArrayOutputStream os;

    public DataOutputStream dos;

    private ByteArrayInputStream is;

    public DataInputStream dis;

    public byte subCmd;

    public Message(byte command) {
        is = null;
        dis = null;
        subCmd = -127;
        this.command = command;
        os = new ByteArrayOutputStream();
        dos = new DataOutputStream(os);
    }

    Message(byte command, byte[] data) {
        os = null;
        dos = null;
        subCmd = -127;
        this.command = command;
        is = new ByteArrayInputStream(data);
        dis = new DataInputStream(is);
    }

    public byte[] toByteArray() {
        int length = 0;
        byte[] data = null;
        byte[] bytes = null;
        try {
            if (dos != null) {
                dos.flush();
                data = os.toByteArray();
                length = data.length;
                dos.close();
            }
            ByteArrayOutputStream b = new ByteArrayOutputStream(3 + length);
            DataOutputStream d = new DataOutputStream(b);
            if (length > 65000) {
                d.writeByte(-32);
                d.writeByte(command);
                d.writeInt(length);
                d.write(data);
            } else {
                d.writeByte(command);
                d.writeShort(length);
                if (length > 0) {
                    d.write(data);
                }
            }
            bytes = b.toByteArray();
            d.close();
        } catch (IOException e) {
        }
        return bytes;
    }

    public void cleanup() {
        try {
            if (dis != null) {
                dis.close();
            }
            if (dos != null) {
                dos.close();
            }
        } catch (IOException e) {
        }
    }

    public boolean readBoolean() throws IOException {
        return dis.readBoolean();
    }

    public byte readByte() throws IOException {
        return dis.readByte();
    }

    public char readChar() throws IOException {
        return dis.readChar();
    }

    public double readDouble() throws IOException {
        return dis.readDouble();
    }

    public float readFloat() throws IOException {
        return dis.readFloat();
    }

    public long readLong() throws IOException {
        return dis.readLong();
    }

    public int readInt() throws IOException {
        return dis.readInt();
    }

    public short readShort() throws IOException {
        return dis.readShort();
    }

    public int readUnsignedShort() throws IOException {
        return dis.readUnsignedShort();
    }

    public int readUnsignedByte() throws IOException {
        return dis.readUnsignedByte();
    }

    public String readUTF() throws IOException {
        return dis.readUTF();
    }

    public void writeBoolean(boolean value) throws IOException {
        dos.writeBoolean(value);
    }

    public void writeChar(char value) throws IOException {
        dos.writeChar(value);
    }

    public void writeByte(int value) throws IOException {
        dos.writeByte(value);
    }

    public void writeBytes(byte[] value) throws IOException {
        dos.writeInt(value.length);
        if (value.length > 0) {
            dos.write(value);
        }
    }

    public void write(byte[] value) throws IOException {
        dos.write(value);
    }

    public void writeInt(int value) throws IOException {
        dos.writeInt(value);
    }

    public void writeShort(int value) throws IOException {
        dos.writeShort(value);
    }

    public void writeDouble(double value) throws IOException {
        dos.writeDouble(value);
    }

    public void writeFloat(float value) throws IOException {
        dos.writeFloat(value);
    }

    public void writeLong(long value) throws IOException {
        dos.writeLong(value);
    }

    public void writeUTF(String value) throws IOException {
        dos.writeUTF(value);
    }
}
