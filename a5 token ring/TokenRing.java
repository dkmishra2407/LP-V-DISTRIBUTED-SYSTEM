//import java.util.*;
//
//public class TokenRing {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("Enter Number Of Nodes: ");
//        int n = sc.nextInt();
//
//        System.out.println("Ring:");
//        for (int i = 0; i < n; i++)
//            System.out.print(i + " -> ");
//        System.out.println("0");
//
//        int choice;
//        int token = 0; // token starts at 0
//
//        do {
//            System.out.println("\nCurrent Token at Process: " + token);
//
//            System.out.print("Enter Sender: "); int sender = sc.nextInt();
//            System.out.print("Enter Receiver: "); int receiver = sc.nextInt();
//            sc.nextLine();
//            System.out.print("Enter Data: "); String data = sc.nextLine();
//
//            // move token until it reaches sender
//            System.out.println("\nToken Passing:");
//            while (token != sender) {
//                System.out.print(token + " -> ");
//                token = (token + 1) % n;
//            }
//            System.out.println(sender);
//
//            // critical section
//            System.out.println("\nProcess " + sender + " ENTERS Critical Section");
//            System.out.println("Sending Data: " + data);
//
//            // data forwarding
//            int i = sender;
//            while (i != receiver) {
//                System.out.println("Data forwarded By " + i + " To " + (i+1)%n);
//                i = (i + 1) % n;
//            }
//
//            System.out.println("Receiver " + receiver + " received data: " + data);
//            System.out.println("Process " + sender + " EXITS Critical Section");
//
//            // pass token to next process
//            token = (sender + 1) % n;
//
//            System.out.print("\nEnter 1 to continue, 0 to stop: ");
//            choice = sc.nextInt();
//
//        } while (choice == 1);
//    }
//}

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TokenRing {

    static int id;
    static int port;
    static String nextHost;
    static int nextPort;

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Process ID: ");
        id = sc.nextInt();

        System.out.print("Enter Port to Listen: ");
        port = sc.nextInt();

        sc.nextLine(); // consume newline

        System.out.print("Enter Next Process IP: ");
        nextHost = sc.nextLine();

        System.out.print("Enter Next Process Port: ");
        nextPort = sc.nextInt();

        // Start server thread to receive token
        new Thread(() -> receiveToken()).start();

        // If this is process 0 → generate token initially
        if (id == 0) {
            Thread.sleep(2000);
            sendToken("TOKEN");
        }
    }

    // 🔹 Receive Token
    public static void receiveToken() {
        try {
            ServerSocket server = new ServerSocket(port);

            while (true) {
                Socket socket = server.accept();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                String token = in.readLine();

                System.out.println("\nProcess " + id + " received TOKEN");

                // Enter Critical Section
                criticalSection();

                // Pass token to next
                sendToken("TOKEN");

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Send Token
    public static void sendToken(String token) {
        try {
            Socket socket = new Socket(nextHost, nextPort);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(token);

            System.out.println("Process " + id + " sent TOKEN to next");

            socket.close();

        } catch (Exception e) {
            System.out.println("Next process not ready...");
        }
    }

    // 🔹 Critical Section
    public static void criticalSection() {
        try {
            System.out.println(">>> Process " + id + " ENTERING CS");
            Thread.sleep(3000); // simulate work
            System.out.println("<<< Process " + id + " EXITING CS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}