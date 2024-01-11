package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.cadicom.entity.Tarifa;
import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.AuthenticatedUser;
import es.uca.cadicom.views.backoffice.MainView;
import jakarta.annotation.security.PermitAll;

import java.util.Optional;


@PermitAll
@PageTitle("Contratos")
@Route(value = "contratos", layout = FrontLayout.class)
@Uses(Icon.class)
public class ContratosView extends Composite<VerticalLayout> {

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;
    private Usuario usuario;

    public ContratosView(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        ComboBox<Telefono> cbTelefono = new ComboBox<>("Telefono");

        Optional<Usuario> maybeUser = authenticatedUser.get();

        if (maybeUser.isPresent()){
            usuario = maybeUser.get();
            cbTelefono.setItems(usuario.getTelefonos());
        }
        cbTelefono.setItemLabelGenerator(Telefono::getNumero);

        VerticalLayout vlGeneral = new VerticalLayout();
        vlGeneral.add(cbTelefono);
        cbTelefono.addValueChangeListener(event -> {

            Tarifa tarifa = cbTelefono.getValue().getTarifa();

            H5 hNombre = new H5("Nombre");
            Paragraph pNombre = new Paragraph(tarifa.getNombre());
            HorizontalLayout hlNombre = new HorizontalLayout(hNombre, pNombre);
            hlNombre.setAlignItems(FlexComponent.Alignment.CENTER);

            H5 hDatos = new H5("Datos");
            Paragraph pDatos = new Paragraph(tarifa.getDatos().toString());
            HorizontalLayout hlDatos = new HorizontalLayout(hDatos, pDatos);
            hlDatos.setAlignItems(FlexComponent.Alignment.CENTER);

            H5 hMinutos = new H5("Minutos");
            Paragraph pMinutos = new Paragraph(tarifa.getMinutos().toString());
            HorizontalLayout hlMinutos = new HorizontalLayout(hMinutos, pMinutos);
            hlMinutos.setAlignItems(FlexComponent.Alignment.CENTER);

            getContent().add(hlNombre,hlDatos,hlMinutos);

        });
        H1 hTarifa = new H1("Tarifa");
        getContent().add(cbTelefono, hTarifa);
    }
}