package lab4.code.search_files_by_keywords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Search extends RecursiveTask<HashMap<String, HashMap<String, Long>>> {
    final File textFile;
    final ArrayList<String> keywords;
    final HashMap<String, HashMap<String, Long>> resultFiles = new HashMap<>();

    public Search(File textFile, ArrayList<String> keywords) {
        this.textFile = textFile;
        this.keywords = keywords;
    }

    @Override
    protected HashMap<String, HashMap<String, Long>> compute() {
        if (textFile.isDirectory()) {
            List<Search> subTasks = new ArrayList<>();
            var subFiles = textFile.listFiles();
            assert subFiles != null;

            for (var subFile : subFiles) {
                var subTask = new Search(subFile, keywords);
                subTasks.add(subTask);
                subTask.fork();
            }

            for (var subTask : subTasks) {
                this.resultFiles.putAll(subTask.join());
            }
        } else {
            try {
                processTextFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.resultFiles;
    }

    private List<String> getWords() throws IOException {
        List<String> words = new ArrayList<>();
        String currentLine;
        var bufferedReader = new BufferedReader(new FileReader(textFile));
        while ((currentLine = bufferedReader.readLine()) != null) {
            var tokens = currentLine.split("[\\s,:;.?!]+");
            for (var token : tokens) {
                if (token.matches("\\p{L}[\\p{L}-']*")) {
                    words.add(token);
                }
            }
        }
        return words;
    }

    private void processTextFile() throws IOException {
        HashMap<String, Long> keywords = new HashMap<>();
        var words = this.getWords();

        for (var word : words) {
            if (this.keywords.contains(word.toLowerCase())) {
                if (keywords.containsKey(word.toLowerCase())) {
                    keywords.put(word.toLowerCase(), keywords.get(word.toLowerCase()) + 1);
                } else {
                    keywords.put(word.toLowerCase(), 1L);
                }
            }
        }

        if (!keywords.isEmpty()) {
            resultFiles.put(textFile.getName(), keywords);
        }
    }
}
