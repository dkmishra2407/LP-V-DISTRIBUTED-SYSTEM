import java.rmi.*;
public interface RemoteAdd extends Remote {
    int add(int a, int b) throws RemoteException;
}