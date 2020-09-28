package agrupamento.comum;

import java.util.*;

public class Distancia {

    public Double maior;
    public int pos;

    public Distancia() {
        this.maior = 0.0;
        this.pos = 0;
    }

    public Double distanciaEuclediana(List<String> objetoA, List<String> objetoB) {
        Double somatorio = 0.0;
        for (int i = 0; i < objetoA.size(); i++) {
            somatorio += Math.pow((Double.parseDouble(objetoB.get(i)) - Double.parseDouble(objetoA.get(i))), 2);
        }
        return Math.sqrt(somatorio);
    }

    public Double distanciaManhattan(List<String> objetoA, List<String> objetoB) {
        Double somatorio = 0.0;
        for (int i = 0; i < objetoA.size(); i++) {
            somatorio += Math.abs((Double.parseDouble(objetoA.get(i)) - Double.parseDouble(objetoB.get(i))));
        }
        return somatorio;
    }

    public double calcula(List<String> objetoA, List<String> objetoB, int tipoDistancia) {
        if (tipoDistancia == 2) return distanciaManhattan(objetoA, objetoB);
        return distanciaEuclediana(objetoA, objetoB);
    }

    public double[][] calculaTodosPares(Map<Integer, Coluna> dadosMap, int distancia) {

        int tamcolum = dadosMap.get(0).valores.size();
        int aux = 0;
        double[][] matrizPares = new double[dadosMap.get(0).valores.size()][dadosMap.get(0).valores.size()];
        List<String> objetoA;
        List<String> objetoB;

        for (int i = 0; i < tamcolum; i++) {
            objetoA = new ArrayList<>();
            for (int j = i; j < tamcolum; j++) {
                objetoB = new ArrayList<>();
                if (aux == i) {
                    for (int k = 0; k < dadosMap.size(); k++) {
                        objetoA.add(dadosMap.get(k).valores.get(i));
                    }
                    aux = aux + 1;
                }
                for (int l = 0; l < dadosMap.size(); l++) {
                    objetoB.add(dadosMap.get(l).valores.get(j));
                }
                matrizPares[i][j] = calcula(objetoA, objetoB, distancia);
            }
        }
        return matrizPares;
    }

}
