package csu.csci325;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by caumen163119 on 3/22/2016.
 */
public class PortScanUDP extends Thread {
    private String ipAddress;
    private int portNumber, mTimeout;
    private boolean isOpen;
    private InetAddress address;


    public PortScanUDP(String ip, int port, int timeout) {
        ipAddress = ip;
        portNumber = port;
        mTimeout = timeout;
    }

    public PortScanUDP(String ip, int port) {
        ipAddress = ip;
        portNumber = port;
        mTimeout = 250;
    }

    public void run() {
        try{
            DatagramSocket socket = new DatagramSocket();
            byte[] bytes = new byte[128];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            address = InetAddress.getByName(ipAddress);
            socket.setSoTimeout(mTimeout);
            socket.connect(address, portNumber);
            socket.send(packet);
            socket.isConnected();
            packet = new DatagramPacket(bytes, bytes.length);
            socket.receive(packet);
            socket.close();
            isOpen = true;
        } catch (Exception ex) {
            isOpen = false;
        }
    }

    public String getPortStatus() {
        if (isOpen)
            return "open and responding";
        return "closed or not responding";
    }

    public String getIP() {
        return ipAddress;
    }

    public int getPort() {
        return portNumber;
    }
}