package ar.edu.utn.mutantes.service;

import ar.edu.utn.mutantes.dto.StatsResponse;
import ar.edu.utn.mutantes.entity.DnaRecord;
import ar.edu.utn.mutantes.exception.InvalidDnaException;
import ar.edu.utn.mutantes.repository.DnaRecordRepository;
import ar.edu.utn.mutantes.validator.DnaValidator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.security.MessageDigest;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector detector;
    private final DnaRecordRepository repository;
    private final DnaValidator validator;

    // ============================================================
    //  MÉTODO PRINCIPAL DEL EXAMEN: detectar mutantes (sincrónico)
    // ============================================================
    public boolean isMutant(String[] dna) {

        // 1. VALIDAR ADN (ANTES DE TODO)
        if (!validator.isValidDna(dna)) {
            throw new InvalidDnaException(
                    "El ADN enviado no es válido. Debe ser NxN y contener solo A,T,C,G."
            );
        }

        // 2. HASH ÚNICO PARA IDENTIFICAR EL ADN
        String hash = hashDna(dna);

        // 3. ¿YA EXISTE EN BD?
        var existing = repository.findByDnaHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        // 4. DETECTAR MUTANTE
        boolean mutant = detector.isMutant(dna);

        // 5. GUARDAR RESULTADO EN BD
        repository.save(new DnaRecord(hash, mutant));

        return mutant;
    }

    // ============================================================
    //  MÉTODO ASÍNCRONO — EJERCICIO 8 (Async Processing)
    // ============================================================
    @Async
    public CompletableFuture<Boolean> analyzeDnaAsync(String[] dna) {
        try {
            boolean result = isMutant(dna);
            return CompletableFuture.completedFuture(result);
        } catch (Exception ex) {
            CompletableFuture<Boolean> future = new CompletableFuture<>();
            future.completeExceptionally(ex);
            return future;
        }
    }

    // ============================================================
    //  STATS
    // ============================================================
    @Cacheable(value = "stats", condition = "@environment.acceptsProfiles('test')")
    public StatsResponse getStats() {

        long countMutants = repository.countByIsMutant(true);
        long countHumans  = repository.countByIsMutant(false);

        double ratio = 0;
        if (countHumans > 0) {
            ratio = (double) countMutants / countHumans;
        }

        return new StatsResponse(countMutants, countHumans, ratio);
    }

    // ============================================================
    //  Hash SHA-256 del ADN
    // ============================================================
    private String hashDna(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            String joined = String.join("", dna);
            byte[] hash = digest.digest(joined.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }

            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error generando hash de ADN", e);
        }
    }
}
