package JavaWorks.Projects.Wordle;
import java.util.*;
import java.io.*;

public class Wordle {
    private ArrayList<String> wordList;
    private Scanner wdls;
    private String currentWord;
    private int totalGuesses;
    private PrintStream out;
    // private PrintStream sys;
    public Wordle() throws FileNotFoundException{
        wdls = new Scanner(new File("C:/Users/Robert J/VS Code/JavaWorks/Projects/Wordle/wordleWordList.txt"));
        wordList = new ArrayList<String>();
        out = new PrintStream(new File("C:/Users/Robert J/VS Code/Javaworks/Projects/Wordle/data_2.txt"));
        // sys = new PrintStream(System.out);
        System.setOut(out);
        while(wdls.hasNext()){
            wordList.add(wdls.next());
        }
        System.setOut(out);
        WordleBot bot = new WordleBot();
        for (String wd : wordList) {
            currentWord = wd;
            bot.resetGame();
            bot.overrideTopWord("trace");
            out.print("trace");
            while(!getColors(bot.getGuess()).equals("nnnnn")){
                bot.filterList(getColors(bot.getGuess()));
                bot.fillCounts();
                bot.fillWordsByScore();
                out.print(","+bot.getGuess());
            }
            out.print("\n");
        }

    }
    public void averageGuesses(String word) throws FileNotFoundException{
        totalGuesses = 0;

        WordleBot bot = new WordleBot();
        for (String wd : wordList) {
            // System.setOut(sys);
            // System.out.print(wd+":");
            currentWord = wd;
            bot.resetGame();
            bot.overrideTopWord(word);
            totalGuesses++;
            while(!getColors(bot.getGuess()).equals("nnnnn")){
                bot.filterList(getColors(bot.getGuess()));
                bot.fillCounts();
                bot.fillWordsByScore();
                totalGuesses++;
            }
            // System.out.println(totalGuesses);
        }
        out.print((float)totalGuesses/wordList.size()+"\n");
    }
    public String getColors(String g){
        String out = "";
        char[] guess = g.toCharArray();
        char[] word = currentWord.toCharArray();
        for (int i = 0; i < 5; i++) {
            if(guess[i]==word[i]){
                out+="n";
            }else if(currentWord.indexOf(guess[i])>=0){
                out+="y";
            }else{
                out+="g";
            }
        }
        return out;
    }
    public static void main(String[] args) throws FileNotFoundException{
        @SuppressWarnings("unused")
        Wordle game1 = new Wordle(); 
    }
}
