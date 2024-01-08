package es.uca.cadicom.views.frontoffice;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Historial")
@Route(value = "historial", layout = FrontLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class DesgloseView extends Composite<VerticalLayout> {

    public DesgloseView(){
        TabSheet tabSheet = new TabSheet();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        tabSheet.setWidth("min-content");
        setTabSheetSampleData(tabSheet);
        getContent().add(tabSheet);

    }

    private void setTabSheetSampleData(TabSheet tabSheet) {
        VerticalLayout vlLlamada = new VerticalLayout();

        Grid gLlamada = new Grid();// iniciar el constructor con datos.class
        gLlamada.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS);
        gLlamada.setWidth("100%");
        gLlamada.getStyle().set("flex-grow", "0");
        vlLlamada.add(gLlamada);

        VerticalLayout vlSms = new VerticalLayout();

        VerticalLayout vlData = new VerticalLayout();

        tabSheet.add("Llamadas", vlLlamada);
        tabSheet.add("SMS", vlSms);
        tabSheet.add("Datos", vlData);
    }
}
