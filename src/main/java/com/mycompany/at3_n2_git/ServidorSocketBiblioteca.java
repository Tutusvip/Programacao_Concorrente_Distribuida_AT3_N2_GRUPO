package com.mycompany.at3_n2_git;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServidorSocketBiblioteca {
    private static final int PORT = 12345; // Porta em que o servidor irá escutar
    private final Biblioteca biblioteca; // Instância da biblioteca que gerencia os livros

    // Construtor da classe que inicializa a biblioteca
    public ServidorSocketBiblioteca() {
        this.biblioteca = new Biblioteca();
    }

    // Método para iniciar o servidor
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // Cria um ServerSocket na porta especificada
            System.out.println("Servidor iniciado na porta " + PORT);

            // Loop infinito para aceitar conexões de clientes
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Aguarda a conexão de um cliente
                new ClientHandler(clientSocket, biblioteca).start(); // Cria e inicia uma nova thread para lidar com o cliente
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // Método principal para iniciar o servidor
    public static void main(String[] args) {
        new ServidorSocketBiblioteca().start(); // Cria uma instância do servidor e o inicia
    }

    // Classe interna para lidar com as conexões dos clientes
    private static class ClientHandler extends Thread {
        private final Socket clientSocket; // Socket do cliente
        private final Biblioteca biblioteca; // Instância da biblioteca compartilhada

        // Construtor que inicializa o socket do cliente e a biblioteca
        public ClientHandler(Socket socket, Biblioteca biblioteca) {
            this.clientSocket = socket;
            this.biblioteca = biblioteca;
        }

        @Override
        public void run() {
            try (
                    // Cria buffers de leitura e escrita para comunicação com o cliente
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String request; // Variável para armazenar a requisição do cliente
                while ((request = in.readLine()) != null) { // Lê a requisição do cliente
                    String[] parts = request.split(":", 2); // Divide a requisição em comando e parâmetros
                    String command = parts[0]; // Obtém o comando

                    // Executa a ação baseada no comando recebido
                    switch (command) {
                        case "LISTAR":
                            List<Livro> livros = biblioteca.listarLivros(); // Obtém a lista de livros da biblioteca
                            out.println(new Gson().toJson(livros)); // Converte a lista de livros para JSON e envia para o cliente
                            break;

                        case "CADASTRAR":
                            Livro novoLivro = new Gson().fromJson(parts[1], Livro.class); // Converte o JSON recebido para um objeto Livro
                            if (biblioteca.cadastrarLivro(novoLivro)) {
                                out.println("Livro cadastrado com sucesso."); // Informa que o livro foi cadastrado com sucesso
                            } else {
                                out.println("Erro ao cadastrar o livro. Livro já existe."); // Informa que o livro já existe
                            }
                            break;

                        case "ALUGAR":
                            String tituloAlugar = parts[1]; // Obtém o título do livro a ser alugado
                            if (biblioteca.alugarLivro(tituloAlugar)) {
                                out.println("Livro alugado com sucesso."); // Informa que o livro foi alugado com sucesso
                            } else {
                                out.println("Erro ao alugar o livro. Livro não disponível."); // Informa que o livro não está disponível
                            }
                            break;

                        case "DEVOLVER":
                            String tituloDevolver = parts[1]; // Obtém o título do livro a ser devolvido
                            if (biblioteca.devolverLivro(tituloDevolver)) {
                                out.println("Livro devolvido com sucesso."); // Informa que o livro foi devolvido com sucesso
                            } else {
                                out.println("Erro ao devolver o livro. Livro não encontrado."); // Informa que o livro não foi encontrado
                            }
                            break;

                        default:
                            out.println("Comando desconhecido."); // Informa que o comando não é reconhecido
                            break;
                    }
                }
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }
    }
}
