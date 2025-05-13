package org.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class Admin {

    private List<Musics> musicas;

    @FXML private Pane Pane_Create;
    @FXML private Pane Pane_Read;
    @FXML private Pane Pane_Update;
    @FXML private Pane Pane_Delete;

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

    @FXML private VBox vboxListagem;

    @FXML
    public void initialize() {
        musicas = Arquivo.ler();
    }

    public void abrirCadastro() {
        exibirPaneCreate();
    }

    public void abrirConsulta() {
        exibirPaneRead();
    }

    public void abrirRemocao() {
        exibirPaneDelete();
    }

    public void abrirAtualizar() {
        exibirPaneUpdate();
    }

    @FXML
    private void exibirPaneCreate() {
        esconderTodos();
        Pane_Create.setVisible(true);
        Pane_Create.setManaged(true);
    }

    @FXML
    private void exibirPaneRead() {
        esconderTodos();
        Pane_Read.setVisible(true);
        Pane_Read.setManaged(true);
    }

    @FXML
    private void exibirPaneUpdate() {
        esconderTodos();
        Pane_Update.setVisible(true);
        Pane_Update.setManaged(true);
    }

    @FXML
    private void exibirPaneDelete() {
        esconderTodos();
        Pane_Delete.setVisible(true);
        Pane_Delete.setManaged(true);
    }

    @FXML
    private void esconderTodos() {
        Pane_Create.setVisible(false);
        Pane_Create.setManaged(false);
        Pane_Read.setVisible(false);
        Pane_Read.setManaged(false);
        Pane_Update.setVisible(false);
        Pane_Update.setManaged(false);
        Pane_Delete.setVisible(false);
        Pane_Delete.setManaged(false);
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Música adicionada com sucesso!");
        alert.showAndWait();
    }

    public void btnListarMusicas() {
        listarMusicas(musicas);
    }

    public void listarMusicas(List<Musics> musicas) {
        vboxListagem.getChildren().clear();
        for (Musics m : musicas) {
            Label musica = new Label(m.toString());
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffff; -fx-font-weight: bold;");
            vboxListagem.getChildren().add(musica);
        }
    }

    public void btnAtualizarMusica() {
        atualizarMusica(musicas);
    }

    public void atualizarMusica(List<Musics> musicas) {
        String nomeMusic = txt_select_msc.getText();

        for (Musics m : musicas) {
            if (m.getNome().equals(nomeMusic)) {
                m.setNome(txt_new_nome_musc.getText());
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
}