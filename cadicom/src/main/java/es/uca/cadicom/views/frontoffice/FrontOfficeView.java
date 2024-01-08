package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.views.backoffice.MainView;

@AnonymousAllowed
@PageTitle("FrontOffice")
@Route(value = "/cliente", layout = FrontLayout.class)
public class FrontOfficeView extends Div{

    public FrontOfficeView() {
        
    }
}

