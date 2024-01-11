package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.AuthenticatedUser;
import es.uca.cadicom.security.SecurityService;
import es.uca.cadicom.security.SecurityUtils;
import es.uca.cadicom.service.UsuarioService;
import es.uca.cadicom.views.backoffice.MainView;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;


@PageTitle("FrontOffice")
@RolesAllowed("USER")
@Route(value = "/cliente", layout = FrontLayout.class)
public class FrontOfficeView extends Div{

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;

    public FrontOfficeView(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        Optional<Usuario> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            Usuario usuario = maybeUser.get();
            List<GrantedAuthority> authorities = usuario.getAuthorities();
            System.out.println("Roles del usuario:");
            for (GrantedAuthority authority : authorities) {
                System.out.println(authority.getAuthority());
            }
        }
    }
}

