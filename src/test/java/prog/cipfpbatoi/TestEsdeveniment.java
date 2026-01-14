package prog.cipfpbatoi;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class TestEsdeveniment {

    private PrintStream oldOut;
    private ByteArrayOutputStream baos;

    @BeforeEach
    public void before() {
        baos = new ByteArrayOutputStream();
        oldOut = System.out;
        System.setOut(new PrintStream(baos, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    public void after() {
        System.setOut(oldOut);
    }

    private static String formatES(Data d) {
        return String.format("%02d/%02d/%04d", d.getDia(), d.getMes(), d.getAny());
    }

    @Test
    public void testConstructor_3parametros_sinUbicacio_yDies() {
        Data hoy = new Data();
        long dies = 5;

        Esdeveniment e = new Esdeveniment(TipusEsdeveniment.ALTRES, "Nota", dies);

        e.mostrar();
        String salida = baos.toString(StandardCharsets.UTF_8);

        Data esperada = hoy.afegir(dies);
        String esperadaStr = formatES(esperada);

        assertTrue(salida.toUpperCase().contains("ALTRES (NOTA)"),
                "No està mostrant el format Tipus (nota). Sortida: " + salida);

        assertFalse(salida.contains(" -> "),
                "No hauria de mostrar ubicació si és null. Sortida: " + salida);

        assertTrue(salida.contains(" - Data: "),
                "Falta el text ' - Data: '. Sortida: " + salida);

        assertTrue(salida.contains(esperadaStr),
                "La data mostrada no coincideix amb avui + " + dies + " dies. Sortida: " + salida);
    }

    @Test
    public void testConstructor_3parametros_conUbicacio_senseDies_perDefecte30() {
        Data hoy = new Data();

        Esdeveniment e = new Esdeveniment(TipusEsdeveniment.FESTIU, "Vacances", "València");

        e.mostrar();
        String salida = baos.toString(StandardCharsets.UTF_8);

        Data esperada = hoy.afegir(30);
        String esperadaStr = formatES(esperada);

        assertTrue(salida.toUpperCase().contains("FESTIU (VACANCES)"),
                "No apareix Tipus (nota). Sortida: " + salida);

        assertTrue(salida.contains(" -> València - Data: "),
                "Format futur amb ubicació incorrecte. Sortida: " + salida);

        assertTrue(salida.contains(esperadaStr),
                "Per defecte hauria de ser avui + 30 dies. Sortida: " + salida);
    }

    @Test
    public void testMostrar_pasado_muestraJaHaTingutLloc() {
        Esdeveniment e = new Esdeveniment(TipusEsdeveniment.ANIVERSARI, "Cumple", "Casa", -1);

        e.mostrar();
        String salida = baos.toString(StandardCharsets.UTF_8);

        assertTrue(salida.toUpperCase().contains("ANIVERSARI (CUMPLE)"),
                "No apareix Tipus (nota). Sortida: " + salida);

        assertTrue(salida.contains(" -> Ja ha tingut lloc el dia "),
                "Quan és passat hauria de mostrar 'Ja ha tingut lloc'. Sortida: " + salida);

        assertTrue(salida.matches("(?s).*\\d{2}/\\d{2}/\\d{4}.*"),
                "Hauria de mostrar una data dd/MM/yyyy. Sortida: " + salida);
    }

    @Test
    public void testSubstituir_cambiaLaFecha() {
        Data hoy = new Data();

        Esdeveniment e = new Esdeveniment(TipusEsdeveniment.ESPECIAL, "X", "Y", 10);

        e.substituir(2);
        e.mostrar();
        String salida = baos.toString(StandardCharsets.UTF_8);

        Data esperada = hoy.afegir(2);
        String esperadaStr = formatES(esperada);

        assertTrue(salida.contains(esperadaStr),
                "Després de substituir, la data no és avui + 2 dies. Sortida: " + salida);
    }

    @Test
    public void testEsMesProximA_true_i_false() {
        Data base = new Data();

        Esdeveniment e1 = new Esdeveniment(TipusEsdeveniment.ALTRES, "A", "U", 5);
        Esdeveniment e2 = new Esdeveniment(TipusEsdeveniment.ALTRES, "B", "U", 10);

        assertTrue(e1.esMesProximA(base, e2), "e1 (5 dies) hauria de ser més pròxim que e2 (10 dies).");
        assertFalse(e2.esMesProximA(base, e1), "e2 (10 dies) NO hauria de ser més pròxim que e1 (5 dies).");
    }

    @Test
    public void testEsMesProximA_nulls_devuelveFalse_yMensaje() {
        Esdeveniment e1 = new Esdeveniment(TipusEsdeveniment.ALTRES, "A", "U", 5);

        boolean res = e1.esMesProximA(null, null);
        String salida = baos.toString(StandardCharsets.UTF_8);

        assertFalse(res, "Amb paràmetres nuls hauria de retornar false.");
        assertTrue(salida.toLowerCase().contains("no poden ser nuls"),
                "Hauria d'imprimir el missatge d'error per null. Sortida: " + salida);
    }

    @Test
    public void testTeLlocDespres_true_false_y_null() {
        Data base = new Data();
        Data abans = base.restar(1);
        Data futur = base.afegir(10);

        Esdeveniment e = new Esdeveniment(TipusEsdeveniment.ESPECIAL, "A", "U", 5);

        assertTrue(e.teLlocDespres(abans), "Un esdeveniment a +5 dies hauria de ser després d'ahir.");
        assertFalse(e.teLlocDespres(futur), "Un esdeveniment a +5 dies NO hauria de ser després d'una data a +10 dies.");

        boolean resNull = e.teLlocDespres(null);
        String salida = baos.toString(StandardCharsets.UTF_8);
        assertFalse(resNull, "Amb dataComparar null hauria de retornar false.");
        assertTrue(salida.toLowerCase().contains("no pot ser nul"),
                "Hauria d'imprimir el missatge d'error per null. Sortida: " + salida);
    }

    @Test
    public void testJaHaTingutLloc() {
        Esdeveniment passat = new Esdeveniment(
                TipusEsdeveniment.ALTRES, "P", "U", -1
        );
        assertTrue(passat.haTingutLloc(), "Un esdeveniment en el passat hauria de ser 'ja ha tingut lloc'.");

        Esdeveniment futur = new Esdeveniment(
                TipusEsdeveniment.ALTRES, "F", "U", 5
        );
        assertFalse(futur.haTingutLloc(), "Un esdeveniment en el futur NO hauria de ser 'ja ha tingut lloc'.");

        Esdeveniment hui = new Esdeveniment(
                TipusEsdeveniment.ALTRES, "H", "U", 0
        );
        assertTrue(hui.haTingutLloc(), "Si és 'hui', no és posterior, per tant es considera 'ja ha tingut lloc'.");
    }


}
