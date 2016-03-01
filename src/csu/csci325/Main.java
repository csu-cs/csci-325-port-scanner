package csu.csci325;

import java.util.Scanner;

/**
 * Created by caumen163119 on 2/9/2016.
 */

public class Main {
    public static void main (String[] args){
        String ipAddress;
        Scanner stdin = new Scanner(System.in);
        int[] ports;
        int[] userSelect = new int[3];
        int timeout = 250, portStart, portEnd;

        System.out.println("Enter the IP address you would like to scan: ");
        ipAddress = stdin.nextLine();

        System.out.println("You entered: " + ipAddress);
        System.out.println("Select port scan technique:");
        System.out.println(" 1 - All Common Ports");
        System.out.println(" 2 - Common TCP Ports");
        System.out.println(" 3 - Common UDP Ports");
        System.out.println(" 4 - Full Port Scan (This may take several minutes)");
        System.out.println(" 5 - Custom Port Scan");
        System.out.println(" 6 - Single Port Scan");

        userSelect[0] = stdin.nextInt();

        while (userSelect[0] < 1 || userSelect[0] > 6) {
            System.out.println("Invalid selection of " + userSelect[0] + " try again: ");
        }

        switch (userSelect[0]) {
            case 1:
                //CommonScan comScan = new CommonScan(ipAddress, "both");
                //comScan.displayResults();
                break;
            case 2:
                //CommonScan tcpScan = new CommonScan(ipAddress, "tcp");
                break;
            case 3:
                //CommonScan udpScan = new CommonScan(ipAddress, "udp");
                break;
            case 4:
                //FullScan fullScan = new FullScan(ipAddress);
                break;
            case 5:
                do {
                System.out.println("Select option:");
                System.out.println(" 1 - Scan a range of ports (i.e. 23-35)");
                System.out.println(" 2 - Scan a list of ports (i.e. 22, 23, 34, 16)");
                userSelect[1] = stdin.nextInt();
                    if (userSelect[1] == 1) {
                        do {
                            System.out.println("Enter lowest port value: ");
                            portStart = stdin.nextInt();
                            System.out.println("Enter highest port value: ");
                            portEnd = stdin.nextInt();
                            if (portStart >= portEnd)
                                System.out.println("Invalid port range given, please enter two different port numbers, first the lowest then the highest.");
                        } while (portStart >= portEnd);
                        //CustomScan cusRangeScan = new CustomScan(ipAddress, portStart, portEnd);
                    } else if (userSelect[1] == 2) {
                        do {
                            System.out.println("How many ports do you want to scan?");
                            userSelect[2] = stdin.nextInt();
                            if (userSelect[2] < 3)
                                System.out.println("This scan is expecting three or more ports, please enter a value higher than 2.");
                        } while (userSelect[2] < 3);
                        ports = new int[userSelect[2]];
                        for (int ind = 0; ind < userSelect[2]; ind++) {
                            System.out.println("Enter port number for scan " + ind + ": ");
                            ports[ind] = stdin.nextInt();
                        }
                        //CustomScan cusListScan = new CustomScan(ipAddress, ports);
                    } else {
                        System.out.println("Invalid option, please try again");
                    }
                } while (userSelect[1] < 1 || userSelect[1] > 2);
                break;
            case 6:
                System.out.println("Which port would you like to scan?");
                portStart = stdin.nextInt();
                PortScan scan1 = new PortScan(ipAddress, portStart, timeout);
                scan1.start();
                while (scan1.isAlive()){}
                System.out.println("Port " + scan1.getPort() + " is " + scan1.getPortStatus());
                break;
        }
    }

    public Main(){

    }
}
