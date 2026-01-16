package prog.cipfpbatoi;

import java.util.Scanner;

public class Menu {
    private final Agenda agenda;

    public Menu() {
        this.agenda = new Agenda();
    }

    public void iniciar() {
        boolean eixir = false;

        while (!eixir) {
            mostrarOpcions();
            int opcio = llegirOpcioMenu(); // 1..4

            switch (opcio) {
                case 1:
                    crearNouEsdeveniment();
                    break;
                case 2:
                    buscarEsdevenimentPerDataIEditar();
                    break;
                case 3:
                    mostrarAgenda();
                    break;
                case 4:
                    System.out.println("Eixir seleccionat. Fins aviat!");
                    eixir = true;
                    break;
            }
            System.out.println();
        }

        GestorIO.tancar();

    }

    private void mostrarOpcions() {
        System.out.println("Menú d'opcions:");
        System.out.println("1. Crear nou esdeveniment");
        System.out.println("2. Buscar esdeveniment per data (i editar la seua data)");
        System.out.println("3. Mostrar l'estat actual de l'agenda");
        System.out.println("4. Eixir");
    }

    private int llegirOpcioMenu() {
        int opcio;
        do {
            opcio = GestorIO.llegirEnter("Seleccione una opció (1-4): ");
            if (opcio < 1 || opcio > 4) {
                System.out.println("Opció no vàlida. seleccione una opció entre 1 i 4.");
            }
        } while (opcio < 1 || opcio > 4);

        return opcio;
    }

    private void crearNouEsdeveniment() {
        TipusEsdeveniment tipus = llegirTipus();
        String nota = llegirNotaNoBuida();

        String ubicacio = GestorIO.llegirText("Ubicació (opcional, enter per ometre): ").trim();
        if (ubicacio.isEmpty()) ubicacio = null;

        int dies;
        do {
            dies = GestorIO.llegirEnter("Dies restants fins l'esdeveniment (>=0): ");
            if (dies < 0) {
                System.out.println("Error: els dies no poden ser negatius.");
            }
        } while (dies < 0);

        Esdeveniment e = new Esdeveniment(tipus, nota, ubicacio, dies);
        agenda.afegirEsdeveniment(e);
    }


    private TipusEsdeveniment llegirTipus() {
        while (true) {
            String txt = GestorIO
                    .llegirText("Tipus (ANIVERSARI/FESTIU/ESPECIAL/ALTRES): ")
                    .trim()
                    .toUpperCase();

            for (TipusEsdeveniment t : TipusEsdeveniment.values()) {
                if (t.name().equals(txt)) {
                    return t;
                }
            }

            System.out.println("Error: tipus d'esdeveniment no vàlid.");
        }
    }


    private String llegirNotaNoBuida() {
        while (true) {
            String nota = GestorIO.llegirText("Nota descriptiva: ").trim();
            if (!nota.isEmpty()) return nota;
            System.out.println("Error: la nota descriptiva no pot estar buida.");
        }
    }

    private void buscarEsdevenimentPerDataIEditar() {
        String txtData = GestorIO.llegirText("Introdueix una data (dd/mm/yyyy): ").trim();
        Data data = new Data(txtData);

        Esdeveniment trobat = agenda.cercarEsdevenimentPerData(data);

        if (trobat == null) {
            System.out.println("No s'ha trobat cap esdeveniment a l'agenda.");
            return;
        }

        System.out.print("Esdeveniment trobat: ");
        trobat.mostrar();

        boolean editar = GestorIO.solicitarConfirmacio("Vols modificar la data d'aquest esdeveniment?");
        if (editar) {
            int nousDies;
            do {
                nousDies = GestorIO.llegirEnter("Nous dies restants (>=0): ");
                if (nousDies < 0) System.out.println("Error: els dies no poden ser negatius.");
            } while (nousDies < 0);

            trobat.substituir(nousDies);
            System.out.println("Data actualitzada.");
        }
    }

    private void mostrarAgenda() {
        agenda.mostrarTotsElsEsdeveniments();
    }


}
