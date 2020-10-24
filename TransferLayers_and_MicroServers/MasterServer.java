import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer {
    /*
     * echo1 reverse2 upper3 lower4 ceaser5 yours6
     */

    private ServerSocket TCPSocket;
    private Socket TCPAccept;

    private DatagramSocket recSocket;
    private DatagramPacket dpRec;
    private byte[] recBuf;

    private DatagramSocket sendSocketUDP;
    private DatagramPacket dpS;
    private InetAddress ip;
    private byte[] sendBuf;

    public MasterServer() {
        try {
            TCPSocket = new ServerSocket(9898);

            recSocket = new DatagramSocket(8080);
            recBuf = new byte[65535];
            dpRec = null;

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
                TCPAccept = TCPSocket.accept();

                BufferedReader inFromC = new BufferedReader(new InputStreamReader(TCPAccept.getInputStream()));
                clientInput = inFromC.readLine();

                System.out.println("Got from client: " + clientInput);

                DataOutputStream outToC = new DataOutputStream(TCPAccept.getOutputStream());

                String toClientS = processData(clientInput);
                System.out.println("Sending to client: " + toClientS);
                outToC.writeBytes(toClientS + '\n');
            }
        } catch (Exception e) {
            System.out.println("Client disconnected");
        }
    }

    public String processData(String inputS) {
        String[] segments = inputS.split(" ");

        inputS = "";
        for (int i = 1; i < segments.length; i++) {
            inputS += segments[i] + " ";
        }

        try {
            int process = Integer.parseInt(segments[0]);

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

    public String performOps(String inS, int ops) {
        try {
            System.out.println(inS + " " + ops);

            sendBuf = inS.getBytes();
            dpRec = new DatagramPacket(recBuf, recBuf.length);

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
                    System.out.println("Skipping, wrong number of server selected");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String outS = new String(data(recBuf));
        recBuf = new byte[65535];

        return outS;
    }

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
