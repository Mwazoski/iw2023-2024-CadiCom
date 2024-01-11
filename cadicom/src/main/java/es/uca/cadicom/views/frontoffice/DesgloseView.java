package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.Composite;
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
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import es.uca.cadicom.entity.*;
import es.uca.cadicom.security.AuthenticatedUser;
import es.uca.cadicom.service.ApiService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@PageTitle("Historial")
@Route(value = "historial", layout = FrontLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class DesgloseView extends Composite<VerticalLayout> {

    private final AccessAnnotationChecker accessChecker;
    private final AuthenticatedUser authenticatedUser;
    private Usuario usuario;
    private ApiService apiService;

    private final DatePicker dpInicio = new DatePicker("Inicio");
    private final DatePicker dpFinal = new DatePicker("Final");
    public DesgloseView(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, ApiService apiService) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.apiService = apiService;

        Optional<Usuario> maybeUser = authenticatedUser.get();

        if (maybeUser.isPresent()) { usuario = maybeUser.get();}

        TabSheet tabSheet = new TabSheet();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        tabSheet.setWidth("100%");

        LocalDate now = LocalDate.now(ZoneId.systemDefault());

        DatePicker dpInicio = new DatePicker("Inicio");
        DatePicker dpFinal = new DatePicker("Final");
        dpInicio.setMax(now);
        dpInicio.setInitialPosition(now);
        dpFinal.setMax(now);
        dpFinal.setOpened(false);
        dpInicio.addValueChangeListener(event -> {
                    dpFinal.setOpened(true);
        });
        dpInicio.addValueChangeListener(e -> dpFinal.setMin(e.getValue()));
        dpFinal.addValueChangeListener(e -> {
            dpInicio.setMax(e.getValue());
            setTabSheet(tabSheet);
        });

        HorizontalLayout hlFechas = new HorizontalLayout();

        hlFechas.add(dpInicio, dpFinal);
        getContent().add(hlFechas);
        getContent().add(tabSheet);

    }

    private void setTabSheet(TabSheet tabSheet) {
        VerticalLayout vlLlamada = new VerticalLayout();

        Grid<RegistroLlamadas> gLlamada = new Grid<>(RegistroLlamadas.class);
        gLlamada.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        gLlamada.setWidth("100%");
        gLlamada.getStyle().set("flex-grow", "0");
        vlLlamada.add(gLlamada);
        List<RegistroLlamadas> Llamadas = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        getUserTelefonosIds().forEach(id ->{
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
        getUserTelefonosIds().forEach(id ->{
            try {
                data.addAll(apiService.getRegistroDatos(id, dpInicio.getValue().format(formatter), dpFinal.getValue().format(formatter)));
            } catch (URISyntaxException | IOException | InterruptedException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        gData.setItems(data);

        tabSheet.add("Datos", vlData);

    }

    private List<String> getUserTelefonosIds(){

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
