import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class PingClient {
    public PingClient(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Required host and port number ");
            return;
        }
        // getting input for host and port from command line

        // host
        String host = args[0];
        // port number for UDP packet transfers
        int port = Integer.parseInt(args[1]);
        // creating datagram socket for sending/receving data
        DatagramSocket socket = new DatagramSocket();
        // host ip address
        InetAddress ip = InetAddress.getByName(host);
        // message details
        String input = " ";
        int seqNo = 0;
        long sendTime = 0;
        long recTime = 0;
        long[] time = new long[10];

        for (int i = 0; i < 10; i++)
            time[i] = 0;
        byte[] recieve = new byte[65535];
        // Datagram packet to be sent and received
        DatagramPacket dp = null;
        DatagramPacket dprec = null;
        // communicating with the PingServer
        while (true) {
            if (seqNo > 9)
                break;
            dp = null;
            dprec = null;
            sendTime = System.currentTimeMillis();
            input = "PING " + Integer.toString(seqNo) + " " + Long.toString(sendTime) + "\r\n";
            dp = new DatagramPacket(input.getBytes(), input.getBytes().length, ip, port);
            dprec = new DatagramPacket(recieve, recieve.length);
            socket.send(dp);
            System.out.println("sent");
            try {
                // setting the socket timeout
                socket.setSoTimeout(1000);

                socket.receive(dprec);

                // System.out.println("Recieved");
            } catch (SocketTimeoutException socketTimeoutException) {
                System.out.println("Packet dropped : " + input);
                time[seqNo] = 1000;
            }
            recTime = System.currentTimeMillis();

            if (time[seqNo] == 0) {
                time[seqNo] = recTime - sendTime;
                System.out.println("Delay " + Long.toString(time[seqNo]) + ": " + new String(dprec.getData()));
            }
            seqNo++;
        }

        // finding the minimum RTT
        long min = time[0];
        for (int i = 1; i < 10; i++)
            if (time[i] < min)
                min = time[i];

        // finding the maximum RTT
        long max = time[0];
        for (int i = 1; i < 10; i++)
            if (time[i] > max)
                max = time[i];

        // finding the average RTT
        double average = 0;
        for (int i = 0; i < 10; i++)
            average += time[i];

        average = average / 10;
        // printing the required average,maximum and minimum RTT
        System.out.println("minimum RTT is: " + Long.toString(min));
        System.out.println("maximum RTT is: " + Long.toString(max));
        System.out.println("average RTT is: " + Double.toString(average));
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        new PingClient(args);
    }
}