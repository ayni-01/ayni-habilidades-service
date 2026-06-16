package com.somosayni.habilidades.infrastructure.persistence.repository;

import com.somosayni.habilidades.infrastructure.persistence.entity.HabilidadValidadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaHabilidadValidadaRepository extends JpaRepository<HabilidadValidadaEntity, String> {
    List<HabilidadValidadaEntity> findByTalentoId(String talentoId);
    Optional<HabilidadValidadaEntity> findByTalentoIdAndNombre(String talentoId, String nombre);
}
