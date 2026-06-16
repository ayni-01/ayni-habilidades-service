package com.somosayni.habilidades.infrastructure.persistence.entity;

import com.somosayni.habilidades.domain.model.HabilidadValidada;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "habilidad_validada")
public class HabilidadValidadaEntity {

    @Id
    private String id;

    @Column(name = "talento_id", nullable = false)
    private String talentoId;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HabilidadValidada.NivelHabilidad nivel;

    @Column(nullable = false)
    private int porcentaje;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public HabilidadValidada toDomain() {
        HabilidadValidada h = new HabilidadValidada(talentoId, nombre, nivel);
        h.setId(id);
        return h;
    }

    public static HabilidadValidadaEntity fromDomain(HabilidadValidada h) {
        HabilidadValidadaEntity e = new HabilidadValidadaEntity();
        e.id = h.getId();
        e.talentoId = h.getTalentoId();
        e.nombre = h.getNombre();
        e.nivel = h.getNivel();
        e.porcentaje = h.getPorcentaje();
        return e;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTalentoId() { return talentoId; }
    public void setTalentoId(String talentoId) { this.talentoId = talentoId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public HabilidadValidada.NivelHabilidad getNivel() { return nivel; }
    public void setNivel(HabilidadValidada.NivelHabilidad nivel) { this.nivel = nivel; }
    public int getPorcentaje() { return porcentaje; }
    public void setPorcentaje(int porcentaje) { this.porcentaje = porcentaje; }
}
