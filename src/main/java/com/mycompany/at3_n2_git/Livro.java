package com.mycompany.at3_n2_git;

import com.google.gson.annotations.SerializedName;

public class Livro {
    @SerializedName("titulo")
    private String titulo;

    @SerializedName("autor")
    private String autor;

    @SerializedName("genero")
    private String genero;

    @SerializedName("numero_exemplares")
    private int numeroExemplares;

    public Livro(String titulo, String autor, String genero, int numeroExemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.numeroExemplares = numeroExemplares;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getNumeroExemplares() {
        return numeroExemplares;
    }

    public void setNumeroExemplares(int numeroExemplares) {
        this.numeroExemplares = numeroExemplares;
    }

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
