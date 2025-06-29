package org.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_senha;
    @FXML
    private Button btn_enviar;


    @FXML
    public void autenticarUsuario() {
        String nome = txt_nome.getText();
        String email = txt_email.getText();
        String senha = txt_senha.getText();

        Usuario usuario = buscarUsuario(nome, email, senha);
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
                }

                // Abrir a tela do usário
                User user = new User();
                user.abrirTela();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) btn_enviar.getScene().getWindow();
        stage.close();
        return usuario;
    }

    private void acessarSistema(Usuario usuario) {
        if (usuario == null) {
            exibirMensagem("Erro", "Usuário, email ou senha inválidos.");
            return;
        }
    }


    private void exibirMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


}

