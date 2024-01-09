package es.uca.cadicom.repository;

import es.uca.cadicom.entity.TelefonoBloqueado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import es.uca.cadicom.entity.Telefono;

public interface TelefonoBloqueadoRepository extends JpaRepository <TelefonoBloqueado, Integer> {
    List<TelefonoBloqueado> findByTelefono(Telefono telefono);
}
