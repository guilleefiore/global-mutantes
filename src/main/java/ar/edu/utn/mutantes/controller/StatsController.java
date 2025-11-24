package ar.edu.utn.mutantes.controller;

import ar.edu.utn.mutantes.dto.StatsResponse;
import ar.edu.utn.mutantes.service.MutantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // opcional pero ordena la API
public class StatsController {

    private final MutantService service;

    public StatsController(MutantService service) {
        this.service = service;
    }

    @GetMapping("/stats")
    public StatsResponse getStats() {
        // el servicio ya maneja ratio seguro (sin división por cero)
        return service.getStats();
    }
}
