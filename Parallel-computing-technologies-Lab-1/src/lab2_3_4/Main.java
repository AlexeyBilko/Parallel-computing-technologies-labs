package lab2_3_4;

import lab2_2.FoxAlgorithm;
import lab2_1.MatrixMultiplier;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] matrixSizes = {100, 500, 1000, 2000, 5000};
        int[] threadCounts = {1, 2, 4, 8};
        int numTests = 5;

        for (int size : matrixSizes) {
            System.out.println("Testing matrix size: " + size);
            int[][] matrixA = generateRandomMatrix(size);
            int[][] matrixB = generateRandomMatrix(size);

            for (int threads : threadCounts) {
                long foxTotalTime = 0;
                long tapeTotalTime = 0;

                System.out.println("Testing with " + threads + " threads...");

                for (int i = 0; i < numTests; i++) {
                    long startTime = System.nanoTime();
                    FoxAlgorithm.foxAlgorithmParallel(matrixA, matrixB);
                    long foxTime = System.nanoTime() - startTime;
                    foxTotalTime += foxTime;

                    startTime = System.nanoTime();
                    MatrixMultiplier.multiplyMatrices(matrixA, matrixB, threads);
                    long tapeTime = System.nanoTime() - startTime;
                    tapeTotalTime += tapeTime;

                    System.out.println("Test " + (i + 1) + ": Fox time = " + foxTime + " ns, Tape time = " + tapeTime + " ns");
                }

                // Calculate average execution time for each algorithm
                double foxAvgTime = (double) foxTotalTime / numTests;
                double tapeAvgTime = (double) tapeTotalTime / numTests;

                System.out.println("Average execution time for Fox algorithm with " + threads + " threads: " + foxAvgTime + " ns");
                System.out.println("Average execution time for Tape algorithm with " + threads + " threads: " + tapeAvgTime + " ns");

                // Compare the efficiency of the algorithms
                if (foxAvgTime < tapeAvgTime) {
                    System.out.println("Fox algorithm is more efficient than Tape algorithm with " + threads + " threads");
                } else if (foxAvgTime > tapeAvgTime) {
                    System.out.println("Tape algorithm is more efficient than Fox algorithm with " + threads + " threads");
                } else {
                    System.out.println("Both algorithms have the same efficiency with " + threads + " threads");
                }
            }
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