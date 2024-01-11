package es.uca.cadicom.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
public class generatePdf {

    private final FacturaService facturaService;
    public generatePdf(FacturaService facturaService){
        this.facturaService = facturaService;
    }
    public ByteArrayInputStream generatePDF() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            document.add(new Paragraph("Hello, PDF!"));
            document.close();
            return new ByteArrayInputStream(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Anchor createDownloadLink() {
        StreamResource pdfResource = new StreamResource("mydocument.pdf", () -> generatePdf());
        pdfResource.setContentType("application/pdf");
        pdfResource.setCacheTime(0);

        Anchor downloadLink = new Anchor(pdfResource, "Download PDF");
        downloadLink.getElement().setAttribute("download", true);
        return downloadLink;
    }

}



