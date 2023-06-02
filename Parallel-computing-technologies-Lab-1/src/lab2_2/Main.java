package lab2_2;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[][] matrix1 = generateRandomMatrix(100);
        int[][] matrix2 = generateRandomMatrix(100);

        int[][] result = FoxAlgorithm.foxAlgorithmParallel(matrix1, matrix2);

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + " ");
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
