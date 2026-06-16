package com.somosayni.habilidades.application.port;

import com.somosayni.habilidades.domain.model.HabilidadValidada;
import java.util.List;
import java.util.Optional;

public interface HabilidadValidadaRepository {
    Optional<HabilidadValidada> findById(String id);
    List<HabilidadValidada> findByTalentoId(String talentoId);
    Optional<HabilidadValidada> findByTalentoIdAndNombre(String talentoId, String nombre);
    HabilidadValidada save(HabilidadValidada habilidad);
    void deleteById(String id);
}
