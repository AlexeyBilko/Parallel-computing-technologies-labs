package lab4.code.static_analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StaticAnalyzerRegular {
    final ArrayDeque<File> filesDeque = new ArrayDeque<>();

    private final HashMap<Integer, Long> map = new HashMap<>();

    public StaticAnalyzerRegular(File textFile) {
        filesDeque.add(textFile);
    }

    public void compute() {
        File currentFile;

        while ((currentFile = filesDeque.poll()) != null) {
            if (currentFile.isDirectory()) {
                var subFiles = currentFile.listFiles();
                assert subFiles != null;
                filesDeque.addAll(Arrays.asList(subFiles));
            } else {
                try {
                    var subSample = this.processTextFile(currentFile);
                    this.extendSample(subSample);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
        HashMap<Integer, Double> distributionLaw = this.getDistributionLaw();

        double mean = 0;
        for (var entry : distributionLaw.entrySet()) {
            mean += entry.getKey() * entry.getValue();
        }

        return mean;
    }

    public double getVariance() {
        HashMap<Integer, Double> distributionLaw = this.getDistributionLaw();
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

    private List<String> getWords(File textFile) throws IOException {
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

    private HashMap<Integer, Long> processTextFile(File textFile) throws IOException {
        HashMap<Integer, Long> subSample = new HashMap<>();
        var words = this.getWords(textFile);
        for (var word : words) {
            if (subSample.containsKey(word.length())) {
                subSample.put(word.length(), subSample.get(word.length()) + 1);
            } else {
                subSample.put(word.length(), 1L);
            }
        }
        return subSample;
    }
}
