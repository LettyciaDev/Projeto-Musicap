package org.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Avaliacao> musicasAvl;
    private Avaliacao avaliacaoSelecionada;
    private List<Musics> musicas;

    @FXML private Pane Pane_Home;
    @FXML private Pane Pane_Review;
    @FXML private Pane Pane_List;
    @FXML private Pane Pane_Avaliacao;
    @FXML private Pane Pane_EditarAvaliacao;
    @FXML private VBox vboxMusicas;
    @FXML private VBox vboxReview;
    @FXML private VBox vboxList;
    @FXML private TextField txt_nota_msc;
    @FXML private TextField txt_comentario;
    @FXML private TextField txt_nova_nota_msc;
    @FXML private TextField txt_novo_comentario;
    @FXML private ScrollPane scrollPaneMusicas;
    @FXML private ScrollPane scrollPaneReview;
    @FXML private ScrollPane scrollPaneList;

    @FXML
    public void initialize() {
        musicas = Arquivo.ler();
        musicasAvl = Arquivo.lerAvaliacoes();
        exibirPaneHome();

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

    public void esconderTodos(){
        Pane_Home.setVisible(false);
        Pane_Home.setManaged(false);
        Pane_Review.setVisible(false);
        Pane_Review.setManaged(false);
        Pane_List.setVisible(false);
        Pane_List.setManaged(false);
        Pane_Avaliacao.setVisible(false);
        Pane_Avaliacao.setManaged(false);
        Pane_EditarAvaliacao.setVisible(false);
        Pane_EditarAvaliacao.setManaged(false);
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
        listarUsuarioMusicas(musicasAvl);

    }

    public void listarMusicas(List<Musics> musicas) {
        vboxMusicas.getChildren().clear();

        for (Musics musicaObj : musicas) {
            Label musica = new Label(musicaObj.toString());
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #ee2b6c; -fx-font-weight: bold; -fx-background-color: #fff; -fx-padding: 5px; -fx-background-radius: 5px");
            VBox.setVgrow(scrollPaneMusicas, Priority.ALWAYS);
            VBox.setMargin(musica, new Insets(2)); // importa javafx.geometry.Insets


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


            vboxReview.getChildren().add(musica);
        }

    }

    public void listarUsuarioMusicas(List<Avaliacao> musicasAvl) {
        this.musicasAvl = musicasAvl; // armazena a referência para uso posterior
        vboxList.getChildren().clear();

        String nomeUsuarioLogado = SessaoUsuario.getInstancia().getUsuario().getNome();

        for (Avaliacao musica : musicasAvl) {
            if (musica.getNomeUsuario().equals(nomeUsuarioLogado)) {
                Label musicaAvl = new Label(musica.mostrarAvaliacao());
                musicaAvl.setStyle("-fx-font-size: 14px; -fx-text-fill: #ee2b6c; -fx-font-weight: bold; -fx-background-color: #fff; -fx-padding: 5px; -fx-background-radius: 5px");
                VBox.setVgrow(scrollPaneList, Priority.ALWAYS);
                VBox.setMargin(musicaAvl, new Insets(2));
                vboxList.getChildren().add(musicaAvl);

                musicaAvl.setOnMouseClicked(event -> {
                    avaliacaoSelecionada = musica; // guarda para edição
                    txt_nova_nota_msc.setText(String.valueOf(musica.getNota()));
                    txt_novo_comentario.setText(musica.getComentario());

                    esconderTodos();
                    Pane_EditarAvaliacao.setVisible(true);
                    Pane_EditarAvaliacao.setManaged(true);
                });

            }


        }

    }

    @FXML
    public void btnSalvarNovaAvaliacao(ActionEvent event){
        if (avaliacaoSelecionada == null) return;

        try {
            int nota = Integer.parseInt(txt_nova_nota_msc.getText());
            String comentario = txt_novo_comentario.getText();

            avaliacaoSelecionada.setNota(nota);
            avaliacaoSelecionada.setComentario(comentario);

            Arquivo.salvarAvaliacoes(musicasAvl);
            listarUsuarioMusicas(musicasAvl);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Avaliação atualizada com sucesso!");
            alert.showAndWait();

            // Opcional: esconde o painel de edição após salvar
            Pane_EditarAvaliacao.setVisible(false);
            Pane_EditarAvaliacao.setManaged(false);
            avaliacaoSelecionada = null;

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Nota inválida. Digite um número.");
            alert.showAndWait();
        }

    }
}
