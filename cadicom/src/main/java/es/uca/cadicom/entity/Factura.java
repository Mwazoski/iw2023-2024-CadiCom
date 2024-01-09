package es.uca.cadicom.entity;

import jakarta.persistence.*;

@Entity
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private Double importe;
    public Double getImporte() { return importe; }
    public void setImporte(Double importe) { this.importe = importe; }

    private String periodo;
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    private Integer minutos;
    public Integer getMinutos() { return minutos; }
    public void setMinutos(Integer minutos) { this.minutos = minutos; }

    @ManyToOne
    private Telefono telefono;
    public Telefono getTelefono() { return telefono; }
    public void setTelefono(Telefono telefono) { this.telefono = telefono; }
    public void setTelefono(String telefono) { this.telefono.setNumero(telefono); }

}
