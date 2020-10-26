package communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket s;
    private DataInputStream clientIn;
    private DataOutputStream clientOut;
    private String toSend;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.clientIn = dis;
        this.clientOut = dos;
    }

    @Override
    public void run() {
        String received;
        toSend = "hello there!";
        while (true) {
            try {
                clientOut.writeUTF(toSend);
                received = clientIn.readUTF();
                if (received.equals("terminate")) {
                    this.s.close();
                    break;
                }
                toSend = "You sent: " + received;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
