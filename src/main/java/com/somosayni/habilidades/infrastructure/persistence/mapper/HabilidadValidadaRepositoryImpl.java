package com.somosayni.habilidades.infrastructure.persistence.mapper;

import com.somosayni.habilidades.application.port.HabilidadValidadaRepository;
import com.somosayni.habilidades.domain.model.HabilidadValidada;
import com.somosayni.habilidades.infrastructure.persistence.entity.HabilidadValidadaEntity;
import com.somosayni.habilidades.infrastructure.persistence.repository.JpaHabilidadValidadaRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class HabilidadValidadaRepositoryImpl implements HabilidadValidadaRepository {

    private final JpaHabilidadValidadaRepository jpaRepository;

    public HabilidadValidadaRepositoryImpl(JpaHabilidadValidadaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<HabilidadValidada> findAll() { return jpaRepository.findAll().stream().map(HabilidadValidadaEntity::toDomain).toList(); }

    @Override
    public HabilidadValidada save(HabilidadValidada h) { return jpaRepository.save(HabilidadValidadaEntity.fromDomain(h)).toDomain(); }

    @Override
    public void deleteById(String id) { jpaRepository.deleteById(id); }

    @Override
    public List<HabilidadValidada> findByTalentoId(String talentoId) { return jpaRepository.findByTalentoId(talentoId).stream().map(HabilidadValidadaEntity::toDomain).toList(); }

    @Override
    public Optional<HabilidadValidada> findById(String id) { return jpaRepository.findById(id).map(HabilidadValidadaEntity::toDomain); }

    @Override
    public Optional<HabilidadValidada> findByTalentoIdAndNombre(String talentoId, String nombre) { return jpaRepository.findByTalentoIdAndNombre(talentoId, nombre).map(HabilidadValidadaEntity::toDomain); }
}
