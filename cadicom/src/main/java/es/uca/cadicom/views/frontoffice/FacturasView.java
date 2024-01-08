package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import es.uca.cadicom.entity.Factura;
import es.uca.cadicom.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@AnonymousAllowed
@PageTitle("Facturas")
@Route(value = "facturas", layout = FrontLayout.class)
@Uses(Icon.class)
public class FacturasView extends Composite<VerticalLayout> {

    public FacturasView() {
        Grid gFactura = new Grid(Factura.class);
        gFactura.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        gFactura.setWidth("100%");
        gFactura.getStyle().set("flex-grow", "0");
        setGridFacturas(gFactura);
        getContent().add(gFactura);
    }

    private void setGridFacturas(Grid grid) {
        //grid.setItems(query -> facturaService.list(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
    }

    //@Autowired()
    private FacturaService facturaService;
}
