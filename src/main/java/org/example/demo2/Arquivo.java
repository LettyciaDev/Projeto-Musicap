package org.example.demo2;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Arquivo {
    private static final String FILE_NAME = "musicas.dat";

    // metodo para salvar a lista de musicas no arquivo
    // Método para salvar a lista de pessoas no arquivo
    public static void salvar(List<Musics> musicas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(musicas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para ler a lista de pessoas do arquivo
    public static List<Musics> ler() {
        List<Musics> musicas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            musicas = (List<Musics>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return musicas;
    }

    // Método para atualizar uma pessoa no arquivo
    public static void atualizar(List<Musics> musicas) {
        salvar(musicas);
    }

    // Método para excluir uma pessoa do arquivo
    public static void excluir(List<Musics> musicas, String nome) {
        musicas.removeIf(m -> m.getNome().equals(nome));
        salvar(musicas);
    }
}