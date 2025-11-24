package ar.edu.utn.mutantes.repository;

import ar.edu.utn.mutantes.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    // Buscar ADN por hash
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    // Saber si un hash ya existe en la BD
    boolean existsByDnaHash(String dnaHash);

    // Contar mutantes y humanos
    long countByIsMutant(boolean isMutant);
}
