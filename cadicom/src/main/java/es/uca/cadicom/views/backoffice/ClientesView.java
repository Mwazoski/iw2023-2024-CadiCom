package es.uca.cadicom.views.backoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import es.uca.cadicom.entity.Factura;
import es.uca.cadicom.entity.Usuario;

import es.uca.cadicom.service.UsuarioService;

import java.util.Collection;
import java.util.List;

@PageTitle("Panel Clientes")
@Route(value = "clientespanel", layout = MainView.class)
@AnonymousAllowed
@Uses(Icon.class)
public class ClientesView extends Composite<VerticalLayout> {

    private UsuarioService usuarioService;
    public ClientesView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        Grid<Usuario> usuarioGrid = UsuarioGrid();
        HorizontalLayout searchLayout = createSearchBar(usuarioGrid);
        getContent().add(searchLayout, usuarioGrid);
    }

    private HorizontalLayout createSearchBar(Grid<Usuario> usuarioGrid) {
        TextField emailSearchField = new TextField();
        emailSearchField.setPlaceholder("Search by email");

        Button searchButton = new Button(VaadinIcon.SEARCH.create(), clickEvent -> searchByEmail(emailSearchField.getValue(), usuarioGrid));
        HorizontalLayout searchLayout = new HorizontalLayout(emailSearchField, searchButton);
        searchLayout.setAlignItems(FlexComponent.Alignment.BASELINE);

        return searchLayout;
    }

    private void searchByEmail(String email, Grid<Usuario> usuarioGrid) {
        if (email == null || email.trim().isEmpty()) {
            usuarioGrid.setItems(usuarioService.getAllUsers()); // Show all users if search field is empty
        } else {
            Usuario usuario = usuarioService.findUserByEmail(email);
            usuarioGrid.setItems(usuario);
        }
    }

    private Grid<Usuario> UsuarioGrid() {
        Grid<Usuario> usuarioGrid = new Grid<>(Usuario.class);
        usuarioGrid.removeAllColumns();

        // Add your custom columns
        usuarioGrid.addColumn(Usuario::getNombre).setHeader("Nombre");
        usuarioGrid.addColumn(Usuario::getApellidos).setHeader("Apellidos");
        usuarioGrid.addColumn(Usuario::getDni).setHeader("Dni");
        usuarioGrid.addColumn(Usuario::getEmail).setHeader("Email");
        // ... other columns ...

        // Add an edit button column
        usuarioGrid.addColumn(new ComponentRenderer<>(usuario -> new Button("Edit", clickEvent -> {
            // Implement your edit logic here
            editUsuario(usuario);
        }))).setHeader("Edit");

        // Add a remove button column
        usuarioGrid.addColumn(new ComponentRenderer<>(usuario -> new Button(VaadinIcon.TRASH.create(), clickEvent -> {
            removeUsuario(usuario);
        }))).setHeader("Remove");

        usuarioGrid.addThemeVariants(GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);

        usuarioGrid.setWidth("100%");
        // Fetch and set the data from getAllUsers
        List<Usuario> usuarios = usuarioService.getAllUsers();
        usuarioGrid.setItems(usuarios);

        return usuarioGrid;
    }

    private void editUsuario(Usuario usuario) {
        System.out.println("Editing User");
    }

    private void removeUsuario(Usuario usuario) {
        System.out.println("Removing User");
    }

}
