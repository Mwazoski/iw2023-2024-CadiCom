package es.uca.cadicom;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route("/facturas")
public class facturasView extends VerticalLayout {
    public facturasView() {
        MainView main = new MainView();

        add(main); // Establece el MainView como el contenido principal de esta vista

    }
}
