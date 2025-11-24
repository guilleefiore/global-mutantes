package ar.edu.utn.mutantes.integration;

import ar.edu.utn.mutantes.dto.DnaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.MessageDigest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MutantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    // ----------------------------------------------------------
    // ️ FUNCIONA PARA OBTENER EXACTAMENTE EL HASH DE MUTANTSERVICE
    // ----------------------------------------------------------
    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generando hash", e);
        }
    }

    // ----------------------------------------------------------
    // TESTS NORMALES
    // ----------------------------------------------------------

    @Test
    void testMutantEndpoint200() throws Exception {
        String[] dna = {
                "ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"
        };

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isOk());
    }

    @Test
    void testMutantEndpoint403() throws Exception {
        String[] dna = {
                "ATGCAT","TACGTA","CGTACG","GCATGC","ATGCAT","TACGTA"
        };

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isForbidden());
    }

    @Test
    void testInvalidDna400() throws Exception {
        String[] dna = {};

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new DnaRequest(dna))))
                .andExpect(status().isBadRequest());
    }
}
