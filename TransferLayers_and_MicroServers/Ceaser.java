import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Ceaser {

    // socket to receive the data from masterServer using UDP
    private DatagramSocket recSocket;
    private DatagramPacket dpReceive;
    private byte[] receiveBuf; // data received is stored here

    // socket to send data to masterServer using UDP
    private DatagramSocket sendSocket;
    private DatagramPacket dpSend;
    private InetAddress ip; // ip address of local machine (localhost)
    private byte[] sendBuf; // data to be sent is stored here

    public Ceaser() {
        try {
            // initializing the receiving socket via UDP
            recSocket = new DatagramSocket(8085);
            receiveBuf = new byte[65535];
            dpReceive = null;

            // initializing the sending socket via UDP
            sendSocket = new DatagramSocket();
            sendBuf = null;
            ip = InetAddress.getLocalHost();

            // function to communicate with MasterServer
            communicate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // communicates with masterServer
    public void communicate() throws Exception {
        while (true) {
            // receiveing data packet initialized and used for getting information from
            // masterServer
            dpReceive = new DatagramPacket(receiveBuf, receiveBuf.length);
            recSocket.receive(dpReceive);

            String str = new String(data(receiveBuf));

            // main data altering function
            str = func(str);

            // sending the data using send data packet
            sendBuf = str.getBytes();
            dpSend = new DatagramPacket(sendBuf, sendBuf.length, ip, 8080);
            sendSocket.send(dpSend);

            // receiving byte [] is re-initialized with null
            receiveBuf = new byte[65535];
        }
    }

    // the function which is responsible for altering data of the source data passed
    // to masterServer
    // alters the characters using offset of 2, wrap around
    public String func(String s) {
        String temp = "";

        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) < 89 || s.charAt(i) < 121) && (s.charAt(i) >= 65 || s.charAt(i) >= 97)) {
                temp += (char) (s.charAt(i) + 2);
            } else if (s.charAt(i) == 89) {
                temp += "A";
            } else if (s.charAt(i) == 121) {
                temp += "a";
            } else if (s.charAt(i) == 90) {
                temp += "B";
            } else if (s.charAt(i) == 122) {
                temp += "b";
            } else {
                temp += s.charAt(i);
            }
        }

        return temp;
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
        new Ceaser();
    }
}
