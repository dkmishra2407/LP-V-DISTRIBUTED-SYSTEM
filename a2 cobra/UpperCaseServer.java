//import UpperCaseModule.*;
//import org.omg.CosNaming.*;
//import org.omg.CORBA.*;
//import org.omg.PortableServer.*;
//
//class UpperCaseServer {
//    public static void main(String args[]) {
//        try {
//            // Initialize ORB
//            ORB orb = ORB.init(args, null);
//
//            // Get reference to RootPOA
//            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
//            rootpoa.the_POAManager().activate();
//
//            // Create servant
//            UpperCaseImpl upperImpl = new UpperCaseImpl();
//
//            // Get object reference
//            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(upperImpl);
//            UpperCase href = UpperCaseHelper.narrow(ref);
//
//            // Get naming context
//            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//
//            // Bind object
//            String name = "UpperCase";
//            NameComponent path[] = ncRef.to_name(name);
//            ncRef.rebind(path, href);
//
//            System.out.println("UpperCase Server ready and waiting...");
//
//            // Wait for client calls
//            orb.run();
//
//        } catch (Exception e) {
//            System.out.println("ERROR: " + e);
//            e.printStackTrace();
//        }
//    }
//}


import UpperCaseModule.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

public class UpperCaseServer {
    static void main(String[] args) {
        try{
            ORB orb = ORB.init(args,null);

            POA rootpoa =  POAHelper.narrow(orb.resolve_initial_reference("RootPOA"));
            rootpoa.the_POAManager().activate();

            UpperCaseImpl upperimpl = new UpperCaseImpl();

            org.omg.CORBA.Object obj = rootpoa.servant_to_reference(upperimpl);
            UpperCase href = UpperCaseHelper.narrow(obj);

            org.omg.CORBA.Object objref = orb.resolve_initial_reference("NameService");
            NamingContextExt ncref = NamingContextExtHelper.narrow(objref);

            String name = "UpperCase";
            NamingContext[] path = href.to_name(name);
            ncref.rebind(path);

            System.out.println("Server running");
            orb.run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}