package ar.edu.utn.mutantes.integration;

import ar.edu.utn.mutantes.dto.DnaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RateLimitingIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private final String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
    };

    @Test
    void testRateLimitingBlocksAfter10Requests() throws Exception {

        DnaRequest request = new DnaRequest(dna);

        // Las primeras 10 deben devolver 200 o 403 (dependiendo del ADN),
        // lo importante es que NO deben dar 429.
        for (int i = 1; i <= 10; i++) {
            mockMvc.perform(post("/mutant")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("X-Forwarded-For", "1.1.1.1")  // <-- fija la IP
                            .content(mapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        // La request N° 11 debe ser bloqueada
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Forwarded-For", "1.1.1.1")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isTooManyRequests());
    }
}
