
/*
Name:       Punit Patel
UCID:       30064251
Tutorial:   T-05
*/

import java.io.*;

public class HttpRequest {
    /** Help variables */
    final static String CRLF = "\r\n";
    final static int HTTP_PORT = 80;
    /** Store the request parameters */
    String method;
    String URI;
    String version;
    String headers = "";
    /** Server and port */
    private String host;
    private int port;

    /**
     * These static variables are used to make the sever host and port number
     * permanent, incase the connection is broken once by unknown request from
     * client
     */
    private static String fHost;
    private static int fPort;
    private static boolean flag = true;

    /** Create HttpRequest by reading it from the client socket */
    public HttpRequest(BufferedReader from) {
        String firstLine = "";
        try {
            firstLine = from.readLine();
            if (firstLine == null) {
                host = fHost;
                port = fPort;
                return;
            }
        } catch (IOException e) {
            System.out.println("Error reading request line: " + e);
        }

        // getting method, URL and version of Webpage
        String[] tmp = firstLine.split(" ");
        method = tmp[0];
        URI = tmp[1];
        version = tmp[2];

        System.out.println("method: " + method);
        System.out.println("URI is: " + URI);

        if (!method.equals("GET")) {
            System.out.println("Error: Method not GET");
        }
        try {
            String line = from.readLine();
            while (line.length() != 0) {
                headers += line + CRLF;
                /*
                 * We need to find host header to know which server to contact in case the
                 * request URI is not complete.
                 */
                if (line.startsWith("Host:")) {
                    tmp = line.split(" ");
                    if (tmp[1].indexOf(':') > 0) {
                        String[] tmp2 = tmp[1].split(":");
                        host = tmp2[0];
                        port = Integer.parseInt(tmp2[1]);
                    } else {
                        host = tmp[1];
                        port = HTTP_PORT;
                    }
                }
                line = from.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading from socket: " + e);
            return;
        }
        System.out.println("Host to contact is: " + host + " at port " + port);
        /*
         * sets the static variables to the host and port of the first time successfully
         * connected server
         */
        if (flag) {
            flag = false;
            fHost = getHost();
            fPort = getPort();
        }
    }

    /** Return host for which this request is intended */
    public String getHost() {
        return host;
    }

    /** Return port for server */
    public int getPort() {
        return port;
    }

    /**
     * Convert request into a string for easy re-sending.
     */
    public String toString() {
        String req = "";

        req = method + " " + URI + " " + version + CRLF;
        req += headers;
        /* This proxy does not support persistent connections */
        req += "Connection: close" + CRLF;
        req += CRLF;

        return req;
    }
}
