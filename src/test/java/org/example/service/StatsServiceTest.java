package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Habilita Mockito
class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository; // Simulamos el repositorio

    @InjectMocks
    private StatsService statsService; // Inyectamos el mock en el servicio

    @Test
    @DisplayName("Debe calcular ratio correcto con mutantes y humanos (0.4)")
    void testGetStatsStandardRatio() {
        // GIVEN: Simulamos que la BD devuelve 40 mutantes y 100 humanos
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        // WHEN: Ejecutamos el método
        StatsResponse response = statsService.getStats();

        // THEN: Verificamos los resultados
        assertEquals(40, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.4, response.getRatio()); // 40 / 100 = 0.4
    }

    @Test
    @DisplayName("Debe manejar división por cero: 0 humanos, >0 mutantes")
    void testGetStatsNoHumans() {
        // Caso borde: Hay mutantes pero 0 humanos.
        // Tu lógica dice: return countMutant > 0 ? (double) countMutant : 0.0;

        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(10, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(10.0, response.getRatio()); // Verifica tu lógica de negocio
    }

    @Test
    @DisplayName("Debe retornar ratio 0.0 si no hay mutantes")
    void testGetStatsNoMutants() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCountMutantDna());
        assertEquals(100, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    @DisplayName("Debe retornar todo en 0 si la BD está vacía")
    void testGetStatsEmptyDatabase() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCountMutantDna());
        assertEquals(0, response.getCountHumanDna());
        assertEquals(0.0, response.getRatio());
    }
}