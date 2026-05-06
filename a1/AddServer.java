import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class AddServer extends UnicastRemoteObject implements RemoteAdd {

    public AddServer() throws RemoteException {}

    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) throws Exception {
        // Start registry programmatically
//        LocateRegistry.createRegistry(1099);

        AddServer obj = new AddServer();
        Naming.rebind("rmi://localhost/AddService", obj);

        System.out.println("Server Ready");
    }
}