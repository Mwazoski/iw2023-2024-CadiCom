package es.uca.cadicom.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import es.uca.cadicom.components.Header;
@PermitAll
@CssImport("./styles/landingpage.css")
@Route(value = "")
@PageTitle("CadiCom")
@AnonymousAllowed
public class LandingPage extends VerticalLayout {

    public LandingPage() {

        addClassName("background");

        Header header = new Header();

        add(header);
    }
}
