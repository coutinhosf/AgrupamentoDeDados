package KMeans;

import Entregas.Dados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GrupoKmeans {

    public int centroide;
    public List<String>  objetoCentroide;
    public List<String>  objetoCentroideAnterior;
    public List<Integer> listaElementos;

        public GrupoKmeans() {
            this.centroide = 0;
            this.listaElementos =  new ArrayList<>();
            this.objetoCentroide =  new ArrayList<>();
            this.objetoCentroideAnterior =  new ArrayList<>();
        }

    public double retornaSorteoInicialCentroide()
    {
        return Math.random() * 150;
    }

    public Map<Integer, GrupoKmeans> retornaMapGrupo(int k) {
        GrupoKmeans gpK;
        Map<Integer, GrupoKmeans> grupoK = new HashMap<Integer, GrupoKmeans>();

        for(int i=0; i<k;i++) {
            gpK =  new GrupoKmeans();
            grupoK.put(i,gpK);
        }

       return  grupoK;
    }

    public List<String> retornaMapCentroideInicial(Map<Integer, Dados> dadosMap, int i)
    {
        List<Integer> listaNumeroSorteado = new ArrayList<>();
        Integer numeroSorteado;

            numeroSorteado = (int)retornaSorteoInicialCentroide();
            for(int j =0; j<dadosMap.size();j++)
                this.objetoCentroide.add(dadosMap.get(j).listaString.get(numeroSorteado));

        return this.objetoCentroide;
    }

    public double retornaMediaColuna(Map<Integer, Dados> dadosMap, int coluna)
    {
        Dados dados =  new Dados();
        List<String> listacoluna = new ArrayList<>();

        for(int i = 0; i< this.listaElementos.size(); i++)
            listacoluna.add(dadosMap.get(coluna).listaString.get(i));

        dados.listaString = listacoluna;

        return dados.retornaMediaDouble();
    }


}
