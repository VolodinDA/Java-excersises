import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class task4 {
    public static ArrayList<LocalTime[]> getInitialTime(String path){
        ArrayList<LocalTime[]> timeList = new ArrayList<>();
        try(Scanner in = new Scanner(new File(path))){
            while (in.hasNext()){
                String[] currString = in.nextLine().split(" ");
                String entrStr = currString[0];
                String extStr = currString[1];
                try {
                    LocalTime entryTime =  LocalTime.parse(entrStr.indexOf(':') == 1 ? "0" + entrStr + ":00" : entrStr + ":00");
                    LocalTime exitTime = LocalTime.parse(extStr.indexOf(':') == 1 ? "0" + extStr + ":00" : extStr + ":00");
                    LocalTime[] currTime = {entryTime, exitTime};
                    timeList.add(currTime);
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    LocalTime[] lt = {LocalTime.MIDNIGHT, LocalTime.MIDNIGHT};
                    timeList.add(lt);
                }
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        Comparator<LocalTime[]> comp = (first, second) -> first[0].compareTo(second[0]);
        Comparator<LocalTime[]> comp1 = (first, second) -> first[1].compareTo(second[1]);
        timeList.sort(comp.thenComparing(comp1));
        return timeList;
    }

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Invalid Argument Input!");
            return;
        }
        String path = new File("").getAbsolutePath() + "\\" + args[0] + ".txt";
        //String path = new File("").getAbsolutePath() + "\\src\\test4.txt";
        ArrayList<LocalTime[]> timeList = getInitialTime(path);

        int max = 0, currNum = 1;
        Queue<LocalTime> lastExit = new LinkedList<>();//очередь для обращения к последнему времени выхода
        LocalTime firstEntry = timeList.get(0)[0];//время последнего входа в рассматриваемо интервале
        LocalTime intervalEnd = null;//конец интервала, выводимый в результате
        //ArrayList<Map.Entry<Integer, String>> intervalList = new ArrayList<>();//список пар "число пользователей" - "время посещения"
        ArrayList<Pair<Integer, String>> intervalList = new ArrayList<>();
        boolean isNewInterval = false;//флаг для отслеживания начала построения последующих интервалов
        //ищем все возможные интервалы, выбираем все с максимальным числом посетителей
        for (int i = 0; i < timeList.size(); i++) {
            if(!isNewInterval)
                lastExit.offer(timeList.get(i)[1]);
            //первый вошел, второй вышел, число посетителей не меняется, считаем дальше
            if (timeList.get(i)[0].compareTo(lastExit.peek()) == 0)
                intervalEnd = lastExit.poll();
            //первый вошел до выхода второго, сужаем интервал, считаем дальше
            if(!(timeList.get(i)[0].compareTo(lastExit.peek()) == 0
                    || timeList.get(i)[0].compareTo(lastExit.peek()) == 1)){
                if(lastExit.size()!= currNum) {//сдвигаем начало интервала только если число посетителей увеличилось
                    firstEntry = timeList.get(i)[0];
                    currNum++;
                }
                if (currNum > max)
                    max = currNum;
                isNewInterval = false;
            }
            //со вторым вышел ещё кто-то или первый вошел позже выхода второго, интервал закончен
            else {
                intervalEnd = lastExit.poll();
                //intervalList.add(Map.entry(currNum--, String.format("%tR %tR", firstEntry, intervalEnd)));
                intervalList.add(new Pair<Integer, String>(currNum--, String.format("%tR %tR", firstEntry, intervalEnd)));
                i--;//нужно рассмотреть текущее время входа timeList(i) в качестве начала следующего интервала
                isNewInterval = true;
            }
        }
        //нужно выбрать конец последнего рассматриваемого интервала,
        //сравнить самый поздний момент выхода из очереди и последний выход за день, выбрать наименьший
        intervalEnd = timeList.get(timeList.size() - 1)[1].compareTo(lastExit.peek()) == -1
                ? timeList.get(timeList.size() - 1)[1] : lastExit.poll();
        //intervalList.add((Map.entry(currNum, String.format("%tR %tR", firstEntry, intervalEnd))));
        intervalList.add(new Pair<Integer, String>(currNum, String.format("%tR %tR", firstEntry, intervalEnd)));
        for(Pair<Integer, String> interval : intervalList){
            if(interval.getKey() == max){
                System.out.println(interval.getValue());
            }
        }
    }
}
