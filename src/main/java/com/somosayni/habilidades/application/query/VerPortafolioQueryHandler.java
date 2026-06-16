package com.somosayni.habilidades.application.query;

import com.somosayni.habilidades.application.port.HabilidadValidadaRepository;
import com.somosayni.habilidades.application.port.InsigniaRepository;
import com.somosayni.habilidades.domain.model.HabilidadValidada;
import com.somosayni.habilidades.domain.model.Insignia;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class VerPortafolioQueryHandler {

    private final HabilidadValidadaRepository habilidadRepository;
    private final InsigniaRepository insigniaRepository;

    public VerPortafolioQueryHandler(HabilidadValidadaRepository habilidadRepository, InsigniaRepository insigniaRepository) {
        this.habilidadRepository = habilidadRepository;
        this.insigniaRepository = insigniaRepository;
    }

    public record Portafolio(List<HabilidadValidada> habilidades, List<Insignia> insignias) {}

    public Portafolio handle(VerPortafolioQuery query) {
        List<HabilidadValidada> habilidades = habilidadRepository.findByTalentoId(query.talentoId());
        List<Insignia> insignias = insigniaRepository.findByTalentoId(query.talentoId());
        return new Portafolio(habilidades, insignias);
    }
}
