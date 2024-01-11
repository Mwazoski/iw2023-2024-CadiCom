package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
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
import es.uca.cadicom.service.TelefonoService;
import es.uca.cadicom.service.UsuarioService;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Panel Clientes")
@Route(value = "clientesmodificar", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ModificarClienteView extends Composite<VerticalLayout> implements HasUrlParameter<Long> {
    private final UsuarioService usuarioService;
    private final FacturaService facturaService;
    private final TarifaService tarifaService;
    private final TelefonoService telefonoService;
    private Usuario usuario;

    public ModificarClienteView(UsuarioService usuarioService, FacturaService facturaService, TarifaService tarifaService, TelefonoService telefonoService) {
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
        this.tarifaService = tarifaService;
        this.telefonoService = telefonoService;
    }

    private VerticalLayout datosPersonalesSection(Button button , Usuario usuario, VerticalLayout showDatosPersonalesSection, VerticalLayout editableDatosPersonalesSection, Long userID  ) {

        H2 hTitulo = new H2("Datos Personales");
        HorizontalLayout htTitleEdit = new HorizontalLayout(hTitulo, button);
        htTitleEdit.setWidth("100%");
        htTitleEdit.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        htTitleEdit.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        button.addClickListener(clickEvent -> {
            if (showDatosPersonalesSection.isVisible()){
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

    private VerticalLayout showDatosPersonalesSection(Usuario usuario, Long userID) {

        usuario = usuarioService.findUserById(userID);

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        Set<Telefono> telefonos = usuario.getTelefonos();

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

    private VerticalLayout editableDatosPersonalesSection(Button saveButton, Usuario usuario) {

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

    private VerticalLayout createFacturaSection(Grid<Factura> facturaGrid, Usuario usuario) {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);
        Set<Telefono> telefonos = usuario.getTelefonos();

        H2 titulo = new H2("Facturas");

        @NotBlank
        TextField txtImporte = new TextField("Importe");

        @NotBlank
        DatePicker txtPeriodo = new DatePicker("Periodo");

        @NotBlank
        TextField txtMinutos = new TextField("Minutos");

        @NotBlank
        ComboBox<String> select = new ComboBox<>("Telefono");
        select.setAllowCustomValue(true);
        List<String> numtelefonos = telefonos.stream()
                .map(Telefono::getNumero)
                .collect(Collectors.toList());
        select.setItems(numtelefonos);

        Button addButton = new Button("Add Factura");
        addButton.addClickListener(clickEvent -> {

            Factura factura = new Factura();
            Double importe = Double.valueOf(txtImporte.getValue());
            String periodo = String.valueOf(txtPeriodo.getValue());
            Integer minutos = Integer.valueOf(txtMinutos.getValue());
            String telefono = select.getValue();

            if (!importe.isNaN()) factura.setImporte(importe);
            if (!periodo.isEmpty()) factura.setPeriodo(periodo);
            if (minutos != 0) factura.setMinutos(minutos);
            if (!telefono.isEmpty()) {
                Set<Telefono> ts = usuario.getTelefonos();
                for (Telefono t : telefonos){
                    if (t.getNumero().equals(telefono)) factura.setTelefono(t);
                }
            }
            facturaService.createFactura(factura);
            UI.getCurrent().getPage().reload();
        });

        horizontalLayout.add(txtPeriodo, select, txtMinutos, txtImporte, addButton);

        layout.add(titulo, horizontalLayout, facturaGrid);

        return layout;
    }


    private VerticalLayout createTelefonosSection(Grid<Telefono> telefonoGrid, Usuario usuario) {
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
                for (Tarifa t : aux){
                    if (t.getNombre().equals(txtTarifa)) telefono.setTarifa(t);
                }
            }
            telefono.setUsuario(usuario);
            telefonoService.createTelefono(telefono);
            UI.getCurrent().getPage().reload();
        });

        horizontalLayout.add(txtNumero,select, addButton);

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
                Button button = new Button(VaadinIcon.EDIT.create());

                VerticalLayout showDatosPersonalesSection = showDatosPersonalesSection(this.usuario, userID);
                showDatosPersonalesSection.setVisible(true);
                VerticalLayout editableDatosPersonalesSection = editableDatosPersonalesSection(button, this.usuario);
                editableDatosPersonalesSection.setVisible(false);

                VerticalLayout datosPersonalesSection = datosPersonalesSection(button, this.usuario, showDatosPersonalesSection, editableDatosPersonalesSection, userID);
                VerticalLayout facturaSection = createFacturaSection(facturaGrid, this.usuario);
                VerticalLayout telefonosSection = createTelefonosSection(telefonoGrid, this.usuario);

                tabView.add("Datos Personales", datosPersonalesSection);
                tabView.add("Facturas", facturaSection);
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

        telefonoGrid.addColumn(Telefono::getNumero).setHeader("Numero");
        telefonoGrid.addColumn(new ComponentRenderer<>(telefono -> {
            // Assuming getTarifa() returns a Tarifa object and you want to display its name
            String tarifaName = telefono.getTarifa() != null ? telefono.getTarifa().getNombre() : "N/A";
            return new Text(tarifaName);
        })).setHeader("Tarifa");
        telefonoGrid.addColumn(Telefono::getRoaming).setHeader("Roaming");
        telefonoGrid.addColumn(Telefono::getCompartirDatos).setHeader("Datos");
        telefonoGrid.addColumn(new ComponentRenderer<>(telefono -> {
//            Button editButton = new Button(VaadinIcon.EDIT.create(), clickEvent -> editTelefono(telefono));
            Button removeButton = new Button(VaadinIcon.TRASH.create(), clickEvent -> {
                removeTelefono(telefono);
            });
            removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            HorizontalLayout actionsLayout = new HorizontalLayout(removeButton);
            actionsLayout.setSpacing(true);
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

//    private void editTelefono(Telefono telefono) {
//
//    }

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

        facturaGrid.addColumn(Factura::getPeriodo).setHeader("Periodo");
        facturaGrid.addColumn(Factura::getTelefono).setHeader("Telefono");
        facturaGrid.addColumn(Factura::getMinutos).setHeader("Minutos");
        facturaGrid.addColumn(Factura::getImporte).setHeader("Importe");
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

    private void editFactura(Factura factura) { }

    private void removeFactura(Factura factura) { }

}
