package JavaWorks.Projects.Wordle;
import java.io.*;
import java.util.*;
public class computation {
    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("C:/Users/Robert J/VS Code/JavaWorks/Projects/Wordle/results.txt"));
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Float> averageSolve = new ArrayList<Float>();
        while(in.hasNextLine()){
            String[] data = in.nextLine().split(": ");
            words.add(data[0]);
            averageSolve.add(Float.parseFloat(data[1]));
        }
       for (int i = 0; i < 5; i++) {
        float value = averageSolve.stream().min(Comparator.<Float>naturalOrder()).get();
        int index = averageSolve.indexOf(value);
        System.out.println(words.get(index)+": "+value);
        averageSolve.set(index, 100f);
       }
       in.close();
    }
}
