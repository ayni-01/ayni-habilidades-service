package com.somosayni.habilidades.application.command;

public record ActualizarHabilidadCommand(
        String talentoId,
        String nombre,
        String nivel
) {}
