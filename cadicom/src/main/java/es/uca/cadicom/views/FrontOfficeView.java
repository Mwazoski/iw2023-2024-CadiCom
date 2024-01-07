package es.uca.cadicom.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("FrontOffice")
@Route(value = "/cliente", layout = MainView.class)
public class FrontOfficeView extends Div{

    public FrontOfficeView() {
        ComboBox<String> comboBox = new ComboBox<>("Browser");
        comboBox.setAllowCustomValue(true);
        add(comboBox);
        comboBox.setItems("Chrome", "Edge", "Firefox", "Safari");
        comboBox.setHelperText("Select or type a browser");
        setText("Bienvenido a la página de inicio. Aquí puedes agregar tu contenido.");
    }
}

