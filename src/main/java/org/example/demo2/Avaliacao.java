package org.example.demo2;

import java.io.Serializable;

public class Avaliacao extends Musics implements Serializable {
    private  int nota;
    private String comentario;
    private boolean escuta;

    public Avaliacao(String nome, String artista, String genero, int ano){
        super(nome, artista, genero, ano);
        this.nota = 0;
        this.comentario = null;
        this.escuta = false;
    }

    public int getNota(){
        return this.nota;
    }

    public void setNota(int nota){
        this.nota = nota;
    }

    public String getComentario(){
        return this.comentario;
    }

    public void setComentario(String comentario){
        this.comentario = comentario;
    }

    public boolean getEscuta(){
        return this.escuta;
    }

    public void setEscuta(boolean escuta){
        this.escuta = escuta;
    }

    public String avaliar() {
        return "Musica: " + getNome() + "Artista: "+ getArtista() + "Gênero: "+ getGenero() + "Ano: " + getAno() + "nota: " + getNota() + "Comentário: " + getComentario();
    }
}
