package es.uca.cadicom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Usuario extends AbstractEntity implements UserDetails{
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

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return this.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> role = new HashSet<>();
    public Set<Role> getRoles() { return role; }
    public void setRoles(Set<Role> roles) { this.role = roles; }

    public void addRoles(Role role) {
        this.role.add(role);
    }

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Telefono> telefonos;
    public Set<Telefono> getTelefonos() { return telefonos; }
    public void setTelefonos(Set<Telefono> telefonos) { this.telefonos = telefonos; }
    public void addTelefono(Telefono telefono) {
        if (telefono != null) {
            if (this.telefonos == null) {
                this.telefonos = new HashSet<Telefono>();
            }
            this.telefonos.add(telefono);
            telefono.setUsuario(this); // Manage the bidirectional relationship
        }
    }
    public void removeOneTelefono(Telefono telefono) {
        if (telefono != null && this.telefonos != null && this.telefonos.contains(telefono)) {
            this.telefonos.remove(telefono);
            telefono.setUsuario(null); // Desvincula el telefono del usuario
        }
    }


    public Usuario() {}

    public Usuario(String nombre, String apellidos, String dni, String email, String password){
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setDni(dni);
        this.setEmail(email);
        this.setPassword(password);
        this.addRoles(Role.USER);
    }

    public Usuario(String nombre, String apellidos, String email, String password){
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setEmail(email);
        this.setPassword(password);
        this.addRoles(Role.USER);
    }

    public Usuario(String email, String password ){
        this.setEmail(email);
        this.setPassword(password);
    }
}