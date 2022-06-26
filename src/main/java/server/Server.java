package server;

import java.io.IOException;
import java.net.*;
import utils.*;

public class Server {
    private DatagramSocket socket;
    private InetAddress targetAddress; // If we want to send message within the computer, we only need one common address.
    private InetAddress myAddress;
    private int myPort = 8889;
    private int targetPort = 8888;

    public Server() throws SocketException, UnknownHostException {
        // Client Constructor
        myAddress = InetAddress.getByName("192.168.0.112"); //get the corresponding InetAddress object by ip.
        targetAddress = InetAddress.getByName("192.168.0.112");
        socket = new DatagramSocket(myPort, myAddress); // binds client's socket to the client's ip and port.
        // after this, the port in owned by this process only.
    }

    public void start(){
        Receiver re = new Receiver(socket);
        re.start();

        Sender se = new Sender(socket, targetAddress, targetPort);
        se.start();
    }

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        server.start();
    }
}
