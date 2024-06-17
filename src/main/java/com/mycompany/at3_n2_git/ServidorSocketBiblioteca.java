package com.mycompany.at3_n2_git;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServidorSocketBiblioteca {
    private static final int PORT = 12345;



    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();

            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServidorSocketBiblioteca().start();
    }

    private static class ClientHandler extends Thread {
        
    }
}
