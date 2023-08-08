package main;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class Main {
    public static void main(String[] args) {
        INDArray Something = Nd4j.zeros(3,5);
        System.out.println(Something);
    }
}