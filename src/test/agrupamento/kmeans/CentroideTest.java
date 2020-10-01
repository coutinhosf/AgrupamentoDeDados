package agrupamento.kmeans;

import agrupamento.comum.Distancia;
import agrupamento.comum.Utils;
import org.jboss.arquillian.junit.container.JUnitTestRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CentroideTest extends JUnitTestRunner {

        @Test
        public void deveRetornarDistanciaCorreta() {
            for (int i=0; i < 100; i++) {
                // numero maximo precisa ser maior que 1
                int numeroMaximo = new Double((Math.random() * 10) + 1).intValue();

                List<Integer> intervalo = IntStream.rangeClosed(0, numeroMaximo-1)
                        .boxed().collect(Collectors.toList());

                Integer numeroInicialAleatorio = Centroide.retornaCentroideInicial(numeroMaximo);

                Assert.assertTrue(intervalo.contains(numeroInicialAleatorio));
            }


        }

}