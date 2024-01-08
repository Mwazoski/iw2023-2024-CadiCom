package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
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
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.cadicom.security.SecurityUtils;

public class MainView extends AppLayout {

    private H2 viewTitle;
    private final transient AuthenticationContext authContext;

    public MainView(AuthenticationContext authContext) {
        this.authContext = authContext;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        if (SecurityUtils.isAuthenticated()) {
            Button logout = new Button("Cerrar Sesion", new Icon(VaadinIcon.SIGN_OUT), click ->
                    SecurityUtils.logout());
            logout.getStyle().set("margin-right", "20px");
            HorizontalLayout headerLayout = new HorizontalLayout(toggle, viewTitle, logout);
            headerLayout.expand(viewTitle);
            headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            headerLayout.setWidthFull();
            addToNavbar(headerLayout);
        } else {
            HorizontalLayout headerLayout = new HorizontalLayout(toggle, viewTitle);
            addToNavbar(headerLayout);
        }
    }

    private void addDrawerContent() {
        H1 appName = new H1("CadiCom");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.LARGE, LumoUtility.MinHeight.FULL, LumoUtility.MinWidth.FULL);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        SideNavItem facturaLink = new SideNavItem("Facturas");
        facturaLink.addItem(new SideNavItem("Añadir", AddFacturasView.class, VaadinIcon.PLUS.create()));
        facturaLink.addItem(new SideNavItem("Modificar", ModificarFacturaView.class,VaadinIcon.EDIT.create()));

        SideNavItem servicioLink = new SideNavItem("Servicios");
        servicioLink.addItem(new SideNavItem("Añadir", AddServicioView.class, VaadinIcon.PLUS.create()));
        servicioLink.addItem(new SideNavItem("Modificar", ModificarServicioView.class,VaadinIcon.EDIT.create()));

        SideNavItem tarifaLink = new SideNavItem("Tarifas");
        tarifaLink.addItem(new SideNavItem("Añadir", AddTarifaView.class, VaadinIcon.PLUS.create()));
        tarifaLink.addItem(new SideNavItem("Modificar", ModificarTarifaView.class,VaadinIcon.EDIT.create()));

        SideNavItem contratoLink = new SideNavItem("Contratos");
        contratoLink.addItem(new SideNavItem("Añadir", AddContratoView.class, VaadinIcon.PLUS.create()));
        contratoLink.addItem(new SideNavItem("Modificar", ModificarContratoView.class,VaadinIcon.EDIT.create()));

        nav.addItem(facturaLink, servicioLink, tarifaLink, contratoLink);
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