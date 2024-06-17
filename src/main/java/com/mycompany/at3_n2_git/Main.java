package com.mycompany.at3_n2_git;

public class Main {
    public static void main(String[] args) {
        // Cria uma nova thread para executar o servidor
        Thread servidorThread = new Thread(() -> {
            ServidorSocketBiblioteca servidor = new ServidorSocketBiblioteca();
            servidor.start(); // Inicia o servidor
        });
        servidorThread.start(); // Inicia a execução da thread do servidor

        // Espera um tempo para garantir que o servidor tenha tempo para iniciar
        try {
            Thread.sleep(1000); // Pausa a execução por 1000 milissegundos (1 segundo)
        } catch (InterruptedException e) {
            e.fillInStackTrace(); // Preenche o stack trace caso ocorra uma interrupção
        }

        // Verifica se a thread do servidor ainda está ativa
        if (servidorThread.isAlive()) {
            // Se o servidor estiver ativo, cria um cliente e executa suas operações
            ClienteSocketBiblioteca cliente = new ClienteSocketBiblioteca();
            cliente.executarCliente(); // Executa o cliente
        }
    }
}
