package org.example.demo2;
import java.sql.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public class SistemaLogin {

        public static void main(String[] args) {
            Usuario usuario = autenticar("joao", "abcd"); // teste com joao ou admin
            acessarSistema(usuario);
        }

        // Método de autenticação
        public static Usuario autenticar(String nome, String senha) {
            Usuario usuario = null;
            String url = "jdbc:sqlite:usuarios.db"; // banco SQLite no mesmo diretório do projeto

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT * FROM usuarios WHERE nome = ? AND senha = ?")) {

                stmt.setString(1, nome);
                stmt.setString(2, senha);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setTipo(rs.getString("tipo"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return usuario;
        }

        // Método para controle de acesso
        public static void acessarSistema(Usuario usuario) {
            if (usuario == null) {
                System.out.println("Usuário ou senha inválidos.");
                return;
            }

            System.out.println("Bem-vindo, " + usuario.getNome() + "!");
            System.out.println("Tipo de acesso: " + usuario.getTipo());

            if (usuario.getTipo().equalsIgnoreCase("ADMIN")) {
                System.out.println("Você pode gerenciar usuários, visualizar relatórios, etc.");
            } else {
                System.out.println("Você pode acessar funcionalidades básicas.");
            }
        }
    }

}