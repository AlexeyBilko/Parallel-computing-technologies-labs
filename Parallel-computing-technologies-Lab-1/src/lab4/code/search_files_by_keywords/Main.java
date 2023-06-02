package lab4.code.search_files_by_keywords;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final String filePath = "/Users/alexeybilko/IdeaProjects/ParallelComputing/Parallel-computing-technologies-labs/Parallel-computing-technologies-Lab-1/src/lab4/files";

        var textsDirectory = new File(filePath);

        ArrayList<String> keywords = new ArrayList<>(Arrays.asList("computer", "algorithm", "search", "technology", "information"));

        Search searchByKeywordsTask = new Search(textsDirectory, keywords);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        HashMap<String, HashMap<String, Long>> filesWithKeywordsStatistics = forkJoinPool.invoke(searchByKeywordsTask);

        System.out.println("Keywords: " + keywords + "\n");

        for (var entry : filesWithKeywordsStatistics.entrySet()) {
            System.out.print(entry.getKey() + " : ");
            System.out.print(entry.getValue());
            System.out.println();
        }

        System.out.println("\nTotal files with keywords found: " + filesWithKeywordsStatistics.size());
    }
}