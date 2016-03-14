package csu.csci325;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by hunterd on 3/14/2016.
 */
public class CustomScan extends Socket {
    private String mIP = "127.0.0.1";
    private int mStartPort = 0;
    private int mEndPort = 65535;
    private int mTimeout = 1000;

    public CustomScan() {}

    private boolean[] getOpenPorts() {
        if(mEndPort-mStartPort < 0 ) {
            boolean portsStatus[] = new boolean[1];
            portsStatus[0] = false;
            return portsStatus;
        }

        boolean portsStatus[] = new boolean[mEndPort - mStartPort];

        System.out.print("\r\nScanning");

        for (int i = 0; i < portsStatus.length; i++) {
            if(i % 5 == 0) {
                System.out.print(".");
            }
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(mIP,mStartPort + i), mTimeout);
                socket.close();
                portsStatus[i] = true;
            } catch (Exception ex) {
                portsStatus[i] = false;
            }
        }

        return portsStatus;
    }

    public int getTimeout () {return mTimeout;}
    public String getIP() {return mIP;}
    public int getStartPort() {return mStartPort;}
    public int getEndPort() {return mEndPort;}

    public boolean setStartPort(int startPort) {
        if(0 <= startPort && startPort <= 65535) {
            try {
                mTimeout = startPort;
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        else return false;
    }

    public boolean setEndPort(int endPort) {
        if(0 <= endPort && endPort <= 65535) {
            try {
                mTimeout = endPort;
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        else return false;
    }

    public boolean setTimeout(int timeout) {
        try {
            mTimeout = timeout;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean setIP(String IP) {
        try {
            mIP = IP;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void printPorts()
    {
        boolean portsStatus[] = this.getOpenPorts();

        System.out.println("\r\nOpen Ports for " + mIP + ": ");

        for (int i = 0; i < portsStatus.length - 1; i++) {
            if (portsStatus[i]) {
                System.out.print(mStartPort + i + ", ");
            }

            if (portsStatus[i]) {
                System.out.print(mStartPort + i + ". ");
            }
        }
    }
}

