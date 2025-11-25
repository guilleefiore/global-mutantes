package ar.edu.utn.mutantes.controller;

import ar.edu.utn.mutantes.dto.StatsResponse;
import ar.edu.utn.mutantes.service.MutantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // agrupa endpoints bajo /api
public class StatsController {

    private final MutantService service;

    public StatsController(MutantService service) {
        this.service = service;
    }

    @Operation(
            summary = "Obtiene estadísticas del ADN analizado",
            description = "Retorna la cantidad de humanos, mutantes y el ratio entre ambos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Estadísticas obtenidas correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StatsResponse.class)
                    )
            )
    })
    @GetMapping("/stats")
    public StatsResponse getStats() {
        return service.getStats();
    }
}
