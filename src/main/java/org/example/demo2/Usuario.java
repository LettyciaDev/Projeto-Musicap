package org.example.demo2;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipo;

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return this.tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}

class SessaoUsuario{
    private static SessaoUsuario instancia;
    private Usuario usuario;

    private SessaoUsuario() {
    }

    public static SessaoUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SessaoUsuario();
        }
        return instancia;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
