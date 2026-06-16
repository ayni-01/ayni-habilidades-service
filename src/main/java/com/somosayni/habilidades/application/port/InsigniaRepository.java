package com.somosayni.habilidades.application.port;

import com.somosayni.habilidades.domain.model.Insignia;
import java.util.List;
import java.util.Optional;

public interface InsigniaRepository {
    Optional<Insignia> findById(String id);
    List<Insignia> findByTalentoId(String talentoId);
    List<Insignia> findByRetoId(String retoId);
    Insignia save(Insignia insignia);
}
