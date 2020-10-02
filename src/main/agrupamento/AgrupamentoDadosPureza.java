package agrupamento;

import agrupamento.comum.Arquivo;
import agrupamento.comum.Coluna;
import agrupamento.kmeans.Centroide;
import agrupamento.kmeans.KMeans;

import java.util.*;


public class AgrupamentoDadosPureza {

    public static Map<String, Integer> contarFrequencias(List<String> list) {
        // hashmap to store the frequency of element
        Map<String, Integer> hm = new HashMap<String, Integer>();

        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        return hm;
    }

    public static void main(String[] args) {
        String nomeArquivo = "iris";

        Arquivo file = new Arquivo(nomeArquivo);
        file.leituraArquivo();

        // selecionar apenas colunas numericas do arquivo
        Map<Integer, Coluna> dadosArquivo = file.selecionarColunasNumericas();

        // selecionar apenas colunas de classe do arquivo
        Map<String, Integer> classes = file.selecionarColunasClasse();

        Map<Integer, Centroide> resultadoKMeansK3 = AgrupamentoDadosPureza.KMeans(dadosArquivo, 1, 10, 3);
        Map<Integer, Centroide> resultadoKMeansK4 = AgrupamentoDadosPureza.KMeans(dadosArquivo, 1, 10, 4);
        Map<Integer, Centroide> resultadoKMeansK5 = AgrupamentoDadosPureza.KMeans(dadosArquivo, 1, 10, 5);
//        System.out.println(classes);

        List<String> valoresDeClassesComIndice = valoresDeClasses(file);
        pureza(resultadoKMeansK3, valoresDeClassesComIndice);
        pureza(resultadoKMeansK4, valoresDeClassesComIndice);
        pureza(resultadoKMeansK5, valoresDeClassesComIndice);
    }

    private static List<String> valoresDeClasses(Arquivo file) {
        List<String> valoresDeClassesComIndice = new ArrayList<String>();
        Object[] colunas = file.getDadosArquivo().values().toArray();
        for (int i = 0; i < colunas.length; i++) {
            Coluna c = (Coluna) colunas[i];
            if (c.tipoDado == "String") {
                valoresDeClassesComIndice = c.valores;
            }
        }
        return valoresDeClassesComIndice;
    }

    private static void pureza(Map<Integer, Centroide> resultadoKMeans, List<String> valoresDeClassesComIndice) {
        for (Centroide c : resultadoKMeans.values()) {
            List<String> cclasses = new ArrayList<String>();
            System.out.println("Cluster(" + c.id + "): " + c.indicesDosObjetos.size() + " elementos");
            for (Integer idx : c.indicesDosObjetos) {
                cclasses.add(valoresDeClassesComIndice.get(idx));
            }
            Map<String, Integer> frequencias = contarFrequencias(cclasses);
            System.out.println(frequencias);
            Integer maior = 0;
            String nomeMaior = "";
            for (Map.Entry<String, Integer> clfq : frequencias.entrySet()) {
                if (clfq.getValue() > maior) {
                    maior = clfq.getValue();
                    nomeMaior = clfq.getKey();
                }
            }
//          System.out.println(nomeMaior + " " + maior);
            for (Map.Entry<String, Integer> clfq : frequencias.entrySet()) {
                System.out.println(clfq.getKey() + ": " + new Double(clfq.getValue()) / c.indicesDosObjetos.size());
            }
            System.out.println();
        }

        // imprime centroides
        for (int i = 0; i < resultadoKMeans.size(); i++)
            System.out.println(resultadoKMeans.get(i).toString());
    }

    public static Map<Integer, Centroide> KMeans(Map<Integer, Coluna> dadosArquivo, Integer distancia, Integer totalInteracao, Integer pontosK) {
        KMeans kmeansAlgoritmo = new KMeans(pontosK, totalInteracao);
        kmeansAlgoritmo.executa(dadosArquivo);
        return kmeansAlgoritmo.centroides;
    }

}
