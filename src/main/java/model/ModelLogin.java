package model;

import java.io.Serializable;

public class ModelLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private boolean usuario_admin;


    public boolean idExiste() { // Método para verificar se o usuário já possui um id ou não
        if (this.id == null) {
            return true; // Inserir novo usuário
        }else if (this.id != null && this.id > 0) {
            return false; // Atualizar usuário
        }
        return id == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getUsuario_admin() {
        return usuario_admin;
    }

    public void setUsuario_admin(boolean usuario_admin) {
        this.usuario_admin = usuario_admin;
    }
}
