package com.somosayni.habilidades.domain.model;

import com.somosayni.shared.domain.model.AggregateRoot;
import java.time.LocalDate;

public class Insignia extends AggregateRoot {

    private String talentoId;
    private String retoId;
    private String empresaId;
    private String titulo;
    private TipoInsignia tipo;
    private LocalDate fechaOtorgada;

    public Insignia() {}

    public Insignia(String talentoId, String retoId, String empresaId, String titulo, TipoInsignia tipo) {
        this.talentoId = talentoId;
        this.retoId = retoId;
        this.empresaId = empresaId;
        this.titulo = titulo;
        this.tipo = tipo;
        this.fechaOtorgada = LocalDate.now();
    }

    public String getTalentoId() { return talentoId; }
    public String getRetoId() { return retoId; }
    public String getEmpresaId() { return empresaId; }
    public String getTitulo() { return titulo; }
    public TipoInsignia getTipo() { return tipo; }
    public LocalDate getFechaOtorgada() { return fechaOtorgada; }

    public enum TipoInsignia {
        TOP_10, VERIFICADO, CREATIVIDAD
    }
}
