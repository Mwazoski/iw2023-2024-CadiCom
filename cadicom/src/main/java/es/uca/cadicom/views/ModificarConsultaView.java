package es.uca.cadicom;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("EditarReclamacionOConsulta")
@Route(value = "editarReclamacionOConsulta", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ModificarConsultaView extends Composite<VerticalLayout> {

    public ModificarConsultaView() {
        H1 h1 = new H1();
        Hr hr = new Hr();
        TextField textField = new TextField();
        H1 h12 = new H1();
        Hr hr2 = new Hr();
        TextField textField2 = new TextField();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        h1.setText("Reclamación o Consulta");
        h1.setWidth("max-content");
        textField.setLabel("Texto escrito por el usuario");
        textField.setWidth("1270px");
        textField.setHeight("300px");
        h12.setText("Respuesta");
        h12.setWidth("max-content");
        textField2.setLabel("Escriba aquí la respuesta al usuario");
        textField2.setWidth("1270px");
        textField2.setHeight("300px");
        getContent().add(h1);
        getContent().add(hr);
        getContent().add(textField);
        getContent().add(h12);
        getContent().add(hr2);
        getContent().add(textField2);
    }
}
