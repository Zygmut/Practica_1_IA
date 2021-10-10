package agents;

// Exemple de Bitxo

public class Bitxo1 extends Agent
{
    static final int PARET = 0;
    static final int BITXO   = 1;
    static final int RES   = -1;

    static final int ESQUERRA = 0;
    static final int CENTRAL  = 1;
    static final int DRETA    = 2;
    
    Estat estat;
    private int repetir_turn;
   
    public Bitxo1(Agents pare) {
        super(pare, "Exemple1", "imatges/robotank1.gif");
        
    }

    @Override
    public void inicia()
    {
        // atributsAgents(v,w,dv,av,ll,es,hy)
        int cost = atributsAgent(6, 5, 600, 30, 23, 5, 5);
        System.out.println("Cost total:"+cost);
        
        // Inicialització de variables que utilitzaré al meu comportament
    }

    @Override
    public void avaluaComportament()
    {
        
         //evita movimientos anteriores
        estat = estatCombat();
        
    
       
        // metodo de combate/movimiento 
        if(estat.numObjectes==0){
                //nos paramos, reevaluamos y nos movemos
                
                moure();
            
            
        }
        else{
            
            atura();
            int IdMesAprop = objecteMesProper();
            if((estat.objectes[IdMesAprop].agafaTipus() == Estat.ESCUT) || (100 == (estat.objectes[IdMesAprop].agafaTipus() -BITXO))) // Si es recurso nuestro o tipo
            {
                mira(estat.objectes[IdMesAprop]);
                endavant();
            }
            else
            {
                if(estat.objectes[IdMesAprop].agafaTipus()!=PARET){
                mira(estat.objectes[IdMesAprop]);
               //System.out.printLn("Voy a disparar al objeto: " + estat.objectes[IdMesAprop].agafaTipus + "\tQue esta a " + estat.objectes[IdMesAprop].agafaDistancia + " distancia")
                llança();
                }
            }
            
        }
    }

    /*
     * Metodo que devuelve el indice que ocupa el objeto más cercano a nuestro agente en el array de objectes  
     */
    private int objecteMesProper(){
        double DistanciaMesPetit=0;
        int IdMesAprop=0;
        for(int i=0; i < (estat.objectes.length-1) ;i++){
            if(estat.objectes[i] != null){
                if(DistanciaMesPetit>estat.objectes[i].agafaDistancia() && estat.objectes[i].agafaTipus()!= Estat.AGENT && estat.objectes[i].agafaTipus()!=PARET){
                    DistanciaMesPetit=estat.objectes[i].agafaDistancia();
                    IdMesAprop= i;
                }
            }
        }
        return IdMesAprop;
    }

    
    /*
    Método que decide hacía donde girar dependiendo del margen que tengamos
    con la pared
    */
    private void moure()
    {
        
        endavant();
        
        // Si hay pared delante, vamos por el lado que más margen nos de
        if(hiHaParetDavant(100))
        {
            double distEsquerra = 9999;
            double distDreta = 9999;

            //obtenemos distancia a la pared de ambos lados
            if(estat.objecteVisor[ESQUERRA] != RES){
                distEsquerra = estat.distanciaVisors[ESQUERRA];
            }
            if (estat.objecteVisor[DRETA] != RES){
                distDreta = estat.distanciaVisors[DRETA];
            }

            //giramos para donde nos de más margen
            if (distEsquerra < distDreta) dreta();
            if (distDreta  < distEsquerra) esquerra();

            if ((distEsquerra >= 50) || (distDreta >= 50) ) atura(); 
        }
    }
    /*
    Método que mira si hay una pared delante dentro de un rango d
    */
    private boolean hiHaParetDavant(int d){


        //Si el visor central ve una pared && esta dentro del rango
        return((estat.objecteVisor[CENTRAL] == PARET) && (estat.distanciaVisors[CENTRAL] < d));
        
            
    }
}