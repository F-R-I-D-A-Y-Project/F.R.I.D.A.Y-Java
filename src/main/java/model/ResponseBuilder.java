package model;

import java.io.*;
import java.util.*;

/**
 * Response Generator of NLP Model
 */
public class ResponseBuilder {
    /**
     * Dictionary storing all existing words in database
     */
    private Set<String> dictionary;
    /**
     * Flag that informs if model was already trained
     */
    private boolean isTrained = false;
    /**
     * String Array containing all questions stored in database
     */
    private String[] databaseTexts;
    /**
     * Path to database
     */
    private String pathToDataSet;

    /**
     * ResponseBuilder constructor. Stores the dataset path
     * @param pathToDataSet
     * @throws IOException
     */
    public ResponseBuilder(String pathToDataSet) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToDataSet))) {
            this.pathToDataSet = pathToDataSet;
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
            throw e;
        }
    }

    /**
     * populates dictionary attribute, training model to be used
     */
    private void populateDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToDataSet))) {
            ArrayList<String> databaseTexts = new ArrayList<>();
            Set<String> dictionary = new HashSet<>();

            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                String question = line.split(";")[0];
                databaseTexts.add(question);
            }

            for (String databaseText : databaseTexts) {
                String[] words = preprocessText(databaseText);
                dictionary.addAll(Arrays.asList(words));
            }
            this.dictionary = dictionary;
            this.databaseTexts = databaseTexts.toArray(new String[10000]);
            isTrained = true;
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
        }
    }

    /**
     * predicted answer to the question prompted by user. Uses the Similarity method to predict the closest
     * question to the one prompted by user
     * @param input
     * @return
     */
    public String get(String input) {
        if (!isTrained) {
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

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToDataSet))) {
            String line;
            while((line = reader.readLine()) != null){
                String[] splitLine = line.split(";");
                if(splitLine[0].equals(databaseTexts[mostSimilarIndex])){
                    return splitLine[1];
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
        }
        return "";
    }

    /**
     * Preprocesses questions stored in database
     * @param text
     * @return
     */
    private String[] preprocessText(String text) {
        try {
            text = text.toLowerCase().replaceAll("[^a-zA-Z0-9]", " ").trim();
            return (text.split("\\s+"));
        } catch (NullPointerException e){
            return new String[0];
        }
    }

    /**
     * returns the index of a question in the database
     * @param s
     * @param st
     * @return
     */
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

    /**
     * calculates the similarity between two vectors
     * @param vectorA
     * @param vectorB
     * @return
     */
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