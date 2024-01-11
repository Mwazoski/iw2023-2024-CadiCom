package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.LineaCliente;
import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.Usuario;

import es.uca.cadicom.service.ApiService;
import es.uca.cadicom.service.UsuarioService;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@PageTitle("Panel Clientes")
@Route(value = "clientespanel", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ClientesView extends Composite<VerticalLayout> {

    private UsuarioService usuarioService;
    private ApiService apiService;
    Grid<Usuario> usuarioGrid;

    public ClientesView(UsuarioService usuarioService, ApiService apiService) {
        this.apiService = apiService;
        this.usuarioService = usuarioService;
        this.usuarioGrid = UsuarioGrid();
        HorizontalLayout searchLayout = createSearchBar();
        Button generateUsersButton = new Button(VaadinIcon.PLAY.create(), clickEvent -> {try {apiService.getLineaClienteAll(); UI.getCurrent().getPage().reload();} catch (URISyntaxException e) { throw new RuntimeException(e); } catch (IOException e) { throw new RuntimeException(e); } catch (InterruptedException e) { throw new RuntimeException(e); } catch (ParseException e) { throw new RuntimeException(e); }});
        getContent().add(generateUsersButton, searchLayout, usuarioGrid);
    }

    private HorizontalLayout createSearchBar() {
        TextField emailSearchField = new TextField();
        emailSearchField.setPlaceholder("Search by email");

        Button searchButton = new Button(VaadinIcon.SEARCH.create(), clickEvent -> searchByEmail(emailSearchField.getValue(), this.usuarioGrid));
        HorizontalLayout searchLayout = new HorizontalLayout(emailSearchField, searchButton);
        searchLayout.setAlignItems(FlexComponent.Alignment.BASELINE);

        return searchLayout;
    }

    private void searchByEmail(String email, Grid<Usuario> usuarioGrid) {
        if (email == null || email.trim().isEmpty()) {
            usuarioGrid.setItems(usuarioService.getAllUsers()); // Show all users if search field is empty
        } else {
            try {
                Usuario usuario = usuarioService.findUserByEmail(email);
                usuarioGrid.setItems(usuario);
            } catch (UsernameNotFoundException e) {
                Notification notification = new Notification();
                notification.setText("Usuario no encontrado con email: " + email);
                notification.setDuration(3000);
                notification.setPosition(Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                notification.open();
            }
        }
    }



    private Grid<Usuario> UsuarioGrid() {
        Grid<Usuario> usuarioGrid = new Grid<>(Usuario.class, false);
        usuarioGrid.removeAllColumns();
        usuarioGrid.setAllRowsVisible(true);

        // Add your custom columns
        usuarioGrid.addColumn(Usuario::getNombre).setHeader("Nombre");
        usuarioGrid.addColumn(Usuario::getApellidos).setHeader("Apellidos");
        usuarioGrid.addColumn(Usuario::getDni).setHeader("Dni");
        usuarioGrid.addColumn(Usuario::getEmail).setHeader("Email");

        usuarioGrid.addColumn(new ComponentRenderer<>(usuario -> {
            Button editButton = new Button(VaadinIcon.EDIT.create(), clickEvent -> editUsuario(usuario));
            Button removeButton = new Button(VaadinIcon.TRASH.create(), clickEvent -> {
                try {
                    removeUsuario(usuario);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

            HorizontalLayout actionsLayout = new HorizontalLayout(editButton, removeButton);
            actionsLayout.setSpacing(true); // Adjust as needed for spacing between buttons
            return actionsLayout;
        })).setHeader("Acciones");


        usuarioGrid.addThemeVariants(GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);

        usuarioGrid.setWidth("100%");

        List<Usuario> usuarios = usuarioService.getAllUsers();
        usuarioGrid.setItems(usuarios);

        return usuarioGrid;
    }

    private void editUsuario(Usuario usuario) { UI.getCurrent().navigate("clientesmodificar/" + usuario.getId()); }

    private void removeUsuario(Usuario usuario) throws URISyntaxException, IOException, ParseException, InterruptedException {
        for (Telefono telefono : usuario.getTelefonos()) {
            LineaCliente removeLineaCliente = apiService.getLineaClienteTelefono(telefono.getNumero());
            apiService.deleteLineaCliente(removeLineaCliente.getId());
        }
        usuarioService.deleteUser(usuario.getEmail());
        Notification notification = new Notification();
        notification.setText("Usuario eliminado correctamente");
        notification.setDuration(3000);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.open();
        updateGrid();
    }

    private void updateGrid() {
        List<Usuario> updatedUsers = usuarioService.getAllUsers(); // Assuming a method to fetch all users
        this.usuarioGrid.setItems(updatedUsers); // 'grid' is your Vaadin Grid instance
    }
}

