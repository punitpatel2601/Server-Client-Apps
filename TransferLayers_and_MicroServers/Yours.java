import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Yours {

    private DatagramSocket recSocket;
    private DatagramPacket dpReceive;
    private byte[] receiveBuf;

    private DatagramSocket sendSocket;
    private DatagramPacket dpSend;
    private InetAddress ip;
    private byte[] sendBuf;

    public Yours() {
        try {
            recSocket = new DatagramSocket(8086);
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
        String temp = "";

        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) <= 90 || s.charAt(i) <= 122) && (s.charAt(i) >= 65 || s.charAt(i) >= 97)) {
                if (s.charAt(i) % 2 == 0) {
                    temp += (char) (s.charAt(i) - 1);
                } else {
                    temp += (char) (s.charAt(i) + 1);
                }
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
        new Yours();
    }
}
