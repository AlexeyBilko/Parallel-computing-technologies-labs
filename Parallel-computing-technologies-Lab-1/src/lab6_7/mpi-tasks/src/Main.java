import mpi.MPI;
import mpi.MPIException;

public class Main{
public static void main(String[] args) throws MPIException {

        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int n = 30;

        try{
        if (rank == 0) {
        String[] A = new String[n * size];
        for (int i = 0; i < n * size; i++) {
        A[i] = String.valueOf((char) ((int) (Math.random() * 26) + 'a'));
        }
        for (int i = 1; i < size; i++) {
        String[] fragment = new String[n];
        System.arraycopy(A, (i - 1) * n, fragment, 0, n);
        MPI.COMM_WORLD.Send(fragment, 0, n, MPI.OBJECT, i, 0);
        }

        String[] fragment = new String[n];
        System.arraycopy(A, 0, fragment, 0, n);
        java.util.Arrays.sort(fragment);

        String[] firstValues = new String[size];
        firstValues[0] = fragment[0];
        for (int i = 1; i < size; i++) {
        MPI.COMM_WORLD.Recv(firstValues, i, 1, MPI.OBJECT, i, 1);
        }

        System.out.println("First values of sorted arrays: ");
        for (int i = 0; i < size; i++) {
        System.out.print(firstValues[i] + " ");
        }
        System.out.println();
        } else {
        String[] fragment = new String[n];
        MPI.COMM_WORLD.Recv(fragment, 0, n, MPI.OBJECT, 0, 0);
        java.util.Arrays.sort(fragment);
        MPI.COMM_WORLD.Send(new String[] {fragment[0]}, 0, 1, MPI.OBJECT, 0, 1);
        }
        } finally {
        MPI.Finalize();
        }

        }
}