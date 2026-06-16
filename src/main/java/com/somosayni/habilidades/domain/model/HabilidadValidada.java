package com.somosayni.habilidades.domain.model;

import com.somosayni.shared.domain.model.AggregateRoot;
import java.time.LocalDate;

public class HabilidadValidada extends AggregateRoot {

    private String talentoId;
    private String nombre;
    private NivelHabilidad nivel;
    private int porcentaje;

    public HabilidadValidada() {}

    public HabilidadValidada(String talentoId, String nombre, NivelHabilidad nivel) {
        this.talentoId = talentoId;
        this.nombre = nombre;
        this.nivel = nivel;
        this.porcentaje = switch (nivel) {
            case BASICO -> 25;
            case INTERMEDIO -> 50;
            case AVANZADO -> 75;
            case EXPERTO -> 100;
        };
    }

    public void actualizarNivel(NivelHabilidad nuevoNivel) {
        this.nivel = nuevoNivel;
        this.porcentaje = switch (nuevoNivel) {
            case BASICO -> 25;
            case INTERMEDIO -> 50;
            case AVANZADO -> 75;
            case EXPERTO -> 100;
        };
    }

    public String getTalentoId() { return talentoId; }
    public String getNombre() { return nombre; }
    public NivelHabilidad getNivel() { return nivel; }
    public int getPorcentaje() { return porcentaje; }

    public enum NivelHabilidad {
        BASICO, INTERMEDIO, AVANZADO, EXPERTO
    }
}
