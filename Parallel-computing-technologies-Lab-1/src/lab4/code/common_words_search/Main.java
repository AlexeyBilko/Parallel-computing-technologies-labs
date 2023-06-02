package lab4.code.common_words_search;

import java.io.File;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final String filePath = "/Users/alexeybilko/IdeaProjects/ParallelComputing/Parallel-computing-technologies-labs/Parallel-computing-technologies-Lab-1/src/lab4/files";

        var textsDirectory = new File(filePath);

        Search searchTask = new Search(textsDirectory);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        HashSet<String> commonWords = forkJoinPool.invoke(searchTask);

        System.out.println("Common words in files:");

        int printedWords = 0;
        for (var word : commonWords) {
            System.out.print(word);
            printedWords++;
            System.out.print(printedWords % 30 == 0 ? "\n" : " ");
        }

        System.out.println("\nNumber of common words in files: " + commonWords.size());
    }
}
