package org.vaadin.example;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.vaadin.example.components.Header;
@PermitAll
@CssImport("styles/landingpage.css")
@Route("/")
public class LandingPage extends VerticalLayout {

    public LandingPage() {

        addClassName("background");

        Header header = new Header();

        add(header);
    }
}
