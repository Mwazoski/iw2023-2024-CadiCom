package es.uca.cadicom;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.MainView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;


@AnonymousAllowed
@PageTitle("Consultas y Reclamaciones")
@Route(value = "ConsultasReclamaciones", layout = MainView.class)
@Uses(Icon.class)
public class ConsultasView extends Composite<VerticalLayout> {

    public ConsultasView() {
        Button buttonPrimary = new Button();
        MultiSelectListBox avatarItems = new MultiSelectListBox();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        buttonPrimary.setText("añadir");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        avatarItems.setWidth("min-content");
        getContent().add(buttonPrimary);
        getContent().add(avatarItems);
    }

}
