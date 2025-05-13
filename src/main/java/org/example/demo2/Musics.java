package org.example.projetomusicap;

import java.io.Serializable;

public class Musics implements Serializable {
    private String nome;
    private String artista;
    private String genero;
    private int ano;

    public Musics(String nome, String artista, String genero, int ano){
        this.nome = nome;
        this.artista = artista;
        this.genero = genero;
        this.ano = ano;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getArtista(){
        return this.artista;
    }

    public void setArtista(String artista){
        this.artista = artista;
    }

    public String getGenero(){
        return this.genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public int getAno(){
        return this.ano;
    }

    public void setAno(int ano){
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\nArtista: " + artista + "\nGênero: " + genero + "\nAno: " + ano;
    }

}