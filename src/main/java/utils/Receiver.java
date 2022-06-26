package utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver extends Thread{
    byte[] receiveBuffer = new byte[255];
    DatagramSocket socket;
    public Receiver(DatagramSocket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        DatagramPacket packetToReceive = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        // we need a new packet obj to accommodate the data from server.

        while (true){
            try {
                socket.receive(packetToReceive); // use the new object above to receive the message from the server.
            } catch (IOException e) {
//                e.printStackTrace();
                break;
            }
            // Actually `receive` method is listening to the port,
            // if a hacker sends the message before the server sends to you, you will receive the hacker's message.
            // But eventually you can decide if you want to ignore it or not.

            String receivedStr = new String(packetToReceive.getData(), 0, packetToReceive.getLength());
            // after you receive the message, you can use `getData` to extract it. But it is also `bytes`. So convert it back to what you want.
            // in this case, you need String.

//            if(receivedStr.equals("quit")) {
//                break;
//            }
            System.out.println("Message from " + packetToReceive.getPort() + ": "+ receivedStr);
            // print
        }
    }
}