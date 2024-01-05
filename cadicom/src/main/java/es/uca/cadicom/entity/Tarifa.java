package es.uca.cadicom.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private String nombre;
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    private Double datos;
    public Double getDatos() { return datos; }
    public void setDatos(Double datos) { this.datos = datos; }

    private Integer minutos;
    public Integer getMinutos() { return minutos; }
    public void setMinutos(Integer minutos) { this.minutos = minutos; }

    private Double precio;
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    @OneToMany(mappedBy = "tarifa")
    private Set<Telefono> telefonos;
    public Set<Telefono> getTelefonos() { return telefonos; }
    public void setTelefonos(Set<Telefono> telefonos) { this.telefonos = telefonos; }
}
