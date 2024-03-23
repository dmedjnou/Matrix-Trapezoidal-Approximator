import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {


    public static List<Double> getData(String filepath) throws IOException {
        List<Double> data = new ArrayList<>();
        List<String> lines = getAllLines(filepath);

        for (String line : lines) {
            data.add(Double.parseDouble(line));
        }

        return data;
    }


    private static List<String> getAllLines(String filepath) throws IOException {
        List<String> lines = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Uh oh, the file seemed to be missing. Fix this");
            throw new RuntimeException("FileNotFound");
        }


        return lines;
    }
}
