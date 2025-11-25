package ar.edu.utn.mutantes.integration;

import ar.edu.utn.mutantes.dto.StatsResponse;
import ar.edu.utn.mutantes.repository.DnaRecordRepository;
import ar.edu.utn.mutantes.service.MutantService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class StatsCacheIntegrationTest {

    @Autowired
    private MutantService service;

    @MockBean
    private DnaRecordRepository repository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testStatsResponseIsCached() {

        when(repository.countByIsMutant(true)).thenReturn(5L);
        when(repository.countByIsMutant(false)).thenReturn(10L);

        StatsResponse r1 = service.getStats();
        StatsResponse r2 = service.getStats(); // debería salir de la caché

        verify(repository, times(1)).countByIsMutant(true);
        verify(repository, times(1)).countByIsMutant(false);

        assertEquals(r1.getRatio(), r2.getRatio());
    }
}
