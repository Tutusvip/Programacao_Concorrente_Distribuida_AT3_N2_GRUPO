package com.mycompany.at3_n2_git;

public class Main {
    public static void main(String[] args) {
        Thread servidorThread = new Thread(() -> {
            ServidorSocketBiblioteca servidor = new ServidorSocketBiblioteca();
            servidor.start();
        });
        servidorThread.start();
        // esperar o servidor iniciar
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }

        if(servidorThread.isAlive()){
            ClienteSocketBiblioteca cliente = new ClienteSocketBiblioteca();
            cliente.executarCliente();
        }
    }
}

