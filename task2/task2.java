import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class task2 {
    public static ArrayList<float[]> getCoords(String path) {
        ArrayList<float[]> dotsList = new ArrayList<>();
        try(Scanner in = new Scanner(new File(path))) {
            while (in.hasNext()){
                String currDot = in.nextLine();
                dotsList.add(new float[]{Float.parseFloat(currDot.split(" ")[0]),
                        Float.parseFloat(currDot.split(" ")[1])});
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return dotsList;
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Invalid number of inputs!");
            return;
        }
        //String polyPath = new File("").getAbsolutePath() + "\\src\\polygon.txt";
        //String dotsPath = new File("").getAbsolutePath() + "\\src\\dots.txt";
        String polyPath = new File("").getAbsolutePath() + "\\" + args[0] + ".txt";
        String dotsPath = new File("").getAbsolutePath() + "\\" + args[1] + ".txt";
        ArrayList<float[]> polygon = getCoords(polyPath);
        ArrayList<float[]> dots = getCoords(dotsPath);
        if(polygon == null || dots == null)
            return;
        polygon.add(polygon.get(0));
        for (float[] dot : dots){
            int zero = 0, minus = 0;
            //float[0] - х, float[1] - y
            for (int i = 0; i < polygon.size() - 1; i++){
                float edge = ((polygon.get(i + 1)[0] - polygon.get(i)[0]) * (dot[1] - polygon.get(i)[1])) -
                        ((polygon.get(i + 1)[1] - polygon.get(i)[1]) * (dot[0] - polygon.get(i)[0]));
                switch (Float.compare(edge, 0)){
                    case 0:
                        zero++;
                        break;
                    case -1:
                        minus++;
                        break;
                }
            }
            if(zero == 2)
                System.out.println("0");
            else if (zero == 1)
                System.out.println("1");
            else if (minus == 4)
                System.out.println("2");
            else if (minus < 4)
                System.out.println("3");
        }
    }
}
