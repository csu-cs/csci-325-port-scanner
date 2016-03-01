package csu.csci325;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by CAumen163119 on 3/1/2016.
 */
public class PortScan extends Thread {
    private String ipAddress;
    private int portNumber, mTimeout;
    private boolean isOpen;


    public PortScan (String ip, int port, int timeout) {
        ipAddress = ip;
        portNumber = port;
        mTimeout = timeout;
    }

    public PortScan (String ip, int port) {
        ipAddress = ip;
        portNumber = port;
        mTimeout = 250;
    }

    public void run() {
        try{
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ipAddress, portNumber), mTimeout);
            socket.close();
            isOpen = true;
        } catch (Exception ex) {
            isOpen = false;
        }
    }

    public String getPortStatus() {
        if (isOpen)
            return "open";
        return "closed";
    }

    public String getIP() {
        return ipAddress;
    }

    public int getPort() {
        return portNumber;
    }
}
