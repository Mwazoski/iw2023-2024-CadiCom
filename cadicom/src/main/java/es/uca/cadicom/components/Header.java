package es.uca.cadicom.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.security.AuthenticatedUser;

import java.util.Optional;

public class Header extends HorizontalLayout{

    private AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessAnnotationChecker;

    public Header(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessAnnotationChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessAnnotationChecker = accessAnnotationChecker;

        HorizontalLayout headerRow = new HorizontalLayout();
        HorizontalLayout headerRowNested = new HorizontalLayout();

        Button buttonTertiary = new Button("Login");
        Button buttonPrimary = new Button("Register");
        buttonTertiary.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("login"));
        });
        buttonPrimary.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("register"));
        });

        Button buttonRedirect = new Button("Comenzar");
        buttonRedirect.addClickListener(event -> {
            Optional<Usuario> maybeUser = authenticatedUser.get();
            if (maybeUser.isPresent()) {
                Usuario usuario = maybeUser.get();
                System.out.println("HOLA");
                usuario.getAuthorities().forEach(e -> System.out.println(e.getAuthority()));
                if(usuario.getAuthorities().stream().anyMatch(e -> "ROLE_USER".equals(e.getAuthority()))){
                    UI.getCurrent().navigate("/cliente");
                } else if(usuario.getAuthorities().stream().anyMatch(e -> "ROLE_ADMIN".equals(e.getAuthority()))){
                    UI.getCurrent().navigate("/admin");
                }
            }
        });

        H2 h2 = new H2("CADICOM");
        h2.setClassName("h2");
        h2.addClickListener(event -> UI.getCurrent().navigate("/"));

        addClassName(LumoUtility.Padding.LARGE);
        setWidth("100%");
        getStyle().set("flex-grow", "1");
        headerRow.setWidthFull();
        setFlexGrow(1.0, headerRow);
        headerRow.addClassName(LumoUtility.Gap.MEDIUM);
        headerRow.addClassName("header");
        headerRow.setWidth("100%");
        headerRow.setMaxHeight("50px");
        headerRow.setHeight("50px");

        headerRow.setAlignSelf(FlexComponent.Alignment.CENTER, h2);
        h2.setWidth("max-content");
        headerRowNested.addClassName(LumoUtility.Gap.LARGE);
        headerRowNested.addClassName(LumoUtility.Padding.LARGE);
        headerRowNested.setWidth("100%");
        headerRowNested.setHeight("50px");
        headerRowNested.setAlignItems(Alignment.CENTER);
        headerRowNested.setJustifyContentMode(JustifyContentMode.END);
        buttonTertiary.setWidth("min-content");

        buttonTertiary.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonRedirect.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(headerRow);
        headerRow.add(h2);
        headerRow.add(headerRowNested);
        Optional<Usuario> maybeUser = authenticatedUser.get();

        if (maybeUser.isPresent()) {
            headerRowNested.add(buttonRedirect);
        } else {
            headerRowNested.add(buttonTertiary);
            headerRowNested.add(buttonPrimary);
        }
    }
}
