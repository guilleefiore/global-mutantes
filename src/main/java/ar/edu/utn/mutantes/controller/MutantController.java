package ar.edu.utn.mutantes.controller;

import ar.edu.utn.mutantes.dto.DnaRequest;
import ar.edu.utn.mutantes.service.MutantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final MutantService service;

    public MutantController(MutantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> checkMutant(@RequestBody DnaRequest request) {

        boolean isMutant = service.isMutant(request.getDna());

        if (isMutant) {
            // MUTANTE → 200 OK
            return ResponseEntity.ok().build();
        } else {
            // HUMANO → 403 FORBIDDEN
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
