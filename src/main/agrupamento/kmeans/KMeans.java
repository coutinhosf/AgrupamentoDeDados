package agrupamento.kmeans;

import agrupamento.comum.Distancia;
import agrupamento.comum.Coluna;
import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KMeans {

    public Map<Integer, Centroide> centroides;
    int interacoes;

    public KMeans(int k, int interacoes) {
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
            atribuiCentroideMaisProximo(colunasDeDados, distancia);
            recalculaCentroide(colunasDeDados);

            if (!existiuMudancaCentroide())
                break;
        }
    }

    private void inicializaCentroides(Map<Integer, Coluna> dadosMap) {
        for (int centroideId = 0; centroideId < centroides.size(); centroideId++) {
            centroides.get(centroideId).objetosAtual = retornaObjetosDoCentroideInicial(dadosMap, centroideId);
            System.out.println(centroides.get(centroideId).objetosAtual);
        }
    }

    private void recalculaCentroide(Map<Integer, Coluna> dadosMap) {
        for (int k = 0; k < centroides.size(); k++) {
            centroides.get(k).objetosAnterior = centroides.get(k).objetosAtual;
            centroides.get(k).objetosAtual = new ArrayList<>();

            for (int i = 0; i < dadosMap.size(); i++) {
                centroides.get(k).objetosAtual.add(String.valueOf(this.centroides.get(k).retornaMediaColuna(dadosMap, i)));
            }
        }
    }

    private void atribuiCentroideMaisProximo(Map<Integer, Coluna> dadosMap, Distancia distancia) {
        for (int j = 0; j < dadosMap.get(0).valores.size(); j++) {
            // calcula distancia do primeiro objeto
            distancia.maior = distancia.calcula(retornaObjetoPorIndice(dadosMap, j), centroides.get(0).objetosAtual, 1);
            distancia.pos = 0;

            Double distanciaObjetos;
            for (int k = 1; k < this.centroides.size(); k++) {
                distanciaObjetos = distancia.calcula(retornaObjetoPorIndice(dadosMap, j), centroides.get(k).objetosAtual, 1);
                if (distanciaObjetos > distancia.maior) {
                    distancia.pos = k;
                    distancia.maior = distanciaObjetos;
                }
            }
            centroides.get(distancia.pos).indicesDosObjetos.add(j);
        }
    }

    public List<String> retornaObjetosDoCentroideInicial(Map<Integer, Coluna> dadosMap, int i) {
        return this.centroides.get(i).retornaObjetosDoCentroideInicial(dadosMap, i);
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

        return soma == 3 ? true : false;
    }

}


