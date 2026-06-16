package com.somosayni.habilidades.application.command;

import com.somosayni.habilidades.application.port.HabilidadValidadaRepository;
import com.somosayni.habilidades.domain.model.HabilidadValidada;
import org.springframework.stereotype.Component;

@Component
public class ActualizarHabilidadCommandHandler {

    private final HabilidadValidadaRepository repository;

    public ActualizarHabilidadCommandHandler(HabilidadValidadaRepository repository) {
        this.repository = repository;
    }

    public HabilidadValidada handle(ActualizarHabilidadCommand command) {
        HabilidadValidada.NivelHabilidad nivel = HabilidadValidada.NivelHabilidad.valueOf(command.nivel().toUpperCase());

        HabilidadValidada habilidad = repository.findByTalentoIdAndNombre(command.talentoId(), command.nombre())
                .orElseGet(() -> new HabilidadValidada(command.talentoId(), command.nombre(), nivel));

        habilidad.actualizarNivel(nivel);
        return repository.save(habilidad);
    }
}
