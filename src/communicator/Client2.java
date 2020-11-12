package communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client2 {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Thread sender;
    private Thread receiver;

    public Client2() {

    }

    public static void main(String[] args) {
        
    }
}
