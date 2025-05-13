package org.example.demo2;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Arquivo {
    private static final String FILE_NAME = "musicas.dat";

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
}
