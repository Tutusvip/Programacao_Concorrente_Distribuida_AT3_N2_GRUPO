package com.mycompany.at3_n2_git;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSocketBiblioteca {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public void executarCliente() {
        try (
                Socket socket = new Socket(HOST, PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
        ) {
            String userInput;
            while (true) {
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
                userInput = scanner.nextLine();


                if ("SAIR".equalsIgnoreCase(userInput)) {
                    break;
                }

                switch (userInput.toUpperCase()) {
                    case "LISTAR":
                        out.println("LISTAR");
                        System.out.println("Livros disponíveis:");
                        String listaLivros = in.readLine();
                        if (!listaLivros.isEmpty()) {
                            String[] livrosArray = listaLivros.split("\\},\\{");
                            System.out.println("--------------------------------------------------------------------------------------------------");
                            System.out.printf("%-25s | %-20s | %-20s | %s%n", "Título", "Autor", "Gênero", "Número de Exemplares");
                            System.out.println("--------------------------------------------------------------------------------------------------");
                            for (String livro : livrosArray) {
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
                        System.out.println("Para cadastrar um novo livro, por favor, forneça as seguintes informações:");
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();

                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();

                        System.out.print("Gênero: ");
                        String genero = scanner.nextLine();

                        System.out.print("Número de exemplares: ");
                        int numeroExemplares = Integer.parseInt(scanner.nextLine());

                        Livro novoLivro = new Livro(titulo, autor, genero, numeroExemplares);
                        out.println("CADASTRAR:" + new Gson().toJson(novoLivro));
                        System.out.println(in.readLine());
                        break;
                    case "ALUGAR":
                        System.out.println("Digite o título do livro para alugar:");
                        String tituloAlugar = scanner.nextLine();
                        out.println("ALUGAR:" + tituloAlugar);
                        System.out.println(in.readLine());
                        break;
                    case "DEVOLVER":
                        System.out.println("Digite o título do livro para devolver:");
                        String tituloDevolver = scanner.nextLine();
                        out.println("DEVOLVER:" + tituloDevolver);
                        System.out.println(in.readLine());
                        break;
                    default:
                        System.out.println("Comando desconhecido.");
                        break;
                }
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
