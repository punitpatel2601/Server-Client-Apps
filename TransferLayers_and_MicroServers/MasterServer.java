
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer {
    // Socket for TCP connection to Client
    private ServerSocket TCPSocket;
    private Socket TCPAccept;

    // UDP connection socket for receiving the data from the microServers
    private DatagramSocket recSocket;
    private DatagramPacket dpRec;
    private byte[] recBuf;

    // UDP connection socket for sending the data to the microServers
    private DatagramSocket sendSocketUDP;
    private DatagramPacket dpS;
    private InetAddress ip;
    private byte[] sendBuf;

    public MasterServer() {
        try {
            // Initializing the port for TCP connection
            TCPSocket = new ServerSocket(9898);

            // Initializing the port for receiving from microServers
            recSocket = new DatagramSocket(8080);
            recBuf = new byte[65535];
            dpRec = null;

            // Initializing the part for sending from microServers
            sendSocketUDP = new DatagramSocket();
            ip = InetAddress.getLocalHost();
            sendBuf = null;

            System.out.println("Waiting for client to connect...");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void initConClient() {
        String clientInput;
        try {
            while (true) {
                // Accept client connection
                TCPAccept = TCPSocket.accept();

                // input from client
                BufferedReader inFromC = new BufferedReader(new InputStreamReader(TCPAccept.getInputStream()));
                clientInput = inFromC.readLine(); // read from client

                System.out.println("Got from client: " + clientInput);

                // output to client
                DataOutputStream outToC = new DataOutputStream(TCPAccept.getOutputStream());

                // write to Client
                String toClientS = processData(clientInput);
                System.out.println("Sending to client: " + toClientS);
                outToC.writeBytes(toClientS + '\n');
            }
        } catch (Exception e) {
            System.out.println("Client disconnected");
        }
    }

    // process the data obtained from client to understand the operations on data
    public String processData(String inputS) {
        String[] segments = inputS.split(" ");

        // source data is inserted in inputS
        inputS = "";
        for (int i = 1; i < segments.length; i++) {
            inputS += segments[i] + " ";
        }

        try {
            int process = Integer.parseInt(segments[0]);

            // this loop checks the operations from back, by performing basic mathematics on
            // input
            while (process > 0) {
                int operation = process % 10;

                inputS = performOps(inputS, operation);

                process = process / 10;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return inputS;
    }

    // Performs the operations by calling microServers
    public String performOps(String inS, int ops) {
        try {
            System.out.println(inS + " " + ops);

            // byte array to be sent in microServers
            sendBuf = inS.getBytes();
            dpRec = new DatagramPacket(recBuf, recBuf.length);

            // switch case to select a specific microServer, by sending and receiving data
            // packets
            switch (ops) {
                case 1:
                    dpS = new DatagramPacket(sendBuf, sendBuf.length, ip, 8081);
                    sendSocketUDP.send(dpS);
                    recSocket.receive(dpRec);
                    break;
                case 2:
                    dpS = new DatagramPacket(sendBuf, sendBuf.length, ip, 8082);
                    sendSocketUDP.send(dpS);
                    recSocket.receive(dpRec);
                    break;
                case 3:
                    dpS = new DatagramPacket(sendBuf, sendBuf.length, ip, 8083);
                    sendSocketUDP.send(dpS);
                    recSocket.receive(dpRec);
                    break;
                case 4:
                    dpS = new DatagramPacket(sendBuf, sendBuf.length, ip, 8084);
                    sendSocketUDP.send(dpS);
                    recSocket.receive(dpRec);
                    break;
                case 5:
                    dpS = new DatagramPacket(sendBuf, sendBuf.length, ip, 8085);
                    sendSocketUDP.send(dpS);
                    recSocket.receive(dpRec);
                    break;
                case 6:
                    dpS = new DatagramPacket(sendBuf, sendBuf.length, ip, 8086);
                    sendSocketUDP.send(dpS);
                    recSocket.receive(dpRec);
                    break;
                default:
                    // skips calling microServer when wrong number is present
                    System.out.println("Skipping, wrong number of server selected");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // creating string to be sent to client
        String outS = new String(data(recBuf));
        recBuf = new byte[65535];

        return outS;
    }

    // function to create string from byte []
    public StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

    public static void main(String[] args) {
        MasterServer ms = new MasterServer();
        ms.initConClient();
    }
}
