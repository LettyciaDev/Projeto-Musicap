package org.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class User {

    private List<Avaliacao> musicasAvl;
    private Avaliacao avaliacaoSelecionada;

    public String user;
    @FXML private Pane Pane_Home;
    @FXML private Pane Pane_Review;
    @FXML
    private Pane Pane_List;
    @FXML private Pane Pane_Profile;
    @FXML private Pane Pane_Avaliacao;
    @FXML private VBox vboxMusicas;
    @FXML private TextField txt_nota_msc;
    @FXML private TextField txt_comentario;
    @FXML private ScrollPane scrollPaneMusicas;
    @FXML private ScrollPane scrollPaneReview;
    @FXML private VBox vboxReview;

    @FXML
    public void initialize() {
        List<Musics> musicas = Arquivo.ler();
        List<Avaliacao> avaliacoes = Arquivo.converterParaAvaliacoes(musicas);
        Arquivo.salvarAvaliacoes(avaliacoes); // grava o arquivo avalicoes.dat
        musicasAvl = avaliacoes;
        musicasAvl = Arquivo.lerAvaliacoes();// nome corrigido
    }

    public void abrirTela(){
        try {
            FXMLLoader loader = new FXMLLoader(User.class.getResource("SceneUser.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Musicap");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNome(String nome){
        this.user = nome;
    }

    public void esconderTodos(){
        Pane_Home.setVisible(false); Pane_Home.setManaged(false);
        Pane_Review.setVisible(false); Pane_Review.setManaged(false);
        Pane_List.setVisible(false); Pane_List.setManaged(false);
        Pane_Profile.setVisible(false); Pane_Profile.setManaged(false);
        Pane_Avaliacao.setVisible(false); Pane_Avaliacao.setManaged(false);
    }

    public void exibirPaneHome(){
        esconderTodos();
        Pane_Home.setVisible(true);
        Pane_Home.setManaged(true);
        listarMusicas(musicasAvl);
    }

    public void exibirPaneReview(){
        esconderTodos();
        Pane_Review.setVisible(true);
        Pane_Review.setManaged(true);
        ListarAvaliacoes(musicasAvl);

    }

    public void exibirPaneList(){
        esconderTodos();
        Pane_List.setVisible(true);
        Pane_List.setManaged(true);

    }

    public void exibirPaneProfile(){
        esconderTodos();
        Pane_Profile.setVisible(true);
        Pane_Profile.setManaged(true);

    }

    public void listarMusicas(List<Avaliacao> musicasAvl) {
        vboxMusicas.getChildren().clear();

        for (Avaliacao m : musicasAvl) {
            Label musica = new Label(m.toString());
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #ee2b6c; -fx-font-weight: bold; -fx-background-color: #fff; -fx-padding: 5px; -fx-background-radius: 5px");
            VBox.setVgrow(scrollPaneMusicas, Priority.ALWAYS);
            VBox.setMargin(musica, new Insets(2));  // importa javafx.geometry.Insets

            // Evento de clique para cada item
            musica.setOnMouseClicked(event -> {
                avaliacaoSelecionada = m;
                txt_nota_msc.setText(String.valueOf(m.getNota()));
                txt_comentario.setText(m.getComentario() != null ? m.getComentario() : "");
                esconderTodos();
                Pane_Avaliacao.setVisible(true);
                Pane_Avaliacao.setManaged(true);
            });

            vboxMusicas.getChildren().add(musica);
        }
    }

    // Salva a avaliação da música selecionada
    public void btnSalvarAvaliacao(){
        if (avaliacaoSelecionada == null) {
            System.out.println("Nenhuma música selecionada.");
            return;
        }

        try {
            int nota = Integer.parseInt(txt_nota_msc.getText());
            String comentario = txt_comentario.getText();

            avaliacaoSelecionada.setNota(nota);
            avaliacaoSelecionada.setComentario(comentario);
            avaliacaoSelecionada.setEscuta(true);

            Arquivo.atualizarAvaliacao(musicasAvl); // salva todas as avaliações
            System.out.println("Avaliação salva com sucesso!");

            exibirPaneHome(); // retorna à tela principal

        } catch (NumberFormatException e) {
            System.out.println("Nota inválida");
        }
    }

    public void ListarAvaliacoes(List<Avaliacao> musicasAvl) {
        vboxReview.getChildren().clear();

        for (Avaliacao m : musicasAvl) {
            Label musica = new Label(m.avaliar());
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #ee2b6c; -fx-font-weight: bold; -fx-background-color: #fff; -fx-padding: 5px; -fx-background-radius: 5px");
            VBox.setVgrow(scrollPaneReview, Priority.ALWAYS);
            VBox.setMargin(musica, new Insets(2));
            vboxReview.getChildren().add(musica);
        }
    }
}
