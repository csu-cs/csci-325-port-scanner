package csu.csci325;

import java.net.InetSocketAddress;
import java.net.Socket;

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
    private boolean[] mPortsStatus;         // Array of bools to indicate whether socket is open
                                            // (true) or closed (false).

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

        // Declare a boolean array of size of the range of ports
        mPortsStatus = new boolean[mEndPort-mStartPort];

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
                isa = new InetSocketAddress(mIP,i+mStartPort);
                sock  = new Socket();
                sock.connect(isa, mTimeout);
                sock.close();
                mPortsStatus[i] = true;
            } catch (Exception ex) {
                mPortsStatus[i] = false;
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

    // Setters
    /*
    * Method sets the starting port for the scan. If it's outside the valid range of
    * ports (0 to 65535) or if it's not an int, return false. Otherwise return true.
    * Returns: boolean to indicate if the port is valid. True = valid.
     */
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

    /*
   * Method sets the ending port for the scan. If it's outside the valid range of
   * ports (0 to 65535) or if it's not an int, return false. Otherwise return true.
   * Returns: boolean to indicate if the port is valid. True = valid.
    */
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
    * Method prints a list of the open ports to the screen.
     */
    public void printPorts()
    {
        System.out.println('\n');
        System.out.println("Open Ports for " + mIP + ": ");

        // Cycle through array and print element number for any that are true.
        for (int i = mStartPort; i < mEndPort; i++) {
            if (mPortsStatus[i]) {
                System.out.print(i + mStartPort + ", ");
            }
        }

        System.out.print('\b' + '\b');
        System.out.println();
    }

    /*
    * Sample main showing an example of how to use the CustomScan class.
    */
    public static void main (String[] args){

        CustomScan cs = new CustomScan();
        cs.setStartPort(0);
        cs.setEndPort(50);
        cs.setIP("127.0.0.1");
        if(cs.getOpenPorts()) {
            cs.printPorts();
        }
        else System.out.println("Invalid ports");
    }
}

