package model;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Model {
    private final InputStream modelIn;
    private SentenceModel mod;
    private final SentenceDetectorME detector;
    public Model(String pathToBin) throws IOException {
         modelIn = new FileInputStream(pathToBin);
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
        return "Huh?";
    }
}
