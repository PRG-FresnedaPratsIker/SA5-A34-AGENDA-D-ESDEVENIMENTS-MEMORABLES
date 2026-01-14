package prog.cipfpbatoi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class TestMenu {

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




}
