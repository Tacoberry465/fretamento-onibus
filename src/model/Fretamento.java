package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Fretamento {
    private int id;
    private LocalDate data;
    private LocalTime horario;
    private String local;
    private String empresa;
    private String linhaOnibus;

    public Fretamento(LocalDate data, LocalTime horario, String local, String empresa, String linhaOnibus) {
        this.data = data;
        this.horario = horario;
        this.local = local;
        this.empresa = empresa;
        this.linhaOnibus = linhaOnibus;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) { this.horario = horario; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getLinhaOnibus() { return linhaOnibus; }
    public void setLinhaOnibus(String linhaOnibus) { this.linhaOnibus = linhaOnibus; }

    @Override
    public String toString() {
        return "Fretamento{" +
                "id=" + id +
                ", data=" + data +
                ", horario=" + horario +
                ", local='" + local + '\'' +
                ", empresa='" + empresa + '\'' +
                ", linhaOnibus='" + linhaOnibus + '\'' +
                '}';
    }
}
