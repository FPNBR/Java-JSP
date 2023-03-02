package model;

import java.io.Serializable;
import java.util.Objects;

public class ModelTelefone implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String numero;
    private ModelLogin usuarioPaiId;
    private ModelLogin usuarioCadastroId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ModelLogin getUsuarioPaiId() {
        return usuarioPaiId;
    }

    public void setUsuarioPaiId(ModelLogin usuarioPaiId) {
        this.usuarioPaiId = usuarioPaiId;
    }

    public ModelLogin getUsuarioCadastroId() {
        return usuarioCadastroId;
    }

    public void setUsuarioCadastroId(ModelLogin usuarioCadastroId) {
        this.usuarioCadastroId = usuarioCadastroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelTelefone that = (ModelTelefone) o;
        return Objects.equals(id, that.id) && Objects.equals(numero, that.numero) && Objects.equals(usuarioPaiId, that.usuarioPaiId) && Objects.equals(usuarioCadastroId, that.usuarioCadastroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, usuarioPaiId, usuarioCadastroId);
    }
}
