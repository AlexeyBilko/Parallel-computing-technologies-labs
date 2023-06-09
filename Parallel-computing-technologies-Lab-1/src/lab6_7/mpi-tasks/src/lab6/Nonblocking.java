package lab6;

import mpi.MPI;
import mpi.Request;
import java.util.ArrayList;
import java.util.Random;

public class Nonblocking {
    public static final int NRA = 500;
    public static final int NCA = 500;
    public static final int NCB = 500;
    public static final int MASTER = 0;

    public static void main(String args[]) throws Exception {
        int numtasks, taskid, numworkers, dest,
                averow, extra, i, j, k;
        double a[][] = new double[NRA][NCA];
        double b[][] = new double[NCA][NCB];
        double c[][] = new double[NRA][NCB];

        int[] offset = {0};
        int[] rows = {0};

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

            initializeMatrixWithRandom(a, 1, 100);
            initializeMatrixWithRandom(b, 1, 100);

            long startTime = System.currentTimeMillis();
            averow = NRA / numworkers;
            extra = NRA % numworkers;

            for (dest = 1; dest <= numworkers; dest++) {
                rows[0] = (dest <= extra) ? averow + 1 : averow;
                var request1 = MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, dest, Tags.OFFSET_FROM_MASTER.ordinal());
                var request2 = MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, dest, Tags.ROWS_FROM_MASTER.ordinal());
                MPI.COMM_WORLD.Isend(a, offset[0], rows[0], MPI.OBJECT, dest, Tags.A_FROM_MASTER.ordinal());
                MPI.COMM_WORLD.Isend(b, 0, NCA, MPI.OBJECT, dest, Tags.B_FROM_MASTER.ordinal());
                request1.Wait();
                request2.Wait();
                offset[0] += rows[0];
            }

            ArrayList<Request> subTasksRequests = new ArrayList<>();
            for (int source = 1; source <= numworkers; source++) {
                var request5 = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, source, Tags.OFFSET_FROM_WORKER.ordinal());
                var request6 = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, source, Tags.ROWS_FROM_WORKER.ordinal());
                request5.Wait();
                request6.Wait();
                subTasksRequests.add(MPI.COMM_WORLD.Irecv(c, offset[0], rows[0], MPI.OBJECT, source, Tags.C_FROM_WORKER.ordinal()));
            }
            for (var request : subTasksRequests) {
                request.Wait();
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
            MPI.Finalize();

        }
        else {
            var request1 = MPI.COMM_WORLD.Irecv(offset, 0, 1, MPI.INT, MASTER, Tags.OFFSET_FROM_MASTER.ordinal());
            var request2 = MPI.COMM_WORLD.Irecv(rows, 0, 1, MPI.INT, MASTER, Tags.ROWS_FROM_MASTER.ordinal());
            request1.Wait();
            request2.Wait();

            MPI.COMM_WORLD.Isend(offset, 0, 1, MPI.INT, MASTER, Tags.OFFSET_FROM_WORKER.ordinal());
            MPI.COMM_WORLD.Isend(rows, 0, 1, MPI.INT, MASTER, Tags.ROWS_FROM_WORKER.ordinal());
            var requestMatrixA = MPI.COMM_WORLD.Irecv(a, 0, rows[0], MPI.OBJECT, MASTER, Tags.A_FROM_MASTER.ordinal());
            var requestMatrixB = MPI.COMM_WORLD.Irecv(b, 0, NCA, MPI.OBJECT, MASTER, Tags.B_FROM_MASTER.ordinal());
            requestMatrixA.Wait();
            requestMatrixB.Wait();


            for (k = 0; k < NCB; k++) {
                for (i = 0; i < rows[0]; i++) {
                    for (j = 0; j < NCA; j++) {
                        c[i][k] += a[i][j] * b[j][k];
                    }
                }
            }

            MPI.COMM_WORLD.Isend(c, 0, rows[0], MPI.OBJECT, MASTER, Tags.C_FROM_WORKER.ordinal());
        }
        MPI.Finalize();
    }

    public static void initializeMatrixWithRandom(double[][] matrix, int minValue, int maxValue) {
        var random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = minValue + random.nextDouble() * maxValue;
            }
        }
    }
}
