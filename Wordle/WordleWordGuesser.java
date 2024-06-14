package JavaWorks.Projects.Wordle;
import java.util.*;
import java.io.*;
public class WordleWordGuesser {

    private Scanner wdls;
    private Scanner in;
    private ArrayList<ArrayList<Integer>> counts;
    private static ArrayList<String> wordList;
    private ArrayList<String> OriginalWordList;
    private ArrayList<String> wordsByScore;
    private boolean isRunning;
    private ArrayList<Integer> Top5P;

    public WordleWordGuesser() throws FileNotFoundException{
        wdls = new Scanner( new File("C:/Users/Robert J/VS Code/JavaWorks/Projects/Wordle/wordleWordList.txt"));
        in = new Scanner(System.in);
        OriginalWordList = new ArrayList<String>();
        
        while(wdls.hasNext()){
            OriginalWordList.add(wdls.next());
        }

        isRunning = true;
        resetGame();
        overrideTopWord("crane");
        System.out.println(getGuess());
        while(isRunning){
            // Format: "ggnny" g==gray, n==green, y==yellow
            String result = in.nextLine();
            if(result.equals("Quit")){
                isRunning=false;
                continue;
            }
            if(result.equals("Restart")){
                resetGame();
                System.out.println(getGuess());
                continue;
            }
            filterList(result);
            fillCounts();
            fillWordsByScore();
            System.out.println(getGuess()+"");
        }
    }
    public void overrideTopWord(String word){
        wordsByScore.add(0, word);
    }
    public void filterList(String r){
        char[] word = wordsByScore.get(0).toCharArray();
        char[] rslt = r.toCharArray();
        for (int i = 0; i < 5; i++) {
            if(rslt[i]=='g'){
                filterWordListGrays(""+word[i]);
            }else if(rslt[i]=='n'){
                filterWordListGreen(""+word[i]+i);
            }else{
                filterWordListYellows(""+word[i]+i);
            }
        } 
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
        WordleWordGuesser game1 = new WordleWordGuesser();
    }
}
