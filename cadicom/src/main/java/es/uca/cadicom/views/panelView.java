package es.uca.cadicom.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@AnonymousAllowed // Es necesario para hacer pruebas sin logear
@PermitAll
@PageTitle("Panel")
@Route(value = "panel", layout = MainView.class)
public class panelView extends VerticalLayout {

    private OrderedList items;
    public panelView() {
        constructUI();
        H2 h2 = new H2("Panel de Control");
        items.add(new H2("Panel de Control1"));
        items.add(new H2("Panel de Control2"));
        items.add(new H2("Panel de Control3"));
        items.add(new H2("Panel de Control4"));
        items.add(new H2("Panel de Control5"));

    }

    private void constructUI() {
        addClassNames("panel-view");
        addClassNames(LumoUtility.MaxWidth.SCREEN_LARGE,
                LumoUtility.Margin.Horizontal.AUTO,
                LumoUtility.Padding.Bottom.LARGE,
                LumoUtility.Padding.Horizontal.LARGE);


        VerticalLayout headerContainer = new VerticalLayout();

        items = new OrderedList();
        items.addClassNames(LumoUtility.Gap.MEDIUM,
                LumoUtility.Display.GRID,
                LumoUtility.ListStyleType.NONE,
                LumoUtility.Margin.NONE,
                LumoUtility.Padding.NONE);

        add(items);

    }
}
