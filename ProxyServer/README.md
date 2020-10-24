Author:		Punit Patel
UCID:		30064251

#Proxy Server

*Description*

A web proxy is an intermediary that interceptseach and every request and (generally) forwards it on to the web server. The servers direct their responses back to the proxy, which in turn passes them onto the client. In this way, a web proxy acts as a middle-person between a clientand a server. Here’s the neat part, though; when requests and responses travelthrough a proxy, the proxy can control what gets passed along and modifies therequests and responses.

This Proxy Server contains three Java files namely, Proxy.java, HttpRequest.java and HttpResponse.java

HttpRequest.java connects to client/user side and sends all the requests to Proxy.java, which then passes the request to main Server.

The data is recieved in Proxy.java which transfers response to HttpResponse.java which converts the content and ultimately passes to client/user.

For our purpose, program should apply these changes:

	•2019 to 2219

	•NBA to TBA

	•World to Titan

	•Drummond to Kobe-B24



#How to Run the Proxy


1) Open your browser setting and search for "proxy settings"

2) Change the HTTP Proxy settings to:

	host: localhost
	port: (any open port number)

3) Open the directory containing all the files in Terminal

4) Use the following commands to run the Proxy:

	javac *.java -Xlint
	java Proxy (the port number, you selected in browser)

5) Open the http website, your Proxy Server should be working.



#>How was testing done?


Steps in the "How to run the proxy" were followed accurately.

Web Browser used was Mozilla FireFox (normal and incognito tab).

Running Environment/OS was Linux Ubuntu 20.04.1 LTS

Proxy is completely able to alter the required texts which includes the headers, body and URLs.

Upon adding in-line CSS, the pictures were not able to load. Due to which CSS was removed in order for pictures to work perfectly.

I was also getting Broken-pipe error repeatedly and Proxy shows "No response from server" instead of that.
That error was mainly observed when pressing back button in browser.

*The Proxy only works for http webpages*
