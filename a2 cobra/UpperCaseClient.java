import UpperCaseModule.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class UpperCaseClient {
    public static void main(String args[]) {
        try {
            // Initialize ORB
//            ORB orb = ORB.init(args, null);
//
//            // Get naming context
//            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//
//            // Resolve object
//            String name = "UpperCase";
//            UpperCase upperImpl = UpperCaseHelper.narrow(ncRef.resolve_str(name));
//
//            // Input from user
//            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            System.out.print("Enter String: ");
//            String str = br.readLine();
//
//            // Call remote method
//            String result = upperImpl.ConvertToUppercase(str);
//
//            System.out.println("Uppercase String: " + result);

            ORB orb = ORB.init(args,null);

            org.omg.CORBA.Object objref = orb.resolve_initial_reference("NameService");
            NamingContextExt ncref = NamingContextExtHelper.narrow(objref);

            String name = "UpperCase";

            UpperCase upperimpl = UpperCaseHelper.narrow(ncref.resolve_str(name));



        } catch (Exception e) {
            System.out.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}