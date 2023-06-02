package lab4.code.static_analysis;

import java.io.*;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class StaticAnalyzer extends RecursiveTask<HashMap<Integer, Long>> {
    final File textFile;
    private final HashMap<Integer, Long> map = new HashMap<>();

    public StaticAnalyzer(File textFile) {
        this.textFile = textFile;
    }

    @Override
    protected HashMap<Integer, Long> compute() {
        if (textFile.isDirectory()) {
            List<StaticAnalyzer> subTasks = new ArrayList<>();
            var subFiles = textFile.listFiles();
            assert subFiles != null;
            for (var subFile : subFiles) {
                var subTask = new StaticAnalyzer(subFile);
                subTasks.add(subTask);
                subTask.fork();
            }
            for (var subTask : subTasks) {
                extendSample(subTask.join());
            }
        } else {
            try {
                processTextFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.map;
    }

    public HashMap<Integer, Double> getDistributionLaw() {
        HashMap<Integer, Double> distributionLaw = new HashMap<>();
        double sum = 0;
        for (var entry : map.entrySet()) {
            sum += entry.getValue();
        }
        for (var entry : map.entrySet()) {
            double probability = entry.getValue() / sum;
            distributionLaw.put(entry.getKey(), probability);
        }
        return distributionLaw;
    }

    public double getMean() {
        var distributionLaw = this.getDistributionLaw();
        double mean = 0;
        for (var entry : distributionLaw.entrySet()) {
            mean += entry.getKey() * entry.getValue();
        }
        return mean;
    }

    public double getVariance() {
        var distributionLaw = this.getDistributionLaw();
        double variance = 0;
        for (var entry : distributionLaw.entrySet()) {
            variance += Math.pow(entry.getKey(), 2) * entry.getValue();
        }
        variance -= Math.pow(this.getMean(), 2);
        return variance;
    }

    public double getStandardDeviation() {
        return Math.sqrt(this.getVariance());
    }

    private void extendSample(HashMap<Integer, Long> extensionSample) {
        for (var entry : extensionSample.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + value);
            } else {
                map.put(key, value);
            }
        }
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
        var words = this.getWords();
        for (var word : words) {
            if (map.containsKey(word.length())) {
                map.put(word.length(), map.get(word.length()) + 1);
            } else {
                map.put(word.length(), 1L);
            }
        }
    }
}
