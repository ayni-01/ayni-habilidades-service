package com.somosayni.habilidades.application.command;

public record OtorgarInsigniaCommand(
        String talentoId,
        String retoId,
        String empresaId,
        String titulo,
        String tipo
) {}
