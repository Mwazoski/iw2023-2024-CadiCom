package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Inicio")
@Route(value = "Inicio", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class BackOfficeView extends Composite<VerticalLayout> {

    public BackOfficeView() {
        H1 h1 = new H1();
        Hr hr = new Hr();
        Paragraph textLarge = new Paragraph();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        h1.setText("Cadicom");
        h1.setWidth("max-content");
        textLarge.setText(
                "Bienvenido al portal para empleados de Cadicom, empresa de telecomunicaciones con sede en Cádiz");
        textLarge.setWidth("100%");
        textLarge.getStyle().set("font-size", "var(--lumo-font-size-xl)");
        getContent().add(h1);
        getContent().add(hr);
        getContent().add(textLarge);
    }
}
