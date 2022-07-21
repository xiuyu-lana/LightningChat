import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.*;
import java.io.FileReader;
import org.json.simple.*;
//import org.json.*;
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
            throws InterruptedException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(new FileReader(args[0]));
        String myAdd = (String) obj.get("myAddress");
        String targetAdd = (String) obj.get("targetAddress");
        String myPort = (String) obj.get("myPort");
        String targetPort = (String) obj.get("targetPort");
        System.out.println(myAdd);
        System.out.println(targetAdd);
        System.out.println(myPort);
        System.out.println(targetPort);
        LightningChat lc = new LightningChat(myAdd, targetAdd, myPort, targetPort);
        lc.start();

        System.exit(0);
    }
}
