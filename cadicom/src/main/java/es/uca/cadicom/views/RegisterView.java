package es.uca.cadicom.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.service.UsuarioService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import es.uca.cadicom.components.Header;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.binder.Binder;

@AnonymousAllowed
@CssImport("./styles/landingpage.css")
@PageTitle("Register")
@Route("/register")
public class RegisterView extends VerticalLayout {

    Header header = new Header();
    private final UsuarioService usuarioService;
    private Binder<Usuario> binder = new Binder<>(Usuario.class);

    @NotBlank
    private TextField tfName = new TextField("Nombre *");

    @NotBlank
    private TextField tfSurname = new TextField("Apellidos *");

    @NotBlank
    private TextField tfDni = new TextField("Dni *");

    @NotBlank
    @Email
    private TextField tfEmail = new TextField("Correo Electronico *");

    @NotBlank
    private PasswordField tfPassword = new PasswordField("Contraseña *");

    private Button bRegister = new Button("Registrar");

    H2 h2 = new H2("Registro");

    public RegisterView(UsuarioService usuarioService) {

        this.usuarioService = usuarioService;
        setSizeFull();
        VerticalLayout vLayout = new VerticalLayout();

        binder.forField(tfEmail)
                .withValidator(new EmailValidator("Por favor, introduzca un correo electrónico válido."))
                .bind(Usuario::getEmail, Usuario::setEmail);

        binder.forField(tfName)
                .withValidator(name -> !name.trim().isEmpty(), "Por favor, introduzca un nombre.")
                .bind(Usuario::getNombre, Usuario::setNombre);

        binder.forField(tfSurname)
                .withValidator(surname -> !surname.trim().isEmpty(), "Por favor, introduzca un apellido.")
                .bind(Usuario::getApellidos, Usuario::setApellidos);

        binder.forField(tfDni)
                .withValidator(dni -> !dni.trim().isEmpty(), "Por favor, introduzca un Dni.")
                .bind(Usuario::getDni, Usuario::setDni);

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
        vLayout.setAlignSelf(FlexComponent.Alignment.CENTER, h2);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        tfName.setMinWidth("300px");
        tfSurname.setMinWidth("300px");
        tfEmail.setMinWidth("300px");
        tfDni.setMinWidth("300px");
        tfEmail.setMinWidth("300px");
        tfPassword.setMinWidth("300px");

        h2.setWidth("max-content");
        tfName.setWidth("max-content");
        tfEmail.setWidth("max-content");
        tfDni.setWidth("max-content");
        tfPassword.setWidth("max-content");

        Button bRegister = new Button("Registrar", event -> registerUser());
        bRegister.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(header);
        add(vLayout);
        vLayout.add(h2);
        vLayout.add(tfName);
        vLayout.add(tfSurname);
        vLayout.add(tfDni);
        vLayout.add(tfEmail);
        vLayout.add(tfPassword);
        vLayout.add(bRegister);
    }

    private void registerUser() {
        if (binder.validate().isOk()) {
            // All validations passed
            Usuario newUsuario = new Usuario(tfName.getValue(), tfSurname.getValue(), tfDni.getValue(), tfEmail.getValue(), tfPassword.getValue());
            boolean userCreated = usuarioService.createUser(newUsuario);

            if (userCreated) {
                UI.getCurrent().navigate("/");
            } else {
                Notification.show("Error: User registration failed.", 3000, Notification.Position.TOP_CENTER);
            }
        } else {
            // Validation errors are automatically shown next to the respective field.
        }
    }
}
