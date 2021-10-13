package agents;

// Exemple de Bitxo
public class Bitxo4 extends Agent {

    static final int PARET = 0;
    static final int BITXO = 1;
    static final int RES = -1;

    static final int ESQUERRA = 0;
    static final int CENTRAL = 1;
    static final int DRETA = 2;

    Estat estat;
    static final int WALL_MARGIN = 70;
    private int repetirAturat = 0;

    public Bitxo4(Agents pare) {
        super(pare, "Exemple4", "imatges/robotank1.gif");

    }

    @Override
    public void inicia() {
        // atributsAgents(v,w,dv,av,ll,es,hy)
        int cost = atributsAgent(6, 9, 600, 50, 23, 5, 5);
        System.out.println("Cost total:" + cost);

        // Inicialització de variables que utilitzaré al meu comportament
    }

    @Override
    public void avaluaComportament() {

        //evita movimientos anteriores
        estat = estatCombat();
        if (repetirAturat != 0) {
            repetirAturat--;
        } else {
            moure();

            if (estat.veigAlgunRecurs == true) {
                int objecteProper = objecteMesProper();
                mira(estat.objectes[objecteProper]);

                if ((estat.objectes[objecteProper].agafaTipus() - estat.id != 100) && (estat.objecteVisor[1] != 0)) {
                    // Recurso a eliminar 
                    llança();
                }

            }
        }

    }

    /*
     * Metodo que devuelve el indice que ocupa el objeto más cercano a nuestro agente en el array de objectes  
     */
    private int objecteMesProper() {
        int DistanciaMesPetit = Integer.MAX_VALUE;
        int IdMesAprop = 0;

        for (int i = 0; i < (estat.objectes.length); i++) {
            try {
                if (DistanciaMesPetit > estat.objectes[i].agafaDistancia()) {
                    DistanciaMesPetit = estat.objectes[i].agafaDistancia();
                    IdMesAprop = i;
                }
            } catch (Exception e) {
            }
        }
        return IdMesAprop;
    }

    private void moure() {

        // Si hay pared delante, vamos por el lado que más margen nos de
        if (estat.enCollisio == true || repetirAturat > 0) {
            if (repetirAturat == 0) {
                atura();
                repetirAturat = 5;
            }
            enrere();
            if (estat.distanciaVisors[DRETA] > estat.distanciaVisors[ESQUERRA]) {
                if (repetirAturat == 5) {
                    esquerra();
                }
            } else {
                if (repetirAturat == 5) {
                    dreta();
                }
            }
        } else {
            if (hiHaParetAprop(WALL_MARGIN)) {
                System.out.println("WALL");
                double distEsquerra = Double.MAX_VALUE;
                double distDreta = Double.MAX_VALUE;

                //obtenemos distancia a la pared de ambos lados
                if (estat.objecteVisor[ESQUERRA] != RES) {
                    distEsquerra = estat.distanciaVisors[ESQUERRA];
                }
                if (estat.objecteVisor[DRETA] != RES) {
                    distDreta = estat.distanciaVisors[DRETA];
                }

                //giramos para donde nos de más margen
                if (distEsquerra < distDreta) {
                    dreta();
                }
                if (distDreta < distEsquerra) {
                    esquerra();
                }

            }else{
                atura();
            }
            endavant();
        }
    }

    /*
    /*
    Método que mira si hay una pared delante dentro de un rango d
     */
    private boolean hiHaParetAprop(int d) {

        //Si el visor central ve una pared && esta dentro del rango
        return (hiHaParetCentre(d) || hiHaParetDreta(d) || hiHaParetEsquerra(d));

    }

    private boolean hiHaParetCentre(int d) {
        return ((estat.objecteVisor[CENTRAL] == PARET) && (estat.distanciaVisors[CENTRAL] < d));
    }

    private boolean hiHaParetDreta(int d) {
        return ((estat.objecteVisor[DRETA] == PARET) && (estat.distanciaVisors[DRETA] < d));
    }

    private boolean hiHaParetEsquerra(int d) {
        return ((estat.objecteVisor[ESQUERRA] == PARET) && (estat.distanciaVisors[ESQUERRA] < d));
    }
}
