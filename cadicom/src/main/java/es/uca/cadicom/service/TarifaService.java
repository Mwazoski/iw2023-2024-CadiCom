package es.uca.cadicom.service;

import es.uca.cadicom.repository.TarifaRepository;
import es.uca.cadicom.entity.Tarifa;
import java.util.List;
import java.util.Optional;

public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    public void createTarifa(Tarifa tarifa) {
        if (tarifa == null) {
            System.err.println("Tarifa object is null.");
            return;
        }
        tarifaRepository.save(tarifa);
        System.out.println("Tarifa created with ID: " + tarifa.getId());
    }

    public List<Tarifa> getTarifaAll() {
        return tarifaRepository.findAll();
    }

    public void updateTarifa(Tarifa updatedTarifa) {
        if (updatedTarifa == null || updatedTarifa.getId() == null) {
            System.err.println("Tarifa or its ID is null.");
            return;
        }

        Optional<Tarifa> existingTarifaOpt = tarifaRepository.findById(updatedTarifa.getId());

        if (existingTarifaOpt.isPresent()) {
            Tarifa existingTarifa = existingTarifaOpt.get();

            if (updatedTarifa.getNombre() != null) {
                existingTarifa.setNombre(updatedTarifa.getNombre());
            }
            if (updatedTarifa.getDatos() != null) {
                existingTarifa.setDatos(updatedTarifa.getDatos());
            }
            if (updatedTarifa.getMinutos() != null) {
                existingTarifa.setMinutos(updatedTarifa.getMinutos());
            }
            if (updatedTarifa.getPrecio() != null) {
                existingTarifa.setPrecio(updatedTarifa.getPrecio());
            }
            tarifaRepository.save(existingTarifa);
            System.out.println("Tarifa with ID " + updatedTarifa.getId() + " has been updated.");
        } else {
            System.err.println("Tarifa with ID " + updatedTarifa.getId() + " not found.");
        }
    }

    public void deleteTarifa(Long id) {
        if (id == null) {
            System.err.println("ID is null.");
            return;
        }

        Optional<Tarifa> tarifa = tarifaRepository.findById(Math.toIntExact(id));

        if (tarifa.isPresent()) {
            tarifaRepository.delete(tarifa.get());
            System.out.println("Tarifa with ID " + id + " has been deleted.");
        } else {
            System.err.println("Tarifa with ID " + id + " not found.");
        }
    }

}


