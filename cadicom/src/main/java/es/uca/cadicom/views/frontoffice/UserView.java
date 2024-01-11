package es.uca.cadicom.views.frontoffice;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Usuario")
@Route(value = "usuario", layout = FrontLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class UserView extends Composite<VerticalLayout> {

}
