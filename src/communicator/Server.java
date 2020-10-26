package communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @source https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 */
public class Server {
    private ServerSocket ss;
    public Server (int port) throws IOException {
        ss = new ServerSocket(port);
    }

    public void run(Scanner in) throws IOException {
        while(true) {
            Socket s = null;
            try {
                s = ss.accept();
                System.out.println("Client: " + s);

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Creating new thread");
                Thread t = new ClientHandler(s, dis, dos);
                t.start();
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
            s.run(scn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
