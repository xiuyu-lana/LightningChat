package server;

import java.io.IOException;
import java.net.*;

public class Server {
    private DatagramSocket socket;
    private InetAddress address; // If we want to send message within the computer, we only need one common address.
    private byte[] buf = new byte[256];
    private int serverPort = 8889;

    public Server() throws SocketException, UnknownHostException {
        byte[] ipAddr = new byte[]{127, 0, 0, 1}; // 127.0.0.1 is the localhost and can be used for processes' communications.
        address = InetAddress.getByAddress(ipAddr); //get the corresponding InetAddress object by ip.
        socket = new DatagramSocket(serverPort, address);
    }

    public void run() throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet); // use a new DatagramPacket obj to receive the data.

        InetAddress address = packet.getAddress(); // get the address of the sender.
        int port = packet.getPort(); // get the port of the sender.


        String received = new String(packet.getData(), 0, packet.getLength());
        // convert bytes data to string.
        System.out.println("Received msg: " + received);
        // print.

        String reply = "Hello, Client!";
        // string message to send back.
        byte[] replyBuffer = reply.getBytes();
        //convert to bytes before sending.
        DatagramPacket packetToReply = new DatagramPacket(replyBuffer, replyBuffer.length, address, port);
        // use a new DatagramPacket obj to store the data.
        socket.send(packetToReply);
        // send the message back to the sender


        System.out.println("Server is closed.");
        socket.close();
    }

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
