package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.Factura;
import es.uca.cadicom.entity.Tarifa;
import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.service.FacturaService;
import es.uca.cadicom.service.TarifaService;
import es.uca.cadicom.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@PageTitle("Panel Clientes")
@Route(value = "clientesmodificar", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ModificarClienteView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {
    private final UsuarioService usuarioService;
    private final FacturaService facturaService;
    private final TarifaService tarifaService;
    private Usuario usuario;

    public ModificarClienteView(UsuarioService usuarioService, FacturaService facturaService, TarifaService tarifaService) {
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
        this.tarifaService = tarifaService;
    }

    private VerticalLayout createDatosPersonalesSection(Usuario usuario) {

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        Set<Telefono> telefonos = usuario.getTelefonos();

        H2 hTitulo = new H2("Datos Personales");
        Button editButton = new Button(VaadinIcon.EDIT.create(), clickEvent -> System.out.println("Click on edit user!"));
        HorizontalLayout htTitleEdit = new HorizontalLayout(hTitulo, editButton);
        htTitleEdit.setWidth("100%");
        htTitleEdit.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        htTitleEdit.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);



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

        layout.add(htTitleEdit, hlNombre, hlApellidos, hlEmail, hlDni);

        return layout;
    }

    private VerticalLayout createFacturaSection(Grid<Factura> facturaGrid, Usuario usuario) {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        Set<Telefono> telefonos = usuario.getTelefonos();

        H2 titulo = new H2("Facturas");

        TextField txtImporte = new TextField("Importe");

        DatePicker txtPeriodo = new DatePicker("Periodo");

        TextField txtMinutos = new TextField("Minutos");

        ComboBox<String> select = new ComboBox<>("Telefono");
        select.setAllowCustomValue(true);
        for (Telefono telefono : telefonos){ select.setItems(telefono.getNumero()); }
        Button addButton = new Button("Add Factura");

        horizontalLayout.add(txtImporte,txtMinutos,txtPeriodo,select, addButton);

        layout.add(titulo, horizontalLayout, facturaGrid);

        return layout;
    }

    private VerticalLayout createServiciosSection() {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        Set<Telefono> telefonos = usuario.getTelefonos();

        H2 titulo = new H2("Servicios");

        TextField txtImporte = new TextField("Importe");

        DatePicker txtPeriodo = new DatePicker("Periodo");

        TextField txtMinutos = new TextField("Minutos");

        ComboBox<String> select = new ComboBox<>("Telefono");
        select.setAllowCustomValue(true);
        for (Telefono telefono : telefonos){ select.setItems(telefono.getNumero()); }
        Button addButton = new Button("Add Factura");

        horizontalLayout.add(txtImporte,txtMinutos,txtPeriodo,select, addButton);

        layout.add(titulo, horizontalLayout);

        return layout;
    }

    private VerticalLayout createTelefonosSection(Grid<Telefono> telefonoGrid) {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        List<Tarifa> tarifas = tarifaService.getTarifaAll();

        H2 titulo = new H2("Telefonos");

        TextField txtImporte = new TextField("Numero");

        ComboBox<String> select = new ComboBox<>("Tarifa");
        select.setAllowCustomValue(true);
        for (Tarifa tarifa : tarifas){ select.setItems(tarifa.getNombre()); }
        Button addButton = new Button("Add Telefono");

        horizontalLayout.add(txtImporte,select, addButton);

        layout.add(titulo, horizontalLayout, telefonoGrid);

        return layout;
    }

    @Override
    public void setParameter(BeforeEvent event, Long userID) {
        if (userID != null) {
            try {
                this.usuario = usuarioService.findUserById(userID);
                H3 nombrePerfil = new H3("Perfil de " + usuario.getNombre() + " " + usuario.getApellidos());

                TabSheet tabView = new TabSheet();
                Grid<Factura> facturaGrid = facturaGrid(this.usuario);
                Grid<Telefono> telefonoGrid = telefonoGrid(this.usuario);

                VerticalLayout datosPersonalesSection = createDatosPersonalesSection(this.usuario);
                VerticalLayout facturaSection = createFacturaSection(facturaGrid, this.usuario);
                VerticalLayout servicioSection = createServiciosSection();
                VerticalLayout telefonosSection = createTelefonosSection(telefonoGrid);

                tabView.add("Datos Personales", datosPersonalesSection);
                tabView.add("Facturas", facturaSection);
                tabView.add("Servicios", servicioSection);
                tabView.add("Telefonos", telefonosSection);
                getContent().add(nombrePerfil, tabView);
            } catch (NumberFormatException e) {
                // Handle the exception
            }
        }
    }

    private Grid<Telefono> telefonoGrid(Usuario usuario) {
        Grid<Telefono> telefonoGrid = new Grid<>(Telefono.class, false);
        telefonoGrid.removeAllColumns();
        telefonoGrid.setAllRowsVisible(true);

        Set<Telefono> telefonos = null;
        if (usuario != null) {
            telefonos = usuario.getTelefonos();
        } else {
            System.out.println("No user");
        }

        telefonoGrid.addColumn(new ComponentRenderer<>(telefono -> {
            Button editButton = new Button(VaadinIcon.EDIT.create(), clickEvent -> editTelefono(telefono));
            Button removeButton = new Button(VaadinIcon.TRASH.create(), clickEvent -> {
                removeTelefono(telefono);
            });
            removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            HorizontalLayout actionsLayout = new HorizontalLayout(editButton, removeButton);
            actionsLayout.setSpacing(true); // Adjust as needed for spacing between buttons
            return actionsLayout;
        })).setHeader("Acciones");

        telefonoGrid.addThemeVariants(GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);

        telefonoGrid.setWidth("100%");
        telefonoGrid.setItems(telefonos);
        return telefonoGrid;
    }

    private void removeTelefono(Telefono telefono) {
    }

    private void editTelefono(Telefono telefono) {
    }

    private Grid<Factura> facturaGrid(Usuario usuario) {
        Grid<Factura> facturaGrid = new Grid<>(Factura.class, false);
        facturaGrid.removeAllColumns();
        facturaGrid.setAllRowsVisible(true);

        List<Factura> facturas = new ArrayList<>();
        Set<Telefono> telefonos = null;
        if (usuario != null) {
            telefonos = usuario.getTelefonos();
        } else {
            System.out.println("No user");
        }

        for (Telefono telefono : telefonos) {
            facturas = facturaService.getAllFacturasByTelefonoId(Long.valueOf(telefono.getId()));
        }

        facturaGrid.addColumn(new ComponentRenderer<>(factura -> {
            Button editButton = new Button(VaadinIcon.EDIT.create(), clickEvent -> editFactura(factura));
            Button removeButton = new Button(VaadinIcon.TRASH.create(), clickEvent -> {
                removeFactura(factura);
            });
            removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            HorizontalLayout actionsLayout = new HorizontalLayout(editButton, removeButton);
            actionsLayout.setSpacing(true); // Adjust as needed for spacing between buttons
            return actionsLayout;
        })).setHeader("Acciones");


        facturaGrid.addThemeVariants(GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);

        facturaGrid.setWidth("100%");
        facturaGrid.setItems(facturas);
        return facturaGrid;
    }

    private void editFactura(Factura factura) {
    }

    private void removeFactura(Factura factura) {
    }

}
