package es.uca.cadicom;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;


/**
 * The main view is a top-level placeholder for other views.
 */
public class MainView extends AppLayout {

    private H2 viewTitle;

    public MainView() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        // Crear botón de Cerrar Sesión
        Button logOutButton = new Button("Cerrar Sesión", new Icon(VaadinIcon.SIGN_OUT));
        logOutButton.getStyle().set("margin-right", "20px");
        logOutButton.addClickListener(e -> {
            // Aquí va la lógica para manejar el cierre de sesión
            // Por ejemplo, redirigir al usuario a la página de inicio de sesión
        });


        HorizontalLayout headerLayout = new HorizontalLayout(toggle, viewTitle, logOutButton);
        headerLayout.expand(viewTitle);
        headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerLayout.setWidthFull();

        addToNavbar(headerLayout);
    }

    private void addDrawerContent() {

        H1 appName = new H1("CadiCom");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.LARGE , LumoUtility.MinHeight.FULL , LumoUtility.MinWidth.FULL);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Panel", "panel", VaadinIcon.DASHBOARD.create()));
        nav.addItem(new SideNavItem("Facturas", "facturas", VaadinIcon.FILE.create()));
        nav.addItem(new SideNavItem("Usuario", "usuario", VaadinIcon.USER.create()));
        nav.addClassNames(LumoUtility.Margin.MEDIUM);
        return nav;
    }

    private Footer createFooter() {
        return new Footer();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}