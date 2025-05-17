package org.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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

    private final String estiloPadrao = "-fx-background-color: transparent;";
    private final String estiloAtivo = "-fx-background-color: transparent; " +
            "-fx-border-color: transparent transparent #6e1fb7 transparent; " +
            "-fx-border-width: 0 0 2px 0;";


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
    @FXML private TextField txt_pesquisa;
    @FXML private ScrollPane scrollPaneMusicas;
    @FXML private ScrollPane scrollPaneReview;
    @FXML private ScrollPane scrollPaneList;
    @FXML private Button btn_home;
    @FXML private Button btn_review;
    @FXML private Button btn_list;
    @FXML private Button btn_pesquisa;

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

            Image icon = new Image(getClass().getResource("/org/images/sound-wave.png").toString());
            stage.getIcons().add(icon);

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
        setBotaoAtivo(btn_home);
    }

    public void exibirPaneReview(){
        esconderTodos();
        Pane_Review.setVisible(true);
        Pane_Review.setManaged(true);
        ListarAvaliacoes(musicasAvl);
        setBotaoAtivo(btn_review);
    }

    public void exibirPaneList(){
        esconderTodos();
        Pane_List.setVisible(true);
        Pane_List.setManaged(true);
        listarUsuarioMusicas(musicasAvl);
        setBotaoAtivo(btn_list);
    }

    private void setBotaoAtivo(Button botaoAtivo) {
        // Remove estilo ativo de todos
        btn_home.setStyle(estiloPadrao);
        btn_review.setStyle(estiloPadrao);
        btn_list.setStyle(estiloPadrao);

        // Aplica estilo no botão clicado
        botaoAtivo.setStyle(estiloAtivo);
    }

    @FXML
    public void btnPesquisarMusica() {
        String musicaSearch = txt_pesquisa.getText().trim().toLowerCase();
        boolean encontrada = false;

        vboxMusicas.getChildren().clear(); // Limpa antes de mostrar resultados

        for (Musics m : musicas) {
            if (m.getMusica().toLowerCase().contains(musicaSearch)) {
                Label musica = new Label(m.toString());
                musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #fff; -fx-font-weight: bold; -fx-background-color: #6e1fb7; -fx-padding: 5px; -fx-background-radius: 5px; -fx-pref-width:300px;");
                VBox.setVgrow(scrollPaneMusicas, Priority.ALWAYS);
                VBox.setMargin(musica, new Insets(2));

                // Evento de clique (como no método listarMusicas)
                musica.setOnMouseClicked(event -> {
                    avaliacaoSelecionada = new Avaliacao(
                            m.getMusica(),
                            m.getArtista(),
                            m.getGenero(),
                            m.getAno()
                    );

                    txt_nota_msc.clear();
                    txt_comentario.clear();

                    esconderTodos();
                    Pane_Avaliacao.setVisible(true);
                    Pane_Avaliacao.setManaged(true);
                });

                vboxMusicas.getChildren().add(musica);
                encontrada = true;
                txt_pesquisa.clear();
            }
        }

        if (!encontrada) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Info");
            alerta.setContentText("Música não encontrada");
            alerta.showAndWait();
        }
    }


    public void listarMusicas(List<Musics> musicas) {
        vboxMusicas.getChildren().clear();

        for (Musics musicaObj : musicas) {
            Label musica = new Label(musicaObj.toString());
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #fff; -fx-font-weight: bold; -fx-background-color: #6e1fb7; -fx-padding: 5px; -fx-background-radius: 5px; -fx-pref-width:300px;");
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
            musica.setStyle("-fx-font-size: 14px; -fx-text-fill: #fff; -fx-font-weight: bold; -fx-background-color: #6e1fb7; -fx-padding: 5px; -fx-background-radius: 5px; -fx-pref-width:300px;");
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
                musicaAvl.setStyle("-fx-font-size: 14px; -fx-text-fill: #fff; -fx-font-weight: bold; -fx-background-color: #6e1fb7; -fx-padding: 5px; -fx-background-radius: 5px; -fx-pref-width:300px;");
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

            exibirPaneList();
            // Opcional: esconde o painel de edição após salvar
            //Pane_EditarAvaliacao.setVisible(false);
            //Pane_EditarAvaliacao.setManaged(false);
            avaliacaoSelecionada = null;

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Nota inválida. Digite um número.");
            alert.showAndWait();
        }
    }

    @FXML
    public void btnExcluirAvaliacao(ActionEvent event) {
        if (avaliacaoSelecionada == null) return;

        musicasAvl.remove(avaliacaoSelecionada);

        Arquivo.salvarAvaliacoes(musicasAvl);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Avaliação excluída com sucesso!");
        alert.showAndWait();

        listarUsuarioMusicas(musicasAvl);

        exibirPaneList();
        avaliacaoSelecionada = null;
    }

}


