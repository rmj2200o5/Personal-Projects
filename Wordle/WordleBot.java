package JavaWorks.Projects.Wordle;
import java.util.*;
import java.io.*;
/*
 * Implemented in Wordle Simulator
 */
public class WordleBot {

    private Scanner wdls;
    private ArrayList<ArrayList<Integer>> counts;
    private static ArrayList<String> wordList;
    private ArrayList<String> OriginalWordList;
    private ArrayList<String> wordsByScore;
    private ArrayList<Integer> Top5P;
    private ArrayList<String> toDo;

    public WordleBot() throws FileNotFoundException{
        wdls = new Scanner( new File("C:/Users/Robert J/VS Code/JavaWorks/Projects/Wordle/wordleWordList.txt"));
        OriginalWordList = new ArrayList<String>();
        while(wdls.hasNext()){
            OriginalWordList.add(wdls.next());
        }
        toDo = new ArrayList<String>();
        resetGame();
    }
    public void overrideTopWord(String word){
        wordsByScore.add(0, word);
    }
    public void filterList(String r){
        char[] word = wordsByScore.get(0).toCharArray();
        char[] rslt = r.toCharArray();
        for (int i = 0; i < 5; i++) {
            if(rslt[i]=='g'){
                toDo.add("g "+word[i]);
            }else if(rslt[i]=='n'){
                toDo.add("n "+word[i]);

            }else{
                toDo.add("y "+word[i]);

            }
        }
        ArrayList<String> tempWordList = new ArrayList<String>();
        for (String w : wordList) {
            boolean gray = true;
            boolean green = true;
            boolean yellow = true;
            for (int i = 0; i < 5; i++) {
                String cmd = toDo.get(i);
                char color = cmd.split(" ")[0].toCharArray()[0];
                String lets = cmd.split(" ")[1];
                if(color=='g'){
                    gray = gray && w.indexOf(lets)==-1;
                }else if(color=='n'){
                    char let = lets.toCharArray()[0];
                    int index = i;
                    green = green && w.charAt(index)==let;
                }else{
                    char let = lets.toCharArray()[0];
                    int index = i;
                    yellow = yellow && w.indexOf(let)>=0 && w.charAt(index)!=let;
                }
            }
            if(gray && green && yellow){
                tempWordList.add(w);
            }
        }
        toDo = new ArrayList<String>();
        wordList = tempWordList;
    }
    public String getGuess(){
        return(wordsByScore.get(0));
    }
    public void resetGame(){
        wordList = new ArrayList<String>();
        for (String word : OriginalWordList) {
            wordList.add(word);
        }
        fillCounts();
        fillWordsByScore();
    }
    /*
     * Why is this here????
     * Can't find any occurance of it being used
     */
    public void Limit(String lets){
        char C = lets.toCharArray()[0];
        int num = Integer.parseInt(lets.substring(1,2));
        ArrayList<String> tempWordList = new ArrayList<String>();
        for (String word : wordList) {
            int cnt = 0;
            for (int i = 0; i < 5; i++) {
                if(word.charAt(i)==C){
                    cnt++;
                }
            }
            if(cnt<=num){
                tempWordList.add(word);
            }
        }
        wordList = tempWordList;
    }

    /**
     * Goes through wordList, removing any word that doesn't contain the specified letter in the specified index
     * Input Format: "Confirmed:xy"
     * x represents any letter that retruns as green
     * y represents the position of that letter from 0-4
     */
    public void filterWordListGreen(String lets){
        String[] sets = lets.split(" ");
        for (String set : sets) {
            char C = set.toCharArray()[0];
            int num = Integer.parseInt(set.substring(1,2));
            ArrayList<String> tempWordList = new ArrayList<String>();
            for (String word : wordList) {
                if(C == word.toCharArray()[num]){
                    tempWordList.add(word);
                }
            }
            wordList = tempWordList;
            
        }
    }

    /**
     * Goes through wordList, removing anyword without the letters provided in the function parameter
     * Input Format: "Needs:xy xy xy xy"
     * x represents any letter that returns as yellow.
     */
    public void filterWordListYellows(String lets){
        String[] sets = lets.split(" ");
        for(String set: sets){
            char c = set.toCharArray()[0];
            int num = Integer.parseInt(set.substring(1, 2));
            ArrayList<String> tempWordList = new ArrayList<String>();
            for (String word : wordList) {
                boolean GoodWord = false;
                if(word.indexOf(c)>=0 && c!=word.toCharArray()[num]){
                    GoodWord = true;
                }
                if (GoodWord){
                    tempWordList.add(word);
                }
            }
            wordList = tempWordList;

        }
    }

    /**
     * Goes through wordlist, removing anywords with the letters provided in the function parameter
     * Input Format: "Remove:xxxx"
     * x represents any letter that returns as gray.
     */
    public void filterWordListGrays(String lets){
        char[] rLets = lets.toCharArray();
        ArrayList<String> tempWordList = new ArrayList<String>();
        for (String word : wordList) {
            boolean isPresent = false;
            for (char C : rLets) {
                if(word.indexOf(C)>=0){
                    isPresent = true;
                }
            }
            if (!isPresent){
                tempWordList.add(word);
            }
        }
        wordList = tempWordList;
    }

    /**
     * Prints the top 5 options for the next Guess.
     */
    public void getTop5(){
        for (int i = 0; i < Math.min(5, wordsByScore.size()); i++) {
            System.out.printf("%d: %s\t%d%n",i+1,wordsByScore.get(i),Top5P.get(i));
        }
 
    }

    /* 
    * Counts the occurance of letters in each position
    * Uses wordList to fill counts 
    */
    public void fillCounts(){
        counts = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 5; i++) {
            counts.add(new ArrayList<Integer>());
            for (int j = 0; j < 26; j++) {
                counts.get(i).add(0);
            }
        }
        for (String word : wordList) {
            char[] w = word.toCharArray();
            for (int i = 0; i < w.length; i++) {
                int index = (int)w[i]-97;
                counts.get(i).set(index, counts.get(i).get(index)+1);
            }
        }
    }

     /*
     * Uses wordList and Scores list to fill wordsByScore
     */
    public void fillWordsByScore(){
        wordsByScore = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        for (String word  : wordList) {
            scores.add(wordScore(word));
        }
        Top5P = new ArrayList<Integer>();
        for (int i = 0; i < wordList.size(); i++) {
            int index = scores.indexOf(Collections.max(scores));
            Top5P.add(Collections.max(scores));
            scores.set(index, -1);
            wordsByScore.add(wordList.get(index));
        }
    }

    /*
    *This method will return a ratings based on how common the letters in a word are.
    */
    public int wordScore(String word){
        int score = 0;
        char[] w = word.toCharArray();
        ArrayList<Character> used = new ArrayList<Character>();
        for (int i = 0; i < 5; i++) {
            if(used.contains(w[i])){
                continue;
            }
            score+=counts.get(i).get(((int)w[i])-97);
            used.add(w[i]);
        }
        return score;
    }
    public static void main(String[] args) throws FileNotFoundException{
        @SuppressWarnings("unused")
        WordleBot game1 = new WordleBot();
    }
}
