package ar.edu.utn.mutantes.controller;

import ar.edu.utn.mutantes.dto.StatsResponse;
import ar.edu.utn.mutantes.service.MutantService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StatsControllerTest {

    @Mock
    private MutantService service;

    @InjectMocks
    private StatsController controller;

    @Test
    void testGetStatsReturnsCorrectValues() {

        // 1. Datos mockeados que el servicio debería devolver
        StatsResponse mockStats = new StatsResponse(40, 100, 0.4);

        // 2. Configurar el mock
        when(service.getStats()).thenReturn(mockStats);

        // 3. Ejecución del método del controlador
        StatsResponse response = controller.getStats();

        // 4. Validaciones
        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio());
    }
}
