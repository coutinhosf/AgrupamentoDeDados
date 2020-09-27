package Entregas;

import java.util.*;

public class Distancia {

  public  Double maior;
  public  int pos;

   public Distancia()
   {
       this.maior =0.0;
       this.pos = 0;
   }

    public double distancia (List<String> objetoA, List<String> objetoB, int distancia)
    {
        double somatorio = 0.0;

        if(distancia ==1) { //euclidiana
            for (int i = 0; i < objetoA.size(); i++) {
                somatorio += Math.pow((Double.parseDouble(objetoA.get(i)) - Double.parseDouble(objetoB.get(i))), 2);
            }
        }
       else if(distancia ==2) { // manhattan
            for (int i = 0; i < objetoA.size(); i++) {
                somatorio += Math.abs((Double.parseDouble(objetoA.get(i)) - Double.parseDouble(objetoB.get(i))));
            }
        }

        return somatorio;
    }


    public double[][] calculaTodosPares( Map<Integer,Dados> dadosMap, int distancia) {

       int tamcolum =  dadosMap.get(0).listaString.size();
       int aux = 0;
       double[][] matrizPares = new double[dadosMap.get(0).listaString.size()][dadosMap.get(0).listaString.size()];
        List<String> objetoA;
        List<String> objetoB;

        for(int i =0; i <tamcolum; i++) {
            objetoA =  new ArrayList<>();
            for (int j = i; j <tamcolum; j++) {
                objetoB = new ArrayList<>();
                if(aux == i) {
                    for (int k = 0; k < dadosMap.size(); k++) {
                        objetoA.add(dadosMap.get(k).listaString.get(i));
                    }
                    aux = aux+1;
                }
                for (int l = 0; l < dadosMap.size(); l++) {
                    objetoB.add(dadosMap.get(l).listaString.get(j));
                }
                        matrizPares[i][j] = distancia(objetoA, objetoB,distancia);
            }
        }
        return matrizPares;
    }

}
