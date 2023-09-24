package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Manages the NLP dataset and returns to the response to the user's input.
 */
public class DataSetManager {

    private final String pathToDataSet;

    public DataSetManager(String pathToDataSet) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToDataSet))) {
            this.pathToDataSet = pathToDataSet;
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
            throw e;
        }
    }

    /**
     * Returns the response to the user's input.
     * @param input String
     *              The user's input.
     * @return String
     *              The response to the user's input.
     */
    public String get(String input){
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToDataSet))) {
            String line;
            while((line = reader.readLine()) != null){
                if(line.contains(input)){
                    return line.substring(input.length() + 1, line.length() - 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
        }
        return "";
    }
}
