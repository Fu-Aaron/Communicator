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
    private Scanner scn;
    private Thread sender;
    private Thread receiver;

    public Client(String address, int port, Scanner scn) throws IOException {
        s = new Socket(address, port);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
        this.scn = scn;
        sender = new Thread(new sendMessage());
        receiver = new Thread(new receiveMessage());
    }

    private class sendMessage implements Runnable {
        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while(true) {
                try {
                    String in = scn.nextLine();
                    dos.writeUTF(in);
                    if (in.equals("terminate"))  {
                        terminate();
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class receiveMessage implements Runnable {
        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while(true) {
                try {
                    String out = dis.readUTF();
                    System.out.println(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void terminate() throws IOException {
        dis.close();
        dos.close();
    }

    public void run() {
       sender.start();
       receiver.start();
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
            Client c = new Client(address, port, scn);
            c.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
