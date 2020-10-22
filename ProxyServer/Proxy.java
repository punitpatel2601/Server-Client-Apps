
/*
Name:       Punit Patel
UCID:       30064251
Tutorial:   T-05
*/

import java.net.*;
import java.io.*;

public class Proxy {
    /** Port for the proxy */
    private static int port;
    /** Socket for client connections */
    private static ServerSocket socket;

    /** Create the Proxy object and the socket */
    public static void init(int p) {
        port = p;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error creating socket: " + e);
            System.exit(-1);
        }
    }

    public static void handle(Socket client) {
        Socket server = null;
        HttpRequest request = null;
        HttpResponse response = null;

        /*
         * Process request. If there are any exceptions, then simply return and end this
         * request. This unfortunately means the client will hang for a while, until it
         * timeouts.
         */

        /* Read request */
        try {
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            request = new HttpRequest(fromClient);
        } catch (IOException e) {
            System.out.println("Error reading request from client: " + e);
            return;
        }
        /* Send request to server */
        try {
            /* Open socket and write request to socket */
            server = new Socket(request.getHost(), request.getPort());
            DataOutputStream toServer = new DataOutputStream(server.getOutputStream());
            toServer.writeBytes(request.toString());
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + request.getHost());
            System.out.println(e);
            return;
        } catch (IOException e) {
            System.out.println("Error reading request from server: " + e);
            return;
        }
        /* Read response and forward it to client */
        try {
            DataInputStream fromServer = new DataInputStream(server.getInputStream());
            response = new HttpResponse(fromServer);
            DataOutputStream toClient = new DataOutputStream(client.getOutputStream());

            try {
                toClient.writeBytes(response.toString());
                // reference taken for below if condition, see reference section of README file
                if (request.URI.endsWith(".html")) {
                    // only convert the part of the body whose URL ends at .html
                    toClient.write(response.bodyToString(true));
                } else {
                    // the part of body whose URL ends other than .html
                    // for example: xyz.jpg or xyz. jpeg are type of images.. should not be
                    // converted
                    toClient.write(response.bodyToString(false));
                }
            } catch (Exception e) {
                System.out.println("No response from server");
            }
            /* Insert object into the cache */

            client.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Read command line arguments and start proxy */
    public static void main(String args[]) {
        int myPort = 0;

        try {
            myPort = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Need port number as argument");
            System.exit(-1);
        } catch (NumberFormatException e) {
            System.out.println("Please give port number as integer.");
            System.exit(-1);
        }

        init(myPort);

        /**
         * Main loop. Listen for incoming connections and spawn a new thread for
         * handling them
         */
        Socket client = null;

        while (true) {
            try {
                client = socket.accept();
                handle(client);
            } catch (IOException e) {
                System.out.println("Error reading request from client: " + e);
                /*
                 * Definitely cannot continue processing this request, so skip to next iteration
                 * of while loop.
                 */
                continue;
            }
        }
    }
}
