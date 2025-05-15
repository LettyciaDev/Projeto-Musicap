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
    private List<Musics> musicas;

    public String user;
    @FXML private Pane Pane_Home;
    @FXML private Pane Pane_Review;
    @FXML private Pane Pane_List;
    @FXML private Pane Pane_Profile;
    @FXML private Pane Pane_Avaliacao;
    @FXML private VBox vboxMusicas;
    @FXML private VBox vboxReview;
    @FXML private VBox vboxList;
    @FXML private TextField txt_nota_msc;
    @FXML private TextField txt_comentario;
    @FXML private ScrollPane scrollPaneMusicas;
    @FXML private ScrollPane scrollPaneReview;
    @FXML private ScrollPane scrollPaneList;

    @FXML
    public void initialize() {
        musicas = Arquivo.ler();
        musicasAvl = Arquivo.lerAvaliacoes();

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
        listarMusicas(musicas);
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

    public void listarMusicas(List<Musics> musicas) {
        vboxMusicas.getChildren().clear();

        for (Musics musicaObj : musicas) {
            Label musica = new Label(musicaObj.toString());
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #ee2b6c; -fx-font-weight: bold; -fx-background-color: #fff; -fx-padding: 5px; -fx-background-radius: 5px");
            VBox.setVgrow(scrollPaneMusicas, Priority.ALWAYS);
            VBox.setMargin(musica, new Insets(2)); // importa javafx.geometry.Insets

            scrollPaneMusicas.setStyle("-fx-background-color: none");
            if (scrollPaneMusicas.getContent() != null) {
                scrollPaneMusicas.getContent().setStyle("-fx-background-color: transparent;");
            }

            // Evento de clique para cada item
            musica.setOnMouseClicked(event -> {
                avaliacaoSelecionada = new Avaliacao(
                  musicaObj.getMusica(),
                  musicaObj.getArtista(),
                  musicaObj.getGenero(),
                  musicaObj.getAno()
                );

                // Limpa campos para nova avaliação
                txt_nota_msc.clear();
                txt_comentario.clear();

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

            musicasAvl.add(avaliacaoSelecionada);

            Arquivo.salvarAvaliacoes(musicasAvl);
            System.out.println("Avaliação salva com sucesso!");

            exibirPaneHome(); // retorna à tela principal

        } catch (NumberFormatException e) {
            System.out.println("Nota inválida");
        }
    }

    public void ListarAvaliacoes(List<Avaliacao> musicasAvl) {
        vboxReview.getChildren().clear();
        for (Avaliacao m : musicasAvl) {
            Label musica = new Label(m.mostrarAvaliacao());
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #ee2b6c; -fx-font-weight: bold; -fx-background-color: #fff; -fx-padding: 5px; -fx-background-radius: 5px");
            VBox.setVgrow(scrollPaneReview, Priority.ALWAYS);
            VBox.setMargin(musica, new Insets(2));

            scrollPaneMusicas.setStyle("-fx-background-color: none");
            if (scrollPaneReview.getContent() != null) {
                scrollPaneReview.getContent().setStyle("-fx-background-color: transparent;");
            }

            musica.setOnMouseClicked(event -> {
                musicasAvl.remove(m);
                Arquivo.salvarAvaliacoes(musicasAvl);
                System.out.println("Avaliação removida!");
                ListarAvaliacoes(musicasAvl); // atualiza a lista na interface
            });

            vboxReview.getChildren().add(musica);
        }

    }

    public void listarUsuarioMusicas(List<Avaliacao> musicasAvl, Usuario usuario) {
        vboxList.getChildren().clear();

        for (Avaliacao musica : musicasAvl) {
            if(musica.getUsuario().equals(usuario.getNome())) {
                Label musicaAvl = new Label(musica.mostrarAvaliacao());
                musicaAvl.setStyle("-fx-font-size: 14px; -fx-text-fill: #ee2b6c; -fx-font-weight: bold; -fx-background-color: #fff; -fx-padding: 5px; -fx-background-radius: 5px");
                VBox.setVgrow(scrollPaneMusicas, Priority.ALWAYS);
                VBox.setMargin(musicaAvl, new Insets(2)); // importa javafx.geometry.Insets

                scrollPaneList.setStyle("-fx-background-color: none");
                if (scrollPaneList.getContent() != null) {
                    scrollPaneList.getContent().setStyle("-fx-background-color: transparent;");
                }
            }
        }
    }
}
