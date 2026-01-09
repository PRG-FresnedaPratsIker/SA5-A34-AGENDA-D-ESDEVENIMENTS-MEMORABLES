package prog.cipfpbatoi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TestData {

    private PrintStream oldOut;
    private ByteArrayOutputStream baos;
    @BeforeEach
    public void before() {
        // Redirigir salida estándar
        baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        oldOut = System.out;
        System.out.flush();
        System.setOut(ps);
    }

    @AfterEach
    public void after() {
        // Reestablecer salida estándar
        System.setOut(oldOut);
    }

    @Test
    public void testConstructorPorDefecto() {

        Data fecha = new Data();
        assertTrue(fecha.getDia() == LocalDate.now().getDayOfMonth() 
                && fecha.getMes() == LocalDate.now().getMonthValue() 
                && fecha.getAny() == LocalDate.now().getYear(), 
                "El constructor por defecto no inicializa los valores de dia, mes y año a los valores indicados en el enunciado");
    }

    @Test
    public void testConstructorParametrizado3Parametros() {
        int dia = 3;
        int mes = 4;
        int anyo = 2015;
        Data fecha = new Data(dia, mes, anyo);
        assertTrue(fecha.getDia() == dia && fecha.getMes() == mes && fecha.getAny() == anyo, 
                "El constructor parametrizado con 3 parámetros no inicializa correctamente los valores");
    }

    @Test
    public void testConstructorParametrizado1ParametroString() {
        int dia = 3;
        int mes = 4;
        int anyo = 2015;
        Data fecha = new Data(dia + "/" + mes + "/" + anyo);
        assertTrue(fecha.getDia() == dia && fecha.getMes() == mes && fecha.getAny() == anyo, "El constructor parametrizado con 1 parámetro String no está inicializando correctamente los valores del objeto");
    }

    @Test
    public void testSet() {
        Data fecha = new Data(3, 5, 2014);
        int diaFinal = 4;
        int mesFinal = 9;
        int anyoFinal = 2020;
        fecha.set(diaFinal, mesFinal, anyoFinal);
        assertTrue(fecha.getDia() == diaFinal && fecha.getMes() == mesFinal && fecha.getAny() == anyoFinal, "El método set no asigna correctamente todos los datos de la fecha");
    }

    @Test
    public void testClone() {
        int dia = 4; int mes = 9; int anyo = 2020;
        Data fecha = new Data(dia, mes, anyo);
        Data fechaClon = fecha.copy();

        assertTrue(fechaClon.getDia() == dia && fechaClon.getMes() == mes && fechaClon.getAny() == anyo, "La fecha clonada y la fecha original no contienen la misma información");
    }

    @Test
    public void testIsEqual() {
        Data fecha1 = new Data(3, 6, 298);
        Data fecha2 = new Data(3, 6, 298);
        Data fecha3 = new Data("03/06/298");
        Data fecha4 = new Data();

        assertTrue(fecha1.isIgual(fecha2), "La fechas creadas con un mismo constructor no coinciden conteniendo la misma información");
        assertTrue(fecha2.isIgual(fecha3), "Las fechas creadas con diferentes constructores no coinciden conteniendo la misma información");
        assertTrue(fecha3.isIgual(fecha2), "Las fechas creadas con diferentes constructores no coinciden conteniendo la misma información");
        assertFalse(fecha1.isIgual(fecha4), "Las fechas son diferentes y se están dando como iguales");
    }

    @Test
    public void testGetDiaSemana() {
        Data fecha = new Data(12, 12, 2023);
        String diaSemanaObtenido = fecha.getDiaSetmana();
        String diaSemanaEsperado = "dimarts";
        assertTrue(diaSemanaObtenido.equalsIgnoreCase(diaSemanaEsperado), "Esperaba que fuera "+ diaSemanaEsperado +" y el 12/12/2023 se ha determinado que es " + diaSemanaObtenido);

        Data fecha2 = new Data(1, 1, 2012);
        diaSemanaObtenido = fecha2.getDiaSetmana();
        diaSemanaEsperado = "diumenge";
        assertTrue(fecha2.getDiaSetmana().equalsIgnoreCase(diaSemanaEsperado), "Esperaba que fuera "+ diaSemanaEsperado +" y el 01/01/2012 se ha determinado que es " + diaSemanaObtenido);
    }

    @Test
    public void testIsFestivo() {
        Data fecha = new Data(12, 12, 2023);
        assertFalse(fecha.isFestiu(), "El día se marca como no festivo cuando realmente no lo es (sábado o domingo)");

        Data fecha2 = new Data(1, 1, 2012);
        assertTrue(fecha2.isFestiu(), "El día no se marca como no festivo cuando realmente lo es (sábado o domingo)");
    }

    @Test
    public void testGetNumeroSemana() {
        Data fecha1 = new Data(15, 1, 2018);
        int numeroSemanaEsperado = 3;
        assertTrue(fecha1.getNumeroSetmana()== 3, "No se está determinando que la fecha 15/1/2018 sea un día de la semana " + numeroSemanaEsperado);

        Data fecha2 = new Data(2,1,2017);
        numeroSemanaEsperado = 2;
        assertTrue(fecha2.getNumeroSetmana() == 2,  "No se está determinando que la fecha 02/01/2017 sea un día de la semana " + numeroSemanaEsperado);

        Data fecha3 = new Data(6,3,2021);
        numeroSemanaEsperado = 10;
        assertTrue(fecha3.getNumeroSetmana() == 10,  "No se está determinando que la fecha 06/03/2021 sea un día de la semana " + numeroSemanaEsperado);

        Data fecha4 = new Data(31, 12, 1996);
        numeroSemanaEsperado = 53;
        assertTrue(fecha4.getNumeroSetmana() == 53,  "No se está determinando que la fecha 31/12/1996 sea un día de la semana " + numeroSemanaEsperado);
    }

    @Test
    public void testAnyadir() {

        Data fechaInicial = new Data(15, 1, 2022);
        Data fechaDiaSiguienteAFechaInicial = new Data(16, 1, 2022);
        assertTrue(fechaInicial.afegir(1).isIgual(fechaDiaSiguienteAFechaInicial), "No se está añadiendo correctamente un día a la fecha 15/1/2022");


        Data fechaInicial2 = new Data(30, 12, 2021);
        Data fechaDosDiasSiguientesAFechaInicial = new Data(1, 1, 2022);
        assertTrue(fechaInicial2.afegir(2).isIgual(fechaDosDiasSiguientesAFechaInicial), "No se está añadiendo correctamente dos días a la fecha 30/12/2021");

        Data ultimoDiaEnero = new Data(31, 1, 2022);
        Data treintaDiasDespues = new Data(2, 3, 2022);
        assertTrue(ultimoDiaEnero.afegir(30).isIgual(treintaDiasDespues), "No se está sumando correctamente treinta días a la fecha 31/1/2022");
    }

    @Test
    public void testRestar() {
        Data fechaInicio = new Data(15, 1, 2022);
        Data fechaFinalEsperada = new Data(14, 1, 2022);
        assertTrue(fechaInicio.restar(1).isIgual(fechaFinalEsperada), "No se está restando correctamente un día al 15/1/2022");

        fechaInicio = new Data(1, 1, 2022);
        fechaFinalEsperada = new Data(30, 12, 2021);
        assertTrue(fechaInicio.restar(2).isIgual(fechaFinalEsperada), "No se está restando correctamente dos días al 1/1/2022");

        fechaInicio = new Data(2, 3, 2022);
        fechaFinalEsperada = new Data(31, 1, 2022);
        assertTrue(fechaInicio.restar(30).isIgual(fechaFinalEsperada), "No se está restando correctamente dos días al 2/3/2022");
    }

    @Test
    public void testMostrarFechaDia1Mes1FormatoES() {

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String textoFechaPorDefectoFormatoES = fechaActual.format(formato);
        Data fechaPorDefecto = new Data();
        fechaPorDefecto.mostrarEnFormatES();
        String salida = baos.toString();
        assertTrue(salida.contains(textoFechaPorDefectoFormatoES), "No se está formateando correctamente la fecha por defecto en formato Español. Debería mostrarse como " + textoFechaPorDefectoFormatoES);
    }

    @Test
    public void testMostrarFechaDia1Mes1FormatoGB() {

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String textoFechaPorDefectoFormatoGB = fechaActual.format(formato);
        Data fechaPorDefecto = new Data();
        fechaPorDefecto.mostrarEnFormatGB();
        String salida = baos.toString();
        assertTrue(salida.contains(textoFechaPorDefectoFormatoGB), "No se está formateando correctamente la fecha por defecto en formato Británico. Debería mostrarse como " + textoFechaPorDefectoFormatoGB);
    }

    @Test
    public void testMostrarFechaDia21Mes3FormatoTexto() {

        String textoFechaFormatoGB = "21-març-1998";
        Data fecha = new Data(21,3,1998);
        fecha.mostrarEnFormatText();
        String salida = baos.toString();
        assertTrue(salida.contains(textoFechaFormatoGB), salida+"No se está formateando correctamente la fecha 21/03/1998 en formato Texto. Debería mostrarse como " + textoFechaFormatoGB);
    }

    @Test
    public void testMostrarFechaDia1Mes3FormatoTexto() {

        String textoFechaFormatoTexto = "01-març-1998";
        Data fecha = new Data(1,3,1998);
        fecha.mostrarEnFormatText();
        String salida = baos.toString();
        assertTrue(salida.contains(textoFechaFormatoTexto), "No se está formateando correctamente la fecha 01/03/1998 en formato Texto. Debería mostrarse como " + textoFechaFormatoTexto);
    }

    @Test
    public void testMostrarFechaDia20Mes10FormatoES() {

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String textoFechaPorDefectoFormatoES = fechaActual.format(formato);
        Data fechaPorDefecto = new Data();
        fechaPorDefecto.mostrarEnFormatES();
        String salida = baos.toString();
        assertTrue(salida.contains(textoFechaPorDefectoFormatoES), salida+"No se está formateando correctamente la fecha por defecto en formato Español. Debería mostrarse como " + textoFechaPorDefectoFormatoES);
    }

    @Test
    public void testMostrarFechaDia20Mes10FormatoGB() {

        String textoFechaFormatoGB = "1970-10-20";
        Data fecha = new Data(20,10,1970);
        fecha.mostrarEnFormatGB();
        String salida = baos.toString();
        assertTrue(salida.contains(textoFechaFormatoGB), "No se está formateando correctamente la fecha 20/10/1970 en formato Británico. Debería mostrarse como " + textoFechaFormatoGB);
    }

    @Test
    public void testIsCorrecta() {
        Data fecha = new Data(29, 2, 1987);
        assertFalse(fecha.isCorrecta(), "La fecha no se determina como incorrecta");

        Data fecha2 = new Data(29, 2, 1992);
        assertTrue(fecha2.isCorrecta(), "La fecha no se determina como correcta");
    }

    @Test
    public void testIsBisiesto() {
        assertFalse(Data.isBisiesto(1986), "El año 1986 no es bisiesto por ser múltiplo de 4 y no de 100");
        assertTrue(Data.isBisiesto(1992), "El año 1992 sí es bisiesto por ser múltiplo de 4 y no de 100");
        assertTrue(Data.isBisiesto(400), "El año 400 sí es bisiesto por ser múltiplo de 4 de 100 y de 400 a la vez");
        assertTrue(Data.isBisiesto(800), "El año 800 sí es bisiesto por ser múltiplo de 4 de 100 y de 400 a la vez");
        assertTrue(Data.isBisiesto(1200), "El año 1200 sí es bisiesto por ser múltiplo de 4 de 100 y de 400 a la vez");
        assertTrue(Data.isBisiesto(1600), "El año 1600 sí es bisiesto por ser múltiplo de 4 de 100 y de 400 a la vez");
        assertTrue(Data.isBisiesto(2000), "El año 2000 sí es bisiesto por ser múltiplo de 4 de 100 y de 400 a la vez");
    }

    @Test
    public void testGetDiasAnyo() {
        assertEquals(365, Data.getDiesAny(1986), "El año 1986 tiene 365 días");
        assertEquals(366, Data.getDiesAny(1992), "El año 1992 tiene 366 días");
    }

    @Test
    public void testGetDiasMes() {
        assertEquals(28, Data.getDiesMes(2, 1986), "El més de febrero del año 1986 tiene 28 días");
        assertEquals(29, Data.getDiesMes(2, 1992), "El més de febrero del año 1992 tiene 29 días");
    }
    
    @Test
    public void testGetDiasDeDiferencia() {
        assertEquals(1462, new Data(13,12,2024).getDiesDeDiferencia(new Data(12,12,2020)), "Entre el día 12/12/2020 y el 13/12/2024 hay una diferencia de 1462 días");
        
    }
    
     @Test
    public void testEsPosteriorA() {
        assertTrue(new Data(13,12,2024).esPosteriorA(new Data(12,12,2024)), "Las fecha 13/12/2024 no se detecta como posterior a la fecha 12/12/2024");
        assertFalse(new Data(12,12,2022).esPosteriorA(new Data(13,12,2024)), "Las fecha 13/12/2024 no se detecta como posterior a la fecha 12/12/2020");
    }



}
