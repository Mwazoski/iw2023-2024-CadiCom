package es.uca.cadicom.service;

import es.uca.cadicom.entity.Telefono;
import es.uca.cadicom.entity.TelefonoBloqueado;
import es.uca.cadicom.repository.TelefonoBloqueadoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelefonoBloqueadoService {

    private final TelefonoBloqueadoRepository telefonoBloqueadoRepository;
    private final TelefonoService telefonoService;

    public TelefonoBloqueadoService(TelefonoBloqueadoRepository telefonoBloqueadoRepository, TelefonoService telefonoService) {
        this.telefonoBloqueadoRepository = telefonoBloqueadoRepository;
        this.telefonoService = telefonoService;
    }

    public void createBloqueado(Long id, String bloquear) {
        Telefono telefono = telefonoService.getTelefono(id);
        Telefono bloqueado = telefonoService.getTelefonoNumero(bloquear);

        TelefonoBloqueado newBloqueado = new TelefonoBloqueado(telefono, bloqueado);
    }

    public List<TelefonoBloqueado> getBloqueados(Long id) {
        Telefono telefono = telefonoService.getTelefono(id);
        if (telefono == null) {
            System.err.println("Telefono with ID " + id + " not found.");
            return new ArrayList<>();
        }
        return telefonoBloqueadoRepository.findByTelefono(telefono);
    }

    public void deleteBloqueado(Integer id) {
        if (id == null) {
            System.err.println("ID is null.");
            return;
        }

        Optional<TelefonoBloqueado> bloqueado = telefonoBloqueadoRepository.findById(id);
        if (bloqueado.isPresent()) {
            telefonoBloqueadoRepository.deleteById(id);
            System.out.println("TelefonoBloqueado with ID " + id + " has been deleted.");
        } else {
            System.err.println("TelefonoBloqueado with ID " + id + " not found.");
        }
    }
}

