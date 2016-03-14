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
    private boolean[] mPortsStatus;

    public CustomScan() {}

    public boolean getOpenPorts() {
        Socket sock;
        InetSocketAddress isa;

        if(mEndPort-mStartPort < 0 ) {
            return false;
        }

        mPortsStatus = new boolean[mEndPort-mStartPort];

        System.out.print("\r\nScanning");

        for (int i = 0; i < mPortsStatus.length; i++) {
            if(i % 5 == 0) {
                System.out.print(".");
            }

            try {
                isa = new InetSocketAddress(mIP,i+mStartPort);
                sock  = new Socket();
                sock.connect(isa, mTimeout);
                sock.close();
                mPortsStatus[i] = true;
//                System.out.println(i);
            } catch (Exception ex) {
                mPortsStatus[i] = false;
            }
        }

        return true;
    }

    public int getTimeout () {return mTimeout;}
    public String getIP() {return mIP;}
    public int getStartPort() {return mStartPort;}
    public int getEndPort() {return mEndPort;}

    public boolean setStartPort(int startPort) {
        if(0 <= startPort && startPort <= 65535) {
            try {
                mStartPort = startPort;
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
                mEndPort = endPort;
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
        //boolean portsStatus[] = this.getOpenPorts();

        System.out.println("\r\nOpen Ports for " + mIP + ": ");

        for (int i = mStartPort; i < mEndPort - 1; i++) {
            if (mPortsStatus[i]) {
                System.out.print(i + mStartPort + ", ");
            }
        }

            if (mPortsStatus[mEndPort - 1]) {
                System.out.print(mEndPort - 1 + ". ");
            }

    }
}

