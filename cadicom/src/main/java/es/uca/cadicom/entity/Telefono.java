package es.uca.cadicom.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private String numero;
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    @ManyToOne
    private Usuario usuario;
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    @OneToMany(mappedBy = "telefono")
    private Set<Factura> facturas;
    public Set<Factura> getFacturas() { return facturas; }
    public void setFacturas(Set<Factura> facturas) { this.facturas = facturas; }

    @ManyToOne
    private Tarifa tarifa;
    public Tarifa getTarifa() { return tarifa; }
    public void setTarifa(Tarifa tarifa) { this.tarifa = tarifa; }
}
