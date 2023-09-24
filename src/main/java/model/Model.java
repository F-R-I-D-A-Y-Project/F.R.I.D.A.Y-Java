package model;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Creates a NLP model to be used in the chatbot.
 */
public class Model {

    private InputStream modelIn;
    private SentenceModel mod;
    private final SentenceDetectorME detector;

    /**
     * Object responsible for formulating the response to a prompt.
     */
    private final DataSetManager dataSetManager;

    /**
     * Creates a new model.
     * @param pathToBin String
     *                  The path to the auxiliary binary file.
     */
    public Model(String pathToBin, String pathToDataSet) throws IOException {
        dataSetManager = new DataSetManager(pathToDataSet);
        try{
            modelIn = new FileInputStream(pathToBin);
        } catch(IOException e){
            System.out.println("File not found");
            throw e;
        }
        try{
            this.mod = new SentenceModel(modelIn);
        } catch(IOException e){
            System.out.println("Could not initialize model");
        } finally {
            if (null != modelIn) {
                try {
                    modelIn.close();
                }
                catch (IOException e) {
                    System.out.println("Could not close Input Stream");
                }
            }
        }
        detector = new SentenceDetectorME(mod);
    }

    /**
     * Detect sentences in the input string.
     * @param input String
     *              The string to be sentence detected.
     * @return String
     *             The String containing individual sentences as elements.
     */
    public String answerTo(String input) {
        String[] sentences = detector.sentDetect(input);
        StringBuilder responseBuilder = new StringBuilder();
        for (String sentence : sentences) {
            String response = dataSetManager.get(sentence);
            if (response != null) {
                if(response.endsWith(".") || response.endsWith("?") || response.endsWith("!") || response.endsWith(" ")){
                    responseBuilder.append(response).append(" ");
                } else {
                    responseBuilder.append(response).append(".");
                }
                responseBuilder.append("\n");
            }
        }
        return responseBuilder.toString().trim();
    }
}
