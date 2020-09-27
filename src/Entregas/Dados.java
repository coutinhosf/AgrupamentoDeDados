package Entregas;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Dados {

    public String nomeColuna;
    public boolean possuiVazio;
    public String tipoDado;
    public String itemFaltante;
    public List<String> listaString;

    public Dados()
    {

    }

    public Dados(String nomeColuna)
    {
        this.nomeColuna = nomeColuna;
        this.possuiVazio = false;
        this.tipoDado = "null";
    }
    public int retornaMediaInteiro()
    {
        int somatorio = 0;
        int total = 0;

        for (String itemLista: this.listaString)
            if(!(itemLista.contains("?"))) {
                somatorio += Integer.parseInt(itemLista);
                total +=1;
            }
         if(total>1)
            return (somatorio/total);
         else
             return somatorio;
    }

    public double retornaMediaDouble()
    {
        double somatorio = 0.0;
        double total = 0.0;

        for (String itemLista: this.listaString) {
            somatorio += Double.parseDouble(itemLista);
            total += 1.0;
        }

        if(total>1.0)
             return (somatorio/total);
        else
            return somatorio;
    }

    public String Moda()
    {
        String palavra = "";
        int qtdade = 0;
        int frequencia =0;

        for (String itemLista: this.listaString) {
            frequencia  = Collections.frequency(this.listaString,itemLista);
            if(frequencia > qtdade && !itemLista.contains("?"))
            {
                palavra = itemLista;
                qtdade = frequencia;
            }
        }
        return palavra;
    }

    public void ItemFaltante(){

        if(this.tipoDado.contains("Inteiro"))
            this.itemFaltante = String.valueOf(retornaMediaInteiro());
        if(this.tipoDado.contains("Double"))
            this.itemFaltante = String.valueOf(retornaMediaDouble());
        if(this.tipoDado.contains("String"))
            this.itemFaltante = Moda();
    }

    public double[][] retornaPares(int distancia, Map<Integer,Dados> dadosMap )
    {
        Distancia pares = new Distancia();
        return pares.calculaTodosPares(dadosMap, distancia);
    }


}
