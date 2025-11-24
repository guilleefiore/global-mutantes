package ar.edu.utn.mutantes.service;

import ar.edu.utn.mutantes.dto.StatsResponse;
import ar.edu.utn.mutantes.entity.DnaRecord;
import ar.edu.utn.mutantes.exception.InvalidDnaException;
import ar.edu.utn.mutantes.repository.DnaRecordRepository;
import ar.edu.utn.mutantes.validator.DnaValidator;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceAsyncTest {

    @Mock
    private MutantDetector detector;

    @Mock
    private DnaRecordRepository repository;

    @Mock
    private DnaValidator validator;

    @InjectMocks
    private MutantService service;

    @Test
    void testAnalyzeDnaAsyncReturnsResult() throws Exception {

        String[] dna = { "AAAA", "CAGT", "TTAT", "AGAC" };

        when(validator.isValidDna(dna)).thenReturn(true);
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        var future = service.analyzeDnaAsync(dna);

        assertTrue(future.get());  // Completa en true
        verify(detector, times(1)).isMutant(dna);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDnaAsyncThrowsForInvalidDna() {
        String[] invalid = {"AT", "AAAA"}; // inválido

        ExecutionException ex = assertThrows(
                ExecutionException.class,
                () -> service.analyzeDnaAsync(invalid).get()
        );

        assertTrue(ex.getCause() instanceof InvalidDnaException);
    }
}
