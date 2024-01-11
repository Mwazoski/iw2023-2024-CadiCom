package es.uca.cadicom.service;

import es.uca.cadicom.entity.Factura;
import es.uca.cadicom.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public void createFactura(Factura factura) {
        if (factura == null) { throw new IllegalArgumentException("Factura cannot be null"); }
        facturaRepository.save(factura);
    }

    public Optional<Factura> getFacturaById(Long id) {
        return facturaRepository.findById(Math.toIntExact(id));
    }

        public List<Factura> getAllFacturasByTelefonoId(Long telefonoId) {
        return facturaRepository.findByTelefonoId(Math.toIntExact(telefonoId));
    }

    public Factura updateFactura(Long id, Factura updatedFactura) {
        if (!facturaRepository.existsById(Math.toIntExact(id))) {
            throw new IllegalArgumentException("Factura with id " + id + " does not exist");
        }
        updatedFactura.setId(Math.toIntExact(id)); // Ensure the ID is set correctly
        return facturaRepository.save(updatedFactura);
    }

    public void deleteFactura(Long id) {
        if (!facturaRepository.existsById(Math.toIntExact(id))) {
            throw new IllegalArgumentException("Factura with id " + id + " does not exist");
        }
        facturaRepository.deleteById(Math.toIntExact(id));
    }

}
