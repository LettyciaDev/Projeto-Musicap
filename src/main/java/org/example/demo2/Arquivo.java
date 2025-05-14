package org.example.demo2;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Arquivo {
    private static final String FILE_NAME = "musicas.dat";
    private static final String FILE_AVL = "avaliacoes.dat";

    // Músicas
    public static void salvar(List<Musics> musicas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(musicas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Musics> ler() {
        List<Musics> musicas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            musicas = (List<Musics>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return musicas;
    }

    public static void atualizar(List<Musics> musicas) {
        salvar(musicas);
    }

    public static void excluir(List<Musics> musicas, String nome) {
        musicas.removeIf(m -> m.getNome().equals(nome));
        salvar(musicas);
    }

    // Avaliações
    public static List<Avaliacao> converterParaAvaliacoes(List<Musics> musicas) {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        for (Musics m : musicas) {
            avaliacoes.add(new Avaliacao(m.getNome(), m.getArtista(), m.getGenero(), m.getAno()));
        }
        return avaliacoes;
    }

    public static void salvarAvaliacoes(List<Avaliacao> avaliacoes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_AVL))) {
            oos.writeObject(avaliacoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Avaliacao> lerAvaliacoes() {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_AVL))) {
            avaliacoes = (List<Avaliacao>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return avaliacoes;
    }

    public static void atualizarAvaliacao(List<Avaliacao> avaliacoes) {
        salvarAvaliacoes(avaliacoes);
    }

    public static void excluirAvaliacao(List<Avaliacao> avaliacoes, String nome) {
        avaliacoes.removeIf(a -> a.getNome().equals(nome));
        atualizarAvaliacao(avaliacoes);
    }
}
