package es.uca.cadicom.service;

import es.uca.cadicom.entity.RegistroLlamadas;
import es.uca.cadicom.repository.TelefonoRepository;
import es.uca.cadicom.entity.Telefono;

import java.util.List;
import java.util.Optional;

public class TelefonoService {

    private final TelefonoRepository telefonoRepository;

    public TelefonoService(TelefonoRepository telefonoRepository) { this.telefonoRepository = telefonoRepository; }

    public void createTelefono(Telefono telefono) {
        if (telefono == null) {
            System.err.println("Telefono object is null.");
            return;
        }
        telefonoRepository.save(telefono);
        System.out.println("Telefono with ID " + telefono.getId() + " has been created.");
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

        Optional<Telefono> existingTelefonoOpt = telefonoRepository.findById(updatedTelefono.getId());

        if (existingTelefonoOpt.isPresent()) {
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

            telefonoRepository.save(existingTelefono);
            System.out.println("Telefono with ID " + updatedTelefono.getId() + " has been updated.");
        } else {
            System.err.println("Telefono with ID " + updatedTelefono.getId() + " not found.");
        }
    }

    public void deleteTelefono(Long id) {
        if (id == null) {
            System.err.println("ID is null.");
            return;
        }

        Optional<Telefono> telefono = telefonoRepository.findById(Math.toIntExact(id));

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
