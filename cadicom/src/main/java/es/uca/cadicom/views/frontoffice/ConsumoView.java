package es.uca.cadicom.views.frontoffice;

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

import es.uca.cadicom.service.ApiService;
import es.uca.cadicom.entity.*;
import es.uca.cadicom.service.TelefonoService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static es.uca.cadicom.service.ApiService.getMonthEndDate;
import static es.uca.cadicom.service.ApiService.getMonthStartDate;

@PageTitle("Consumo")
@Route(value = "consumo", layout = FrontLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ConsumoView extends Composite<VerticalLayout> {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ApiService apiService = new ApiService(restTemplate);
    public ConsumoView() {
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
        barData.setValue(0.5); //value barra barData.setValue();

        NativeLabel nlblData = new NativeLabel("100 GB");// input datos totales
        nlblData.setId("pblblData");
        h2Data.setWidth("max-content");
        barData.getElement().setAttribute("aria-labelledby", "pblblData");

        Span progressBarLabelValue = new Span("50%"); // input texto derecha
        HorizontalLayout progressBarLabel = new HorizontalLayout(nlblData, progressBarLabelValue);
        progressBarLabel.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Hr separador1 = new Hr();
        HorizontalLayout hlSms = new HorizontalLayout();
        Icon iconSms = new Icon("vaadin:comment-ellipsis");
        H2 h2Sms = new H2("SMS's");
        ProgressBar barSms = new ProgressBar();

        hlSms.setWidthFull();
        vlGeneral.setFlexGrow(1.0, hlSms);
        hlSms.addClassName(Gap.MEDIUM);
        hlSms.setWidth("100%");
        hlSms.setHeight("min-content");
        hlSms.setAlignItems(Alignment.CENTER);
        hlSms.setJustifyContentMode(JustifyContentMode.START);
        h2Sms.setWidth("max-content");
        barSms.setValue(0.5); //value barra barSms.setValue();

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


        getContent().add(vlGeneral);
        vlGeneral.add(hlData);
        hlData.add(iconData);
        hlData.add(h2Data);
        vlGeneral.add(progressBarLabel, barData);
        vlGeneral.add(separador1);
        vlGeneral.add(hlSms);
        hlSms.add(iconSms);
        hlSms.add(h2Sms);
        vlGeneral.add(barSms);
        vlGeneral.add(separador2);
        vlGeneral.add(hlLlamada);
        hlLlamada.add(iconLlamada);
        hlLlamada.add(h2Llamada);
        vlGeneral.add(barLlamada);
    }


}
