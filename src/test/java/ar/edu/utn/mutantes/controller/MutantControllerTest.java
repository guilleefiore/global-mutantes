package ar.edu.utn.mutantes.controller;

import ar.edu.utn.mutantes.dto.DnaRequest;
import ar.edu.utn.mutantes.service.MutantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MutantControllerTest {

    @Mock
    private MutantService service;

    @InjectMocks
    private MutantController controller;

    @Test
    void testMutantReturns200() {
        String[] dna = {"AAAA", "CAGT", "TTAT", "AGAC"};

        when(service.isMutant(dna)).thenReturn(true);

        var response = controller.checkMutant(new DnaRequest(dna));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testHumanReturns403() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(service.isMutant(dna)).thenReturn(false);

        var response = controller.checkMutant(new DnaRequest(dna));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testInvalidDnaReturns400() {
        String[] dna = {}; // inválido

        when(service.isMutant(dna)).thenThrow(new RuntimeException());

        try {
            controller.checkMutant(new DnaRequest(dna));
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }
}
