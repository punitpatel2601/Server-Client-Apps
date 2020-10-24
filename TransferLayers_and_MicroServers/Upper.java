import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Upper {

    private DatagramSocket recSocket;
    private DatagramPacket dpReceive;
    private byte[] receiveBuf;

    private DatagramSocket sendSocket;
    private DatagramPacket dpSend;
    private InetAddress ip;
    private byte[] sendBuf;

    public Upper() {
        try {
            recSocket = new DatagramSocket(8083);
            receiveBuf = new byte[65535];
            dpReceive = null;

            sendSocket = new DatagramSocket();
            sendBuf = null;
            ip = InetAddress.getLocalHost();

            communicate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void communicate() throws Exception {
        while (true) {
            dpReceive = new DatagramPacket(receiveBuf, receiveBuf.length);
            recSocket.receive(dpReceive);
            System.out.println("Got from master server: " + data(receiveBuf));

            String str = new String(data(receiveBuf));

            str = func(str);

            sendBuf = str.getBytes();
            dpSend = new DatagramPacket(sendBuf, sendBuf.length, ip, 8080);
            sendSocket.send(dpSend);

            receiveBuf = new byte[65535];
        }
    }

    public String func(String s) {
        return s.toUpperCase();
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
        new Upper();
    }
}
