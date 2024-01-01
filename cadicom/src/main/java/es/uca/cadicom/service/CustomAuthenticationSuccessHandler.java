package es.uca.cadicom.service;

import es.uca.cadicom.entity.Usuario;
import jakarta.servlet.ServletException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session != null) {
            // Assuming the user details are in the authentication principal
            Usuario userDetails = (Usuario) authentication.getPrincipal();

            // Store the user details in the session or perform any other needed operations
            session.setAttribute("user", userDetails);

            // Call the parent class to handle the redirection
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
