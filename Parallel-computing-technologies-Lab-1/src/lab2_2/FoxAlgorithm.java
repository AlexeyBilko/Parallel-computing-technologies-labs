package lab2_2;

import java.util.concurrent.*;

public class FoxAlgorithm {
    private static final ForkJoinPool pool = new ForkJoinPool();

    public static int[][] foxAlgorithmParallel(int[][] matrix1, int[][] matrix2) {
        int m = matrix1.length;
        int blocksize = (int) Math.sqrt(m);
        int submatrixescount = m / blocksize;

        int[][] result = new int[m][m];
        int[][] temp = new int[blocksize][blocksize];

        for (int k = 0; k < submatrixescount; k++) {
            for (int i = 0; i < submatrixescount; i++) {
                for (int j = 0; j < submatrixescount; j++) {
                    pool.invoke(new FoxTask(matrix1, matrix2, result, temp, k, i, j, blocksize));
                }
            }
        }

        return result;
    }

    private static class FoxTask extends RecursiveAction {
        private final int[][] matrix1;
        private final int[][] matrix2;
        private final int[][] result;
        private final int[][] temp;
        private final int k;
        private final int i;
        private final int j;
        private final int blocksize;

        public FoxTask(int[][] a, int[][] b, int[][] c, int[][] temp, int k, int i, int j, int nb) {
            this.matrix1 = a;
            this.matrix2 = b;
            this.result = c;
            this.temp = temp;
            this.k = k;
            this.i = i;
            this.j = j;
            this.blocksize = nb;
        }

        @Override
        protected void compute() {
            int startI = i * blocksize;
            int startJ = j * blocksize;
            int startK = k * blocksize;

            for (int ii = startI; ii < startI + blocksize; ii++) {
                for (int jj = startJ; jj < startJ + blocksize; jj++) {
                    temp[ii - startI][jj - startJ] = 0;
                    for (int kk = startK; kk < startK + blocksize; kk++) {
                        temp[ii - startI][jj - startJ] += matrix1[ii][kk] * matrix2[kk][jj];
                    }
                }
            }

            for (int ii = startI; ii < startI + blocksize; ii++) {
                for (int jj = startJ; jj < startJ + blocksize; jj++) {
                    result[ii][jj] += temp[ii - startI][jj - startJ];
                }
            }
        }
    }
}