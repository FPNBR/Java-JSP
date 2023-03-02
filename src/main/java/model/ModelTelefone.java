package model;

import java.io.Serializable;
import java.util.Objects;

public class ModelTelefone implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String numero;
    private ModelLogin usuario_pai_id;
    private ModelLogin usuario_cad_id;

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

    public ModelLogin getUsuario_pai_id() {
        return usuario_pai_id;
    }

    public void setUsuario_pai_id(ModelLogin usuario_pai_id) {
        this.usuario_pai_id = usuario_pai_id;
    }

    public ModelLogin getUsuario_cad_id() {
        return usuario_cad_id;
    }

    public void setUsuario_cad_id(ModelLogin usuario_cad_id) {
        this.usuario_cad_id = usuario_cad_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelTelefone that = (ModelTelefone) o;
        return Objects.equals(id, that.id) && Objects.equals(numero, that.numero) && Objects.equals(usuario_pai_id, that.usuario_pai_id) && Objects.equals(usuario_cad_id, that.usuario_cad_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, usuario_pai_id, usuario_cad_id);
    }
}
