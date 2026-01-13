package prog.cipfpbatoi;

public class Esdeveniment {

    /*
    Aquesta classe conté tota la informació essencial sobre: el tipus d’esdeveniment (un valor entre Aniversari, Festiu, Especial o Altres), una nota descriptiva, la ubicació (opcional) i la data concreta en què es durà a terme l’esdeveniment (es obligatori fer servir la classe Data anteriorment implementada).
     */
    private TipusEsdeveniment tipus;
    private String nota;
    private String ubicacio;
    private Data data;


    public Esdeveniment(TipusEsdeveniment tipus, String nota, String ubicacio, long diesRestants) {
        this.tipus = tipus;
        this.nota = nota;
        this.ubicacio = ubicacio;

        Data hoy = new Data();
        this.data = hoy.afegir(diesRestants);
    }

    public Esdeveniment(TipusEsdeveniment tipus, String nota, long diesRestants) {
        this(tipus, nota, null, diesRestants);
    }

    public Esdeveniment(TipusEsdeveniment tipus, String nota, String ubicacio) {
        this(tipus, nota, ubicacio, 30);
    }


    public void mostrar() {
        Data hoy = new Data();

        if (data.esPosteriorA(hoy)) {
            System.out.print(tipus + " (" + nota + ")");

            if (ubicacio != null && !ubicacio.isEmpty()) {
                System.out.print(" -> " + ubicacio);
            }

            System.out.print(" - Data: ");
            data.mostrarEnFormatES();
        } else {
            System.out.print(tipus + " (" + nota + ") -> Ja ha tingut lloc el dia ");
            data.mostrarEnFormatES();
        }
    }


    public void substituir(int nuevosDias) {
        Data hoy = new Data();
        this.data = hoy.afegir(nuevosDias);
    }


    public boolean esMesProximA(Data dataComparar, Esdeveniment altreEsdeveniment) {
        if (dataComparar == null || altreEsdeveniment == null) {
            throw new IllegalArgumentException("La data de comparació i l'altre esdeveniment no poden ser nuls.");
        }

        long diesFinsActual = this.data.getDiesDeDiferencia(dataComparar);
        long diesFinsAltre = altreEsdeveniment.data.getDiesDeDiferencia(dataComparar);

        return diesFinsActual < diesFinsAltre;

    }


}
