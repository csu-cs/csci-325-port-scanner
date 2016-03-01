package csu.csci325;

import java.util.Scanner;

/**
 * Created by caumen163119 on 2/16/2016.
 */
public class Mockup {
    public static void main (String[] args) {
        String ip;
        int portStart, portEnd, ports[], userSelection;
        Scanner stdin = new Scanner(System.in);

        System.out.println("Enter the IP address you would like to scan: ");
        ip = stdin.nextLine();

        System.out.println("You entered: " + ip);
        System.out.println("Select port scan technique:");
        System.out.println(" 1 - All Common Ports");
        System.out.println(" 2 - Common TCP Ports");
        System.out.println(" 3 - Common UDP Ports");
        System.out.println(" 4 - Full Port Scan (This may take several minutes)");
        System.out.println(" 5 - Custom Range Scan");

        userSelection = stdin.nextInt();

        while (userSelection < 0 || userSelection > 5) {
            System.out.println("Invalid selection of " + userSelection + " try again: ");
        }

        switch (userSelection) {
            case 1:
                System.out.println("You selected a common TCP & UDP ports scan: No open ports found.");
                break;
            case 2:
                System.out.println("You selected a common TCP ports scan: No open ports found.");
                break;
            case 3:
                System.out.println("You selected a common UDP ports scan: No open ports found.");
                break;
            case 4:
                System.out.println("You selected a full port scan, this may take some time: No open ports found.");
                break;
            case 5:
                System.out.println("You selected a custom range port scan, please enter the starting port value: ");
                portStart = stdin.nextInt();
                System.out.println("You selected a custom range port scan starting with port " + portStart + ", please enter the ending port value: ");
                portEnd = stdin.nextInt();
                System.out.println("You selected a custom range port scan starting with port " + portStart + " and ending at port " + portEnd + ": No open ports found.");
                break;
        }
    }
}
