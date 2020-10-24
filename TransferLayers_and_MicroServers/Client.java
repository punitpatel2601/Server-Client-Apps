import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private Socket socket;
    public BufferedReader scan;

    public Client(String sName, int sPort) {
        try {

            scan = new BufferedReader(new InputStreamReader(System.in));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        initCon(sName, sPort);
    }

    public void initCon(String sN, int sP) {
        while (true) {
            try {
                socket = new Socket(sN, sP);

                BufferedReader inFromS = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream outToS = new DataOutputStream(socket.getOutputStream());

                System.out.print("Enter any line with the operations before it... : ");
                String sendString = scan.readLine();

                if (sendString.compareTo("EXIT") == 0) {
                    return;
                }

                System.out.println("Sending to server");
                outToS.writeBytes(sendString + '\n');

                String response = "";
                response = inFromS.readLine();

                System.out.println("Server response: " + response);

                terminate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void terminate() {
        try {
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client cl = new Client(args[0], Integer.parseInt(args[1]));

        // System.out.println(args[0] + " " + args[1]);
        cl.terminate();
    }
}