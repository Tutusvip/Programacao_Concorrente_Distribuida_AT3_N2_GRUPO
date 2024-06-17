package com.mycompany.at3_n2_git;

import com.google.gson.annotations.SerializedName;

public class Livro {
    // Atributos da classe Livro com anotação para serialização com Gson
    @SerializedName("titulo")
    private String titulo;

    @SerializedName("autor")
    private String autor;

    @SerializedName("genero")
    private String genero;

    @SerializedName("numero_exemplares")
    private int numeroExemplares;

    // Construtor da classe Livro para inicializar os atributos
    public Livro(String titulo, String autor, String genero, int numeroExemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.numeroExemplares = numeroExemplares;
    }

    // Método getter para obter o título do livro
    public String getTitulo() {
        return titulo;
    }

    // Método getter para obter o número de exemplares do livro
    public int getNumeroExemplares() {
        return numeroExemplares;
    }

    // Método setter para definir o número de exemplares do livro
    public void setNumeroExemplares(int numeroExemplares) {
        this.numeroExemplares = numeroExemplares;
    }

    // Método toString para representar o objeto Livro como uma string
    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                ", numeroExemplares=" + numeroExemplares +
                '}';
    }
}
