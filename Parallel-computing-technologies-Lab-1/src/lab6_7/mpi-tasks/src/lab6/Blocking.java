package lab6;

import mpi.MPI;
import java.util.Random;

public class Blocking {
    public static final int NRA = 500;
    public static final int NCA = 500;
    public static final int NCB = 500;
    public static final int MASTER = 0;
    public static final int FROM_MASTER = 1;
    public static final int FROM_WORKER = 2;

    public static void main(String args[]) throws Exception {
        int numtasks, taskid, numworkers, source, dest,
                averow, extra, i, j, k;
        double a[][] = new double[NRA][NCA];
        double b[][] = new double[NCA][NCB];
        double c[][] = new double[NRA][NCB];

        int[] offset={0};
        int[] rows={0};

        MPI.Init(args);
        numtasks = MPI.COMM_WORLD.Size();
        taskid = MPI.COMM_WORLD.Rank();

        if (numtasks < 2) {
            System.out.println("Need at least two MPI tasks. Quitting...");
            MPI.Finalize();
            System.exit(1);
        }

        numworkers = numtasks - 1;

        if (taskid == MASTER) {
            System.out.println("Main has started with " + numtasks + " tasks.");

            initializeMatrixWithRandom(a,1,100);
            initializeMatrixWithRandom(b,1,100);

            long startTime = System.currentTimeMillis();
            averow = NRA / numworkers;
            extra = NRA % numworkers;
            for (dest = 1; dest <= numworkers; dest++) {
                rows[0] = (dest <= extra) ? averow + 1 : averow;

                MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(a, offset[0], rows[0], MPI.OBJECT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(b, 0, NCA, MPI.OBJECT, dest, FROM_MASTER);
                offset[0] += rows[0];
            }

            for (source = 1; source <= numworkers; source++) {
                MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, source, FROM_WORKER);
                MPI.COMM_WORLD.Recv(c, offset[0], rows[0], MPI.OBJECT, source, FROM_WORKER);
            }

            long endTime = System.currentTimeMillis();

            System.out.println("****");
            System.out.println("Result Matrix:");
            for (i = 0; i < NRA; i++) {
                System.out.println();
                for (j = 0; j < NCB; j++)
                    System.out.print(c[i][j] + " ");
            }
            System.out.println("\n********");
            System.out.println("Done.");

            System.out.println("Execution time: " + (endTime - startTime) + " ms");
            System.out.println("Validation returned is:" + Validator.validate(a, b, c));
        }
        else {
            MPI.COMM_WORLD.Recv(offset, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(rows, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(a, 0, rows[0], MPI.OBJECT, MASTER, FROM_MASTER);
            MPI.COMM_WORLD.Recv(b, 0, NCA, MPI.OBJECT, MASTER, FROM_MASTER);

            for (k = 0; k < NCB; k++) {
                for (i = 0; i < rows[0]; i++) {
                    for (j = 0; j < NCA; j++)
                        c[i][k] += a[i][j] * b[j][k];
                }
            }

            MPI.COMM_WORLD.Send(offset, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(rows, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(c, 0, rows[0], MPI.OBJECT, MASTER, FROM_WORKER);

        }

        MPI.Finalize();
    }

    public static void initializeMatrixWithRandom(double[][] matrix, int low, int high) {
        var random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = low + random.nextDouble() * high;
            }
        }
    }
}
