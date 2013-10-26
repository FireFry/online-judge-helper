import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Compressor {

    public static void main(String[] args) throws Exception {
        File file = new File("ProblemTemplate.java");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int stage = 0;
        while ((line = reader.readLine()) != null) {
            line = line.replace("Template", "%id%");
            if (stage == 0) {
                System.out.println(line);
                if (line.contains("Here goes some helping code:")) {
                    stage = 1;
                    System.out.print("    ");
                }
            } else if (stage == 1) {
                if (line.startsWith("}")) {
                    System.out.println();
                    System.out.println(line);
                    stage = 2;
                } else {
                    System.out.print(line.trim());
                }
            } else {
                System.out.println(line);
            }
        }
    }

}
