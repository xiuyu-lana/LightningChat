package client;

import java.net.*;
import utils.*;



public class Client {
    private DatagramSocket socket;
    // Socket is an abstraction for the network communication.
    // Bob binds his socket to an ip and a port, and Alice binds her socket to another ip and a port.
    // Then the message will be sent and received through the socket.
    private InetAddress targetAddress; // If we want to send message within the computer, we only need one common address.
    private InetAddress myAddress;
    private int myPort = 8888; // This is the source port.
    private int targetPort = 8889; // This is the destination port.




    public Client() throws SocketException, UnknownHostException {
        // Client Constructor
        myAddress = InetAddress.getByName("192.168.0.112"); //get the corresponding InetAddress object by ip.
        targetAddress = InetAddress.getByName("192.168.0.112");
        socket = new DatagramSocket(myPort, myAddress); // binds client's socket to the client's ip and port.
        // after this, the port in owned by this process only.
    }

    public void start() throws InterruptedException {
        Receiver re = new Receiver(socket);
        re.start();

        Sender se = new Sender(socket, targetAddress, targetPort);
        se.start();

        re.join();
    }





    public static void main(String[] args) throws InterruptedException {
        Client client = null;
        try {
            client = new Client();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        client.start();
        System.exit(0);
    }
}



