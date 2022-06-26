package server;

import java.io.IOException;
import java.net.*;
import utils.*;

public class Server {
    private DatagramSocket socket;
    private InetAddress address; // If we want to send message within the computer, we only need one common address.
    private byte[] receiveBuf = new byte[256];
    private int serverPort = 8889;
    private int clientPort = 8888;
    int count = 0;

    public Server() throws SocketException, UnknownHostException {
        byte[] ipAddr = new byte[]{127, 0, 0, 1}; // 127.0.0.1 is the localhost and can be used for processes' communications.
        address = InetAddress.getByAddress(ipAddr); //get the corresponding InetAddress object by ip.
        socket = new DatagramSocket(serverPort, address);
    }

    public void start(){
        Receiver re = new Receiver(socket);
        re.start();

        Sender se = new Sender(socket, address, clientPort);
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
