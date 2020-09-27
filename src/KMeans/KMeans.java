package KMeans;
import Entregas.Dados;
import Entregas.Distancia;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KMeans {

    public Map<Integer, GrupoKmeans> mapKmeans;
    int interacoes;

    public KMeans(int k, int interacoes){
        this.mapKmeans =  new GrupoKmeans().retornaMapGrupo(k);
        this.interacoes = interacoes;
    }

    public void retornaAlgoritmoKmeans(Map<Integer, Dados> dadosMap)
    {
        Distancia distancia =  new Distancia();
        Double distanciaObjetos;

        for(int i =0; i<this.mapKmeans.size();i++)
             this.mapKmeans.get(i).objetoCentroide = retornaMapCentroide(dadosMap,i);

        for(int i =0; i<interacoes;i++)
        {
            atribuiCentroideMaisProximo(dadosMap, distancia);
            recalculaCentroide(dadosMap);

            if(!existiuMudancaCentroide())
                break;
        }
    }

    private void recalculaCentroide(Map<Integer, Dados> dadosMap) {

        for(int k = 0; k < mapKmeans.size(); k++)
        {
            mapKmeans.get(k).objetoCentroideAnterior =  mapKmeans.get(k).objetoCentroide;
            mapKmeans.get(k).objetoCentroide = new ArrayList<>();

            for(int i = 0; i<dadosMap.size();i++)
            {
                mapKmeans.get(k).objetoCentroide.add(String.valueOf(this.mapKmeans.get(k).retornaMediaColuna(dadosMap,i)));
            }
        }
    }

    private void atribuiCentroideMaisProximo(Map<Integer, Dados> dadosMap, Distancia distancia) {
        Double distanciaObjetos;
        for(int j = 0; j < dadosMap.get(0).listaString.size(); j++) {

              for(int k =0; k< this.mapKmeans.size();k++) {
                  if(k==0) {
                      distancia.maior = distancia.distancia(retornaObjeto(dadosMap, j), this.mapKmeans.get(k).objetoCentroide, 1);
                      distancia.pos = k;
                  }
                  else
                  {
                     distanciaObjetos = distancia.distancia(retornaObjeto(dadosMap, j), this.mapKmeans.get(k).objetoCentroide, 1);
                     if( distanciaObjetos > distancia.maior)
                             distancia.pos = k;
                  }
              }
              mapKmeans.get(distancia.pos).listaElementos.add(j);
        }
    }

    public List<String> retornaMapCentroide(Map<Integer, Dados> dadosMap,int i){
        return this.mapKmeans.get(i).retornaMapCentroideInicial(dadosMap,i);
    }

    public List<String> retornaObjeto(Map<Integer, Dados> dadosMap,int i)
    {
        List<String> objetoA = new ArrayList<>();
        for (int k = 0; k < dadosMap.size(); k++) {
            objetoA.add(dadosMap.get(k).listaString.get(i));
        }

        return objetoA;
    }

    public boolean existiuMudancaCentroide()
    {
        int soma = 0;
        for(int i =0; i < this.mapKmeans.size(); i++)
            if(this.mapKmeans.get(i).objetoCentroide.containsAll(this.mapKmeans.get(i).objetoCentroideAnterior)
                    && this.mapKmeans.get(i).objetoCentroideAnterior.containsAll(this.mapKmeans.get(i).objetoCentroide))
                soma+=1;

        return soma==3 ? true : false;
    }

}


