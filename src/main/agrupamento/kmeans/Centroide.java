package agrupamento.kmeans;

import agrupamento.comum.Coluna;

import java.util.*;


public class Centroide {

    public int id;
    public int indiceDoObjetoInicialVirtual;
    public List<String> objetosAtual;
    public List<String> objetosAnterior;

    public List<Integer> indicesDosObjetos;
    Random generator;

    @Override
    public String toString() {
        return "Centroide(" + id + "), PI: " + indiceDoObjetoInicialVirtual + ", Indices=" + indicesDosObjetos.toString();
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

    public Map<Integer, Centroide> retornaMapGrupo(int k) {
        Centroide gpK;
        Map<Integer, Centroide> grupoK = new HashMap<Integer, Centroide>();

        for (int i = 0; i < k; i++) {
            gpK = new Centroide(i);
            grupoK.put(i, gpK);
        }

        return grupoK;
    }

    public List<String> retornaObjetosDoCentroideInicial(Map<Integer, Coluna> dadosMap, Integer indiceObjeto) {
        indiceDoObjetoInicialVirtual = indiceObjeto;

        // adiciona atributos do objeto do indice ao centroide
        for (int j = 0; j < dadosMap.size(); j++) {
            objetosAtual.add(dadosMap.get(j).valores.get(indiceObjeto));
        }
        return objetosAtual;
    }

    public double retornaMediaColuna(Map<Integer, Coluna> dadosMap, int indiceObjeto) {
        Coluna coluna = new Coluna();
        List<String> valoresColuna = new ArrayList<>();

        for (int i = 0; i < indicesDosObjetos.size(); i++)
            valoresColuna.add(dadosMap.get(indiceObjeto).valores.get(i));

        coluna.valores = valoresColuna;
        Double resultado = coluna.retornaMediaDouble();
//        System.out.println(resultado);
        return resultado;
    }


}
