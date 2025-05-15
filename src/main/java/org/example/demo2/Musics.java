package org.example.demo2;

import java.io.Serializable;

public class Musics implements Serializable {
    private String musica;
    private String artista;
    private String genero;
    private int ano;

    public Musics(String musica, String artista, String genero, int ano){
        this.musica = musica;
        this.artista = artista;
        this.genero = genero;
        this.ano = ano;
    }

    public String getMusica(){ return musica; }
    public void setMusica(String nome){ this.musica = nome; }

    public String getArtista(){ return artista; }
    public void setArtista(String artista){ this.artista = artista; }

    public String getGenero(){ return genero; }
    public void setGenero(String genero){ this.genero = genero; }

    public int getAno(){ return ano; }
    public void setAno(int ano){ this.ano = ano; }

    @Override
    public String toString() {
        return "Música: " + musica + "\nArtista: " + artista + "\nGênero: " + genero + "\nAno: " + ano;
    }
}
