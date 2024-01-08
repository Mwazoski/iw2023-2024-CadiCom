package es.uca.cadicom.components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import java.io.InputStream;

public class ImageComponentCreator {

    private final String sImage;

    public ImageComponentCreator(String sImage) { this.sImage = sImage; }

    public Image create() {
        StreamResource streamResource = new StreamResource(this.sImage, () -> {
            InputStream inputStream = getClass().getResourceAsStream("/" + this.sImage);
            if (inputStream == null) {
                throw new RuntimeException("Image not found: " + this.sImage);
            }
            return inputStream;
        });

        return new Image(streamResource, "Description for " + this.sImage);
    }
}
