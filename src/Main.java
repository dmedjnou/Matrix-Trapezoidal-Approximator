import java.util.List;

public class Main {
    private static final String FILEPATH = "./CSV/A28.csv";

    public static void main(String[] args) {
        TrapezoidalApproximator approximator = new TrapezoidalApproximator();
        approximator.loadCSVValues(FILEPATH);
        approximator.generateXValues();


        List<Double> bn_values = approximator.performTrapezoidalApproximation();
        roundAllValues(bn_values);

        int R = TrapezoidalApproximator.findIndexOfZero(bn_values); //method returns 0 based index
        System.out.println("R: " + R);

        bn_values = bn_values.subList(0, R);
        System.out.println("b_n: " + bn_values);
        //printValues("b_n", bn_values);              //uncomment for vertical printing
    }

    private static void roundAllValues(List<Double> values) {
        values.replaceAll(aDouble -> (double) Math.round(aDouble)); //I love lambdas
    }


    public static void printValues(String name, List<Double> values) {
        System.out.printf("Values of %s: [\n", name);
        for (Double value : values) {
            System.out.println("\t" + value);
        }
        System.out.println("]");
    }
}