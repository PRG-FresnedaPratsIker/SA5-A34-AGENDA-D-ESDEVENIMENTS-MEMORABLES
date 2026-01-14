package prog.cipfpbatoi;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class TestMenu {

    private PrintStream oldOut;
    private ByteArrayOutputStream baos;

    @BeforeEach
    public void before() {
        oldOut = System.out;
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    public void after() {
        System.setOut(oldOut);
    }

    @Test
    public void testConstructor_creaAgenda() throws Exception {
        Menu menu = new Menu();

        Field f = Menu.class.getDeclaredField("agenda");
        f.setAccessible(true);
        Object agenda = f.get(menu);

        assertNotNull(agenda, "El Menu hauria de crear una Agenda en el constructor.");
    }

    @Test
    public void testMostrarOpcions_muestraOpciones() throws Exception {
        Menu menu = new Menu();

        Method m = Menu.class.getDeclaredMethod("mostrarOpcions");
        m.setAccessible(true);
        m.invoke(menu);

        String salida = baos.toString(StandardCharsets.UTF_8);

        assertTrue(salida.contains("Menú d'opcions:"), "Ha de mostrar el títol del menú.");
        assertTrue(salida.contains("1. Crear nou esdeveniment"), "Ha de mostrar l'opció 1.");
        assertTrue(salida.contains("2. Buscar esdeveniment per data"), "Ha de mostrar l'opció 2.");
        assertTrue(salida.contains("3. Mostrar l'estat actual de l'agenda"), "Ha de mostrar l'opció 3.");
        assertTrue(salida.contains("4. Eixir"), "Ha de mostrar l'opció 4.");
    }
}
