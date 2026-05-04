import java.rmi.*;

public class PracMPI {
    public static void main(String[] args) throws Exception{
        MPI.init();

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int n = 8;
        int chunksize = n / size;

        int[] send = null;
        int[] recv = new int[chunksize];

        if(rank == 0){
            send = new int[]{1,2,3,4,5,6,7,8};
        }
        else{
            send = new int[n];
        }

        MPI.scatter(send,0,chunksize,MPI.INT,send,0,chunksize,MPI.INT,0)
    }
}