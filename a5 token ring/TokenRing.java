import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class TokenRing {
    private int myPort;
    private String nextHost;
    private int nextPort;
    private boolean hasToken;

    TokenRing(int myPort, String nextHost, int nextPort, boolean hasToken) {
        this.myPort = myPort;
        this.nextHost = nextHost;
        this.nextPort = nextPort;
        this.hasToken = hasToken;
    }

    public void startCirculation() throws Exception {

        // Server thread to receive token
        new Thread(() -> {
            try{
                ServerSocket serverSocket = new ServerSocket(myPort);
                System.out.println("Listening on port " + myPort);

                while (true) {
                    Socket socket = serverSocket.accept();
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    String token = in.readUTF();

                    if ("TOKEN".equals(token)) {
                        System.out.println("Token RECEIVED from " + socket.getRemoteSocketAddress());
                        hasToken = true;
                    }

                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // new Thread().start();

        // Main loop
        while (true) {
            if (hasToken) {
                enterCriticalSection();
                sendToken();
                hasToken = false;
            }
            Thread.sleep(2000);
        }
    }

    private void enterCriticalSection() throws Exception {
        System.out.println(">>>> ENTERED CRITICAL SECTION");
        Thread.sleep(3000);
        System.out.println("<<<< EXITED CRITICAL SECTION");
    }

    private void sendToken() {
        try (Socket socket = new Socket(nextHost, nextPort);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            out.writeUTF("TOKEN");
            System.out.println("Token SENT to " + socket.getRemoteSocketAddress());

        } catch (Exception e) {
            System.out.println("Failed to send token: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        int myPort = Integer.parseInt(args[0]);
        String nextHost = args[1];
        int nextPort = Integer.parseInt(args[2]);
        boolean hasToken = Boolean.parseBoolean(args[3]);

        TokenRing node = new TokenRing(myPort, nextHost, nextPort, hasToken);
        node.startCirculation();
    }
}