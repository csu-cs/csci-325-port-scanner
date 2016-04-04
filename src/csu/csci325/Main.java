package csu.csci325;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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

        while(!validIP(ipAddress)){
            System.out.println("Invalid IP address, please enter a valid IP address:");
            ipAddress = stdin.nextLine();
        }

        System.out.println("You entered: " + ipAddress);
        System.out.println("Select port scan technique:");
        System.out.println(" 1 - All Common Ports");
        System.out.println(" 2 - Common TCP Ports");
        System.out.println(" 3 - Common UDP Ports ((will only find ports that are responding))");
        System.out.println(" 4 - Full Port Scan (This may take several minutes)");
        System.out.println(" 5 - Custom Port Scan");
        System.out.println(" 6 - Single Port Scan");

        userSelect[0] = stdin.nextInt();

        while (userSelect[0] < 1 || userSelect[0] > 6) {
            System.out.println("Invalid selection of " + userSelect[0] + " try again: ");
            userSelect[0] = stdin.nextInt();
        }

        switch (userSelect[0]) {
            case 1:
                CommonScan comScan = new CommonScan(ipAddress, "both");
                comScan.displayResults();
                break;
            case 2:
                CommonScan tcpScan = new CommonScan(ipAddress, "tcp");
                tcpScan.displayResults();
                break;
            case 3:
                CommonScan udpScan = new CommonScan(ipAddress, "udp");
                udpScan.displayResults();
                break;
            case 4:
                FullScan fullScan = new FullScan(ipAddress);
                System.out.println("Select option: ");
                System.out.println("1 - List all open ports");
                System.out.println("2 - List all closed ports");
                System.out.println("3 - List all ports");
                userSelect[2] = stdin.nextInt();
                while (userSelect[2] < 1 || userSelect[2] > 3){
                    System.out.println("Invalid option, please enter 1, 2, or 3");
                    userSelect[2] = stdin.nextInt();
                }
                do {
                    System.out.println("Scanning . . .");
                    fullScan.fullScan();
                    if(userSelect[2] == 1) {
                        fullScan.displayOpen();
                        break;
                    }
                    else if(userSelect[2] == 2) {
                        fullScan.displayClose();
                        break;
                    }
                    else if(userSelect[2] == 3) {
                        fullScan.displayResults();
                        break;
                    }
                } while (userSelect[2] >= 1 && userSelect[2] <= 3);
                break;
            case 5:
                CustomScan cs = new CustomScan();
                boolean validChoice = false;
                char choice;

                while (!validChoice) {
                    System.out.println("Select option:");
                    System.out.println(" 1 - Enter a range of ports to scan (i.e. 23-35)");
                    System.out.println(" 2 - Enter a list of ports to scan (i.e. 22, 23, 34, 16)");
                    userSelect[1] = stdin.nextInt();

                    if (userSelect[1] == 1) {
                        validChoice = true;
                        do {
                            System.out.println("Enter lowest port value: ");
                            portStart = stdin.nextInt();
                            System.out.println("Enter highest port value: ");
                            portEnd = stdin.nextInt();

                            if (portStart >= portEnd) {
                                System.out.println("Invalid port range given.");
                                System.out.println("Please enter lowest port first.");
                            }
                            else if (portStart < 0 || portEnd > 65535) {
                                System.out.println("Invalid port range given.");
                                System.out.println("Valid ports are 0 to 65535.");
                            }
                        } while (!cs.setStartPort(portStart) || !cs.setEndPort(portEnd));

                        System.out.println("Start Port: " + cs.getStartPort());
                        System.out.println("End Port: " + cs.getEndPort());
                        cs.buildStack(true);

                    } else if (userSelect[1] == 2) {
                        validChoice = true;
                        cs.buildStack(false);

                    } else {
                        System.out.println("Invalid option, please try again");
                        validChoice = false;
                    }
                    System.out.println(" Done entering ports? (Y/N)");
                    if(validChoice) {
                        try {
                            choice = stdin.next().charAt(0);
                            if (choice == 'y' || choice == 'Y') {
                                validChoice = true;
                            } else if (choice == 'n' || choice == 'N') {
                                validChoice = false;
                            }
                        } catch (Exception ex) {
                            System.out.println("Invalid choice");
                            validChoice = false;
                        }
                    }
                }
                cs.printPorts();
                cs.getOpenPorts();
                cs.printOpenPorts();
                break;
            case 6:
                System.out.println("Which port would you like to scan?");
                portStart = stdin.nextInt();
                while(!validPort(portStart)){
                    System.out.println("Invalid port given, please choose a port between 0 and 65535");
                }
                PortScan scan1 = new PortScan(ipAddress, portStart, timeout);
                scan1.start();
                while (scan1.isAlive()){}
                System.out.println("Port " + scan1.getPort() + " is " + scan1.getPortStatus());
                break;
        }
    }

    public Main(){

    }

    private static boolean validIP(String ip){
        if (ip == null || ip.isEmpty())
            return false;

        ip = ip.trim();
        if ((ip.length() < 6) && (ip.length() > 15))
            return false;

        try {
            Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        } catch (PatternSyntaxException ex) {
            return false;
        }
    }

    private static boolean validPort(int port){
        return (port >= 0 && port <= 65535);
    }
}
