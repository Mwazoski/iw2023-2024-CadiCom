package es.uca.cadicom.service;

import es.uca.cadicom.entity.Role;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(Usuario usuario) {
        if (usuario == null) {
            System.err.println("Usuario es null");
            return false;
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            System.err.println("Email is null or empty");
            return false;
        }

        Usuario existingUser = usuarioRepository.findByEmail(usuario.getEmail());

        if (existingUser != null) {
            System.err.println("A user with the same email already exists");
            return false;
        }

        try {
            String encryptedPassword = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(encryptedPassword);
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }

    public Usuario getUser(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.err.println("Email is null or empty.");
            return null;
        }

        Optional<Usuario> userOpt = Optional.ofNullable(usuarioRepository.findByEmail(email));

        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            System.err.println("User with email " + email + " not found.");
            return null;
        }
    }

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public void updateUser(Usuario updatedUser) {

        if (updatedUser == null || updatedUser.getEmail().trim().isEmpty()) {
            System.err.println("User object is null or email is empty");
            return;
        }

        Optional<Usuario> userOpt = (usuarioRepository.findById(updatedUser.getId()));

        if (userOpt.isPresent()) {
            Usuario existingUser = userOpt.get();

            // Update fields of existingUser with values from updatedUser, if they are not null
            if (updatedUser.getNombre() != null) {
                existingUser.setNombre(updatedUser.getNombre());
            }
            if (updatedUser.getApellidos() != null) {
                existingUser.setApellidos(updatedUser.getApellidos());
            }
            if (updatedUser.getDni() != null) {
                existingUser.setDni(updatedUser.getDni());
            }
            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword());
            }

            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            usuarioRepository.save(existingUser);
            System.out.println("User with email " + updatedUser.getEmail() + " has been updated.");
        } else {
            System.err.println("User with email " + updatedUser.getEmail() + " not found.");
        }
    }

    public void deleteUser(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.err.println("Email is null or empty");
            return;
        }

        Optional<Usuario> userOpt = Optional.ofNullable(usuarioRepository.findByEmail(email));

        if (userOpt.isPresent()) {
            // Delete the user
            usuarioRepository.delete(userOpt.get());
            System.out.println("User with email " + email + " has been deleted.");
        } else {
            System.err.println("User with email " + email + " not found.");
        }
    }

    public boolean validateUserCredentials(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {
            System.err.println("Usuario or credentials are null");
            return false;
        }
        try {
            Usuario existingUser = usuarioRepository.findByEmail(usuario.getEmail());
            if (existingUser == null) {
                System.err.println("User not found");
                return false;
            }
            return passwordEncoder.matches(usuario.getPassword(), existingUser.getPassword());
        } catch (Exception e) {
            System.err.println("Error validating user: " + e.getMessage());
            return false;
        }
    }

    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        CustomAuthenticationSuccessHandler handler = new CustomAuthenticationSuccessHandler();
        handler.setUseReferer(false);
        handler.setDefaultTargetUrl("/cliente");
        return handler;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new User(usuario.getEmail(), usuario.getPassword(), Collections.singletonList(authority));
    }

    public Usuario findUserByEmail(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return usuario;
    }

    public Usuario findUserById(Long id) throws UsernameNotFoundException {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found with id: " + id));
    }

}
