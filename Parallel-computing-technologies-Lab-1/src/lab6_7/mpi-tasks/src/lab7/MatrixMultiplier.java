package lab7;

import mpi.MPI;

import java.util.Random;

public class MatrixMultiplier {
    public static int NRA = 500;
    public static int NCA = 500;
    public static int NCB = 500;
    public static int MASTER = 0;

    public static void main(String[] args) {
        double[][] a = new double[NRA][NCA];
        double[][] b = new double[NCA][NCB];
        double[][] c = new double[NRA][NCB];

        int taskId, tasksNumber;

        MPI.Init(args);
        taskId = MPI.COMM_WORLD.Rank();
        tasksNumber = MPI.COMM_WORLD.Size();

        long started = 0, ended;

        if (taskId == MASTER) {
            System.out.println("Started with " + tasksNumber + " tasks");
            initializeMatrixWithRandom(a, 1, 100);
            initializeMatrixWithRandom(b, 1, 100);
            started = System.currentTimeMillis();
        }

        int rowsPerTask = NRA / tasksNumber;
        int extraRows = NRA % tasksNumber;

        var rowsCounts = new int[tasksNumber];
        var rowsOffsets = new int[tasksNumber];
        for (int i = 0; i < tasksNumber; i++) {
            rowsCounts[i] = i < extraRows ? rowsPerTask + 1 : rowsPerTask;
            rowsOffsets[i] = i == 0 ? 0 : rowsOffsets[i - 1] + rowsCounts[i - 1];
        }
        var rowsInTask = rowsCounts[taskId];

        var aRowsBuffer = new double[rowsInTask][NCA];

        MPI.COMM_WORLD.Scatterv(a, 0, rowsCounts, rowsOffsets, MPI.OBJECT, aRowsBuffer, 0, rowsInTask, MPI.OBJECT, MASTER);

        MPI.COMM_WORLD.Bcast(b, 0, NCA, MPI.OBJECT, MASTER);

        var cRowsBuffer = new double[rowsInTask][NCB];
        for (int k = 0; k < NCB; k++) {
            for (int i = 0; i < rowsInTask; i++) {
                for (int j = 0; j < NCA; j++) {
                    cRowsBuffer[i][k] += aRowsBuffer[i][j] * b[j][k];
                }
            }
        }

        MPI.COMM_WORLD.Gatherv(cRowsBuffer,0, rowsInTask, MPI.OBJECT, c, 0, rowsCounts, rowsOffsets, MPI.OBJECT, MASTER);

        //MPI.COMM_WORLD.Allgatherv(cRowsBuffer, 0, rowsInTask, MPI.OBJECT, c, 0, rowsCounts, rowsOffsets, MPI.OBJECT);

        if (taskId == MASTER) {
            ended = System.currentTimeMillis();
            /*System.out.println("****");
            System.out.println("Result Matrix:");
            for (int i = 0; i < NCA; i++) {
                System.out.println();
                for (int j = 0; j < NCB; j++)
                    System.out.print(c[i][j] + " ");
            }*/
            System.out.println("\n********");
            System.out.println("Done.");

            System.out.println("Execution time: " + (ended - started) + " ms");
            System.out.println("Validation returned is:" + lab7.Validator.validate(a, b, c));
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
