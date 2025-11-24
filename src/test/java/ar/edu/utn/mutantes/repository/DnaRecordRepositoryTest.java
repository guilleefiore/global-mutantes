package ar.edu.utn.mutantes.repository;

import ar.edu.utn.mutantes.entity.DnaRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DnaRecordRepositoryTest {

    @Autowired
    private DnaRecordRepository repository;

    @Test
    void testSaveAndFind() {
        DnaRecord record = new DnaRecord("abc123", true);
        repository.save(record);

        Optional<DnaRecord> found = repository.findByDnaHash("abc123");

        assertTrue(found.isPresent());
        assertEquals("abc123", found.get().getDnaHash());
        assertTrue(found.get().isMutant());
    }

    @Test
    void testCountMutantsAndHumans() {
        repository.save(new DnaRecord("h1", false));
        repository.save(new DnaRecord("h2", false));
        repository.save(new DnaRecord("m1", true));

        assertEquals(2, repository.countByIsMutant(false));
        assertEquals(1, repository.countByIsMutant(true));
    }
}
