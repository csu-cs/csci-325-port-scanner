package csu.csci325;

import java.net.*;
import java.util.Scanner;

/**
 * Created by Mark on 3/23/2016.
 */
public class FullScan {
    private int mTimeout = 200;
    boolean portStatus[] = new boolean[65535];
    String IP;
    String c;
    Scanner sc = new Scanner(System.in);

    public FullScan (String ip) {
        IP = ip;
    }

    //will scan every port for the given IP address
    public boolean fullScan () {
        try {
            threads(IP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
    //creates 62 threads each scanning 1057 ports
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

    //the run class for the threads
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


    //display all open ports 500 at a time.
    public void displayOpen() {
        int check = 0;
        int check2 = 0;
        System.out.println("Ports: ");
        for(int index = 0; index < 65535; index++) {
            if (portStatus[index]) {
                System.out.print(index + ", ");
            }
            if(check == 25) {
                System.out.println("are open.");
                System.out.println();
                check = 0;
            }
            if(check2 == 500) {
                System.out.println("\n" + "Enter a key to show next 500 or enter q to quit");
                c = sc.next();
                if(c.equals("q")) {
                    return;
                }
                check2 = 0;
            }
            check++;
            check2++;
        }

    }
    //display all close ports 500 at a time.
    public void displayClose() {
        int check = 0;
        int check2 = 0;

        System.out.println("Ports: ");
        for(int index = 0; index < 65535; index++) {
            if (!portStatus[index]) {
                System.out.print(index + ", ");
            }
            if(check == 25) {
                System.out.println();
                check = 0;
            }
            if(check2 == 500) {
                System.out.println("are not open.");
                System.out.println("\n" + "Enter a key to show next 500 or enter q to quit");
                c = sc.next();
                if(c.equals("q")) {
                    return;
                }
                check2 = 0;
            }
            check++;
            check2++;
        }

    }
}

