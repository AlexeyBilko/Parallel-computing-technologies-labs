package lab4.code.static_analysis;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final String filePath = "/Users/alexeybilko/IdeaProjects/ParallelComputing/Parallel-computing-technologies-labs/Parallel-computing-technologies-Lab-1/src/lab4/files";

        File files = new File(filePath);
        //Parallel:
        /*StaticAnalyzer parallel = new StaticAnalyzer(files);

        ForkJoinPool forkJoinPool = new ForkJoinPool(8);

        long start = System.currentTimeMillis();
        forkJoinPool.invoke(parallel);
        long finish = System.currentTimeMillis();

        System.out.println("Execution time: " + (finish - start) + " ms");

        System.out.println("Results:\n");
        printDistributionLaw(parallel.getDistributionLaw());

        System.out.println("\nMean: " + parallel.getMean());
        System.out.println("Variance: " + parallel.getVariance());
        System.out.println("Standard Derivation: " + parallel.getStandardDeviation());*/
        //Regular
        StaticAnalyzerRegular regular = new StaticAnalyzerRegular(files);
        long start = System.currentTimeMillis();
        regular.compute();
        long finish = System.currentTimeMillis();

        System.out.println("Execution time: " + (finish - start) + " ms");

        System.out.println("Results:\n");
        printDistributionLaw(regular.getDistributionLaw());

        System.out.println("\nMean: " + regular.getMean());
        System.out.println("Variance: " + regular.getVariance());
        System.out.println("Standard Derivation: " + regular.getStandardDeviation());


    }

    public static void printDistributionLaw(HashMap<Integer, Double> distributionLaw) {
        System.out.print("word length (x)  / ");
        for (Integer key : distributionLaw.keySet()) {
            System.out.printf("%-10d / ", key);
        }

        System.out.print("\nappeared (p)     / ");
        for (Double value : distributionLaw.values()) {
            System.out.printf("%-10.6f / ", value);
        }
        System.out.println();
    }

}
