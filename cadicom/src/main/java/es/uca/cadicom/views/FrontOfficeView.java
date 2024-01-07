package es.uca.cadicom.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed // Es necesario para hacer pruebas sin logear
@PageTitle("FrontOffice")
@Route(value = "/cliente", layout = FrontLayout.class)
public class FrontOfficeView extends Div{
    public FrontOfficeView() {

    }
}

