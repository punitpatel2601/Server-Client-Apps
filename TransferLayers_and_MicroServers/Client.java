import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    // Socket for TCP connection
    private Socket socket;
    public BufferedReader scan;
    // source data
    private String srcData;

    public Client(String sName, int sPort) {
        try {
            scan = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the source data: ");
            srcData = scan.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // initializing connection
        initCon(sName, sPort);
    }

    public void initCon(String sN, int sP) {
        while (true) {
            try {
                socket = new Socket(sN, sP); // connect to MasterServer using TCP

                // input and output streams
                BufferedReader inFromS = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream outToS = new DataOutputStream(socket.getOutputStream());

                // operations on the source data
                System.out.println("Enter the operations (only numeric): ");
                String sendString = scan.readLine();
                sendString += " " + srcData;

                // System.out.println("Sending to server");
                outToS.writeBytes(sendString + '\n');

                // response from server
                String response = "";
                response = inFromS.readLine();

                System.out.println("\nServer response: " + response + "\n\n");

                // function call to close connection
                terminate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // function to close connection
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

        // terminate the connection, if not terminated while exiting
        cl.terminate();
    }
}