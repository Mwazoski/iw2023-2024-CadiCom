package es.uca.cadicom.entity;

public class RegistroLlamadas {

    private String numeroDestino;
    public String getNumeroDestino() { return numeroDestino; }
    public void setNumeroDestino(String numeroDestino) { this.numeroDestino = numeroDestino; }

    private Integer segundos;
    public Integer getSegundos() { return segundos; }
    public void setSegundos(Integer segundos) { this.segundos = segundos; }

    private String fecha;
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
