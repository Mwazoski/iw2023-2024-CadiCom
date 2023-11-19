package org.vaadin.example;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route("/panel")
public class panelView extends VerticalLayout {
    public panelView() {
        MainView main = new MainView();
        H2 h2 = new H2("Panel de Control");

        add(h2);
        add(main); // Establece el MainView como el contenido principal de esta vista

    }
}
