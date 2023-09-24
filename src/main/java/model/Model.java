package model;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Creates a NLP model to be used in the chatbot.
 */
public class Model {

    private InputStream modelIn;
    private SentenceModel mod;
    private final SentenceDetectorME detector;

    /**
     * A HashMap containing the responses to the user's input.
     */
    private final HashMap<String, String> responses = new HashMap<String, String>();

    /**
     * Creates a new model.
     * @param pathToBin String
     *                  The path to the auxiliary binary file.
     */
    public Model(String pathToBin) throws IOException {

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
     * Adds all responses to the HashMap.
     */
    private void addResponses(){
        responses.put("Hello", "Hello, how are you?");
        responses.put("How are you?", "I'm fine, thank you.");
        responses.put("What is your name?", "My name is F.R.I.D.A.Y.");

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
            System.out.println(sentence.trim());
            String response = responses.get(sentence.trim());
            System.out.println(response);
            if (response != null) {
                responseBuilder.append(response).append(". ");
            }
        }

        return responseBuilder.toString().trim();
    }
}
