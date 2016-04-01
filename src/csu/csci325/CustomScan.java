package csu.csci325;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author: Kenneth Hunter
 * Description: Class scans a user defined range of ports for open ports. Instantiate a CustomScan
 * object, and the user can define a custom range, scan, and then define a different custom
 * range and scan with the same object.
 */

public class CustomScan extends Socket {
    private String mIP = "127.0.0.1";       // IP address of computer to scan. Default is local.
    private int mStartPort = 0;             // Starting port of the range to scan. Default is 0.
    private int mEndPort = 65535;           // Ending port of the range to scan. Default is 65535.
    private int mTimeout = 1000;            // Timeout in ms for individual port query. Default is 1 sec.
    private int[][] mPortsStatus;           // 2D Array of ints to indicate whether socket is open
                                            // (1) or closed (0).
    private Stack<Integer> iStack = new LinkedListStack<Integer>();
    Scanner stdIn = new Scanner(System.in);

    public CustomScan() {}

    /*
    * Method scans for open ports in the range from mStartPort to mEndPort and stores the results
    * in mPortsStatus.
     * Returns: boolean to indicate whether port range is valid. True = valid range.
    */

    public boolean getOpenPorts() {
        // Declare variables
        Socket sock;
        InetSocketAddress isa;

        // Check if port range is valid
        if(mEndPort-mStartPort < 0 ) {
            return false;
        }
        else if(iStack.size() == 0){
            return false;
        }

        // Declare a boolean array of size of the range of ports
        mPortsStatus = new int[iStack.size()][2];

        // Print scanning a continue to print dots as long as the scanning continues
        // so the user knows the program hasn't locked.
        System.out.print("\r\nScanning");

        for (int i = 0; i < mPortsStatus.length; i++) {
            if(i % 5 == 0) {
                System.out.print(".");
            }

            // If we can connect to the socket, it's open. Mark that element in the array as
            // true. If there's an exception, it's closed, so mark it false.
            try {
                isa = new InetSocketAddress(mIP, iStack.peek());
                sock  = new Socket();
                sock.connect(isa, mTimeout);
                sock.close();
                mPortsStatus[i][0] = iStack.pop();
                mPortsStatus[i][1] = 1;
            } catch (Exception ex) {
                mPortsStatus[i][0] = iStack.pop();
                mPortsStatus[i][1] = 0;
            }
        }

        // Everything went okay, so return true.
        return true;
    }

    // Getters
    public int getTimeout () {return mTimeout;}
    public String getIP() {return mIP;}
    public int getStartPort() {return mStartPort;}
    public int getEndPort() {return mEndPort;}
    public void printPorts() {System.out.println(iStack.toString());}

    // Setters
    /*
    * Method sets the starting port for the scan. If it's outside the valid range of
    * ports (0 to 65535) or if it's not an int, return false. Otherwise return true.
    * Returns: boolean to indicate if the port is valid. True = valid.
     */
    public boolean setStartPort(int startPort) {
        if(validPort(startPort)) {
            try {
                mStartPort = startPort;
                return true;
            } catch (Exception ex) {
                return false;
            }
        } else return false;
    }

    /*
   * Method sets the ending port for the scan. If it's outside the valid range of
   * ports (0 to 65535) or if it's not an int, return false. Otherwise return true.
   * Returns: boolean to indicate if the port is valid. True = valid.
    */
    public boolean setEndPort(int endPort) {
        if(validPort(endPort)) {
            try {
                mEndPort = endPort;
                return true;
            } catch (Exception ex) {
                return false;
            }
        } else return false;
    }

    /*
   * Method sets the timeout for the scan. If it's not an int, return false.
   * Otherwise return true.
   * Returns: boolean to indicate if the timeout was successfully set. True = success.
    */
    public boolean setTimeout(int timeout) {
        try {
            mTimeout = timeout;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /*
    * Method sets the IP address for the scan. It takes the IP address as a string
    * in the form 127.0.0.1 and returns a bool to indicate if it was successfully set.
    * Returns: boolean to indicate if the address was successfully set.
     */
    public boolean setIP(String IP) {
        try {
            mIP = IP;
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /*
    * Method builds the stack of ports to scan. It takes a boolean that indicates whether
    * the stack will be built from a range (mStartPort to mEndPort) or allow the user to
    * enter individual ports to add to the stack.
    * Returns: boolean to indicate that the stack was successfully built with valid ports.
     */
    public boolean buildStack(boolean isRange) {
        if(isRange) {
            if (mEndPort > mStartPort && validPort(mEndPort) && validPort(mStartPort)) {
                for (int i = mStartPort; i <= mEndPort; i++) {
                    iStack.push(i);
                }
                return true;
            } else return false;
        } else {
            System.out.println("Enter ports to scan separated by a space (1 2 3 10). 'Q' and <Enter> to finish.");
            while(stdIn.hasNextInt()) {
                if(validPort(iStack.peek())) {
                    iStack.push(stdIn.nextInt());
                } else {
                    System.out.println("Invalid port.");
                    return false;
                }
            }
            return true;
        }
    }

    public boolean validPort(int port) {
        if (port >= 0 && port <= 65535) {
            return true;
        } else return false;
    }

    /*
    * Method prints a list of the open ports to the screen.
     */
    public void printOpenPorts()
    {
        System.out.println('\n');
        System.out.println("Open Ports for " + mIP + ": ");

        // Cycle through array and print element number for any that are true.
        for (int i = 0; i < mPortsStatus.length; i++) {
            if (mPortsStatus[i][1] == 1) {
                System.out.print(" " + mPortsStatus[i][0] + " ");
            }
        }

       System.out.println();
    }

    /*
    * Sample main showing an example of how to use the CustomScan class.
    */
    public static void main (String[] args){

        CustomScan cs = new CustomScan();
        cs.setStartPort(0);
        cs.setEndPort(50);
        cs.buildStack(true);
        cs.buildStack(false);
        cs.printPorts();
        System.out.println();
        cs.setIP("127.0.0.1");
        if(cs.getOpenPorts()) {
            cs.printOpenPorts();
        }
        else System.out.println("Invalid ports");
    }
}

