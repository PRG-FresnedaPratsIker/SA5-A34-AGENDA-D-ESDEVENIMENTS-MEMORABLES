package prog.cipfpbatoi;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class TestAgenda {

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

    private static Esdeveniment getSlot(Agenda agenda, String fieldName) throws Exception {
        Field f = Agenda.class.getDeclaredField(fieldName);

        f.setAccessible(true);

        return (Esdeveniment) f.get(agenda);
    }


    @Test
    public void testAfegirEsdeveniment_valid_noImprime_ySeGuardaEnSlot1() throws Exception {
        Agenda agenda = new Agenda();
        Esdeveniment e1 = new Esdeveniment(TipusEsdeveniment.ALTRES, "Esdeveniment 1", 10);

        agenda.afegirEsdeveniment(e1);

        String salida = baos.toString(StandardCharsets.UTF_8);
        assertTrue(salida.isEmpty(), "No s'hauria de mostrar cap missatge quan s'afegeix un esdeveniment vàlid.");

        assertSame(e1, getSlot(agenda, "esdeveniment1"), "L'esdeveniment no s'ha guardat en esdeveniment1.");
        assertNull(getSlot(agenda, "esdeveniment2"), "esdeveniment2 hauria de ser null.");
        assertNull(getSlot(agenda, "esdeveniment3"), "esdeveniment3 hauria de ser null.");
    }

    @Test
    public void testAfegirEsdeveniment_passat_imprimeError_yNoSeGuarda() throws Exception {
        Agenda agenda = new Agenda();
        Esdeveniment passat = new Esdeveniment(TipusEsdeveniment.ALTRES, "Passat", -1);

        agenda.afegirEsdeveniment(passat);

        String salida = baos.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("No es pot afegir l'esdeveniment perquè ja ha tingut lloc."),
                "Hauria d'imprimir el missatge quan l'esdeveniment ja ha tingut lloc.");

        assertNull(getSlot(agenda, "esdeveniment1"), "No s'hauria de guardar cap esdeveniment.");
        assertNull(getSlot(agenda, "esdeveniment2"), "No s'hauria de guardar cap esdeveniment.");
        assertNull(getSlot(agenda, "esdeveniment3"), "No s'hauria de guardar cap esdeveniment.");
    }

    @Test
    public void testCercarEsdevenimentPerData_noEsdevenimentsDevuelveNull() {
        Agenda agenda = new Agenda();
        Data dataBuscada = new Data().afegir(5);

        Esdeveniment resultat = agenda.cercarEsdevenimentPerData(dataBuscada);
        assertNull(resultat, "Quan no hi ha esdeveniments, hauria de retornar null.");
    }

    @Test
    public void testCercarEsdevenimentPerData_variosEsdeveniments() {
        Agenda agenda = new Agenda();

        Esdeveniment e1 = new Esdeveniment(TipusEsdeveniment.ALTRES, "Esdeveniment 1", 3);
        Esdeveniment e2 = new Esdeveniment(TipusEsdeveniment.ESPECIAL, "Esdeveniment 2", 7);
        Esdeveniment e3 = new Esdeveniment(TipusEsdeveniment.ALTRES, "Esdeveniment 3", 10);

        agenda.afegirEsdeveniment(e1);
        agenda.afegirEsdeveniment(e2);
        agenda.afegirEsdeveniment(e3);

        Data dataBuscada = new Data().afegir(6);

        Esdeveniment resultat = agenda.cercarEsdevenimentPerData(dataBuscada);

        assertSame(e2, resultat, "Hauria de retornar l'esdeveniment més proper a la data buscada.");
    }

    @Test
    public void testMostrarTotsElsEsdeveniments_noEsdeveniments_imprimeMensaje() {
        Agenda agenda = new Agenda();

        agenda.mostrarTotsElsEsdeveniments();

        String salida = baos.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("No hi ha cap esdeveniment registrat a l'agenda."), "Hauria d'imprimir el missatge quan no hi ha esdeveniments.");
    }

    @Test
    public void testMostrarTotsElsEsdeveniments_variosEsdeveniments_imprimeCorrectamente() {
        Agenda agenda = new Agenda();

        Esdeveniment e1 = new Esdeveniment(TipusEsdeveniment.ALTRES, "Esdeveniment 1", 3);
        Esdeveniment e2 = new Esdeveniment(TipusEsdeveniment.ESPECIAL, "Esdeveniment 2", 7);

        agenda.afegirEsdeveniment(e1);
        agenda.afegirEsdeveniment(e2);

        agenda.mostrarTotsElsEsdeveniments();

        String salida = baos.toString(StandardCharsets.UTF_8);

        assertTrue(salida.contains("Esdeveniment 1"), "Hauria d'imprimir l'esdeveniment 1.");
        assertTrue(salida.contains("Esdeveniment 2"), "Hauria d'imprimir l'esdeveniment 2.");
    }

}






