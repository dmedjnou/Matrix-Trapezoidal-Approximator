import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TrapezoidalApproximator {
    private List<Double> FXvalues;
    private List<Double> xValues;
    private int N;
    private double deltaX;

    public TrapezoidalApproximator() {
        xValues = new ArrayList<>();
    }

    public void loadCSVValues(String filepath) {
        try {
            FXvalues = CSVReader.getData(filepath);
        } catch (IOException e) {
            System.err.println("IOException thrown, ending runtime");
            throw new RuntimeException();
        }

        N = FXvalues.size() - 1;
    }

    public static int findIndexOfZero(List<Double> values) throws NoSuchElementException {
        for (int i = 0; i < values.size(); i++) {
            Double v =  values.get(i);
            if (v.equals(0.0)) return i;
        }
        throw new NoSuchElementException();
    }

    public void generateXValues() {
        double lowerBound = 0;
        double upperBound = Math.PI;

        this.deltaX = (upperBound - lowerBound)/N;

        xValues.add(lowerBound);
        for (int i = 1; i < N; i++) {
            xValues.add(lowerBound + deltaX * i);
        }
        xValues.add(upperBound);
    }

    private List<List<Double>> getSinKXMatrix() {
        List<List<Double>> allSinKXValues = new ArrayList<>();

        for (int k = 1; k <= N; k++) {
            allSinKXValues.add(getSinKXValues(k));
        }

        return allSinKXValues;
    }

    public List<Double> getSinKXValues(int k) {
        List<Double> sinValues = new ArrayList<>();

        for (Double xValue : xValues) {
            sinValues.add(Math.sin(k * xValue));
        }

        return sinValues;
    }


    private List<List<Double>> getGofXMatrix() {
        List<List<Double>> valueMatrix = getSinKXMatrix();

        for (List<Double> valuesForGivenK : valueMatrix) {
            for (int i = 0; i < valuesForGivenK.size(); i++) {
                Double sinKXValue =  valuesForGivenK.get(i);
                valuesForGivenK.set(i, sinKXValue * FXvalues.get(i));
            }
        }

        return valueMatrix;
    }

    public List<Double> performTrapezoidalApproximation() {
        List<Double> Bn_Values = new ArrayList<>();
        List<List<Double>> gxMatrix = getGofXMatrix();

        for (List<Double> gxValues : gxMatrix) {
            int lastIndex = gxValues.size()-1;
            double B_n = (2.0/Math.PI) * deltaX * (0.5*gxValues.get(0)
                                                + sum(gxValues.subList(1,lastIndex))
                                                +0.5*gxValues.get(lastIndex));
            Bn_Values.add(B_n);
        }

        return Bn_Values;
    }

    private double sum(List<Double> values) {
        double result = 0;

        for (Double value : values) {
            result += value;
        }

        return result;
    }
}
