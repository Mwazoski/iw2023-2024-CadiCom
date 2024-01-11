package es.uca.cadicom.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.security.AuthenticatedUser;
import jakarta.annotation.security.PermitAll;
import es.uca.cadicom.components.Header;

@PermitAll
@CssImport("./styles/landingpage.css")
@Route(value = "")
@PageTitle("CadiCom")
@AnonymousAllowed
public class LandingPage extends VerticalLayout {

    public LandingPage(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessAnnotationChecker) {

        addClassName("background");

        Header header = new Header(authenticatedUser, accessAnnotationChecker);

        add(header);
    }
}
