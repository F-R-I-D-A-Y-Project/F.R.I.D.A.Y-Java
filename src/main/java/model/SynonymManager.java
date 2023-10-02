package model;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;


/**
 * Manages the NLP dataset and returns to the response to the user's input.
 */
public class SynonymManager {

    private final String pathToDataSet;
    private final HashMap<String,String> map = new HashMap<>();

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
                    return Arrays.stream(synonyms).filter(s -> s.split("[ |]").length == 1).sorted();
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
        if(map.containsKey(input)){
            return map.get(input);
        }
        String aux = getSynonyms(input).findFirst().orElse("");
        if(input.compareTo(aux) < 0){
            map.put(aux,input);
            return input;
        } else{
            map.put(input,aux);
            return aux;
        }
    }
}
