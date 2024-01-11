package es.uca.cadicom.views.frontoffice;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import es.uca.cadicom.entity.Tarifa;
import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.AuthenticatedUser;
import es.uca.cadicom.service.TarifaService;
import es.uca.cadicom.service.TelefonoService;
import es.uca.cadicom.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Usuario")
@Route(value = "usuario", layout = FrontLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
@Uses(Icon.class)
public class UserView extends Composite<VerticalLayout> {
    private final UsuarioService usuarioService;
    private Usuario usuario;
    private final AuthenticatedUser authenticatedUser;
    private final TarifaService tarifaService;
    private final TelefonoService telefonoService;

    public UserView(UsuarioService usuarioService, TarifaService tarifaService, TelefonoService telefonoService, AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.usuarioService = usuarioService;
        this.tarifaService = tarifaService;
        this.telefonoService = telefonoService;
        this.authenticatedUser = authenticatedUser;
        Optional<Usuario> maybeUser = authenticatedUser.get();

        if (maybeUser.isPresent()) {
            usuario = maybeUser.get();
        }

        H3 nombrePerfil = new H3("Perfil de " + usuario.getNombre() + " " + usuario.getApellidos());

        TabSheet tabView = new TabSheet();
        Button button = new Button(VaadinIcon.EDIT.create());

        VerticalLayout showDatosPersonalesSection = showDatosPersonalesSection();
        showDatosPersonalesSection.setVisible(true);
        VerticalLayout editableDatosPersonalesSection = editableDatosPersonalesSection(button);
        editableDatosPersonalesSection.setVisible(false);

        VerticalLayout datosPersonalesSection = datosPersonalesSection(button, showDatosPersonalesSection, editableDatosPersonalesSection);
        Grid<Telefono> telefonoGrid = telefonoGrid();
        VerticalLayout telefonosSection = createTelefonosSection(telefonoGrid);

        tabView.add("Datos Personales", datosPersonalesSection);
        tabView.add("Telefonos", telefonosSection);
        getContent().add(nombrePerfil, tabView);
    }

    private Grid<Telefono> telefonoGrid() {
        Grid<Telefono> telefonoGrid = new Grid<>(Telefono.class, false);
        telefonoGrid.removeAllColumns();
        telefonoGrid.setAllRowsVisible(true);

        Set<Telefono> telefonos = null;
        if (usuario != null) {
            telefonos = usuario.getTelefonos();
        } else {
            System.out.println("No user");
        }

        telefonoGrid.addColumn(Telefono::getNumero).setHeader("Numero");
        telefonoGrid.addColumn(new ComponentRenderer<>(telefono -> {
            String tarifaName = telefono.getTarifa() != null ? telefono.getTarifa().getNombre() : "N/A";
            return new Text(tarifaName);
        })).setHeader("Tarifa");
        telefonoGrid.addColumn(new ComponentRenderer<>(telefono -> {
            return new Text(telefono.getRoaming() ? "SI" : "NO");
        })).setHeader("Roaming");

        telefonoGrid.addColumn(new ComponentRenderer<>(telefono -> {
            return new Text(telefono.getCompartirDatos() ? "SI" : "NO");
        })).setHeader("Datos");

        telefonoGrid.addThemeVariants(GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);

        telefonoGrid.setWidth("100%");
        telefonoGrid.setItems(telefonos);
        return telefonoGrid;
    }

    private VerticalLayout datosPersonalesSection(Button button, VerticalLayout showDatosPersonalesSection, VerticalLayout editableDatosPersonalesSection) {

        H2 hTitulo = new H2("Datos Personales");
        HorizontalLayout htTitleEdit = new HorizontalLayout(hTitulo, button);
        htTitleEdit.setWidth("100%");
        htTitleEdit.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        htTitleEdit.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        button.addClickListener(clickEvent -> {
            if (showDatosPersonalesSection.isVisible()) {
                button.setIcon(VaadinIcon.SAFE.create());
                showDatosPersonalesSection.setVisible(false);
                editableDatosPersonalesSection.setVisible(true);

            } else {
                button.setIcon(VaadinIcon.EDIT.create());
                editableDatosPersonalesSection.setVisible(false);
                UI.getCurrent().getPage().reload();
            }
        });

        VerticalLayout vParent = new VerticalLayout(htTitleEdit, showDatosPersonalesSection, editableDatosPersonalesSection);
        return vParent;
    }

    private VerticalLayout showDatosPersonalesSection() {

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");

        H5 hNombre = new H5("Nombre");
        Paragraph pNombre = new Paragraph(usuario.getNombre());
        HorizontalLayout hlNombre = new HorizontalLayout(hNombre, pNombre);
        hlNombre.setAlignItems(FlexComponent.Alignment.CENTER);

        H5 hApellidos = new H5("Apellidos");
        Paragraph pApellidos = new Paragraph(usuario.getApellidos());
        HorizontalLayout hlApellidos = new HorizontalLayout(hApellidos, pApellidos);
        hlApellidos.setAlignItems(FlexComponent.Alignment.CENTER);

        H5 hDni = new H5("Dni");
        Paragraph pDni = new Paragraph(usuario.getDni());
        HorizontalLayout hlDni = new HorizontalLayout(hDni, pDni);
        hlDni.setAlignItems(FlexComponent.Alignment.CENTER);

        H5 hEmail = new H5("Email");
        Paragraph pEmail = new Paragraph(usuario.getEmail());
        HorizontalLayout hlEmail = new HorizontalLayout(hEmail, pEmail);
        hlEmail.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(hlNombre, hlApellidos, hlEmail, hlDni);

        return layout;
    }

    private VerticalLayout editableDatosPersonalesSection(Button saveButton) {

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");

        H5 hNombre = new H5("Nombre");
        TextField pNombre = new TextField();
        pNombre.setPlaceholder(usuario.getNombre());
        HorizontalLayout hlNombre = new HorizontalLayout(hNombre, pNombre);
        hlNombre.setAlignItems(FlexComponent.Alignment.CENTER);

        H5 hApellidos = new H5("Apellidos");
        TextField pApellidos = new TextField();
        pApellidos.setPlaceholder(usuario.getApellidos());
        HorizontalLayout hlApellidos = new HorizontalLayout(hApellidos, pApellidos);
        hlApellidos.setAlignItems(FlexComponent.Alignment.CENTER);

        H5 hDni = new H5("Dni");
        TextField pDni = new TextField();
        pDni.setPlaceholder(usuario.getDni());
        HorizontalLayout hlDni = new HorizontalLayout(hDni, pDni);
        hlDni.setAlignItems(FlexComponent.Alignment.CENTER);

        H5 hEmail = new H5("Email");
        TextField pEmail = new TextField();
        pEmail.setPlaceholder(usuario.getEmail());
        HorizontalLayout hlEmail = new HorizontalLayout(hEmail, pEmail);
        hlEmail.setAlignItems(FlexComponent.Alignment.CENTER);

        saveButton.addClickListener(clickEvent -> {

            String newNombre = pNombre.getValue();
            String newApellidos = pApellidos.getValue();
            String newDni = pDni.getValue();
            String newEmail = pEmail.getValue();

            if (!newNombre.isEmpty()) usuario.setNombre(newNombre);
            if (!newApellidos.isEmpty()) usuario.setApellidos(newApellidos);
            if (!newDni.isEmpty()) usuario.setDni(newDni);
            if (!newEmail.isEmpty()) usuario.setEmail(newEmail);

            usuarioService.updateUser(usuario);

        });

        layout.add(hlNombre, hlApellidos, hlEmail, hlDni);

        return layout;
    }

    private VerticalLayout createTelefonosSection(Grid<Telefono> telefonoGrid) {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        List<Tarifa> tarifas = tarifaService.getTarifaAll();

        H2 titulo = new H2("Telefonos");

        TextField txtNumero = new TextField("Numero");

        ComboBox<String> select = new ComboBox<>("Tarifa");
        select.setAllowCustomValue(true);
        List<String> tarifaNames = tarifas.stream()
                .map(Tarifa::getNombre)
                .collect(Collectors.toList());
        select.setItems(tarifaNames);

        Button addButton = new Button("Add Telefono");
        addButton.addClickListener(clickEvent -> {

            Telefono telefono = new Telefono();
            String numero = txtNumero.getValue();
            String txtTarifa = select.getValue();

            if (!numero.isEmpty()) telefono.setNumero(numero);
            if (!txtTarifa.isEmpty()) {
                List<Tarifa> aux = tarifaService.getTarifaAll();
                for (Tarifa t : aux) {
                    if (t.getNombre().equals(txtTarifa)) telefono.setTarifa(t);
                }
            }
            telefono.setUsuario(usuario);
            telefonoService.createTelefono(telefono);
            UI.getCurrent().getPage().reload();
        });

        horizontalLayout.add(txtNumero, select, addButton);

        layout.add(titulo, horizontalLayout, telefonoGrid);

        return layout;
    }

    private void removeTelefono(Telefono telefono) {

    }

//    private void editTelefono(Telefono telefono) {
//
//    }

}

