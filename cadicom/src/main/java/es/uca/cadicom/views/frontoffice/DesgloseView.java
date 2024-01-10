package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.*;
import es.uca.cadicom.service.ApiService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@PageTitle("Historial")
@Route(value = "historial", layout = FrontLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class DesgloseView extends Composite<VerticalLayout> {

    private ApiService apiService;

    private DatePicker dpInicio = new DatePicker("Inicio");
    private DatePicker dpFinal = new DatePicker("Final");
    public DesgloseView(){
        TabSheet tabSheet = new TabSheet();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        tabSheet.setWidth("100%");
        setTabSheet(tabSheet);

        LocalDate now = LocalDate.now(ZoneId.systemDefault());

        DatePicker dpInicio = new DatePicker("Inicio");
        DatePicker dpFinal = new DatePicker("Final");
        dpInicio.setMax(now);
        dpFinal.setMax(now);
        dpInicio.addValueChangeListener(e -> dpFinal.setMin(e.getValue()));
        dpFinal.addValueChangeListener(e -> dpInicio.setMax(e.getValue()));

        HorizontalLayout hlFechas = new HorizontalLayout();

        hlFechas.add(dpInicio, dpFinal);

        getContent().add(hlFechas);
        getContent().add(tabSheet);

    }

    private void setTabSheet(TabSheet tabSheet) {
        VerticalLayout vlLlamada = new VerticalLayout();
        Date fechaActual = new Date();


        Grid<RegistroLlamadas> gLlamada = new Grid<>(RegistroLlamadas.class);
        gLlamada.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        gLlamada.setWidth("100%");
        gLlamada.getStyle().set("flex-grow", "0");
        vlLlamada.add(gLlamada);
        List<RegistroLlamadas> Llamadas = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        getUserId().forEach(id ->{
            try {
                Llamadas.addAll(apiService.getRegistroLlamadas(id, dpInicio.getValue().format(formatter), dpFinal.getValue().format(formatter)));
            } catch (URISyntaxException | IOException | InterruptedException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
        gLlamada.setItems(Llamadas);

        VerticalLayout vlData = new VerticalLayout();

        tabSheet.add("Llamadas", vlLlamada);

        Grid<RegistroDatos> gData = new Grid<>(RegistroDatos.class);
        gData.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);

        gData.getColumnByKey("date").setHeader("Fecha");
        gData.setWidth("100%");
        gData.getStyle().set("flex-grow", "0");
        vlData.add(gData);
        List<RegistroDatos> data = new ArrayList<>();
        getUserId().forEach(id ->{
            try {
                data.addAll(apiService.getRegistroDatos(id, dpInicio.getValue().format(formatter), dpFinal.getValue().format(formatter)));
            } catch (URISyntaxException | IOException | InterruptedException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        gData.setItems(data);

        tabSheet.add("Datos", vlData);

    }

    private List<String> getUserId(){
        Usuario usuario = (Usuario) UI.getCurrent().getSession().getAttribute("user");
        Set<Telefono> telefonos = usuario.getTelefonos();

        List<String> UUIDS = new ArrayList<>();

        telefonos.forEach(e -> {
            try {
                UUIDS.add((apiService.getLineaClienteTelefono(e.getNumero())).getId());
            } catch (URISyntaxException | IOException | InterruptedException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });

        return UUIDS;
    }
}
