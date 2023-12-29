package es.uca.cadicom;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.awt.*;

@AnonymousAllowed // Es necesario para hacer pruebas sin logear
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

