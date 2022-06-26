import java.net.*;
import utils.*;

public class LightningChat {
    private DatagramSocket socket;
    private InetAddress targetAddress; // If we want to send message within the computer, we only need one common address.
    private InetAddress myAddress;
    private int myPort; // This is the source port.
    private int targetPort; // This is the destination port.

    public LightningChat(String myAddress, String targetAddress,
                         String myPort, String targetPort) throws SocketException, UnknownHostException {
        this.myAddress = InetAddress.getByName(myAddress);
        this.targetAddress = InetAddress.getByName(targetAddress);
        this.myPort = Integer.parseInt(myPort);
        this.targetPort = Integer.parseInt(targetPort);
        socket = new DatagramSocket(this.myPort, this.myAddress);
    }

    public void start() throws InterruptedException {
        Receiver re = new Receiver(socket);
        re.start();

        Sender se = new Sender(socket, targetAddress, targetPort);
        se.start();

        re.join();
    }

    public static void main(String[] args)
            throws InterruptedException, SocketException, UnknownHostException {
        LightningChat lc = new LightningChat(args[0], args[1], args[2], args[3]);
        lc.start();

        System.exit(0);
    }
}
