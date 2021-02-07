import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class task3 {
    static final int CASH_COUNT = 16;
    public static void main(String[] args) {
        if(args.length!= 1){
            System.out.println("Invalid number of inputs!");
            return;
        }
        String dirPath = new File("").getAbsolutePath() + "\\" + args[0];
        double[] commonSum = new double[CASH_COUNT];
        try(Stream<Path> pathStream = Files.walk(Paths.get(dirPath))){
            List<File> files = pathStream
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            for (File currCash : files) {
                Scanner in = new Scanner(currCash);
                int i = 0;
                while (in.hasNext()){
                    commonSum[i++] += Double.parseDouble(in.nextLine());
                }
            }
        }
        catch (IOException ex) {
            System.out.println("No such directory exist!");
            return;
        }
        double max = 0.0;
        int maxPos = 0;
        for (int i = 0; i < CASH_COUNT; i++){
            if(commonSum[i] > max){
                max = commonSum[i];
                maxPos = i;
            }
        }
        System.out.println(maxPos + 1);
    }
}
