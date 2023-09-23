package main;

import chat.Chat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            Chat chat = new Chat("../../../src/main/java/main/opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin");
        } catch(IOException e){
            System.out.println("terminating");
        }
    }
}