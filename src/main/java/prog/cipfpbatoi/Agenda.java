package prog.cipfpbatoi;


public class Agenda {
    private Esdeveniment esdeveniment1;
    private Esdeveniment esdeveniment2;
    private Esdeveniment esdeveniment3;


    public Agenda() {
    }


    public void afegirEsdeveniment(Esdeveniment nouEsdeveniment) {
        if (nouEsdeveniment.haTingutLloc()) {
            System.out.println("No es pot afegir l'esdeveniment perquè ja ha tingut lloc.");
            return;
        }

        if (esdeveniment1 == null) {
            esdeveniment1 = nouEsdeveniment;
        } else if (esdeveniment2 == null) {
            esdeveniment2 = nouEsdeveniment;
        } else if (esdeveniment3 == null) {
            esdeveniment3 = nouEsdeveniment;
        } else {
            Esdeveniment mesAntic = esdeveniment1;
            int indexMesAntic = 1;

            if (esdeveniment2.esMesProximA(new Data(), mesAntic)) {
                mesAntic = esdeveniment2;
                indexMesAntic = 2;
            }
            if (esdeveniment3.esMesProximA(new Data(), mesAntic)) {
                mesAntic = esdeveniment3;
                indexMesAntic = 3;
            }

            switch (indexMesAntic) {
                case 1:
                    esdeveniment1 = nouEsdeveniment;
                    break;
                case 2:
                    esdeveniment2 = nouEsdeveniment;
                    break;
                case 3:
                    esdeveniment3 = nouEsdeveniment;
                    break;
            }
        }


    }


    public Esdeveniment cercarEsdevenimentPerData(Data data) {
        Esdeveniment mesProxim = null;
        long minDiferencia = Long.MAX_VALUE;

        Esdeveniment[] esdeveniments = {esdeveniment1, esdeveniment2, esdeveniment3};

        for (Esdeveniment e : esdeveniments) {
            if (e != null) {
                long diferencia = e.getData().getDiesDeDiferencia(data);
                if (diferencia < minDiferencia) {
                    minDiferencia = diferencia;
                    mesProxim = e;
                }
            }
        }

        return mesProxim;
    }



    public void mostrarTotsElsEsdeveniments() {
        if (esdeveniment1 == null && esdeveniment2 == null && esdeveniment3 == null) {
            System.out.println("No hi ha cap esdeveniment registrat a l'agenda.");
            return;
        }
        if (esdeveniment1 != null) {
            esdeveniment1.mostrar();
            System.out.println();
        }
        if (esdeveniment2 != null) {
            esdeveniment2.mostrar();
            System.out.println();
        }
        if (esdeveniment3 != null) {
            esdeveniment3.mostrar();
            System.out.println();
        }


    }
}
