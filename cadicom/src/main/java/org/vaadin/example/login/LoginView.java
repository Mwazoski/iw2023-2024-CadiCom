package org.vaadin.example.login;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.example.components.Header;

@AnonymousAllowed
@CssImport("styles/landingpage.css")
@PageTitle("Login")
@Route("/login")
public class LoginView extends VerticalLayout {

    Header header = new Header();
    LoginForm loginForm = new LoginForm();
    public LoginView() {

        addClassName("background");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        loginForm.setAction("login");

        add(header , loginForm);
    }
}
