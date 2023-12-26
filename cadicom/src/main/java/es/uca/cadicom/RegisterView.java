package es.uca.cadicom;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import es.uca.cadicom.components.Header;

@AnonymousAllowed
@CssImport("styles/landingpage.css")
@PageTitle("Register")
@Route("/register")
public class RegisterView extends VerticalLayout {

    Header header = new Header();
    public RegisterView() {

        setSizeFull();

        VerticalLayout vLayout = new VerticalLayout();

        H2 h2 = new H2("Registro");

        @NotBlank
        TextField tfName = new TextField("Nombre *");

        @NotBlank
        TextField tfSurname = new TextField("Apellidos *");

        @NotBlank
        TextField tfDni = new TextField("Dni *");

        @NotBlank
        @Email
        TextField tfEmail = new TextField("Correo Electronico *");

        @NotBlank
        TextField tfPassword = new TextField("Contraseña *");

        Button bRegister = new Button("Registrar");

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
        tfPassword.setWidth("max-content");

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
}
