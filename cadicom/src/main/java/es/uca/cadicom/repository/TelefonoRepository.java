package es.uca.cadicom.repository;

import es.uca.cadicom.entity.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoRepository extends JpaRepository<Telefono, Integer> {
}
