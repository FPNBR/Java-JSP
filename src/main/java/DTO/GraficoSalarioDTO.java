package DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GraficoSalarioDTO implements Serializable {
    List<String> perfis = new ArrayList<>();
    List<Double> salarios = new ArrayList<>();

    public List<String> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<String> perfis) {
        this.perfis = perfis;
    }

    public List<Double> getSalarios() {
        return salarios;
    }

    public void setSalarios(List<Double> salarios) {
        this.salarios = salarios;
    }
}
