package Entregas;

import KMeans.KMeans;
import SingleLink.SingleLink;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Arquivo {

    private String nomeArquivo;
    public String diretorio;
    public int distancia;
    public int totalInteracao;
    public int pontosK;
    private Map<Integer,Dados> mapaDadosArquivos;
    private int totalColunas;

    public Arquivo(String nomeArquivo, String distancia, String totalInteracao, String pontosK){

        this.nomeArquivo = nomeArquivo;
        this.diretorio = new File("").getAbsolutePath() +"\\"+nomeArquivo+".csv";
        this.totalColunas = 0;
        this.distancia = Integer.parseInt(distancia);
        this.totalInteracao = Integer.parseInt(totalInteracao);
        this.pontosK = Integer.parseInt(pontosK);
    }

    public void leituraArquivo(){
        int cabecalhoFlag = 0;
        String[] cabecalho;
        String[] dados;
        BufferedReader br = null;
        String linhaArquivo;
        try
        {
            br = new BufferedReader(new FileReader(diretorio));
            while ((linhaArquivo = br.readLine()) != null)
            {
                  if(cabecalhoFlag == 0) {
                      cabecalho = linhaArquivo.split(",");
                      mapeaCabecalho(cabecalho);
                     cabecalhoFlag = 1;
                  }
                  else
                  {
                      dados  = linhaArquivo.split(",");
                      lerTodasColunas(dados);
                  }
            }
        }
        catch (FileNotFoundException e){
                e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapeaCabecalho(String[] cabecalho) {
            Dados cabecalhoColunas;
            this.totalColunas = cabecalho.length;
            this.mapaDadosArquivos = new HashMap<Integer, Dados>();
            for(int i = 0; i<cabecalho.length; i++)
            {
                cabecalhoColunas = new Dados(cabecalho[i]);
                this.mapaDadosArquivos.put(i,cabecalhoColunas);
                this.mapaDadosArquivos.get(i).listaString =  new ArrayList<>();
            }
    }

    private void lerTodasColunas(String[] colunas) {
        for(int i = 0; i<colunas.length; i++) {

            if(colunas[i].contains("?") && this.mapaDadosArquivos.get(i).possuiVazio == false) {
                this.mapaDadosArquivos.get(i).listaString.add(colunas[i]);
                    temVazio(i);
            }
            else if(colunas[i].contains("?") && this.mapaDadosArquivos.get(i).possuiVazio == true) {
                this.mapaDadosArquivos.get(i).listaString.add(colunas[i]);
                temVazio(i);
            }
            else if(this.mapaDadosArquivos.get(i).possuiVazio == false) {
                this.mapaDadosArquivos.get(i).listaString.add(colunas[i]);
                if(this.mapaDadosArquivos.get(i).tipoDado.contains("null"))
                    tipoDado(retiraAspas(colunas[i]),i);
            }
            else if(this.mapaDadosArquivos.get(i).possuiVazio == true) {
                this.mapaDadosArquivos.get(i).listaString.add(colunas[i]);
                    tipoDado(retiraAspas(colunas[i]),i);
            }
        }

    }

    private void temVazio(int i) {
        this.mapaDadosArquivos.get(i).possuiVazio = true;
    }

    private void tipoDado(String dado,int i) {
        if(dado.trim().matches("^[ A-Za-z]+$"))
            this.mapaDadosArquivos.get(i).tipoDado= "String";
        else if(dado.contains("."))
            this.mapaDadosArquivos.get(i).tipoDado= "Double";
        else
            this.mapaDadosArquivos.get(i).tipoDado= "Inteiro";
    }

    public Map<Integer, Dados> getMapaDadosArquivos() {
        return mapaDadosArquivos;
    }

    public void setMapaDadosArquivos(Map<Integer, Dados> mapaDadosArquivos) {
        this.mapaDadosArquivos = mapaDadosArquivos;
    }

    public String retiraAspas(String palavra){
        StringBuilder sb = new StringBuilder();

        char[] tab = palavra.toCharArray();
        for( char current : tab ){
            if( current != '"' )
                sb.append( current );
        }
        return sb.toString();
    }

    public void salvaNovoArquivo() {
        FileWriter arquivo;
        FileWriter arquivoDistancia;
        FileWriter arquivoSingleLink;
        try {
            arquivo = new FileWriter(new File("").getAbsolutePath()+"\\"+"ArquivoSemFaltantes.csv");
            arquivo.write(retornaStringItensFaltantes());
            arquivo.close();

            arquivoDistancia = new FileWriter(new File("").getAbsolutePath()+"\\"+"EntregaDistancia.csv");
            arquivoDistancia.write(retornaStringTodosPares(this.distancia));
            arquivoDistancia.close();

          //  retornaKmeans();


            arquivoSingleLink = new FileWriter(new File("").getAbsolutePath()+"\\"+"SingleLink.txt");
            arquivoSingleLink.write(retornaSingleLink(this.distancia));
            arquivoSingleLink.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void colocaFaltantes()
    {
        //ColocaPosicoesFaltantes
        for(int i = 0; i<this.mapaDadosArquivos.size(); i++) {
            if(this.mapaDadosArquivos.get(i).possuiVazio == true)
                this.mapaDadosArquivos.get(i).ItemFaltante();
        }

    }

    private String retornaStringItensFaltantes() {

        colocaFaltantes();

        String cabecalho = "";
        String linha = "";
        String linhas = "";
        int totalItensLista = 0;

        for(int i =0; i <this.totalColunas; i++)
            cabecalho += this.mapaDadosArquivos.get(i).nomeColuna+",";

        totalItensLista = this.mapaDadosArquivos.get(0).listaString.size();
        cabecalho.substring(0, cabecalho.length()-1);

        cabecalho += "\n";

        for(int i = 0; i<totalItensLista;i++)
        {
            linha = "";
            for(int k = 0; k <this.totalColunas; k++) {

                if(!(this.mapaDadosArquivos.get(k).listaString.get(i).contains("?")))
                    linha += this.mapaDadosArquivos.get(k).listaString.get(i) +",";
                else
                    linha += this.mapaDadosArquivos.get(k).itemFaltante+",";
            }
         linhas += linha.substring(0, linha.length()-1) + "\n";
        }

        return cabecalho+linhas;
    }

    private String retornaStringTodosPares(int distancia)
    {
        String linha;
        String linhas = "";
        Distancia calculo = new Distancia();
        double[][]  matrizDistancia = calculo.calculaTodosPares(this.mapaDadosArquivos,distancia);

        for(int i = 0; i<matrizDistancia.length;i++)
        {
            linha = "";
            for(int k = 0; k <matrizDistancia[0].length; k++) {
                    linha += String.format("%.2f",matrizDistancia[i][k]).replace(",",".")+",";
            }
            linhas += linha.substring(0, linha.length()-1) + "\n";
        }

        return linhas;
    }

    private String retornaKmeans()
    {
        KMeans kmeansAlgoritmo =  new KMeans(this.pontosK ,this.totalInteracao);
        kmeansAlgoritmo.retornaAlgoritmoKmeans(this.mapaDadosArquivos);

        for(int i =0; i<kmeansAlgoritmo.mapKmeans.size();i++ )
        {
            System.out.println("Centroide:" +i);
            for(int j=0; j< kmeansAlgoritmo.mapKmeans.get(i).listaElementos.size(); j++)
                     System.out.print(kmeansAlgoritmo.mapKmeans.get(i).listaElementos.get(j) + ", ") ;
            System.out.println("\n");
        }

        return"";
    }
    private String retornaSingleLink(int distancia)
    {
        SingleLink algoritmoSinlgeLink;
        Distancia calculo = new Distancia();
        double[][]  matrizDistancia = calculo.calculaTodosPares(this.mapaDadosArquivos,distancia);
        algoritmoSinlgeLink = new SingleLink(matrizDistancia);


        return algoritmoSinlgeLink.retornaSingleLink(matrizDistancia);
    }

}
