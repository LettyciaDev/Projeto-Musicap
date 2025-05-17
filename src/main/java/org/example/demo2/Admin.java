package org.example.demo2;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Admin {

    private List<Musics> musicas;

    @FXML private Pane Pane_Create;
    @FXML private Pane Pane_Read;
    @FXML private Pane Pane_Update;
    @FXML private Pane Pane_Delete;
    @FXML private Pane Pane_EditarUsuario;
    @FXML private Pane Pane_RemoverUsuario;
    @FXML private Pane Pane_AdicionarUsuario;

    @FXML private TextField txt_nome_musc;
    @FXML private TextField txt_artista;
    @FXML private TextField txt_gener;
    @FXML private TextField txt_ano;

    @FXML private TextField txt_select_msc;
    @FXML private TextField txt_new_nome_musc;
    @FXML private TextField txt_new_artista;
    @FXML private TextField txt_new_gener;
    @FXML private TextField txt_new_ano;

    @FXML private TextField txt_delet_musc;

    @FXML private TextField txt_nome_user;
    @FXML private TextField txt_email_user;
    @FXML private TextField txt_senha_user;

    @FXML private TextField txt_remove_user;


    @FXML private VBox vboxListagem;
    @FXML private ScrollPane scrollPaneList;

    @FXML
    public void initialize() {
        musicas = Arquivo.ler();
    }

    public void abrirCadastro() { exibirPaneCreate(); }
    public void abrirConsulta() { exibirPaneRead(); }
    public void abrirRemocao() { exibirPaneDelete(); }
    public void abrirAtualizar() { exibirPaneUpdate(); }
    public void abrirEditar() { exibirPaneEditar(); }
    public void abrirAdicionar() { exibirPaneAdicionar(); }
    public void abrirRemover() { exibirPaneRemover(); }

    private void exibirPaneCreate() {
        esconderTodos();
        Pane_Create.setVisible(true);
        Pane_Create.setManaged(true);
    }

    private void exibirPaneRead() {
        esconderTodos();
        Pane_Read.setVisible(true);
        Pane_Read.setManaged(true);
        listarMusicas(musicas);
    }

    private void exibirPaneUpdate() {
        esconderTodos();
        Pane_Update.setVisible(true);
        Pane_Update.setManaged(true);
    }

    private void exibirPaneDelete() {
        esconderTodos();
        Pane_Delete.setVisible(true);
        Pane_Delete.setManaged(true);
    }

    private void exibirPaneEditar() {
        esconderTodos();
        Pane_EditarUsuario.setVisible(true);
        Pane_EditarUsuario.setManaged(true);
    }

    private void exibirPaneAdicionar() {
        esconderTodos();
        Pane_AdicionarUsuario.setVisible(true);
        Pane_AdicionarUsuario.setManaged(true);
    }

    private void exibirPaneRemover() {
        esconderTodos();
        Pane_RemoverUsuario.setVisible(true);
        Pane_RemoverUsuario.setManaged(true);
    }

    private void esconderTodos() {
        Pane_Create.setVisible(false);
        Pane_Create.setManaged(false);
        Pane_Read.setVisible(false);
        Pane_Read.setManaged(false);
        Pane_Update.setVisible(false);
        Pane_Update.setManaged(false);
        Pane_Delete.setVisible(false);
        Pane_Delete.setManaged(false);
        Pane_EditarUsuario.setVisible(false);
        Pane_EditarUsuario.setManaged(false);
        Pane_AdicionarUsuario.setVisible(false);
        Pane_AdicionarUsuario.setManaged(false);
        Pane_RemoverUsuario.setVisible(false);
        Pane_RemoverUsuario.setManaged(false);
    }

    public void btnAdicionarMusica() {
        adicionarMusica(musicas);
    }

    public void adicionarMusica(List<Musics> musicas) {
        String nomeMusic = txt_nome_musc.getText();
        String artista = txt_artista.getText();
        String genero = txt_gener.getText();
        int ano = Integer.parseInt(txt_ano.getText());

        musicas.add(new Musics(nomeMusic, artista, genero, ano));
        Arquivo.salvar(musicas);
        mostrarAlerta("Música adicionada com sucesso!");
    }


    public void listarMusicas(List<Musics> musicas) {
        vboxListagem.getChildren().clear();
        for (Musics m : musicas) {
            Label musica = new Label(m.toString());
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #fff; -fx-font-weight: bold; -fx-background-color: #6e1fb7; -fx-padding: 5px; -fx-background-radius: 5px; -fx-pref-width:300px;");
            VBox.setVgrow(scrollPaneList, Priority.ALWAYS);
            VBox.setMargin(musica, new Insets(2));// importa javafx.geometry.Insets

            vboxListagem.getChildren().add(musica);
        }
    }

    public void btnAtualizarMusica() {
        atualizarMusica(musicas);
    }

    public void atualizarMusica(List<Musics> musicas) {
        String nomeMusic = txt_select_msc.getText();

        for (Musics m : musicas) {
            if (m.getMusica().equals(nomeMusic)) {
                m.setMusica(txt_new_nome_musc.getText());
                m.setArtista(txt_new_artista.getText());
                m.setGenero(txt_new_gener.getText());
                m.setAno(Integer.parseInt(txt_new_ano.getText()));
                Arquivo.atualizar(musicas);
                mostrarAlerta("Música atualizada com sucesso!");
                return;
            }
        }
        mostrarAlerta("Música não encontrada!");
    }

    public void btnRemoverMusica() {
        removerMusica(musicas);
    }

    public void removerMusica(List<Musics> musicas) {
        String nomeMusic = txt_delet_musc.getText();
        Arquivo.excluir(musicas, nomeMusic);
        mostrarAlerta("Música excluída com sucesso!");
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void btnAdicionarUsuario() {
        adicionarUsuario();
    }

    public void adicionarUsuario() {
        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)")) {

            String nome = txt_nome_user.getText();
            String email = txt_email_user.getText();
            String senha = txt_senha_user.getText();
            String tipo = "usuario";

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, tipo);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Dados inseridos com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnRemoverUsuario() {
        removerUsuario();
    }

    public void removerUsuario() {
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM usuarios WHERE email = ?")) {

            String email = txt_remove_user.getText();

            stmt.setString(1, email);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Cadastro removido com sucesso.");
            } else {
                System.out.println("Nenhum cadastro foi removido. Verifique os dados.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover o cadastro: " + e.getMessage());
        }
    }
}
