import java.util.Random;
public class Exponent {
    static double exponent(double lambda) { // A function that generates a value within an Exponential Distribution with given parameters
        Random rand = new Random();
        return -Math.log(1 - rand.nextDouble()) / lambda;
    }
}
