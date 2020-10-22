Author:		Punit Patel
UCID:		30064251
Tutorial:	T05

**Proxy Server**

This Proxy Server contains three Java files namely, Proxy.java, HttpRequest.java and HttpResponse.java

HttpRequest.java connects to client/user side and sends all the requests to Proxy.java, which then passes the request to main Server.

The data is recieved in Proxy.java which transfers response to HttpResponse.java which converts the content and ultimately passes to client/user.


*** How to Run the Proxy ***


1) Open your browser setting and search for "proxy settings"
2) Change the HTTP Proxy settings to:
		host: localhost
		port: (any open port number)
3) Open the directory containing all the files in Terminal
4) Use the following commands to run the Proxy:
		javac *.java -Xlint
		java Proxy (the port number, you selected in browser)
5) Open the http website, your Proxy Server should be working.


>>> How was testing done?


Steps in the "How to run the proxy" were followed accurately.

Web Browser used was Mozilla FireFox (normal and incognito tab).

Running Environment/OS was Linux Ubuntu 20.04.1 LTS

Proxy is completely able to alter the required texts which includes the headers, body and URLs.

Upon adding in-line CSS, the pictures were not able to load. Due to which CSS was removed in order for pictures to work perfectly.

I was also getting Broken-pipe error repeatedly and Proxy shows "No response from server" instead of that.
That error was mainly observed when pressing back button in browser.

*The Proxy only works for http webpages*


Reference:

The "if condition in Proxy.java at line 66" was given by my cousin, Piyush Patel.
