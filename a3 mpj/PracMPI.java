import mpi.*;
import java.util.Random;

public class PracMPI {
    public static void main(String args[]) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size(); // it is the value we pass during execution of the code

        int[] send = null;
        int n = 8; // MUST be divisible by size
        int chunkSize = n / size;

        int[] recv = new int[chunkSize];

        // ROOT initializes array
        if (rank == 0) {

            send = new int[]{1,2,3,4,5,6,7,8};

            // INCASE OF RANDOMLY GENERATED ARRAY
            /*
            send = new int[n];
            Random rand = new Random();

            for (int i = 0; i < n; i++)
                send[i] = rand.nextInt(10);
            */

        } else {
            send = new int[n]; // dummy array for other processes
        }

        // SCATTER
        MPI.COMM_WORLD.Scatter(send, 0, chunkSize, MPI.INT, recv, 0, chunkSize, MPI.INT, 0);

        // SUM (2.1)

        int sum = 0;
        for (int i = 0; i < recv.length; i++)
            sum += recv[i];

        System.out.println("Process " + rank + " Sum: " + sum);


        // -----------------------------------------------------------------------------------------------------

        // MULTIPLICATION (2.2)
        /*
        int prod = 1;
        for (int i = 0; i < recv.length; i++)
            prod *= recv[i];

        System.out.println("Process " + rank + " Product: " + prod);
        */
        // -----------------------------------------------------------------------------------------------------

        // AVERAGE (2.3)
        /*
        double localSum = 0;

        // each process computes sum of its chunk
        for (int i = 0; i < recv.length; i++)
            localSum += recv[i];

        // local average
        double localAvg = localSum / recv.length;
        System.out.println("Process " + rank + " Local Avg: " + localAvg);

        // gather all local averages at root
        double[] gathered = new double[size];
        MPI.COMM_WORLD.Gather(new double[]{localAvg}, 0, 1, MPI.DOUBLE, gathered, 0, 1, MPI.DOUBLE, 0);

        // root computes final average
        if (rank == 0) {
            double finalSum = 0;
            for (int i = 0; i < gathered.length; i++)
                finalSum += gathered[i];

            double finalAvg = finalSum / size;

            System.out.println("Final Average: " + finalAvg);
        }
        */
        // -----------------------------------------------------------------------------------------------------

        // RECIPROCAL (2.4)
        /*
        // create a recArr array that stores reciprocals of the elements of recv array
        double[] recArr = new double[recv.length];

        for (int i = 0; i < recv.length; i++)
            recArr[i] = 1.0 / recv[i];

        // Gather results at root
        double[] result = new double[n];
        MPI.COMM_WORLD.Gather(recArr, 0, chunkSize, MPI.DOUBLE, result, 0, chunkSize, MPI.DOUBLE, 0);

        // Root prints final array
        if (rank == 0) {
            System.out.print("Reciprocal Array: ");
            for (int i = 0; i < result.length; i++)
                System.out.print(result[i] + " ");
            System.out.println();
        }
        */
        // -----------------------------------------------------------------------------------------------------

        MPI.Finalize();
    }
}