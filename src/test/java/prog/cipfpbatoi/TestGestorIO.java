package prog.cipfpbatoi;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class TestGestorIO {

    private PrintStream oldOut;
    private InputStream oldIn;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() throws Exception {
        oldOut = System.out;
        oldIn = System.in;

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(oldOut);
        System.setIn(oldIn);
    }

    private void setInput(String data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);
        GestorIO.__setInputStreamForTests(in);
    }

    @Test
    public void testLlegirEnter_OK() {
        setInput("123\n");
        int valor = GestorIO.llegirEnter("Introdueix un número: ");
        assertEquals(123, valor);

        String salida = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("Introdueix un número: "));
    }

    @Test
    public void testLlegirEnter_reintentaHastaValido() {
        setInput("hola\n-7\n");
        int valor = GestorIO.llegirEnter("Num: ");
        assertEquals(-7, valor);

        String salida = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("Num: "));
        assertTrue(salida.contains("Error: Introdueix un número enter vàlid."));
    }

    @Test
    public void testLlegirText() {
        setInput("Esto es una línea\n");
        String texto = GestorIO.llegirText("Escriu: ");
        assertEquals("Esto es una línea", texto);

        String salida = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("Escriu: "));
    }

    @Test
    public void testSolicitarConfirmacio_devuelveTrueConS() {
        setInput("s\n");
        boolean ok = GestorIO.solicitarConfirmacio("Confirmes?");
        assertTrue(ok);

        String salida = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("Confirmes? (s/n): "));
    }

    @Test
    public void testSolicitarConfirmacio_repiteHastaSN() {
        setInput("quizas\nn\n");
        boolean ok = GestorIO.solicitarConfirmacio("Segur?");
        assertFalse(ok);

        String salida = outContent.toString(StandardCharsets.UTF_8);
        assertTrue(countOccurrences(salida, "Segur? (s/n): ") >= 2);
    }

    private static int countOccurrences(String text, String sub) {
        int count = 0, idx = 0;
        while ((idx = text.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }



}
