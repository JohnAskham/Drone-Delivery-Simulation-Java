package StaticMethods;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomGeneratedNumbers {

    public static Random random = new Random();

    // place inn a random seed here
    public static void setSeed() {
        random = new Random(69);
    }

   /*
    * Generates random int from the two given int values
    * @params min being the min value
    * @params max being the max value
    */
    public static int getRandomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }
        return random.nextInt((max - min) + 1) + min;
    }

    /*
     * Generates a random double from the two given ints
     * @params min being the min value
     * @params max being the max value
     */
    public static double getRandomDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }
        double randomValue = min + (max - min) * random.nextDouble();
        return roundToThreeDecimalPlaces(randomValue);
    }

    /*
     * Rounds a given dobule value to three decimals
     * @value the passed decimal value
     */
    private static double roundToThreeDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
