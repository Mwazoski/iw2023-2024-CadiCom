package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.service.UsuarioService;

@PageTitle("Panel Clientes")
@Route(value = "clientesmodificar/:userID", layout = MainView.class) // Include userID in the path
@AnonymousAllowed
@Uses(Icon.class)
public class ModificarUsuarioView extends Composite<VerticalLayout> implements HasUrlParameter<String> {
    private final UsuarioService usuarioService;
    private Usuario usuario;

    public ModificarUsuarioView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        VerticalLayout tarifaSection = createTarifasSection();
        VerticalLayout servicioSection = createServiciosSection();
        getContent().add(tarifaSection, servicioSection);
    }

    private VerticalLayout createTarifasSection() {
        VerticalLayout layout = new VerticalLayout();

        H2 titulo = new H2("Tarifas");
        TextField textField = new TextField("Tarifa");
        Button addButton = new Button("Add Tarifa");
        layout.add(titulo, textField, addButton);

        return layout;
    }

    private VerticalLayout createServiciosSection() {
        VerticalLayout layout = new VerticalLayout();

        H2 titulo = new H2("Servicios");
        TextField textField = new TextField("Servicio");
        Button addButton = new Button("Add Servicio");
        layout.add(titulo, textField, addButton);

        return layout;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String userID) {
        if (userID != null) {
            try {
                this.usuario = usuarioService.findUserById(Long.parseLong(userID));
                // Update UI components here based on the loaded user
            } catch (NumberFormatException e) {
                // Handle the exception
            }
        }
    }

}
