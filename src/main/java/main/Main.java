package main;

import chat.Chat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            Chat chat = new Chat();
        } catch(IOException e){
            System.out.println("terminating");
        }
    }
}