package model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;


/**
 * Manages the NLP dataset and returns to the response to the user's input.
 */
public class SynonymManager {

    private final String pathToDataSet;

    public SynonymManager(String pathToDataSet) throws IOException{
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
    private Stream<String> getSynonyms(String input){
        try (CSVReader reader = new CSVReader(new FileReader(pathToDataSet))) {
            String[] line;
            while((line = reader.readNext()) != null){
                if(line[0].equals(input)){
                    String[] synonyms = line[2].split(";");
                    System.out.println(Arrays.toString(synonyms));
                    return Arrays.stream(synonyms).filter(s -> s.split(" ").length == 1).sorted();
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read dataset file");
        } catch (CsvValidationException e){
            System.out.println("I dunno");
        }
        return Stream.empty();
    }

    public String getKeyWordSynonym(String input){
        if(input.compareTo(getSynonyms(input).findFirst().orElse("")) < 0){
            return input;
        } else{
            return getSynonyms(input).findFirst().orElse("");
        }
    }
}
