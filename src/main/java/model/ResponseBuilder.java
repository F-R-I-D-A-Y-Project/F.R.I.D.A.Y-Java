package model;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.SynonymManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResponseBuilder {
    private SynonymManager synonymManager;
    private Set<String> dictionary;
    private boolean isPopulated = false;
    private String[] databaseTexts;
    private String pathToDataSet;

    public ResponseBuilder(String pathToDataSet) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToDataSet))) {
            this.pathToDataSet = pathToDataSet;
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
            throw e;
        }

        try {
            this.synonymManager = new SynonymManager("archive/synonyms.csv");
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
            throw e;
        }
    }

    private void populateDictionary() {
        try (CSVReader reader = new CSVReader(new FileReader(pathToDataSet))) {
            ArrayList<String> databaseTexts = new ArrayList<>();
            Set<String> dictionary = new HashSet<>();

            String[] line;
            while((line = reader.readNext()) != null){
                databaseTexts.add(line[0]);
            }

            for (String databaseText : databaseTexts) {
                String[] words = preprocessText(databaseText);
                dictionary.addAll(Arrays.asList(words));
            }

            this.dictionary = dictionary;
            this.databaseTexts = databaseTexts.toArray(new String[10000]);
            isPopulated = true;
            System.out.println(dictionary);
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
        } catch (CsvValidationException e){
            System.out.println("I dunno");
        }
    }

    public String get(String input) {
        if (!isPopulated) {
            populateDictionary();
        }


        double[][] featureVectors = new double[databaseTexts.length][dictionary.size()];
        for (int i = 0; i < databaseTexts.length; i++) {
            String[] words = preprocessText(databaseTexts[i]);
            for (String word : words) {
                int wordIndex = getWordIndex(word, dictionary);
                featureVectors[i][wordIndex]++;
            }
        }
        String[] inputWords = preprocessText(input);
        double[] inputFeatureVector = new double[dictionary.size()];
        for (String word : inputWords) {
            int wordIndex = getWordIndex(word, dictionary);
            if (wordIndex != -1) {
                inputFeatureVector[wordIndex]++;
            }
        }

        double maxSimilarity = -1.0;
        int mostSimilarIndex = -1;

        for (int i = 0; i < featureVectors.length; i++) {
            double similarity = calculateCosineSimilarity(inputFeatureVector, featureVectors[i]);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                mostSimilarIndex = i;
            }
        }

        try (CSVReader reader = new CSVReader(new FileReader(pathToDataSet))) {
            String[] line;
            while((line = reader.readNext()) != null){
                if(line[0].equals(databaseTexts[mostSimilarIndex])){
                    return line[1];
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
        } catch (CsvValidationException e){
            System.out.println("I dunno");
        }
//        System.out.println("Frase mais similar: " + databaseTexts[mostSimilarIndex]);
//        System.out.println("Similaridade de Cosseno: " + maxSimilarity);
        return "";
    }

    private String[] preprocessText(String text) {
        text = text.toLowerCase().replaceAll("[^a-zA-Z0-9]", " ").trim();
        return text.split("\\s+");
    }

    private int getWordIndex(String s, Set<String> st) {
        int index = 0;
        for (String dictWord : st) {
            if (dictWord.equals(s)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private double calculateCosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += vectorA[i] * vectorA[i];
            normB += vectorB[i] * vectorB[i];
        }
        //divisão por zero
        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}