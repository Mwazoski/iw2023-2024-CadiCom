package es.uca.cadicom.repository;

import es.uca.cadicom.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
}
