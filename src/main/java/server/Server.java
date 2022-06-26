package server;

import java.io.IOException;
import java.net.*;

public class Server {
    private DatagramSocket socket;
    private InetAddress address; // If we want to send message within the computer, we only need one common address.
    private byte[] receiveBuf = new byte[256];
    private int serverPort = 8889;
    int count = 0;

    public Server() throws SocketException, UnknownHostException {
        byte[] ipAddr = new byte[]{127, 0, 0, 1}; // 127.0.0.1 is the localhost and can be used for processes' communications.
        address = InetAddress.getByAddress(ipAddr); //get the corresponding InetAddress object by ip.
        socket = new DatagramSocket(serverPort, address);
    }

    public void run() throws IOException {
        DatagramPacket packet = new DatagramPacket(receiveBuf, receiveBuf.length);
        while(true){
            socket.receive(packet); // use a new DatagramPacket obj to receive the data.
            count++;

            InetAddress address = packet.getAddress(); // get the address of the sender.
            int port = packet.getPort(); // get the port of the sender.


            String received = new String(packet.getData(), 0, packet.getLength());
            // convert bytes data to string.
            System.out.println("Received msg: " + received);
            // print.


            if(received.equals("quit")) {
                send("Bye!", address, port);
                System.out.println("Server is closed.");
                socket.close();
                break;
            } else if(count % 3 == 0) {
                send("yes", address, port);
                send("I know", address, port);
                send("this is 3n", address, port);
            } else if (received.equals("[smile]")) {
                send("^_^", address, port);
            } else {
                send("Howdy! Client", address, port);
            }


        }



    }

    // send the message back to the sender
    private void send(String msg, InetAddress address, int port)  throws IOException{
        // string message to send back.
        byte[] replyBuffer = msg.getBytes();
        //convert to bytes before sending.
        DatagramPacket packetToReply = new DatagramPacket(replyBuffer, replyBuffer.length, address, port);
        // use a new DatagramPacket obj to store the data.
        socket.send(packetToReply);
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
