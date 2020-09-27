import Entregas.Arquivo;

import java.util.Scanner;


public class AgrupamentoDados {

    public static void main(String[] args) {

        String nomeArquivo;
        String distancia;
        String interacoes;
        String pontosK;
        Arquivo file;
        Scanner entrada = new Scanner(System.in);

        System.out.println("----- Agrupamento De Entregas.Dados ----- Entrega03 -----");
        System.out.print("Nome Entregas.Arquivo CSV: ");
        nomeArquivo =  entrada.nextLine();
        System.out.print("Entregas.Distancia:\n 1 - Euclidiana \n 2 - Manhattan \n Digite o Numero:  ");
        distancia =  entrada.nextLine();
/*
        System.out.println("Total de Interações:");
        interacoes =  entrada.nextLine();
        System.out.println("Total de pontos K:");
        pontosK =  entrada.nextLine();
*/
        file = new Arquivo(nomeArquivo,distancia,"10","5");
        System.out.println(file.diretorio);

        file.leituraArquivo();
        file.salvaNovoArquivo();
    }

}
