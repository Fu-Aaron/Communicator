package communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket s;
    private DataInputStream clientIn;
    private DataOutputStream clientOut;
    private String name;
    private String received;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, String name) {
        this.s = s;
        this.clientIn = dis;
        this.clientOut = dos;
        this.name = name;
    }

    public String getClientName() {
        return name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                send();
                received = receive();
                if (received.equals("terminate")) {
                    terminate();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void send() throws IOException {
        if (Server.clientList.size() == 1) {
            clientOut.writeUTF("No other clients connected...");
        } else {
            for (ClientHandler ch : Server.clientList) {
                ch.clientOut.writeUTF(getClientName() + " writes: " + received);
            }
        }
    }

    private String receive() throws IOException {
        return clientIn.readUTF();
    }

    private void terminate() throws IOException {
        this.s.close();
        Server.clientList.remove(this);
        System.out.println(getClientName() + "has been terminated. There are: " + Server.clientList.size() + " clients left.");
    }
}
