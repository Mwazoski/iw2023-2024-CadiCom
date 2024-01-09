package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.SecurityService;
import es.uca.cadicom.security.SecurityUtils;
import es.uca.cadicom.service.UsuarioService;
import es.uca.cadicom.views.backoffice.MainView;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.userdetails.UserDetails;

//@RolesAllowed("USUARIO")
@PageTitle("FrontOffice")
@AnonymousAllowed
@Route(value = "/cliente", layout = FrontLayout.class)
public class FrontOfficeView extends Div{

    public FrontOfficeView() {

        Usuario usuario = (Usuario) UI.getCurrent().getSession().getAttribute("user");
        H2 h2 = new H2(usuario.getEmail());
        add(h2);
    }
}

