package ar.edu.utn.mutantes.service;

import ar.edu.utn.mutantes.dto.StatsResponse;
import ar.edu.utn.mutantes.entity.DnaRecord;
import ar.edu.utn.mutantes.repository.DnaRecordRepository;
import ar.edu.utn.mutantes.validator.DnaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutantServiceTest {

    private MutantService service;
    private MutantDetector detector;
    private DnaRecordRepository repository;
    private DnaValidator validator;

    @BeforeEach
    void setup() {
        detector = mock(MutantDetector.class);
        repository = mock(DnaRecordRepository.class);
        validator = new DnaValidator();
        service = new MutantService(detector, repository, validator);
    }

    @Test
    void testMutantAlreadyExists() {
        String[] dna = {"AAAA", "TTTT", "CCCC", "GGGG"};
        String hash = "abc123";

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.of(new DnaRecord(hash, true)));

        boolean result = service.isMutant(dna);

        assertTrue(result);
        verify(detector, never()).isMutant(any()); // NO debe ejecutarlo
    }

    @Test
    void testNewMutantIsSaved() {
        String[] dna = {"AAAA", "TTTT", "CCCC", "GGGG"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());

        when(detector.isMutant(dna)).thenReturn(true);

        boolean result = service.isMutant(dna);

        assertTrue(result);
        verify(repository).save(any(DnaRecord.class));
    }

    @Test
    void testStats() {
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse stats = service.getStats();

        assertEquals(40, stats.getCountMutantDna());
        assertEquals(100, stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio());
    }
}
