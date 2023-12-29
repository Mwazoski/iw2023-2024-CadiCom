package es.uca.cadicom;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@AnonymousAllowed
@PageTitle("FrontOffice")
@Route("/cliente")
public class FrontEndView extends VerticalLayout {
    public FrontEndView() {
        MainView main = new MainView();

        add(main);

    }
}
