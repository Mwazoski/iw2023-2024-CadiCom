package es.uca.cadicom.repository;

import es.uca.cadicom.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
    List<Factura> findByTelefonoId(Integer telefono_id);
}
