package JavaWorks.Projects.Wordle;
import java.util.*;
public class Filter {
    private static ArrayList<String> wordList;
    private ArrayList<String> toDo;
    private String guess;
    private String result;
    // Constructor
    public Filter(ArrayList<String> wL){
        setWordList(wL);
    }

    // Setters
    public void setGuess(String s) {
        guess = s;
    }
    public void setResult(String s){
        result = s;
    }
    public void setWordList(ArrayList<String> wL){
        wordList = wL;
    }

    // Getters
    public ArrayList<String> getWordList(){
        return wordList;
    }

    public void newGuess(String word, String r){
        setGuess(word);
        setResult(r);
        filterList();
    }
    public void filterList(){
        char[] word = guess.toCharArray();
        char[] rslt = result.toCharArray();
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
    

}
