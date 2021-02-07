import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class task1 {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Invalid input!");
            return;
        }
        //String path = new File("").getAbsolutePath()+ "\\src\\test1.txt";
        String path = new File("").getAbsolutePath() + "\\" + args[0] + ".txt";
        double perc90 = 0.0, median = 0.0, sum = 0.0;
        short max = Short.MIN_VALUE, min = Short.MAX_VALUE;
        ArrayList<Short> nums = new ArrayList<>();

        try (Scanner in = new Scanner(new File(path))){
            while (in.hasNext()){
                short currItem = Short.parseShort(in.nextLine());
                if (currItem > max) max = currItem;
                if (currItem < min) min = currItem;
                nums.add(currItem);
                sum += currItem;
            }
        }
        catch (Exception ex) {
            System.out.println("No such file in directory!");
            return;
        }

        Collections.sort(nums);
        median = nums.size() % 2 == 1 ? nums.get(nums.size() / 2)
                : (nums.get((nums.size() / 2) - 1) + nums.get(nums.size() / 2)) / 2.0;
        double pos = (nums.size() - 1) * 0.9 + 1;
        if(pos == 1d)
            perc90 = nums.get(0);
        else if (pos == nums.size())
            perc90 = nums.get(nums.size() - 1);
        else {
            int k = (int) pos;
            double d = pos - k;
            perc90 = nums.get(k - 1) + d * (nums.get(k) - nums.get(k - 1));
        }
        System.out.printf("%.2f\n", perc90);
        System.out.printf("%.2f\n", median);
        System.out.printf("%.2f\n", (double) max);
        System.out.printf("%.2f\n", (double) min);
        System.out.printf("%.2f\n", sum / nums.size() );
    }
}



