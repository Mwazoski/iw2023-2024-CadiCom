package es.uca.cadicom.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    private String nombre;
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    private String apellidos;
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    private String dni;
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    private String role;
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }


    @OneToMany(mappedBy = "usuario")
    private Set<Telefono> telefonos;
    public Set<Telefono> getTelefonos() { return telefonos; }
    public void setTelefonos(Set<Telefono> telefonos) { this.telefonos = telefonos; }
    public void setTelefonos(Telefono telefono) {
        if (telefono != null) {
            if (this.telefonos == null) {
                this.telefonos = new HashSet<Telefono>();
            }
            this.telefonos.add(telefono);
        }
    }

    public Usuario() {}

    public Usuario(String nombre, String apellidos, String dni, String email, String password){
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setDni(dni);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole("USUARIO");
    }

    public Usuario(String email, String password ){
        this.setEmail(email);
        this.setPassword(password);
    }
}