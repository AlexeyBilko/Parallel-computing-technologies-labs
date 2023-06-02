package lab2_1;

import lab2_1.MatrixMultiplier;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[][] matrix1 = generateRandomMatrix(100);
        int[][] matrix2 = generateRandomMatrix(100);
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

    public static int[][] generateRandomMatrix(int size) {
        int[][] matrix = new int[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt();
            }
        }

        return matrix;
    }
}
