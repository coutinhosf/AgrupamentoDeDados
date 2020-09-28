package agrupamento.comum;

public class Utils {

    public static Double truncarDouble(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
