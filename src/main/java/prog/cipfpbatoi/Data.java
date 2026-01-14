/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog.cipfpbatoi;


/**
 *
 * @author batoi
 */
import java.util.Calendar;
import java.util.StringTokenizer;

public class Data {

    private int dia;
    private int mes;
    private int any;

    public Data() {
        Calendar cal = Calendar.getInstance();
        this.dia = cal.get(Calendar.DAY_OF_MONTH);
        this.mes = cal.get(Calendar.MONTH) + 1; // Calendar: 0..11
        this.any = cal.get(Calendar.YEAR);
    }

    public Data(int dia, int mes, int any) {
        this.dia = dia;
        this.mes = mes;
        this.any = any;
    }

    public Data(String fecha) {
        StringTokenizer st = new StringTokenizer(fecha, "/");
        this.dia = Integer.parseInt(st.nextToken());
        this.mes = Integer.parseInt(st.nextToken());
        this.any = Integer.parseInt(st.nextToken());
    }

    public void set(int dia, int mes, int any) {
        this.dia = dia;
        this.mes = mes;
        this.any = any;
    }

    public Data copy() {
        return new Data(this.dia, this.mes, this.any);
    }

    public int getDia() {
        return this.dia;
    }

    public int getMes() {
        return this.mes;
    }

    public int getAny() {
        return this.any;
    }

    public void mostrarEnFormatES() {
        System.out.printf("%02d/%02d/%04d%n", this.dia, this.mes, this.any);
    }

    public void mostrarEnFormatGB() {
        System.out.printf("%04d-%02d-%02d%n", this.any, this.mes, this.dia);
    }

    public void mostrarEnFormatText() {
        System.out.printf("%d-%s-%d%n", this.dia, getMesEnFormatText(), this.any);
    }

    public boolean isIgual(Data data) {
        if (data == null) return false;
        return this.dia == data.dia && this.mes == data.mes && this.any == data.any;
    }

    public String getDiaSetmana() {
        int dies = getDiesTranscorregutsOrigen();
        int r = dies % 7;
        return getNomDiaSetmana(r);
    }

    public boolean isFestiu() {
        int r = getDiesTranscorregutsOrigen() % 7;
        return r == 0 || r == 6;
    }

    public int getNumeroSetmana() {
        int dayOfYear = getDiesTranscorregutsEnAny() + 1;

        Data jan1 = new Data(1, 1, this.any);
        int dowJan1 = jan1.getDiesTranscorregutsOrigen() % 7;

        int deltaToMonday = (1 - dowJan1 + 7) % 7;
        int firstMondayDayOfYear = 1 + deltaToMonday;

        if (firstMondayDayOfYear == 1) {
            return 1 + (dayOfYear - 1) / 7;
        } else {
            if (dayOfYear < firstMondayDayOfYear) return 1;
            return 2 + (dayOfYear - firstMondayDayOfYear) / 7;
        }
    }

    public Data afegir(long dies) {
        if (dies < 0) return this.copy();

        int d = this.dia;
        int m = this.mes;
        int a = this.any;

        long restant = dies;

        while (restant > 0) {
            int diesMes = getDiesMes(m, a);
            int diesQueQuedenEnMes = diesMes - d;

            if (restant <= diesQueQuedenEnMes) {
                d += (int) restant;
                restant = 0;
            } else {
                restant -= (diesQueQuedenEnMes + 1);
                d = 1;
                m++;
                if (m > 12) {
                    m = 1;
                    a++;
                }
            }
        }

        return new Data(d, m, a);
    }

    public Data restar(long dies) {
        if (dies < 0) return this.copy();

        int d = this.dia;
        int m = this.mes;
        int a = this.any;

        long restant = dies;

        while (restant > 0) {
            if (restant < d) {
                d -= (int) restant;
                restant = 0;
            } else {
                restant -= d;
                m--;
                if (m < 1) {
                    m = 12;
                    a--;
                }
                d = getDiesMes(m, a);
            }
        }

        return new Data(d, m, a);
    }

    public boolean isCorrecta() {
        if (any < 1) return false;
        if (mes < 1 || mes > 12) return false;
        if (dia < 1) return false;

        int maxDia = getDiesMes(mes, any);
        return dia <= maxDia;
    }

    public long getDiesDeDiferencia(Data data) {
        if (data == null) return -1L;
        long a = this.getDiesTranscorregutsOrigen();
        long b = data.getDiesTranscorregutsOrigen();
        return Math.abs(a - b);
    }

    public boolean esPosteriorA(Data data) {
        if (data == null) return false;
        if (this.any != data.any) return this.any > data.any;
        if (this.mes != data.mes) return this.mes > data.mes;
        return this.dia > data.dia;
    }

    private String getMesEnFormatText() {
        switch (this.mes) {
            case 1:
                return "gener";
            case 2:
                return "febrer";
            case 3:
                return "març";
            case 4:
                return "abril";
            case 5:
                return "maig";
            case 6:
                return "juny";
            case 7:
                return "juliol";
            case 8:
                return "agost";
            case 9:
                return "setembre";
            case 10:
                return "octubre";
            case 11:
                return "novembre";
            case 12:
                return "desembre";
            default:
                return "mes_invalid";
        }
    }

    private int getDiesTranscorregutsEnAny() {
        int total = 0;
        for (int m = 1; m < this.mes; m++) {
            total += getDiesMes(m, this.any);
        }
        total += (this.dia - 1);
        return total;
    }

    private int getDiesTranscorregutsOrigen() {
        int total = 0;

        for (int a = 1; a < this.any; a++) {
            total += getDiesAny(a);
        }

        total += getDiesTranscorregutsEnAny();
        return total;
    }

    private static String getNomDiaSetmana(int numDiaSetmana) {
        switch (numDiaSetmana) {
            case 0:
                return "diumenge";
            case 1:
                return "dilluns";
            case 2:
                return "dimarts";
            case 3:
                return "dimecres";
            case 4:
                return "dijous";
            case 5:
                return "divendres";
            case 6:
                return "dissabte";
            default:
                return "dia_invalid";
        }
    }


    public static boolean isBisiesto(int any) {
        return (any % 4 == 0 && any % 100 != 0) || (any % 400 == 0);
    }

    public static int getDiesMes(int mes, int any) {
        switch (mes) {
            case 1:
                return 31;
            case 2:
                return isBisiesto(any) ? 29 : 28;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                return -1;
        }
    }

    public static int getDiesAny(int any) {
        return isBisiesto(any) ? 366 : 365;
    }
}

