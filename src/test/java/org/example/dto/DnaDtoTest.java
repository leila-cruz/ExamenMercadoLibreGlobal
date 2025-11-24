package org.example.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DnaDtoTest {

    @Test
    void testDnaRequest() {
        DnaRequest request = new DnaRequest();
        String[] dna = {"AAAA", "TTTT"};
        request.setDna(dna);

        assertArrayEquals(dna, request.getDna());
        // El @ValidDnaSequence se prueba en el ControllerTest, aquí probamos el POJO
    }

    @Test
    void testStatsResponse() {
        // Probamos el constructor @AllArgsConstructor
        StatsResponse response = new StatsResponse(100, 200, 0.5);

        assertEquals(100, response.getCountMutantDna());
        assertEquals(200, response.getCountHumanDna());
        assertEquals(0.5, response.getRatio());

        // Probamos setters y constructor vacío
        StatsResponse empty = new StatsResponse();
        empty.setCountMutantDna(1);
        assertEquals(1, empty.getCountMutantDna());
    }
}