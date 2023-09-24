package main;

import chat.Chat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
        try{
            Chat chat = new Chat("opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin");
        } catch(IOException e){
            System.out.println("terminating");
        }
    }
}