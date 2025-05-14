package org.example.demo2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/APP_MUSICA";
        String usuario = "root";
        String senha = "86640568";

        try {
            conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }

        return conn;
    }
}
