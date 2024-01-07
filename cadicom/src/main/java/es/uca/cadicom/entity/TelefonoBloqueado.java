package es.uca.cadicom.entity;

import jakarta.persistence.*;

@Entity
public class TelefonoBloqueado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @ManyToOne
    private Telefono telefono;
    public Telefono getTelefono() { return telefono; }
    public void setTelefono(Telefono telefono) { this.telefono = telefono; }

    @ManyToOne
    private Telefono bloqueado;
    public Telefono getBloqueado() { return bloqueado; }
    public void setBloqueado(Telefono bloqueado) { this.bloqueado = bloqueado; }

    public TelefonoBloqueado() {}

    public TelefonoBloqueado(Telefono telefono, Telefono bloqueado) {
        this.telefono = telefono;
        this.bloqueado = bloqueado;
    }
}
