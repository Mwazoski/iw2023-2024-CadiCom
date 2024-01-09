package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.Tarifa;
import es.uca.cadicom.service.TarifaService;

import java.util.List;

@PageTitle("Panel Tarifas")
@Route(value = "tarifaspanel", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class TarifasView extends Composite<VerticalLayout> {

    private final TarifaService tarifaService;
    public TarifasView(TarifaService tarifaService) {
        this.tarifaService = tarifaService;

        Grid<Tarifa> tarifaGrid = TarifaGrid();
        getContent().add(tarifaGrid);
    }

    private Grid<Tarifa> TarifaGrid() {
        Grid<Tarifa> tarifaGrid = new Grid<>(Tarifa.class);
        tarifaGrid.removeAllColumns();

        tarifaGrid.addColumn(Tarifa::getNombre).setHeader("Nombre");
        tarifaGrid.addColumn(Tarifa::getDatos).setHeader("Datos");
        tarifaGrid.addColumn(Tarifa::getMinutos).setHeader("Minutos");
        tarifaGrid.addColumn(Tarifa::getPrecio).setHeader("Precio");

        tarifaGrid.addColumn(new ComponentRenderer<>(Tarifa -> {
            Button usuariosButton = new Button(VaadinIcon.USERS.create(), clickEvent -> getUsuarios(Tarifa));
            Button editButton = new Button(VaadinIcon.EDIT.create(), clickEvent -> editTarifa(Tarifa));
            Button removeButton = new Button(VaadinIcon.TRASH.create(), clickEvent -> removeTarifa(Tarifa));
            removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            HorizontalLayout actionsLayout = new HorizontalLayout(usuariosButton, editButton, removeButton);
            actionsLayout.setSpacing(true);
            return actionsLayout;
        })).setHeader("Acciones");

        tarifaGrid.addThemeVariants(GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);

        tarifaGrid.setWidth("100%");
        // Fetch and set the data from getAllUsers
        List<Tarifa> tarifas = tarifaService.getTarifaAll();
        tarifaGrid.setItems(tarifas);

        return tarifaGrid;
    }

    private void getUsuarios(Tarifa tarifa) {
        // UI.getCurrent().navigate("clientesmodificar/" + usuario.getId());
        System.out.println("Cogiendo usuarios");
    }
    private void editTarifa(Tarifa tarifa) {
        // UI.getCurrent().navigate("clientesmodificar/" + usuario.getId());
        System.out.println("Editando tarifas");
    }

    private void removeTarifa(Tarifa tarifa) {
        System.out.println("Removing Tarifa");
    }

}
