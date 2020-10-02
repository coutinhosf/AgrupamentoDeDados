package agrupamento.kmeans;

import agrupamento.comum.Distancia;
import agrupamento.comum.Coluna;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KMeans {

    public Map<Integer, Centroide> centroides;
    int interacoes;
    int k;

    public KMeans(int k, int interacoes) {
        this.k = k;
        this.centroides = new Centroide().retornaMapGrupo(k);
        this.interacoes = interacoes;
    }

    public void executa(Map<Integer, Coluna> colunasDeDados) {
        // inicializa distancia para todos os pontos 0
        Distancia distancia = new Distancia();
        Double distanciaObjetos;

        // inicializa centroides
        inicializaCentroides(colunasDeDados);

        // atribui objetos aos centroides e recalcula centroides
        for (int i = 0; i < interacoes; i++) {
            atribuiObjetoAoCentroideMaisProximo(colunasDeDados, distancia);
            recalculaCentroide(colunasDeDados);

            if (!existiuMudancaCentroide())
                break;
        }
    }

    public  Integer retornaIndiceAleatorioDoObjeto(Integer numeroMaximoObjetos) {
        return (new Double (Math.random() * numeroMaximoObjetos)).intValue();
    }

    private void inicializaCentroides(Map<Integer, Coluna> dadosMap) {
        List<Integer> indicesSorteados = new ArrayList();

        while (indicesSorteados.size() < centroides.size()) {
            // sorteia um numero para o centroide inicial
            Integer indiceAleatorio = retornaIndiceAleatorioDoObjeto(dadosMap.get(0).valores.size());
            if (!indicesSorteados.contains(indiceAleatorio))
                indicesSorteados.add(indiceAleatorio);
        }

        for (int centroideId = 0; centroideId < centroides.size(); centroideId++) {
            Centroide c = centroides.get(centroideId);

            c.objetosAtual = c.retornaObjetosDoCentroideInicial(dadosMap, indicesSorteados.get(centroideId));
            System.out.println(centroides.get(centroideId) + " " + centroides.get(centroideId).objetosAtual);
        }
    }

    private void recalculaCentroide(Map<Integer, Coluna> dadosMap) {
        for (int k = 0; k < centroides.size(); k++) {
            centroides.get(k).objetosAnterior = centroides.get(k).objetosAtual;

            if (!centroides.get(k).indicesDosObjetos.isEmpty()) {
                centroides.get(k).objetosAtual = new ArrayList<>();
                for (int i = 0; i < dadosMap.size(); i++) {
                    centroides.get(k).objetosAtual.add(String.valueOf(centroides.get(k).retornaMediaColuna(dadosMap, i)));
                }
            }
        }
    }

    private void atribuiObjetoAoCentroideMaisProximo(Map<Integer, Coluna> dadosMap, Distancia distancia) {
        for (int j = 0; j < dadosMap.get(0).valores.size(); j++) {
            // calcula distancia do primeiro objeto
            distancia.maior = distancia.calcula(retornaObjetoPorIndice(dadosMap, j), centroides.get(0).objetosAtual, 1);
            distancia.pos = 0;

            Double distanciaObjetos;
            for (int k = 1; k < centroides.size(); k++) {
                distanciaObjetos = distancia.calcula(retornaObjetoPorIndice(dadosMap, j), centroides.get(k).objetosAtual, 1);
                if (distanciaObjetos > distancia.maior) {
                    distancia.pos = k;
                    distancia.maior = distanciaObjetos;
                }
            }
            Centroide c = centroides.get(distancia.pos);
            c.indicesDosObjetos.add(j);
        }
    }

    public List<String> retornaObjetoPorIndice(Map<Integer, Coluna> dadosMap, int i) {
        List<String> objetoA = new ArrayList<>();
        for (int k = 0; k < dadosMap.size(); k++) {
            objetoA.add(dadosMap.get(k).valores.get(i));
        }

        return objetoA;
    }

    public boolean existiuMudancaCentroide() {
        int soma = 0;
        for (int i = 0; i < this.centroides.size(); i++)
            if (this.centroides.get(i).objetosAtual.containsAll(this.centroides.get(i).objetosAnterior)
                    && this.centroides.get(i).objetosAnterior.containsAll(this.centroides.get(i).objetosAtual))
                soma += 1;

        return soma == centroides.size();
    }

}


