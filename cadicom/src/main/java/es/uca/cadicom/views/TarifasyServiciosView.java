package es.uca.cadicom.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import java.util.ArrayList;
import java.util.List;

@AnonymousAllowed
@PageTitle("Tarifas y Servicios")
@Route(value = "TarifasServicios", layout = MainView.class)
//@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class TarifasyServiciosView extends Composite<VerticalLayout> {

    public TarifasyServiciosView() {
            TabSheet tabSheet = new TabSheet();
            HorizontalLayout layoutRow = new HorizontalLayout();

            getContent().setWidth("100%");
            getContent().getStyle().set("flex-grow", "1");
            tabSheet.setWidth("100%");
            setTabSheetSampleData(tabSheet);
            layoutRow.setWidthFull();
            getContent().setFlexGrow(1.0, layoutRow);
            layoutRow.addClassName(Gap.MEDIUM);
            layoutRow.setWidth("100%");
            layoutRow.getStyle().set("flex-grow", "1");
            getContent().add(tabSheet);
            getContent().add(layoutRow);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setMultiSelectListBoxSampleData(MultiSelectListBox multiSelectListBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        multiSelectListBox.setItems(sampleItems);
        multiSelectListBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
        multiSelectListBox.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
    }
    private void setTabSheetSampleData(TabSheet tabSheet) {
        VerticalLayout layoutRow = new VerticalLayout();
        MultiSelectListBox textItems = new MultiSelectListBox();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        textItems.setWidth("min-content");
        setMultiSelectListBoxSampleData(textItems);
        buttonPrimary.setText("Añadir");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        textItems.add(buttonPrimary);
        tabSheet.add("Tarifas", textItems);
        tabSheet.add("Servicios",textItems);
    }
}


