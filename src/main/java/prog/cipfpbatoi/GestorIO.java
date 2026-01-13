package prog.cipfpbatoi;

import java.util.Scanner;

public class GestorIO {
    private static final Scanner sc = new Scanner(System.in);


    public static int llegirEnter(String missatge) {
        System.out.print(missatge);

        while (!sc.hasNextInt()) {
            System.out.println("Error: Introdueix un número enter vàlid.");
            sc.next();
            System.out.print(missatge);
        }

        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    public static String llegirText(String missatge) {
        System.out.print(missatge);
        return sc.nextLine();
    }

    public static boolean solicitarConfirmacio(String missatge) {
        String resposta;
        do {
            System.out.print(missatge + " (s/n): ");
            resposta = sc.nextLine().trim().toLowerCase();
        } while (!resposta.equals("s") && !resposta.equals("n"));

        return resposta.equals("s");
    }

    public static void tancar() {
        sc.close();
    }

}
