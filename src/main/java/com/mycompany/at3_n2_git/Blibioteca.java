package com.mycompany.at3_n2_git;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class Biblioteca {
    private static final String FILE_PATH =  System.getProperty("user.dir") + "/src/main/java/org/PcdProject/biblioteca.json";
    private List<Livro> livros;
    private final Gson gson;

    public Biblioteca() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.livros = new ArrayList<>();
        carregarLivros();
    }

    private void carregarLivros() {
        System.out.println("Diretório de execução: " + System.getProperty("user.dir"));
        System.out.println(FILE_PATH);
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type livroListType = new TypeToken<ArrayList<Livro>>() {}.getType();
                this.livros = gson.fromJson(reader, livroListType);
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        } else {
            System.err.println("Arquivo " + FILE_PATH + " não encontrado.");
        }
    }

    private void salvarLivros() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(livros, writer);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public boolean cadastrarLivro(Livro livro) {
        for (Livro l : livros) {
            if (l.getTitulo().equalsIgnoreCase(livro.getTitulo())) {
                return false; // Livro já existe
            }
        }
        livros.add(livro);
        salvarLivros();
        return true;
    }

    public boolean alugarLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getNumeroExemplares() > 0) {
                livro.setNumeroExemplares(livro.getNumeroExemplares() - 1);
                salvarLivros();
                return true;
            }
        }
        return false;
    }

    public boolean devolverLivro(String titulo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livro.setNumeroExemplares(livro.getNumeroExemplares() + 1);
                salvarLivros();
                return true;
            }
        }
        return false;
    }
}
