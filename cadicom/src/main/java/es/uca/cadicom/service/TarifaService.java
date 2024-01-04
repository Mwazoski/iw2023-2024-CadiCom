package es.uca.cadicom.service;

import es.uca.cadicom.repository.TarifaRepository;

public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }
}


