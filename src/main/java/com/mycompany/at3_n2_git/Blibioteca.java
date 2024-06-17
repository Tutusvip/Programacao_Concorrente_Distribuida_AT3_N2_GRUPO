package com.mycompany.at3_n2_git;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class Biblioteca {
    // Caminho para o arquivo JSON que armazena os dados da biblioteca
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/java/org/PcdProject/biblioteca.json";
    private List<Livro> livros; // Lista de livros na biblioteca
    private final Gson gson; // Instância de Gson para conversão entre JSON e objetos Java

    // Construtor da classe Biblioteca
    public Biblioteca() {
        this.gson = new GsonBuilder().setPrettyPrinting().create(); // Inicializa o Gson com formatação bonita
        this.livros = new ArrayList<>(); // Inicializa a lista de livros
        carregarLivros(); // Carrega os livros do arquivo JSON
    }

    // Método para carregar os livros do arquivo JSON
    private void carregarLivros() {
        System.out.println("Diretório de execução: " + System.getProperty("user.dir"));
        System.out.println(FILE_PATH);
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type livroListType = new TypeToken<ArrayList<Livro>>() {}.getType(); // Define o tipo para deserialização
                this.livros = gson.fromJson(reader, livroListType); // Carrega os livros do arquivo JSON
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        } else {
            System.err.println("Arquivo " + FILE_PATH + " não encontrado.");
        }
    }

    // Método para salvar os livros no arquivo JSON
    private void salvarLivros() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(livros, writer); // Converte a lista de livros para JSON e salva no arquivo
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // Método para listar todos os livros
    public List<Livro> listarLivros() {
        return livros;
    }

    // Método para cadastrar um novo livro
    public boolean cadastrarLivro(Livro livro) {
        for (Livro l : livros) {
            if (l.getTitulo().equalsIgnoreCase(livro.getTitulo())) {
                return false; // Livro já existe
            }
        }
        livros.add(livro); // Adiciona o novo livro à lista
        salvarLivros(); // Salva a lista atualizada no arquivo JSON
        return true;
    }

    // Método para alugar um livro
    public boolean alugarLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getNumeroExemplares() > 0) {
                livro.setNumeroExemplares(livro.getNumeroExemplares() - 1); // Reduz o número de exemplares
                salvarLivros(); // Salva a lista atualizada no arquivo JSON
                return true;
            }
        }
        return false;
    }

    // Método para devolver um livro
    public boolean devolverLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livro.setNumeroExemplares(livro.getNumeroExemplares() + 1); // Aumenta o número de exemplares
                salvarLivros(); // Salva a lista atualizada no arquivo JSON
                return true;
            }
        }
        return false;
    }
}
