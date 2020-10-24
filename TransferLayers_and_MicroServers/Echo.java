import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Echo {

    // socket to receive the data from masterServer using UDP
    private DatagramSocket recSocket;
    private DatagramPacket dpReceive;
    private byte[] receiveBuf; // data received is stored here

    // socket to send data to masterServer using UDP
    private DatagramSocket sendSocket;
    private DatagramPacket dpSend;
    private InetAddress ip; // ip address of local machine (localhost)
    private byte[] sendBuf; // data to be sent is stored here

    public Echo() {
        try {
            // initializing the receiving socket via UDP
            recSocket = new DatagramSocket(8081);
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
    // this one prints the same string as passed to it
    public String func(String s) {
        return s;
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
        new Echo();
    }
}
