package SingleLink;

import Entregas.Arquivo;

public class SingleLink {

    private int posObjetoA;
    private int posObjetoB;
    private double menorDistancia;
    private int[] vetorVerificacaoGrupos;
    private String[] vetorStringGrupos;
    private double[][] matrizPares;

    public SingleLink(double[][] matriz) {
        this.matrizPares = matriz;
        this.vetorVerificacaoGrupos = new int[matriz.length];
    }

    private void retornaMenorDaMatrizDistancia(double[][] matriz) {
            double menor=0;
            int auxilio = 0;

        for(int i = 0; i< matriz.length; i++){
                for(int j = i; j<matriz.length; j++){

                        if(auxilio==0 && matriz[i][j] > 0) {
                            menor = matriz[i][j];
                            posObjetoA = i;
                            posObjetoB = j;
                            auxilio = 1;
                        }
                        else if(menor > matriz[i][j] && matriz[i][j] > 0){
                            menor = matriz[i][j];
                            posObjetoA = i;
                            posObjetoB = j;
                        }
                }
        }

        if(posObjetoA<posObjetoB) {
            vetorVerificacaoGrupos[posObjetoB] = 1;

        }
            else {

            vetorVerificacaoGrupos[posObjetoA] = 1;
        }

        matriz[posObjetoA][posObjetoB] = 0;
        matriz[posObjetoB][posObjetoA] = 0;
        this.menorDistancia = menor;

    }

    private double retornaMinEntreDoisObjetos(double objA, double objB) {
        return objA<objB ? objA : objB;
    }

    private double[] retornaJuncaoObjetos(double[][] matriz)
    {
        double[] juncao = new double[matriz.length];

        for(int i =0; i <matriz.length; i++)
        {
            if((i != posObjetoA) && (i != posObjetoB))
            {
                if(i<posObjetoA && i< posObjetoB)
                    juncao[i] = retornaMinEntreDoisObjetos(matriz[i][posObjetoA], matriz[i][posObjetoB]);
                else if(i>posObjetoA && i< posObjetoB)
                    juncao[i] = retornaMinEntreDoisObjetos(matriz[posObjetoA][i], matriz[i][posObjetoB]);
                else if(i<posObjetoA && i>posObjetoB)
                    juncao[i] = retornaMinEntreDoisObjetos(matriz[i][posObjetoA], matriz[posObjetoB][i]);
                else if(i>posObjetoA && i>posObjetoB)
                    juncao[i] = retornaMinEntreDoisObjetos(matriz[posObjetoA][i], matriz[posObjetoB][i]);
            }
            else
                juncao[i] = this.menorDistancia;
        }

        return juncao;
    }

    private double[][] atualizaMatrizProximidade(double[][] matriz, double[] juncao ) {

        //Elimina a cruz do objeto colocando a posiçao zerada;
        for(int i =0; i< matrizPares.length;i++){
            for(int j = i; j< matrizPares.length;j++ ){

                    if(i<posObjetoB){
                        if(j == posObjetoA)
                            matriz[i][j] =juncao[i];
                        if(i == posObjetoA && j == posObjetoB )
                            matriz[i][j] =0;
                    }

                    else if(i==posObjetoB) {
                        matriz[i][j]=0;
                    }
                    else if((i == posObjetoA) && (j > posObjetoB) ) {
                        matriz[i][j]=juncao[j];
                    }
            }
        }
        return matriz;
    }

    private boolean verificaVetorGrupo() {
        int totaldeGruposDiferenteDeUm = vetorVerificacaoGrupos.length;

        for(int i = 0; i<this.vetorVerificacaoGrupos.length;i++)
            if(vetorVerificacaoGrupos[i]==1) {
                totaldeGruposDiferenteDeUm--;
               if(totaldeGruposDiferenteDeUm == 1)
                     return false;
            }

        return true;
    }

    public String retornaSingleLink(double[][] matriz)
    {
        int tamanhoGrupo = matriz.length;
        vetorStringGrupos = new String[matriz.length];
        double[] juncao;
        boolean fim = false;
        String saida =  new String();

        do {

            retornaMenorDaMatrizDistancia(matriz);
            saida += retornaString(saida);
            juncao = retornaJuncaoObjetos(matriz);
            atualizaMatrizProximidade(matriz,juncao);

            tamanhoGrupo--;

        }while(tamanhoGrupo != 1);

        return saida;
    }

    private String retornaString(String saida) {
        if (saida== "") {
            return saida+String.valueOf(posObjetoA)+","+String.valueOf(posObjetoB)+"\n";
        }

        if(saida.contains(String.valueOf(posObjetoA))) {
            saida.replace("\n", "");
            return saida + "," + String.valueOf(posObjetoB) + "\n";
        }
        else
           return saida+String.valueOf(posObjetoA)+"\n";
    }



}
