package agrupamento.kmeans;

import agrupamento.comum.Coluna;

import java.util.*;


public class Centroide {

    public int id;
    public List<String> objetosAtual;
    public List<String> objetosAnterior;

    public List<Integer> indicesDosObjetos;

    @Override
    public String toString() {
        return "Centroide (" + id + "), Indices=" + indicesDosObjetos.toString();
    }

    public Centroide() {
        this.id = 0;
        this.indicesDosObjetos = new ArrayList<>();
        this.objetosAtual = new ArrayList<>();
        this.objetosAnterior = new ArrayList<>();
    }

    public Centroide(Integer id) {
        this.id = id;
        this.indicesDosObjetos = new ArrayList<>();
        this.objetosAtual = new ArrayList<>();
        this.objetosAnterior = new ArrayList<>();
    }

    public static Integer retornaCentroideInicial(Integer numeroMaximoDeObjetos) {
        return (new Double (Math.random() * numeroMaximoDeObjetos)).intValue();
    }

    public Map<Integer, Centroide> retornaMapGrupo(int k) {
        Centroide gpK;
        Map<Integer, Centroide> grupoK = new HashMap<Integer, Centroide>();

        for (int i = 0; i < k; i++) {
            gpK = new Centroide(i);
            grupoK.put(i, gpK);
        }

        return grupoK;
    }

    public List<String> retornaObjetosDoCentroideInicial(Map<Integer, Coluna> dadosMap, Integer numeroObjetos) {
        // sorteia um numero para o centroide inicial
        Integer numeroSorteado = retornaCentroideInicial(numeroObjetos);

        // atribui objetos ao centroide
        for (int j = 0; j < dadosMap.size(); j++) {
            this.objetosAtual.add(dadosMap.get(j).valores.get(numeroSorteado));
        }

        return this.objetosAtual;
    }

    public double retornaMediaColuna(Map<Integer, Coluna> dadosMap, int indiceColuna) {
        Coluna coluna = new Coluna();
        List<String> listacoluna = new ArrayList<>();

        for (int i = 0; i < this.indicesDosObjetos.size(); i++)
            listacoluna.add(dadosMap.get(indiceColuna).valores.get(i));

        coluna.valores = listacoluna;

        return coluna.retornaMediaDouble();
    }


}
