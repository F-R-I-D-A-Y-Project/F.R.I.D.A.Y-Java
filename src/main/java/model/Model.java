package model;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Model {
    /**
     * Creates a NLP model.
     */
    private InputStream modelIn;
    private SentenceModel mod;
    private final SentenceDetectorME detector;
    public Model(String pathToBin) throws IOException {
        /**
         * Creates a new model.
         * @param pathToBin String
         *                  The path to the auxiliary binary file.
         */
        try{
            modelIn = new FileInputStream(pathToBin);
        } catch(IOException e){
            System.out.println("File not found");
            throw e;
        }
        try{
            this.mod = new SentenceModel(modelIn);
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            if (null != modelIn) {
                try {
                    modelIn.close();
                }
                catch (IOException e) {

                }
            }
        }
        detector = new SentenceDetectorME(mod);
    }

    public String answerTo(String input) {
        /**
         * Detect sentences in the input string.
         * @param input String
         *              The string to be sentence detected.
         * @return String
         *             The String containing individual sentences as elements.
         */
        String[] sentences = detector.sentDetect(input);

        if (sentences.length > 0) {
            return sentences[0];
        } else {
            return "No sentences found in the input.";
        }
    }
}
