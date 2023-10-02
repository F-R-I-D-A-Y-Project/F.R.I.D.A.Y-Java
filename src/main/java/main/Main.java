package main;

import chat.Chat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            Chat chat = new Chat("opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin", "dataBase.csv");
        } catch(IOException e){
            System.out.println("terminating");
        }
    }
}