# PING
README MANUAL

Author:	Punit Patel
UCID:	30064251


# Description

The PingServer behaves like a ping and get UDP datagram packets through datagram socket from PingClient.
After that it displays the received UDP datagram packet, and resend it back to PingClient sometimes after some time (simulated delay) or otherwise does not send anything (Dropped package).
The PingClient behaves like a client to PingServer. It sends 10 requests which are UDP datagram packets to PingServer with a sequence number, the word “PING” and a timestamp. It sends UDP datagram packets to the PingServer waits for a reply until timeout.
If packet is not received, timeout occurs that datagram packet is considered dropped, otherwise the contents of the received packet along with its delay time are displayed.
When all of the Client's 10 requests are finished, the PingClient shows the minimum, maximum and average RTTs.


# How to run

First open the directory where the file is located in server, using command:
		cd directory_path
Now after that run :
		javac PingServer.java
		java PingServer <port_number>
Then in a different terminal window in the directory where PingClient.java is located do the
following:
		javac PingClient.java
		java PingClient <host_ip_address> <port_number_used_in_server>


# Testing

Steps in the “How to run” were followed closely.
Running Environment was Linux Ubuntu 20.04.
PingServer was ran on Google Cloud Platform.


# Screenshots

The Screenshots are attached in the directory.
