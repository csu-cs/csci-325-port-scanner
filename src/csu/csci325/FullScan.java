package csu.csci325;

import java.net.*;

/**
 * Created by Mark on 3/23/2016.
 */
public class FullScan {
    private int mTimeout = 200;
    boolean portStatus[] = new boolean[65535];
    String IP;

    public FullScan (String ip) {
        IP = ip;
    }

    public boolean fullScan () {
        try {
            threads(IP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
    public void threads (String IP) throws InterruptedException{
        int threads = 62;
        int count = 62;
        Thread[] multiThread = new Thread[count];
        for(int i = 0; i < multiThread.length; i++) {
            multiThread[i] = new Thread(new Runner());
            multiThread[i].start();
        }
        for(int i = 0; i < multiThread.length; i++) {
            multiThread[i].join();
        }
    }

    public class Runner implements Runnable {
        final int startPort = 0;

        public Runner() {

        }

        @Override
        public void run() {
            for(int port = startPort; port < 1057; port ++) {
                    try {
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress(IP, port), mTimeout);
                        socket.close();
                        portStatus[port] = true;
                    } catch (Exception ex) {
                        portStatus[port] = false;
                    }
                }
            }
    }
    public int getTimeout () {
        return mTimeout;
    }

    public void setTimeout (int Timeout) {
        mTimeout = Timeout;
    }

    public void displayResults() {
        for(int index = 0; index < 65535;  index++) {
            if(portStatus[index]) {
                System.out.println("Port:" + index + " is open");
            }
            else {
                System.out.println("Port: " + index + " is not open");
            }
        }
    }

    public void displayOpen() {
        int check = 0;
        System.out.println("Ports: ");
        for(int index = 0; index < 65535; index++) {
            if (portStatus[index]) {
                System.out.print(index + ", ");
            }
            if(check == 30) {
                System.out.println();
                check = 0;
            }
            check++;
        }
        System.out.println("are open.");
    }
    public void displayClose() {
        int check = 0;
        System.out.println("Ports: ");
        for(int index = 0; index < 65535; index++) {
            if (!portStatus[index]) {
                System.out.print(index + ", ");
            }
            if(check == 30) {
                System.out.println();
                check = 0;
            }
            check++;
        }
        System.out.println("are not open.");
    }
    public static void main(String[] args) {
        FullScan FS = new FullScan("192.168.56.1");

        FS.fullScan();
        FS.displayClose();

    }

}
