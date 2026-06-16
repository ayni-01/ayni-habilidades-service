package com.somosayni.habilidades.application.command;

import com.somosayni.habilidades.application.port.InsigniaRepository;
import com.somosayni.habilidades.domain.model.Insignia;
import org.springframework.stereotype.Component;

@Component
public class OtorgarInsigniaCommandHandler {

    private final InsigniaRepository repository;

    public OtorgarInsigniaCommandHandler(InsigniaRepository repository) {
        this.repository = repository;
    }

    public Insignia handle(OtorgarInsigniaCommand command) {
        Insignia insignia = new Insignia(
                command.talentoId(),
                command.retoId(),
                command.empresaId(),
                command.titulo(),
                Insignia.TipoInsignia.valueOf(command.tipo().toUpperCase())
        );
        return repository.save(insignia);
    }
}
