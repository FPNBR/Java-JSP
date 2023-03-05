package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ModelLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String login;
    private String nome;
    private String email;
    private String senha;
    private boolean usuarioAdmin;
    private String perfil;
    private String sexo;
    private String fotoUsuario;
    private String extensaoFotoUsuario;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String numeroCasa;
    private Date dataNascimento;
    private Double rendaMensal;
    private List<ModelTelefone> modelTelefones = new ArrayList<>();

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getUsuarioAdmin() {
        return usuarioAdmin;
    }

    public void setUsuarioAdmin(boolean usuarioAdmin) {
        this.usuarioAdmin = usuarioAdmin;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getExtensaoFotoUsuario() {
        return extensaoFotoUsuario;
    }

    public void setExtensaoFotoUsuario(String extensaoFotoUsuario) {
        this.extensaoFotoUsuario = extensaoFotoUsuario;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(Double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public List<ModelTelefone> getModelTelefones() {
        return modelTelefones;
    }

    public void setModelTelefones(List<ModelTelefone> modelTelefones) {
        this.modelTelefones = modelTelefones;
    }
}
