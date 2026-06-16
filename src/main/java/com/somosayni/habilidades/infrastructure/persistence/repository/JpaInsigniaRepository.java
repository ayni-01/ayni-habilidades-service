package com.somosayni.habilidades.infrastructure.persistence.repository;

import com.somosayni.habilidades.infrastructure.persistence.entity.InsigniaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaInsigniaRepository extends JpaRepository<InsigniaEntity, String> {
    List<InsigniaEntity> findByTalentoId(String talentoId);
    List<InsigniaEntity> findByRetoId(String retoId);
}
