package communicator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Attempting to create a communication client.
 * Needs to create a new socket, inputting an ip(local = 127.0.0.1)
 * Also takes in server port.
 * Needs to have a main method, I will run it.
 * @source https://www.geeksforgeeks.org/introducing-threads-socket-programming-java/
 */
public class Client {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Client(String address, int port) throws IOException {
        s = new Socket(address, port);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
    }

    public void send(String in) throws IOException {
        dos.writeUTF(in);
    }

    public void receive() throws IOException {
        System.out.println(dis.readUTF());
    }

    public void terminate() throws IOException {
        dis.close();
        dos.close();
    }

    public void run(Scanner in) throws IOException {
        String input;
        while(true) {
            receive();
            input = in.nextLine();
            send(input);
            if (input.equals("terminate")) {
                terminate();
                in.close();
                System.out.println("Client has been terminated.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String address;
        int port;

        System.out.println("Please enter the ip (local is 127.0.0.1) ...");
        address = scn.nextLine();

        System.out.println("Please enter the port");
        port = scn.nextInt();
        scn.reset();

        System.out.println("Thank you!");
        try {
            Client c = new Client(address, port);
            c.run(scn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
