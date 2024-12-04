package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BestEffortExtraTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void init() {
        // Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                new Traslado(1, 0, 1, 100, 10),
                new Traslado(2, 0, 1, 400, 20),
                new Traslado(3, 3, 4, 500, 50),
                new Traslado(4, 4, 3, 500, 11),
                new Traslado(5, 1, 0, 1000, 40),
                new Traslado(6, 1, 0, 1000, 41),
                new Traslado(7, 6, 3, 2000, 42)
        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2)
                    encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " + e1 + " en el arreglo " + s2.toString());
        }
    }
    
    @Test
    void despachar_vacio() {
        Traslado[] nuevos = new Traslado[] {};

        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        assertTrue(sis.despacharMasRedituables(1).length == 0);
        assertTrue(sis.despacharMasAntiguos(1).length == 0);

        assertEquals(new ArrayList<>(), sis.ciudadesConMayorGanancia());
        assertEquals(new ArrayList<>(), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertEquals(0, sis.gananciaPromedioPorTraslado());
    }

    @Test
    void despachar_mas_elementos_de_los_que_hay() {
        Traslado[] nuevos = new Traslado[] { new Traslado(1, 3, 4, 1, 7) };
        BestEffort sis_redituable = new BestEffort(this.cantCiudades, nuevos);
        BestEffort sis_antiguo = new BestEffort(this.cantCiudades, nuevos);

        assertEquals(1, sis_redituable.despacharMasRedituables(3).length);
        assertEquals(1, sis_antiguo.despacharMasAntiguos(3).length);

    }

    @Test
    void despachar_mixto_de_a_varios() {
        Traslado[] nuevos = new Traslado[] {
                new Traslado(1, 3, 4, 1, 7),
                new Traslado(7, 6, 5, 40, 6),
                new Traslado(6, 5, 6, 3, 8),
                new Traslado(2, 2, 1, 41, 4),
                new Traslado(3, 3, 4, 100, 5),
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(1);
        sis.despacharMasAntiguos(1);
        sis.despacharMasRedituables(1);

        assertEquals(3, sis.ciudadConMayorSuperavit());
        assertEquals(46, sis.gananciaPromedioPorTraslado());
    }

    @Test
    void mayor_superavit_redituables() {
        Traslado[] nuevos = new Traslado[] {
                new Traslado(1, 3, 6, 110, 7),
                new Traslado(7, 6, 5, 120, 6),
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasRedituables(1);
        assertEquals(6, sis.ciudadConMayorSuperavit());

        sis.despacharMasRedituables(1);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
    }
}
