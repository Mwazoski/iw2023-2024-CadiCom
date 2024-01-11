package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import es.uca.cadicom.entity.Factura;
import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.AuthenticatedUser;
import es.uca.cadicom.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@AnonymousAllowed
@PageTitle("Facturas")
@Route(value = "facturas", layout = FrontLayout.class)
@Uses(Icon.class)
public class FacturasView extends Composite<VerticalLayout> {

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;
    private Usuario usuario;

    public FacturasView(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        Optional<Usuario> maybeUser = authenticatedUser.get();

        ComboBox<Telefono> cbTelefono = new ComboBox<>("Telefono");
        if (maybeUser.isPresent()) {
            usuario = maybeUser.get();
            cbTelefono.setItems(usuario.getTelefonos());
        }

        cbTelefono.setItemLabelGenerator(Telefono::getNumero);

        getContent().add(cbTelefono);
        Grid gFactura = new Grid(Factura.class);
        gFactura.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        gFactura.setWidth("100%");
        gFactura.getStyle().set("flex-grow", "0");
        if(!cbTelefono.isEmpty()) setGridFacturas(gFactura, cbTelefono.getValue());
        getContent().add(gFactura);
    }

    private void setGridFacturas(Grid grid, Telefono telefono) {
        grid.setItems(facturaService.getAllFacturasByTelefonoId((long)telefono.getId()));
    }

    private FacturaService facturaService;
}
