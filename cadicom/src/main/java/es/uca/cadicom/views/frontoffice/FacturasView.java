package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import es.uca.cadicom.views.backoffice.MainView;

@AnonymousAllowed
@PageTitle("Facturas")
@Route(value = "facturas", layout = FrontLayout.class)
@Uses(Icon.class)
public class FacturasView extends Composite<VerticalLayout> {

    public FacturasView() {
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
