package es.uca.cadicom;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.vaadin.example.MainView;

@PermitAll
@Route("/usuario")
public class userView extends VerticalLayout {
    public userView() {
        MainView main = new MainView();

        add(main); // Establece el MainView como el contenido principal de esta vista

    }
}
