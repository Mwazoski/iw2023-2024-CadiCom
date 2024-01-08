package es.uca.cadicom.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.Tarifa;
import es.uca.cadicom.service.TarifaService;
import jakarta.annotation.security.PermitAll;
import es.uca.cadicom.components.Header;

import java.util.List;

@PermitAll
@CssImport("./styles/landingpage.css")
@Route(value = "")
@PageTitle("CadiCom")
@AnonymousAllowed
public class LandingPage extends VerticalLayout {

    private TarifaService tarifaService;

    public LandingPage(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
        addClassName("background");

        Header header = new Header();
        add(header);
    }

}