package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.Factura;
import es.uca.cadicom.entity.Tarifa;
import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.service.TarifaService;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.util.List;
import java.util.Set;

@PageTitle("Panel Tarifas")
@Route(value = "tarifaspanel", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class TarifasView extends Composite<VerticalLayout> {

    private final TarifaService tarifaService;
    private final Grid<Tarifa> tarifaGrid;
    public TarifasView(TarifaService tarifaService) {
        this.tarifaService = tarifaService;

        VerticalLayout editTarifaForm = createEditTarifaSection();
        tarifaGrid = TarifaGrid();
        getContent().add(editTarifaForm, tarifaGrid);
    }

    private VerticalLayout createEditTarifaSection() {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.END);

        H2 titulo = new H2("Crear Tarifa");

        TextField txtNombre = new TextField("Nombre");

        NumberField txtDatos = new NumberField("Datos");

        TextField txtMinutos = new TextField("Minutos");

        NumberField txtPrecio = new NumberField("Precio");

        Button saveButton = new Button("Crear Tarifa");
        saveButton.addClickListener(clickEvent -> {

            Tarifa tarifa = new Tarifa();
            String nombre = txtNombre.getValue();
            Double datos = txtDatos.getValue();
            int minutos = Integer.parseInt(txtMinutos.getValue());
            Double precio = txtPrecio.getValue();

            if (!datos.isNaN()) tarifa.setDatos(datos);
            if (!nombre.isEmpty()) tarifa.setNombre(nombre);
            if (!precio.isNaN()) tarifa.setPrecio(precio);
            if (minutos != 0) tarifa.setMinutos(minutos);

            tarifaService.createTarifa(tarifa);
            UI.getCurrent().getPage().reload();
        });
        horizontalLayout.add(txtNombre, txtDatos, txtMinutos, txtPrecio, saveButton);

        layout.add(titulo, horizontalLayout);

        return layout;
    }

    private Grid<Tarifa> TarifaGrid() {
        Grid<Tarifa> tarifaGrid = new Grid<>(Tarifa.class);
        tarifaGrid.removeAllColumns();

        tarifaGrid.addColumn(Tarifa::getNombre).setHeader("Nombre");
        tarifaGrid.addColumn(Tarifa::getDatos).setHeader("Datos");
        tarifaGrid.addColumn(Tarifa::getMinutos).setHeader("Minutos");
        tarifaGrid.addColumn(Tarifa::getPrecio).setHeader("Precio");

        tarifaGrid.addColumn(new ComponentRenderer<>(Tarifa -> {
            Button removeButton = new Button(VaadinIcon.TRASH.create(), clickEvent -> removeTarifa(Tarifa));
            removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            HorizontalLayout actionsLayout = new HorizontalLayout(removeButton);
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

    private void removeTarifa(Tarifa tarifa) {
        tarifaService.deleteTarifa(tarifa.getId());
        Notification notification = new Notification();
        notification.setText("Tarifa eliminada correctamente");
        notification.setDuration(3000);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.open();
        updateGrid();
    }

    private void updateGrid() {
        List<Tarifa> updatedTarifas = tarifaService.getTarifaAll();
        this.tarifaGrid.setItems(updatedTarifas);
    }

}
