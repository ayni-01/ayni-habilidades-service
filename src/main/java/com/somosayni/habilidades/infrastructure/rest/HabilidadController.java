package com.somosayni.habilidades.infrastructure.rest;

import com.somosayni.habilidades.application.command.*;
import com.somosayni.habilidades.application.query.VerPortafolioQuery;
import com.somosayni.habilidades.application.query.VerPortafolioQueryHandler;
import com.somosayni.habilidades.domain.model.HabilidadValidada;
import com.somosayni.habilidades.domain.model.Insignia;
import com.somosayni.habilidades.application.port.InsigniaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/habilidades")
public class HabilidadController {

    private final ActualizarHabilidadCommandHandler actualizarHandler;
    private final VerPortafolioQueryHandler portafolioHandler;

    public HabilidadController(
            ActualizarHabilidadCommandHandler actualizarHandler,
            VerPortafolioQueryHandler portafolioHandler) {
        this.actualizarHandler = actualizarHandler;
        this.portafolioHandler = portafolioHandler;
    }

    record ActualizarHabilidadRequest(String nombre, String nivel) {}

    @PostMapping
    public ResponseEntity<HabilidadValidada> actualizarHabilidad(
            @RequestHeader("X-User-Id") String talentoId,
            @RequestBody ActualizarHabilidadRequest body) {
        HabilidadValidada h = actualizarHandler.handle(new ActualizarHabilidadCommand(talentoId, body.nombre(), body.nivel()));
        return ResponseEntity.status(HttpStatus.CREATED).body(h);
    }

    @GetMapping("/portafolio/{talentoId}")
    public ResponseEntity<VerPortafolioQueryHandler.Portafolio> verPortafolio(@PathVariable String talentoId) {
        return ResponseEntity.ok(portafolioHandler.handle(new VerPortafolioQuery(talentoId)));
    }
}

@RestController
@RequestMapping("/api/v1/insignias")
class InsigniaController {

    private final OtorgarInsigniaCommandHandler otorgaHandler;
    private final InsigniaRepository insigniaRepository;

    public InsigniaController(OtorgarInsigniaCommandHandler otorgaHandler, InsigniaRepository insigniaRepository) {
        this.otorgaHandler = otorgaHandler;
        this.insigniaRepository = insigniaRepository;
    }

    record OtorgarInsigniaRequest(String talentoId, String retoId, String titulo, String tipo) {}

    @PostMapping
    public ResponseEntity<Insignia> otorgar(
            @RequestHeader("X-User-Id") String empresaId,
            @RequestBody OtorgarInsigniaRequest body) {
        Insignia insignia = otorgaHandler.handle(new OtorgarInsigniaCommand(body.talentoId(), body.retoId(), empresaId, body.titulo(), body.tipo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(insignia);
    }

    @GetMapping("/talento/{talentoId}")
    public ResponseEntity<List<Insignia>> insigniasPorTalento(@PathVariable String talentoId) {
        List<Insignia> insignias = insigniaRepository.findByTalentoId(talentoId);
        return ResponseEntity.ok(insignias);
    }
}
