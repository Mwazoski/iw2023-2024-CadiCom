package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.AuthenticatedUser;
import es.uca.cadicom.security.SecurityService;
import es.uca.cadicom.security.SecurityUtils;
import org.springframework.security.core.userdetails.User;

import java.io.ByteArrayInputStream;
import java.util.Optional;


public class FrontLayout extends AppLayout {

    private final AccessAnnotationChecker accessChecker;
    private final AuthenticatedUser authenticatedUser;
    private H2 viewTitle;


    public FrontLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("CadiCom");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Consumo", "consumo", VaadinIcon.DASHBOARD.create()));
        nav.addItem(new SideNavItem("Historial", "historial", VaadinIcon.DATABASE.create()));
        nav.addItem(new SideNavItem("Facturas", "facturas", VaadinIcon.FILE.create()));
        nav.addItem(new SideNavItem("Contratos", "contratos", VaadinIcon.PACKAGE.create()));
        nav.addClassNames(LumoUtility.Margin.MEDIUM);
        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<Usuario> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            Usuario usuario = maybeUser.get();

            Avatar avatar = new Avatar(usuario.getNombre());
            avatar.setThemeName("small");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(usuario.getNombre());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);

            userName.getSubMenu().addItem("Editar perfil", e -> {
                UI.getCurrent().navigate(UserView.class);
            });
            userName.getSubMenu().addItem("Cerrar sesión", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
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