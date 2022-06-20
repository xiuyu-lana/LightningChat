package client;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    private DatagramSocket socket;
    // Socket is an abstraction for the network communication.
    // Bob binds his socket to an ip and a port, and Alice binds her socket to another ip and a port.
    // Then the message will be sent and received through the socket.
    private InetAddress address; // If we want to send message within the computer, we only need one common address.
    private int clientPort = 8888; // This is the source port.
    private int serverPort = 8889; // This is the destination port.

    public Client() throws SocketException, UnknownHostException {
        // Client Constructor
        byte[] ipAddr = new byte[]{127, 0, 0, 1}; // 127.0.0.1 is the localhost and can be used for processes' communications.
        address = InetAddress.getByAddress(ipAddr); //get the corresponding InetAddress object by ip.
        socket = new DatagramSocket(clientPort, address); // binds client's socket to the client's ip and port.
        // after this, the port in owned by this process only.
    }

    public void sendHello() throws IOException {
        String msg = "Howdy, server!";
        sendMessage(msg);
    }



    public void close() {
        // call this function to close the socket. Then another process can re-use the port.
        // there are totally 65535 ports in each computer.
        socket.close();
    }

    public void sendMessage(String msg) throws IOException {
        byte[] sendBuffer = msg.getBytes();
        byte[] receiveBuffer = new byte[255];
        // Network only allow bytes to go through.
        // All objects/information need to be converted to bytes before sending.

        DatagramPacket packetToSend = new DatagramPacket(sendBuffer, sendBuffer.length, address, serverPort);
        socket.send(packetToSend); // send to server

        DatagramPacket packetToReceive = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        // we need a new packet obj to accommodate the data from server.

        socket.receive(packetToReceive); // use the new object above to receive the message from the server.
        // Actually `receive` method is listening to the port,
        // if a hacker sends the message before the server sends to you, you will receive the hacker's message.
        // But eventually you can decide if you want to ignore it or not.

        String receivedStr = new String(packetToReceive.getData(), 0, packetToReceive.getLength());
        // after you receive the message, you can use `getData` to extract it. But it is also `bytes`. So convert it back to what you want.
        // in this case, you need String.

        System.out.println("Reply from the server:"+receivedStr);
        // print
    }

    public static void main(String[] args) {
        Client client = null;
        try {
            client = new Client();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
//            client.sendHello();
            Scanner sc = new Scanner(System.in);

            client.sendMessage(sc.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.close();
    }
}
