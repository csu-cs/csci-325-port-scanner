package csu.csci325;

import java.util.Scanner;

/**
 * Scans the 100 most commonly used ports based on inputs.
 */
public class CommonScan {
    Scanner stdin = new Scanner(System.in);
    private java.util.List<PortScan> portScans= new java.util.ArrayList<>();
    private java.util.List<PortScanUDP> portScansUDP= new java.util.ArrayList<>();
    private int[] tcp = new int[]
            {
                    1, 2, 7, 9, 13, 21, 22, 23, 25, 26, 37, 53, 79, 80, 81, 88, 106, 110, 111,
                    113, 119, 135, 139, 143, 144, 179, 199, 389, 427, 443, 444, 445, 465,
                    513, 514, 515, 543, 544, 548, 554, 587, 631, 646, 873, 990, 993, 995,
                    1025, 1026, 1027, 1028, 1029, 1433, 1720, 1723, 1755, 1900, 2000, 2001,
                    2049, 2121, 2717, 3000, 3128, 3306, 3389, 3986, 4899, 5000, 5009, 5051,
                    5060, 5101, 5190, 5357, 5432, 5631, 5666, 5800, 5900, 6000, 6001, 6646,
                    7070, 8000, 8008, 8009, 8080, 8081, 8443, 8888, 9100, 9999, 10000, 32768,
                    49152, 49153, 49154, 49155, 49156
            };
    private int[] udp = new int[]
            {
                    3, 4, 7, 9, 17, 19, 49, 53, 67, 68, 69, 80, 88, 111, 120, 123, 135, 136, 137,
                    138, 139, 158, 161, 162, 177, 427, 443, 445, 497, 500, 514, 515, 518, 520,
                    593, 623, 626, 631, 996, 997, 998, 999, 1022, 1023, 1025, 1026, 1027, 1028,
                    1029, 1030, 1433, 1645, 1646, 1701, 1718, 1719, 1812, 1813, 1900, 2000,
                    2048, 2049, 2222, 2223, 3283, 3456, 3703, 4444, 4500, 5000, 5060, 5353,
                    5632, 9200, 10000, 17185, 20031, 30718, 31337, 32768, 32769, 32771, 32815,
                    33281, 49152, 49153, 49154, 49156, 49181, 49182, 49185, 49186, 49188, 49190,
                    49191, 49192, 49193, 49194, 49200, 49201
            };
    private String mIPAddress;

    /**
     * Sets up the port scanner to scan a particular ip and protocol with the predefined timeout of 250ms
     * @param ip = valid ip address (i.e. "192.168.1.1")
     * @param scope if = "tcp" then sets up scanning tcp ports only, if = "udp" then sets up scanning udp ports only, otherwise scans both.
     */
    public CommonScan(String ip, String scope) {
        mIPAddress = ip;

        if (scope.equals("tcp")){
            for (int i = 0; i < tcp.length; i++){
                portScans.add(new PortScan(mIPAddress, tcp[i]));
            }
            tcpScan();
        } else if (scope.equals("udp")){
            for (int i = 0; i < udp.length; i++){
                portScansUDP.add(new PortScanUDP(mIPAddress, udp[i]));
            }
            udpScan();
        } else {
            for (int i = 0; i < tcp.length; i++){
                portScans.add(new PortScan(mIPAddress, tcp[i]));
            }
            tcpScan();
            for (int i = 0; i < udp.length; i++){
                portScansUDP.add(new PortScanUDP(mIPAddress, udp[i]));
            }
            udpScan();
        }
    }

    /**
     * Scans each tcp Port
     */
    private void tcpScan(){
        System.out.println("Commencing tcp scans.");
        portScans.stream().forEach(p -> p.start());
        System.out.println("All tcp scans completed.");
    }

    /**
     * Scans each udp port
     */
    private void udpScan(){
        System.out.println("Commencing udp scans.");
        portScansUDP.stream().forEach(p -> p.start());
        System.out.println("All udp scans completed.");
    }

//The commented out functions are for testing purposes
    /*
    public java.util.List<PortScan> getTCPResults(){
        return portScans;
    }

    public java.util.List<PortScanUDP> getUDPResults(){
        return portScansUDP;
    }
    */

    /**
     * Prints out the port status of each port in rows of 5 at a time.
     * Does nothing if neither tcpScan() or udpScan() have been called.
     */
    public void displayResults(){
        if (!portScans.isEmpty()) {
            System.out.println("TCP Port Status");
            System.out.print("Port: ");
            for (int j = 0; j < 100; j += 5){
                for (int i = 0; i < 5; i++){
                    System.out.print(portScans.get(i + j).getPort() + " is " + portScans.get(i + j).getPortStatus());
                    if (i == 4) {
                        System.out.println();
                        if (j < 94)
                            System.out.print("Port: ");
                    }
                    else
                        System.out.print(" | ");
                }
            }
            //portScans.stream().forEach(p -> System.out.println("TCP Port: " + p.getPort() + " is " + p.getPortStatus()));
        }
        if (!portScansUDP.isEmpty()) {
            System.out.println("UDP Port Status");
            System.out.print("Port: ");
            for (int j = 0; j < 100; j += 5){
                for (int i = 0; i < 5; i++){
                    System.out.print(portScansUDP.get(i+j).getPort() + " is " + portScansUDP.get(i+j).getPortStatus());
                    if (i == 4) {
                        System.out.println();
                        System.out.print("Port: ");
                    }
                    else
                        System.out.print(" | ");
                }
            }
            // portScansUDP.stream().forEach(p -> System.out.println("UDP Port: " + p.getPort() + " is " + p.getPortStatus()));
        }
    }

    public static void main (String[] args){
    }
}