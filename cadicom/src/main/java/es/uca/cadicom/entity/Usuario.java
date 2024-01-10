package es.uca.cadicom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario extends AbstractEntity{

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String nombre;

    @Setter
    @Getter
    private String apellidos;

    @Setter
    @Getter
    private String dni;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> role;
    public Set<Role> getRoles() { return role; }
    public void setRoles(Set<Role> roles) { this.role = roles; }


    @Setter
    @Getter
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private Set<Telefono> telefonos;

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
        //this.setRole("USUARIO");
    }

    public Usuario(String nombre, String apellidos, String email, String password){
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setEmail(email);
        this.setPassword(password);
        //this.setRole("USUARIO");
    }

    public Usuario(String email, String password ){
        this.setEmail(email);
        this.setPassword(password);
    }
}