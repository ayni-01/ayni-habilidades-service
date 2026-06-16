package com.somosayni.habilidades.infrastructure.persistence.mapper;

import com.somosayni.habilidades.application.port.InsigniaRepository;
import com.somosayni.habilidades.domain.model.Insignia;
import com.somosayni.habilidades.infrastructure.persistence.entity.InsigniaEntity;
import com.somosayni.habilidades.infrastructure.persistence.repository.JpaInsigniaRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class InsigniaRepositoryImpl implements InsigniaRepository {

    private final JpaInsigniaRepository jpaRepository;

    public InsigniaRepositoryImpl(JpaInsigniaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<Insignia> findAll() { return jpaRepository.findAll().stream().map(InsigniaEntity::toDomain).toList(); }

    @Override
    public Insignia save(Insignia i) { return jpaRepository.save(InsigniaEntity.fromDomain(i)).toDomain(); }

    @Override
    public List<Insignia> findByRetoId(String retoId) { return jpaRepository.findByRetoId(retoId).stream().map(InsigniaEntity::toDomain).toList(); }

    @Override
    public List<Insignia> findByTalentoId(String talentoId) { return jpaRepository.findByTalentoId(talentoId).stream().map(InsigniaEntity::toDomain).toList(); }

    @Override
    public Optional<Insignia> findById(String id) { return jpaRepository.findById(id).map(InsigniaEntity::toDomain); }
}
