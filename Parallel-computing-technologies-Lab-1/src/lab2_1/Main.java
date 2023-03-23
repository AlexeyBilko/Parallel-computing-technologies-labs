package lab2_1;

import lab2_1.MatrixMultiplier;

public class Main {
    public static void main(String[] args) {
        int[][] matrix1 = {{1, 2}, {3, 4}};
        int[][] matrix2 = {{5, 6}, {7, 8}};
        int numberOfThreads = 4;

        MatrixMultiplier.Result result = MatrixMultiplier.multiplyMatrices(matrix1, matrix2, numberOfThreads);

        int[][] resultMatrix = result.getResultMatrix();

        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[0].length; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
