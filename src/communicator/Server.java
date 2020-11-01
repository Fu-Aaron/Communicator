package communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

/**
 * @source https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 */
public class Server {
    private ServerSocket ss;
    /**
     * Vectors are apparently thread safe, recommended to use vectors for comms.
     * After much research, static public variables are the best way to have this be accessible.
     * No modifier means package private
     * @source https://www.geeksforgeeks.org/multi-threaded-chat-application-set-1/
     */
    static Vector<ClientHandler> clientList;

    public Server (int port) throws IOException {
        ss = new ServerSocket(port);
        clientList = new Vector<>();
    }

    public void run() throws IOException {
        int i = 0;
        while(true) {
            Socket s = null;
            try {
                s = ss.accept();
                System.out.println("Client: " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Creating new thread");
                ClientHandler t = new ClientHandler(s, dis, dos, "Client" + i);
                clientList.add(t);
                System.out.println(t.getClientName() + " has been created.");
                t.start();
                i++;
            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int port;

        System.out.println("Please enter the port");
        port = scn.nextInt();
        scn.reset();

        System.out.println("Thank you!");
        try {
            Server s = new Server(port);
            s.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
