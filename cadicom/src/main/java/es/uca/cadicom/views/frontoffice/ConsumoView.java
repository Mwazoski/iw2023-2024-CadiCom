package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import es.uca.cadicom.entity.LineaCliente;
import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.AuthenticatedUser;
import es.uca.cadicom.service.ApiService;
import es.uca.cadicom.service.FacturaService;
import es.uca.cadicom.service.UsuarioService;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Set;

@PageTitle("Consumo")
@Route(value = "consumo", layout = FrontLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ConsumoView extends Composite<VerticalLayout> {

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;
    private Usuario usuario;
    LocalDate now = LocalDate.now(ZoneId.systemDefault());
    private final RestTemplate restTemplate;
    private final UsuarioService usuarioService;
    private final ApiService apiService;
    private final FacturaService facturaService;


    public ConsumoView(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker, RestTemplate restTemplate, ApiService apiService, UsuarioService usuarioService, FacturaService facturaService) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.restTemplate = restTemplate;
        this.apiService = apiService;
        this.usuarioService = usuarioService;
        this.facturaService = facturaService;
        Optional<Usuario> maybeUser = authenticatedUser.get();


        ComboBox<Telefono> cbTelefono = new ComboBox<>("Telefono");
        if (maybeUser.isPresent()) {
            usuario = maybeUser.get();
            cbTelefono.setItems(usuario.getTelefonos());
        }

        cbTelefono.setItemLabelGenerator(Telefono::getNumero);

        VerticalLayout vlGeneral = new VerticalLayout();
        vlGeneral.add(cbTelefono);

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        vlGeneral.setWidthFull();
        getContent().setFlexGrow(1.0, vlGeneral);
        vlGeneral.addClassName(Gap.XLARGE);
        vlGeneral.addClassName(Padding.XLARGE);
        vlGeneral.setWidth("100%");
        vlGeneral.getStyle().set("flex-grow", "1");
        vlGeneral.setJustifyContentMode(JustifyContentMode.START);
        vlGeneral.setAlignItems(Alignment.CENTER);

        HorizontalLayout hlData = new HorizontalLayout();
        Icon iconData = new Icon("lumo:bar-chart");
        H2 h2Data = new H2("Datos móviles");
        ProgressBar barData = new ProgressBar();

        hlData.setWidthFull();
        vlGeneral.setFlexGrow(1.0, hlData);
        hlData.addClassName(Gap.MEDIUM);
        hlData.setWidth("100%");
        hlData.setHeight("min-content");
        hlData.setAlignItems(Alignment.CENTER);
        hlData.setJustifyContentMode(JustifyContentMode.START);
        //barData.setValue(0.5); //value barra barData.setValue();


        NativeLabel nlblData = new NativeLabel("100 MB");// input datos totales
        nlblData.setId("pblblData");
        h2Data.setWidth("max-content");
        barData.getElement().setAttribute("aria-labelledby", "pblblData");

        Span progressBarLabelValueData = new Span("50%"); // input texto derecha
        HorizontalLayout progressBarLabel = new HorizontalLayout(nlblData, progressBarLabelValueData);
        progressBarLabel.setJustifyContentMode(JustifyContentMode.BETWEEN);



        Hr separador2 = new Hr();
        HorizontalLayout hlLlamada = new HorizontalLayout();
        Icon iconLlamada = new Icon("lumo:phone");
        H2 h2Llamada = new H2("Llamadas");
        ProgressBar barLlamada = new ProgressBar();

        hlLlamada.setWidthFull();
        vlGeneral.setFlexGrow(1.0, hlLlamada);
        hlLlamada.addClassName(Gap.MEDIUM);
        hlLlamada.setWidth("100%");
        hlLlamada.setHeight("min-content");
        hlLlamada.setAlignItems(Alignment.CENTER);
        hlLlamada.setJustifyContentMode(JustifyContentMode.START);
        h2Llamada.setWidth("max-content");
        //barLlamada.setValue(0.5); //value barra barLlamada.setValue();

        NativeLabel nlblLlamada = new NativeLabel("100 Sec");// input datos totales
        nlblLlamada.setId("pblblData");
        h2Data.setWidth("max-content");
        barData.getElement().setAttribute("aria-labelledby", "pblblData");

        Span progressBarLabelValueLlamada = new Span("0%"); // input texto derecha
        HorizontalLayout progressBarLabelLlamada = new HorizontalLayout(nlblLlamada, progressBarLabelValueLlamada);
        progressBarLabelLlamada.setJustifyContentMode(JustifyContentMode.BETWEEN);

        cbTelefono.addValueChangeListener(event -> {
            setProgressBarValueData(barData, progressBarLabelValueData, nlblData, event.getValue());
            setProgressBarValueData(barLlamada, progressBarLabelValueLlamada, nlblLlamada, event.getValue());
        });

        getContent().add(vlGeneral);
        vlGeneral.add(hlData);
        hlData.add(iconData);
        hlData.add(h2Data);
        vlGeneral.add(progressBarLabel, barData);
        vlGeneral.add(separador2);
        vlGeneral.add(hlLlamada);
        hlLlamada.add(iconLlamada);
        hlLlamada.add(h2Llamada);
        vlGeneral.add(progressBarLabel, barLlamada);

    }

    private void setProgressBarValueData(ProgressBar progressBar, Span total, NativeLabel value, Telefono telefono) {
        LineaCliente lineaCliente = null;
        Double datosTarifa = telefono.getTarifa().getDatos();
        Double relacion;
        try {
            lineaCliente = apiService.getLineaClienteTelefono(telefono.getNumero());
            relacion = apiService.getRegistroDatosSuma(lineaCliente.getId(), apiService.getMonthStartDate(now.getYear(), now.getMonthValue()), apiService.getMonthEndDate(now.getYear(), now.getMonthValue())) / datosTarifa;
            progressBar.setValue(0.5);
            value.setText(String.valueOf((int) (relacion * 100)));
            total.setText(Double.toString(relacion).split("\\.")[0]);
        } catch (URISyntaxException | IOException | InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void setProgressBarValueSec(ProgressBar progressBar, Span total, NativeLabel value, Telefono telefono) {
        LineaCliente lineaCliente = null;
        Double secTarifa = (double) telefono.getTarifa().getMinutos();
        Double relacion;
        try {
            lineaCliente = apiService.getLineaClienteTelefono(telefono.getNumero());
            relacion = apiService.getRegistroLlamadasSuma(lineaCliente.getId(), apiService.getMonthStartDate(now.getYear(), now.getMonthValue()), apiService.getMonthEndDate(now.getYear(), now.getMonthValue()))/secTarifa;
            progressBar.setValue(relacion);
            value.setText(String.valueOf((int)(relacion*100)));
            total.setText(Double.toString(relacion).split("\\.")[0]);
        } catch (URISyntaxException | IOException | InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}