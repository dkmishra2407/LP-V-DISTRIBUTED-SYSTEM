import java.rmi.*;
import java.util.Scanner;
public class AddClient {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        RemoteAdd obj = (RemoteAdd) Naming.lookup("rmi://localhost/AddService");
        int a,b;
        a = sc.nextInt();
        b = sc.nextInt();
        System.out.println("Sum = " + obj.add(a, b));
    }
}