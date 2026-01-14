package prog.cipfpbatoi;

public class Esdeveniment {

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
            System.out.println("La data de comparació i l'altre esdeveniment no poden ser nuls.");
            return false;
        }

        long diesFinsActual = this.data.getDiesDeDiferencia(dataComparar);
        long diesFinsAltre = altreEsdeveniment.data.getDiesDeDiferencia(dataComparar);

        return diesFinsActual < diesFinsAltre;

    }


    public boolean teLlocDespres(Data dataComparar) {
        if (dataComparar == null) {
            System.out.println("La data de comparació no pot ser nul·la.");
            return false;
        }

        return this.data.esPosteriorA(dataComparar);
    }



    public boolean haTingutLloc() {
        Data hoy = new Data();
        return !this.data.esPosteriorA(hoy);
    }


    public Data getData() {
        return this.data;
    }

}
