package lab2_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplier {
    public static class Result {
        private int[][] resultMatrix;

        public Result(int[][] resultMatrix) {
            this.resultMatrix = resultMatrix;
        }

        public int[][] getResultMatrix() {
            return resultMatrix;
        }

        public void setResultMatrix(int[][] resultMatrix) {
            this.resultMatrix = resultMatrix;
        }
    }

    public static Result multiplyMatrices(int[][] matrix1, int[][] matrix2, int numberOfThreads) {
        int m = matrix1.length;
        int n = matrix1[0].length;
        int p = matrix2[0].length;
        int[][] result = new int[m][p];

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                executor.execute(new MultiplicationTask(matrix1, matrix2, result, i, j));
            }
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Result(result);
    }

    private static class MultiplicationTask implements Runnable {
        private int[][] matrix1;
        private int[][] matrix2;
        private int[][] result;
        private int i;
        private int j;

        public MultiplicationTask(int[][] matrix1, int[][] matrix2, int[][] result, int i, int j) {
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.result = result;
            this.i = i;
            this.j = j;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int k = 0; k < matrix1[0].length; k++) {
                sum += matrix1[i][k] * matrix2[k][j];
            }
            result[i][j] = sum;
        }
    }
}
