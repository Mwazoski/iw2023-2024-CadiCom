package es.uca.cadicom.service;

import es.uca.cadicom.entity.RegistroLlamadas;
import es.uca.cadicom.entity.Usuario;
import es.uca.cadicom.repository.TelefonoRepository;
import es.uca.cadicom.entity.Telefono;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelefonoService {

    private final TelefonoRepository telefonoRepository;

    public TelefonoService(TelefonoRepository telefonoRepository) { this.telefonoRepository = telefonoRepository; }

    public boolean createTelefono(Telefono telefono) {
        if (telefono == null) {
            System.err.println("Telefono object is null.");
            return false;
        }

        Telefono existingTelefono = telefonoRepository.findByNumero(telefono.getNumero());

        if (existingTelefono != null) {
            System.err.println("A phone with the same number already exists");
            return false;
        }

        try {
            telefonoRepository.save(telefono);
            return true;
        } catch (Exception e) {
            System.err.println("Error creating phone: " + e.getMessage());
            return false;
        }
    }

    public Telefono getTelefono(Long id) {
        if (id == null) {
            System.err.println("ID is null.");
            return null;
        }

        Optional<Telefono> telefono = telefonoRepository.findById(Math.toIntExact(id));

        if (telefono.isPresent()) {
            return telefono.get();
        } else {
            System.err.println("Telefono with ID " + id + " not found.");
            return null;
        }
    }

    public void updateTelefono(Telefono updatedTelefono) {
        if (updatedTelefono == null || updatedTelefono.getId() == null) {
            System.err.println("Telefono or its ID is null.");
            return;
        }

        Optional<Telefono> existingTelefonoOpt = telefonoRepository.findById(Math.toIntExact(updatedTelefono.getId()));

        if (existingTelefonoOpt.isPresent()) {
            Telefono existingTelefono = getTelefono(updatedTelefono, existingTelefonoOpt);
            telefonoRepository.save(existingTelefono);
            System.out.println("Telefono with ID " + updatedTelefono.getId() + " has been updated.");
        } else {
            System.err.println("Telefono with ID " + updatedTelefono.getId() + " not found.");
        }
    }

    private static Telefono getTelefono(Telefono updatedTelefono, Optional<Telefono> existingTelefonoOpt) {
        Telefono existingTelefono = existingTelefonoOpt.get();

        if (updatedTelefono.getNumero() != null) {
            existingTelefono.setNumero(updatedTelefono.getNumero());
        }
        if (updatedTelefono.getUsuario() != null) {
            existingTelefono.setUsuario(updatedTelefono.getUsuario());
        }
        if (updatedTelefono.getTarifa() != null) {
            existingTelefono.setTarifa(updatedTelefono.getTarifa());
        }
        if (updatedTelefono.getCompartirDatos() != null) {
            existingTelefono.setCompartirDatos(updatedTelefono.getCompartirDatos());
        }
        if (updatedTelefono.getRoaming() != null) {
            existingTelefono.setRoaming(updatedTelefono.getRoaming());
        }
        return existingTelefono;
    }

    public void deleteTelefono(Integer id) {
        if (id == null) {
            System.err.println("ID is null.");
            return;
        }

        Optional<Telefono> telefono = telefonoRepository.findById(id);

        if (telefono.isPresent()) {
            telefonoRepository.delete(telefono.get());
            System.out.println("Telefono with ID " + id + " has been deleted.");
        } else {
            System.err.println("Telefono with ID " + id + " not found.");
        }
    }


    public Telefono getTelefonoNumero(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            System.err.println("Phone number is null or empty.");
            return null;
        }

        Telefono telefono = telefonoRepository.findByNumero(numero);
        if (telefono != null) {
            return telefono;
        } else {
            System.err.println("Telefono with number " + numero + " not found.");
            return null;
        }
    }

}
