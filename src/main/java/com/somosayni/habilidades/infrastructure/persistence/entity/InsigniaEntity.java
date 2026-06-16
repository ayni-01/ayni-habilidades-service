package com.somosayni.habilidades.infrastructure.persistence.entity;

import com.somosayni.habilidades.domain.model.Insignia;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "insignia")
public class InsigniaEntity {

    @Id
    private String id;

    @Column(name = "talento_id", nullable = false)
    private String talentoId;

    @Column(name = "reto_id", nullable = false)
    private String retoId;

    @Column(name = "empresa_id", nullable = false)
    private String empresaId;

    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Insignia.TipoInsignia tipo;

    @Column(name = "fecha_otorgada", nullable = false)
    private LocalDate fechaOtorgada;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
    }

    public Insignia toDomain() {
        Insignia i = new Insignia(talentoId, retoId, empresaId, titulo, tipo);
        i.setId(id);
        return i;
    }

    public static InsigniaEntity fromDomain(Insignia i) {
        InsigniaEntity e = new InsigniaEntity();
        e.id = i.getId();
        e.talentoId = i.getTalentoId();
        e.retoId = i.getRetoId();
        e.empresaId = i.getEmpresaId();
        e.titulo = i.getTitulo();
        e.tipo = i.getTipo();
        e.fechaOtorgada = i.getFechaOtorgada();
        return e;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTalentoId() { return talentoId; }
    public void setTalentoId(String talentoId) { this.talentoId = talentoId; }
    public String getRetoId() { return retoId; }
    public void setRetoId(String retoId) { this.retoId = retoId; }
    public String getEmpresaId() { return empresaId; }
    public void setEmpresaId(String empresaId) { this.empresaId = empresaId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Insignia.TipoInsignia getTipo() { return tipo; }
    public void setTipo(Insignia.TipoInsignia tipo) { this.tipo = tipo; }
    public LocalDate getFechaOtorgada() { return fechaOtorgada; }
    public void setFechaOtorgada(LocalDate fechaOtorgada) { this.fechaOtorgada = fechaOtorgada; }
}
