package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

import es.uca.cadicom.entity.LineaCliente;
import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.service.ApiService;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

@PageTitle("Consumo")
@Route(value = "consumo", layout = FrontLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ConsumoView extends Composite<VerticalLayout> {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ApiService apiService = new ApiService(restTemplate);

    public ConsumoView() {
        Usuario usuario = (Usuario) UI.getCurrent().getSession().getAttribute("user");
        VerticalLayout vlGeneral = new VerticalLayout();

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

        Span progressBarLabelValue = new Span("50%"); // input texto derecha
        HorizontalLayout progressBarLabel = new HorizontalLayout(nlblData, progressBarLabelValue);
        progressBarLabel.setJustifyContentMode(JustifyContentMode.BETWEEN);

        setProgressBarValueData(barData, progressBarLabelValue, nlblData);

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
        barLlamada.setValue(0.5); //value barra barLlamada.setValue();

        NativeLabel nlblLlamada = new NativeLabel("100 Sec");// input datos totales
        nlblLlamada.setId("pblblData");
        h2Data.setWidth("max-content");
        barData.getElement().setAttribute("aria-labelledby", "pblblData");

        Span progressBarLabelValueLlamada = new Span("50%"); // input texto derecha
        HorizontalLayout progressBarLabelLlamada = new HorizontalLayout(nlblLlamada, progressBarLabelValueLlamada);
        progressBarLabelLlamada.setJustifyContentMode(JustifyContentMode.BETWEEN);

        setProgressBarValueData(barLlamada, progressBarLabelValueLlamada, nlblLlamada);


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

    private void setProgressBarValueData(ProgressBar progressBar, Span total, NativeLabel value) {
        Usuario usuario = (Usuario) UI.getCurrent().getSession().getAttribute("user");
        // Set<Telefono> telefonos = usuario.getTelefonos();
        LineaCliente lineaCliente = null;
        Double datosTarifa = 4000d; //susrituir
        Double relacion;
        try {
            lineaCliente = apiService.getLineaCliente("0cd84b29-4f4e-4b9b-9658-6c0eb8c0f8c9");

            relacion = apiService.getRegistroDatosSuma(lineaCliente.getId(), "2000-11-12", "2024-12-12") / datosTarifa;
            progressBar.setValue(relacion);
            value.setText(String.valueOf((int) (relacion * 100)));
            total.setText(Double.toString(relacion).split("\\.")[0]);
        } catch (URISyntaxException | IOException | InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void setProgressBarValueSec(ProgressBar progressBar, Span total, NativeLabel value) {
        Usuario usuario = (Usuario) UI.getCurrent().getSession().getAttribute("user");
        Set<Telefono> telefonos = usuario.getTelefonos();
        LineaCliente lineaCliente = null;
        Double secTarifa = 4000d; //susrituir
        Double relacion;
        try {
            lineaCliente = apiService.getLineaCliente("0cd84b29-4f4e-4b9b-9658-6c0eb8c0f8c9");

            relacion = apiService.getRegistroLlamadasSuma(lineaCliente.getId(), "2000-11-12", "2024-12-12")/secTarifa;
            progressBar.setValue(relacion);
            value.setText(String.valueOf((int)(relacion*100)));
            total.setText(Double.toString(relacion).split("\\.")[0]);
        } catch (URISyntaxException | IOException | InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}