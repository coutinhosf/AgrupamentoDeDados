package agrupamento.comum;

import agrupamento.singlelink.SingleLink;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arquivo {

    private String nomeArquivo;
    public String diretorio;
    private Map<Integer, Coluna> dadosArquivo;
    private int totalColunas;

    public Arquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.diretorio = new File("").getAbsolutePath() + "/" + nomeArquivo + ".csv";
        this.totalColunas = 0;
    }

    public void leituraArquivo() {
        int cabecalhoFlag = 0;
        String[] cabecalho;
        String[] dados;
        BufferedReader br = null;
        String linhaArquivo;
        try {
            br = new BufferedReader(new FileReader(diretorio));
            while ((linhaArquivo = br.readLine()) != null) {
                if (cabecalhoFlag == 0) {
                    cabecalho = linhaArquivo.split(",");
                    mapeaCabecalho(cabecalho);
                    cabecalhoFlag = 1;
                } else {
                    dados = linhaArquivo.split(",");
                    lerTodasColunas(dados);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapeaCabecalho(String[] cabecalho) {
        Coluna cabecalhoColunas;
        this.totalColunas = cabecalho.length;
        this.dadosArquivo = new HashMap<Integer, Coluna>();
        for (int i = 0; i < cabecalho.length; i++) {
            cabecalhoColunas = new Coluna(cabecalho[i]);
            this.dadosArquivo.put(i, cabecalhoColunas);
            this.dadosArquivo.get(i).valores = new ArrayList<>();
        }


    }

    private void lerTodasColunas(String[] colunas) {
        for (int i = 0; i < colunas.length; i++) {

            if (colunas[i].contains("?") && this.dadosArquivo.get(i).possuiVazio == false) {
                this.dadosArquivo.get(i).valores.add(colunas[i]);
                temVazio(i);
            } else if (colunas[i].contains("?") && this.dadosArquivo.get(i).possuiVazio == true) {
                this.dadosArquivo.get(i).valores.add(colunas[i]);
                temVazio(i);
            } else if (this.dadosArquivo.get(i).possuiVazio == false) {
                this.dadosArquivo.get(i).valores.add(colunas[i]);
                if (this.dadosArquivo.get(i).tipoDado.contains("null"))
                    atribuirTipoAoDado(retiraAspas(colunas[i]), i);
            } else if (this.dadosArquivo.get(i).possuiVazio == true) {
                this.dadosArquivo.get(i).valores.add(colunas[i]);
                atribuirTipoAoDado(retiraAspas(colunas[i]), i);
            }
        }

    }

    private void temVazio(int i) {
        this.dadosArquivo.get(i).possuiVazio = true;
    }

    private void atribuirTipoAoDado(String dado, int i) {
        if (dado.trim().matches("^[ A-Za-z-_]+$"))
            this.dadosArquivo.get(i).tipoDado = "String";
        else if (dado.contains("."))
            this.dadosArquivo.get(i).tipoDado = "Double";
        else
            this.dadosArquivo.get(i).tipoDado = "Inteiro";
    }

    public Map<Integer, Coluna> getDadosArquivo() {
        return dadosArquivo;
    }

    public void setDadosArquivo(Map<Integer, Coluna> dadosArquivo) {
        this.dadosArquivo = dadosArquivo;
    }

    public String retiraAspas(String palavra) {
        StringBuilder sb = new StringBuilder();

        char[] tab = palavra.toCharArray();
        for (char current : tab) {
            if (current != '"')
                sb.append(current);
        }
        return sb.toString();
    }

    public void salvaNovoArquivo() {
        FileWriter arquivo;
        FileWriter arquivoDistancia;
        FileWriter arquivoSingleLink;
        try {
            arquivo = new FileWriter(new File("").getAbsolutePath() + File.separator + "ArquivoSemFaltantes.csv");
            arquivo.write(retornaStringItensFaltantes());
            arquivo.close();

//            arquivoDistancia = new FileWriter(new File("").getAbsolutePath() + "\\" + "EntregaDistancia.csv");
//            arquivoDistancia.write(retornaStringTodosPares(this.distancia));
//            arquivoDistancia.close();

//            retornaKmeans(this);
//            arquivoSingleLink = new FileWriter(new File("").getAbsolutePath()+"\\"+"main.java.SingleLink.txt");
//            arquivoSingleLink.write(retornaSingleLink(this.distancia));
//            arquivoSingleLink.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void colocaFaltantes() {
        //ColocaPosicoesFaltantes
        for (int i = 0; i < this.dadosArquivo.size(); i++) {
            if (this.dadosArquivo.get(i).possuiVazio == true)
                this.dadosArquivo.get(i).ItemFaltante();
        }

    }

    private String retornaStringItensFaltantes() {

        colocaFaltantes();

        String cabecalho = "";
        String linha = "";
        String linhas = "";
        int totalItensLista = 0;

        for (int i = 0; i < this.totalColunas; i++)
            cabecalho += this.dadosArquivo.get(i).nome + ",";

        totalItensLista = this.dadosArquivo.get(0).valores.size();
        cabecalho.substring(0, cabecalho.length() - 1);

        cabecalho += "\n";

        for (int i = 0; i < totalItensLista; i++) {
            linha = "";
            for (int k = 0; k < this.totalColunas; k++) {

                if (!(this.dadosArquivo.get(k).valores.get(i).contains("?")))
                    linha += this.dadosArquivo.get(k).valores.get(i) + ",";
                else
                    linha += this.dadosArquivo.get(k).itemFaltante + ",";
            }
            linhas += linha.substring(0, linha.length() - 1) + "\n";
        }

        return cabecalho + linhas;
    }

    private String retornaStringTodosPares(int distancia) {
        String linha;
        String linhas = "";
        Distancia calculo = new Distancia();
        double[][] matrizDistancia = calculo.calculaTodosPares(this.dadosArquivo, distancia);

        for (int i = 0; i < matrizDistancia.length; i++) {
            linha = "";
            for (int k = 0; k < matrizDistancia[0].length; k++) {
                linha += String.format("%.2f", matrizDistancia[i][k]).replace(",", ".") + ",";
            }
            linhas += linha.substring(0, linha.length() - 1) + "\n";
        }

        return linhas;
    }

    public Map<Integer, Coluna> selecionarColunasNumericas() {
        Map<Integer, Coluna> projecao = new HashMap<Integer, Coluna>();
        for (Map.Entry<Integer, Coluna> entradaMapa : dadosArquivo.entrySet()) {
            if (!entradaMapa.getValue().tipoDado.contains("String"))
                projecao.put(entradaMapa.getKey(), entradaMapa.getValue());
        }
        return projecao;
    }

    private String retornaSingleLink(int distancia) {
        SingleLink algoritmoSinlgeLink;
        Distancia calculo = new Distancia();
        double[][] matrizDistancia = calculo.calculaTodosPares(dadosArquivo, distancia);
        algoritmoSinlgeLink = new SingleLink(matrizDistancia);


        return algoritmoSinlgeLink.retornaSingleLink(matrizDistancia);
    }

    public Map<String, Integer> selecionarColunasClasse() {
        List<String> classes = new ArrayList();
        Map<String, Integer> classesMap = new HashMap();

        for (Map.Entry<Integer, Coluna> entradaMapa : dadosArquivo.entrySet()) {
            if (entradaMapa.getValue().tipoDado.contains("String")) {
                classes = entradaMapa.getValue().valores;
            }
        }
        for (String classe : classes){
            if (classesMap.containsKey(classe)) {
                classesMap.put(classe, classesMap.get(classe) + 1);
            } else {
                classesMap.put(classe, 1);
            }
        }
        return classesMap;
    }
}
