package com.mycompany.at3_n2_git;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSocketBiblioteca {
    // Constantes para o endereço e porta do servidor
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public void executarCliente() {
        // Tenta conectar ao servidor e gerenciar comunicação I/O
        try (
                Socket socket = new Socket(HOST, PORT); // Cria um socket conectado ao servidor
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Buffer para leitura de dados do servidor
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Writer para enviar dados ao servidor
                Scanner scanner = new Scanner(System.in) // Scanner para entrada do usuário
        ) {
            String userInput;
            while (true) {
                // Menu de opções para o usuário
                System.out.println("Escolha uma opção:");
                System.out.println("+----------------------------------------+");
                System.out.println("| Opção    | Descrição                    |");
                System.out.println("+----------------------------------------+");
                System.out.println("| LISTAR   | Listar todos os livros       |");
                System.out.println("| CADASTRAR| Cadastrar um novo livro      |");
                System.out.println("| ALUGAR   | Alugar um livro              |");
                System.out.println("| DEVOLVER | Devolver um livro alugado    |");
                System.out.println("| SAIR     | Encerrar o programa          |");
                System.out.println("+----------------------------------------+");

                System.out.print("Opção: ");
                userInput = scanner.nextLine(); // Lê a opção do usuário

                // Se a opção for "SAIR", termina o loop
                if ("SAIR".equalsIgnoreCase(userInput)) {
                    break;
                }

                // Trata as diferentes opções do menu
                switch (userInput.toUpperCase()) {
                    case "LISTAR":
                        // Envia comando "LISTAR" ao servidor
                        out.println("LISTAR");
                        System.out.println("Livros disponíveis:");
                        String listaLivros = in.readLine(); // Lê a resposta do servidor
                        if (!listaLivros.isEmpty()) {
                            String[] livrosArray = listaLivros.split("\\},\\{"); // Divide a resposta em livros individuais
                            System.out.println("--------------------------------------------------------------------------------------------------");
                            System.out.printf("%-25s | %-20s | %-20s | %s%n", "Título", "Autor", "Gênero", "Número de Exemplares");
                            System.out.println("--------------------------------------------------------------------------------------------------");
                            for (String livro : livrosArray) {
                                // Formata a resposta e exibe os livros
                                livro = livro.replace("{", "").replace("}", "").replace("\"", "");
                                String[] camposLivro = livro.split(",");
                                System.out.printf("%-25s | %-20s | %-20s | %s%n", camposLivro[0].split(":")[1], camposLivro[1].split(":")[1], camposLivro[2].split(":")[1], camposLivro[3].split(":")[1]);
                            }
                            System.out.println("--------------------------------------------------------------------------------------------------");
                        } else {
                            System.out.println("Nenhum livro disponível.");
                        }
                        break;
                    case "CADASTRAR":
                        // Coleta informações do novo livro do usuário
                        System.out.println("Para cadastrar um novo livro, por favor, forneça as seguintes informações:");
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();

                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();

                        System.out.print("Gênero: ");
                        String genero = scanner.nextLine();

                        System.out.print("Número de exemplares: ");
                        int numeroExemplares = Integer.parseInt(scanner.nextLine());

                        // Cria um novo objeto Livro e envia ao servidor
                        Livro novoLivro = new Livro(titulo, autor, genero, numeroExemplares);
                        out.println("CADASTRAR:" + new Gson().toJson(novoLivro));
                        System.out.println(in.readLine()); // Lê a resposta do servidor
                        break;
                    case "ALUGAR":
                        // Coleta o título do livro para alugar
                        System.out.println("Digite o título do livro para alugar:");
                        String tituloAlugar = scanner.nextLine();
                        out.println("ALUGAR:" + tituloAlugar); // Envia comando ao servidor
                        System.out.println(in.readLine()); // Lê a resposta do servidor
                        break;
                    case "DEVOLVER":
                        // Coleta o título do livro para devolver
                        System.out.println("Digite o título do livro para devolver:");
                        String tituloDevolver = scanner.nextLine();
                        out.println("DEVOLVER:" + tituloDevolver); // Envia comando ao servidor
                        System.out.println(in.readLine()); // Lê a resposta do servidor
                        break;
                    default:
                        // Trata comandos desconhecidos
                        System.out.println("Comando desconhecido.");
                        break;
                }
            }
        } catch (IOException e) {
            e.fillInStackTrace(); // Trata exceções de I/O
        }
    }
}
