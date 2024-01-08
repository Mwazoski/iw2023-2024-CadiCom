package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.cadicom.entity.Factura;

@PageTitle("Factura")
@Route(value = "facturaspanel", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class FacturaView  extends Composite<VerticalLayout> {

    public FacturaView() {
        // Initialize the grid
        Grid<Factura> facturaGrid = FacturaGrid();

        // Optionally, set the items or data provider for the grid here
        // For example: facturaGrid.setItems(...);

        // Add the grid to the layout
        getContent().add(facturaGrid);
    }

    public Grid<Factura> FacturaGrid() {
        Grid<Factura> facturaGrid = new Grid<>(Factura.class);
        facturaGrid.addThemeVariants(GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        facturaGrid.setWidth("100%");
        return facturaGrid;
    }
}
