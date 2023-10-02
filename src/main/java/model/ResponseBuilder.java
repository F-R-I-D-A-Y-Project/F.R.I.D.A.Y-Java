package model;
import model.SynonymManager;

import java.io.IOException;

public class ResponseBuilder {
    private SynonymManager synonymManager;
    public ResponseBuilder(String pathToDataSet) throws IOException {
        try{
            this.synonymManager = new SynonymManager("archive/synonyms.csv");
        } catch(IOException e){
            System.out.println("Could not read dataset file");
            throw e;
        }
    }

    public String get(String input){
        return "";
    }
}
