package utils;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender extends Thread {
    DatagramSocket socket;
    Scanner sc;
    private int port;
    private InetAddress address;

    public Sender(DatagramSocket socket, InetAddress address, int port) {
        this.socket = socket;
        this.address = address;
        this.port =port;
        sc = new Scanner(System.in);
    }
    @Override
    public void run() {
        while(true) {
            String msg = sc.nextLine();
            try {
                sendMessage(msg);
            } catch (IOException e) {
//                e.printStackTrace();
                break;
            }
            if(msg.equals("quit")) {
                System.out.println("Chat has been closed");
                socket.close();
                break;
            }
        }
    }

    public void sendMessage(String msg) throws IOException {
        byte[] sendBuffer = msg.getBytes();
        // Network only allow bytes to go through.
        // All objects/information need to be converted to bytes before sending.

        DatagramPacket packetToSend = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        socket.send(packetToSend); // send to server

    }
}
