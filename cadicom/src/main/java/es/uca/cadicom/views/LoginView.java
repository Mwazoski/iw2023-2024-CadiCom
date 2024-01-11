package es.uca.cadicom.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.components.Header;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.AuthenticatedUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;
    private LoginForm loginForm = new LoginForm();

    Header header = new Header();
    public LoginView(AuthenticatedUser authenticatedUser) {

        this.authenticatedUser = authenticatedUser;

        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Inicio Sesión");
        i18nForm.setUsername("Correo electrónico");
        i18nForm.setPassword("Contraseña");
        i18nForm.setSubmit("Iniciar sesión");
        i18n.setForm(i18nForm);
        i18n.setAdditionalInformation(null);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(header);
        loginForm.setI18n(i18n);
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));
        add(loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Usuario> maybeUser = authenticatedUser.get();
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            Usuario usuario = maybeUser.get();

            List<GrantedAuthority> authorities = usuario.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                System.out.println(authority.getAuthority());
                System.out.println("USER".equals(authority.getAuthority()));
            }
            if (authorities.stream().anyMatch(auth -> "USER".equals(auth.getAuthority()))) {
                event.forwardTo("/cliente");
            } else if(authorities.stream().anyMatch(auth -> "ADMIN".equals(auth.getAuthority()))){
                event.forwardTo("/admin");
            }

        }

        loginForm.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}