package lab2_2;

import java.util.concurrent.*;

public class FoxAlgorithm {
    private static final ForkJoinPool pool = new ForkJoinPool();

    public static int[][] foxAlgorithmParallel(int[][] a, int[][] b) {
        int n = a.length;
        int nb = (int) Math.sqrt(n);
        int q = n / nb;

        int[][] c = new int[n][n];
        int[][] temp = new int[nb][nb];

        for (int k = 0; k < q; k++) {
            for (int i = 0; i < q; i++) {
                for (int j = 0; j < q; j++) {
                    pool.invoke(new FoxTask(a, b, c, temp, k, i, j, nb));
                }
            }
        }

        return c;
    }

    private static class FoxTask extends RecursiveAction {
        private final int[][] a;
        private final int[][] b;
        private final int[][] c;
        private final int[][] temp;
        private final int k;
        private final int i;
        private final int j;
        private final int nb;

        public FoxTask(int[][] a, int[][] b, int[][] c, int[][] temp, int k, int i, int j, int nb) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.temp = temp;
            this.k = k;
            this.i = i;
            this.j = j;
            this.nb = nb;
        }

        @Override
        protected void compute() {
            int startI = i * nb;
            int startJ = j * nb;
            int startK = k * nb;

            for (int ii = startI; ii < startI + nb; ii++) {
                for (int jj = startJ; jj < startJ + nb; jj++) {
                    temp[ii - startI][jj - startJ] = 0;
                    for (int kk = startK; kk < startK + nb; kk++) {
                        temp[ii - startI][jj - startJ] += a[ii][kk] * b[kk][jj];
                    }
                }
            }

            for (int ii = startI; ii < startI + nb; ii++) {
                for (int jj = startJ; jj < startJ + nb; jj++) {
                    c[ii][jj] += temp[ii - startI][jj - startJ];
                }
            }
        }
    }
}