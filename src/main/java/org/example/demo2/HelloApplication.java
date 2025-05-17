package org.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloApplication extends Application {

    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_senha;
    @FXML
    private Button btn_enviar;

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBold.ttf"), 12);
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 640);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
                // seta o icone do programa
        Image icon = new Image(getClass().getResource("/org/images/sound-wave.png").toString());
        stage.getIcons().add(icon);

                // faz o programa não poder ser mudado de tamanho pelo usuário
        stage.setResizable(false);
                // faz com que ao apertar ESC o usuario feche a tela de LOGIN
        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBold.ttf"), 12);
        System.out.println("Fonte carregada: " + (font != null ? font.getName() : "Erro"));


    }

    @FXML
    public void initialize() {
        btn_enviar.setOnAction(event -> autenticarUsuario());
    }

    private void autenticarUsuario() {
        String nome = txt_nome.getText();
        String email = txt_email.getText();
        String senha = txt_senha.getText();

        Usuario usuario = buscarUsuario(nome, email, senha);

        // Armazena o nome do usuario logado
        if(usuario != null){
            SessaoUsuario.getInstancia().setUsuario(usuario);
        }
        acessarSistema(usuario);
    }

    private Usuario buscarUsuario(String nome, String email, String senha) {
        Usuario usuario = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM usuarios WHERE nome = ? AND email = ? AND senha = ?")) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(rs.getString("tipo"));

                if (usuario.getTipo().equalsIgnoreCase("ADMIN")) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo2/SceneAdmin.fxml"));
                        Scene scene = new Scene(loader.load());

                        Stage stage = new Stage();
                        stage.setTitle("Tela Principal");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(usuario.getTipo().equalsIgnoreCase("USUARIO")){
                    User user = new User();
                    user.abrirTela();
                }  else {
                    System.out.println("erro");
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    private void acessarSistema(Usuario usuario) {
        if (usuario == null) {
            exibirMensagem("Erro", "Usuário, email ou senha inválidos.");
        }
    }

    private void exibirMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}