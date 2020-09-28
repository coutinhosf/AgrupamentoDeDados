package agrupamento;

import agrupamento.comum.Arquivo;

import java.util.Scanner;


public class AgrupamentoDados {

    public static void main(String[] args) {
        String nomeArquivo = "iris";
/*
        System.out.println("Total de Interações:");
        interacoes =  entrada.nextLine();
        System.out.println("Total de pontos K:");
        pontosK =  entrada.nextLine();
*/
        Arquivo file = new Arquivo(nomeArquivo, "1","100","5");

        file.leituraArquivo();
        file.salvaNovoArquivo();
    }

}
