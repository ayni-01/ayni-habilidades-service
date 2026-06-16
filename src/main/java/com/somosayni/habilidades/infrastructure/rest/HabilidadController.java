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

    @PostMapping
    public ResponseEntity<HabilidadValidada> actualizarHabilidad(
            @RequestHeader("X-User-Id") String talentoId,
            @RequestParam String nombre,
            @RequestParam String nivel) {
        HabilidadValidada h = actualizarHandler.handle(new ActualizarHabilidadCommand(talentoId, nombre, nivel));
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

    @PostMapping
    public ResponseEntity<Insignia> otorgar(
            @RequestHeader("X-User-Id") String empresaId,
            @RequestParam String talentoId,
            @RequestParam String retoId,
            @RequestParam String titulo,
            @RequestParam String tipo) {
        Insignia insignia = otorgaHandler.handle(new OtorgarInsigniaCommand(talentoId, retoId, empresaId, titulo, tipo));
        return ResponseEntity.status(HttpStatus.CREATED).body(insignia);
    }

    @GetMapping("/talento/{talentoId}")
    public ResponseEntity<List<Insignia>> insigniasPorTalento(@PathVariable String talentoId) {
        List<Insignia> insignias = insigniaRepository.findByTalentoId(talentoId);
        return ResponseEntity.ok(insignias);
    }
}
