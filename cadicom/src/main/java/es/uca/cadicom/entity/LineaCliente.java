package es.uca.cadicom.entity;

import jakarta.persistence.*;

public class LineaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    private String nombre;
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    private String apellidos;
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    private String compania;
    public String getCompania() { return compania; }
    public void setCompania(String compania) { this.compania = compania; }

    private String numero;
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}
