package es.uca.cadicom.views;

import com.google.protobuf.Api;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.components.Header;

import es.uca.cadicom.entity.LineaCliente;

import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.service.UsuarioService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import es.uca.cadicom.service.UsuarioService;
import es.uca.cadicom.service.ApiService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@Route("login")
@PageTitle("Login | Cadicom")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    Header header = new Header();
    private final UsuarioService usuarioService;
    private Binder<Usuario> binder = new Binder<>(Usuario.class);

    @NotBlank
    @Email
    private TextField tfEmail = new TextField("Correo Electronico *");

    @NotBlank
    private PasswordField tfPassword = new PasswordField("Contraseña *");

    private final ApiService apiService;

    public LoginView(UsuarioService usuarioService, ApiService apiService){

        this.usuarioService = usuarioService;
        this.apiService = apiService;

        setSizeFull();
        VerticalLayout vLayout = new VerticalLayout();

        binder.forField(tfEmail)
                .withValidator(new EmailValidator("Por favor, introduzca un correo electrónico válido."))
                .bind(Usuario::getEmail, Usuario::setEmail);

        binder.forField(tfPassword)
                .withValidator(password -> !password.trim().isEmpty(), "Por favor, introduzca una contrasña.")
                .bind(Usuario::getPassword, Usuario::setPassword);

        setWidth("100%");
        getStyle().set("flex-grow", "1");
        vLayout.setWidthFull();
        setFlexGrow(1.0, vLayout);
        vLayout.setWidth("100%");
        vLayout.setMinWidth("300px");
        vLayout.setMaxWidth("80%");
        vLayout.getStyle().set("flex-grow", "1");
        vLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        vLayout.setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        tfEmail.setMinWidth("300px");
        tfPassword.setMinWidth("300px");

        tfEmail.setWidth("max-content");
        tfPassword.setWidth("max-content");

        Button bLogin = new Button("Iniciar Sesión", event -> loginUser());
        bLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Shortcuts.addShortcutListener(bLogin, () -> bLogin.click(), Key.ENTER);

        add(header);
        add(vLayout);
        vLayout.add(tfEmail);
        vLayout.add(tfPassword);
        vLayout.add(bLogin);
    }

    private void loginUser() {
        if (binder.validate().isOk()) {
            Usuario usuario = new Usuario(tfEmail.getValue(), tfPassword.getValue());
            boolean userCreated = usuarioService.validateUserCredentials(usuario);
            if (userCreated) {
                usuario = usuarioService.findUserByEmail(usuario.getEmail());
                UI.getCurrent().getSession().setAttribute("user", usuario);
                UI.getCurrent().navigate("/cliente");
            } else {
                Notification.show("Error: User registration failed.", 3000, Notification.Position.TOP_CENTER);
            }
        } else {
            // Validation errors are automatically shown next to the respective field.
        }
    }
}
