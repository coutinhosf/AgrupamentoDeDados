package agrupamento;

import agrupamento.comum.Arquivo;
import agrupamento.comum.Coluna;
import agrupamento.kmeans.Centroide;
import agrupamento.kmeans.KMeans;

import java.util.Map;


public class AgrupamentoDados {

    public static void main(String[] args) {
        String nomeArquivo = "iris";

        Arquivo file = new Arquivo(nomeArquivo);
        file.leituraArquivo();

        // selecionar apenas solunas numericas do arquivo
        Map<Integer, Coluna> dadosArquivo = file.selecionarColunasNumericas();
        Map<Integer, Centroide> resultadoKMeans = AgrupamentoDados.KMeans(dadosArquivo,1,10,5);

        // imprime centroides
        for (int i = 0; i < resultadoKMeans.size(); i++) {
            System.out.println(resultadoKMeans.get(i).toString());
//            System.out.println("Centroide: " + i);
//            for (int j = 0; j < resultadoKMeans.get(i).indicesDosObjetos.size(); j++)
//                System.out.print(resultadoKMeans.get(i).indicesDosObjetos.get(j) + ", ");
//            System.out.println("\n");
        }
    }

    public static Map<Integer, Centroide> KMeans(Map<Integer, Coluna> dadosArquivo, Integer distancia, Integer totalInteracao, Integer pontosK ) {
        KMeans kmeansAlgoritmo = new KMeans(pontosK, totalInteracao);
        kmeansAlgoritmo.executa(dadosArquivo);
        return kmeansAlgoritmo.centroides;
    }

}
