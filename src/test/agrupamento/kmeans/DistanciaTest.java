package agrupamento.kmeans;

import agrupamento.comum.Distancia;
import agrupamento.comum.Utils;
import org.jboss.arquillian.junit.container.JUnitTestRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DistanciaTest extends JUnitTestRunner {
    Distancia distancia;

    List<String> objetoA = Arrays.asList("10.0", "10.0");
    List<String> objetoB = Arrays.asList("5.0", "5.0");
    List<String> objetoC = Arrays.asList("0", "0");

    @Before
    public void startup() {
        distancia = new Distancia();
    }

    @Test
    public void deveRetornarDistanciaCorreta() {
        Assert.assertEquals(new Double(7.07), Utils.truncarDouble(distancia.distanciaEuclediana(objetoA, objetoB), 2));
        Assert.assertEquals(new Double(14.14), Utils.truncarDouble(distancia.distanciaEuclediana(objetoA, objetoC), 2));

        Assert.assertEquals(new Double(10.0), Utils.truncarDouble(distancia.distanciaManhattan(objetoA, objetoB), 2));
        Assert.assertEquals(new Double(20.0), Utils.truncarDouble(distancia.distanciaManhattan(objetoC, objetoA), 2));
        Assert.assertEquals(new Double(10.0), Utils.truncarDouble(distancia.distanciaManhattan(objetoC, objetoB), 2));
    }

    @Test
    public void deveRetornarDistanciaZeroEntreOsMesmosPontos() {
        // distancia entre os mesmos objetos
        Assert.assertEquals(new Double(0), Utils.truncarDouble(distancia.distanciaEuclediana(objetoA, objetoA), 2));
        Assert.assertEquals(new Double(0), Utils.truncarDouble(distancia.distanciaEuclediana(objetoB, objetoB), 2));
        Assert.assertEquals(new Double(0), Utils.truncarDouble(distancia.distanciaEuclediana(objetoC, objetoC), 2));

        Assert.assertEquals(new Double(0), Utils.truncarDouble(distancia.distanciaManhattan(objetoA, objetoA), 2));
        Assert.assertEquals(new Double(0), Utils.truncarDouble(distancia.distanciaManhattan(objetoB, objetoB), 2));
        Assert.assertEquals(new Double(0), Utils.truncarDouble(distancia.distanciaManhattan(objetoC, objetoC), 2));
    }
}
