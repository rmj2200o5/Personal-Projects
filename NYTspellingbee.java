package JavaWorks.Projects;

import java.util.*;
import java.io.*;

public class NYTspellingbee
{
	private String rules, commands;
	private Scanner dict, sc, gms, introFiles;
	private int score, maxScore;
	private ArrayList<String> alreadyGuessed, possibleWordList, dictionary, games;
	private ArrayList<Character> letters;
	private boolean isRunning, usePresets;
	private final String[] ranks = {"Beginner","Good Start","Moving Up","Good","Solid","Nice","Great","Amazing","Genius", "Queen Bee"};
	private final double[] rankRatios = {0, 0.02, 0.05, 0.08, 0.15, 0.25, 0.4, 0.5, 0.7, 1};
	
	//Constructor
	public NYTspellingbee() throws FileNotFoundException
	{
		dict = new Scanner(new File("C:/Users/Robert J/VS Code/JavaWorks/dictionary.txt"));
		gms = new Scanner(new File("C:/Users/Robert J/VS Code/JavaWorks/games.txt"));
		sc = new Scanner(System.in);
		
		setIntroStrings();
		games = new ArrayList<String>();
		dictionary = new ArrayList<String>();
		
		while(gms.hasNext())
			games.add(gms.next());
		
		while(dict.hasNext())
			dictionary.add(dict.next());
		
		resetInstanceVariables();
		
		// setLetters();
		Collections.sort(dictionary);
		// fillPossibleWordList();
		System.out.println("*****WELCOME TO THE SPELLING BEE GAME*****");
		System.out.println(rules);
		System.out.println(commands);
		confirmGameStart();
		updateGameMode();
		
		if(!usePresets)
			enterLetters();
		else
		{
			setLetters();
			fillPossibleWordList();
		}
		
		while(isRunning)
		{
			//s.o.p input a word or choose a menu option
			printCurrentRank();
			System.out.println("Score: " + score);
			printLetters();
			System.out.println("Please enter a word or command. (type !info if you need help)");
			
			String str = sc.nextLine().toLowerCase();
			
			System.out.println();
			
			if(str.length()>0 && str.charAt(0)=='!')
				getMenuOption(str); //call correct menu option
			else if(isWord(str))
				makeGuess(str);
		}
		
	}
	
	//Continuously prompts user to input 7 characters until a working letter set is entered
	public void enterLetters()
	{
		boolean confirmInput = false;
		while(!confirmInput)
		{
			boolean keepChecking = true;
			System.out.println("Please enter 7 different characters from a-z (ex. abcdefg). ");
			System.out.println("The first letter you enter will be in the center!");
			String str = sc.nextLine().toLowerCase();
			System.out.println();
			
			if(str.length()!=7)
			{
				System.out.println("You must enter exactly 7 letters!\n");
				keepChecking = false;
			}
			if(keepChecking)
			{
				for(int i=0;i<str.length();i++)
				{
					char temp = str.charAt(i);
					
					if(temp<'a'||temp>'z')
					{
						System.out.println("You may only enter characters from a-z!\n");
						keepChecking = false;
						break;
					}
				}
			}
			if(keepChecking)
			{
				ArrayList<Character> tempLetters = new ArrayList<Character>();
				for(int i=0;i<str.length();i++)
					tempLetters.add(str.charAt(i));
				boolean hasPangram = false;
				for(int i=0;i<dictionary.size();i++)
				{
					boolean pangram = true;
					for(int j=0;j<tempLetters.size();j++)
						if(dictionary.get(i).indexOf(""+tempLetters.get(j))<0)
							pangram = false;
					for(int j=0;j<dictionary.get(i).length();j++)
						if(!tempLetters.contains(dictionary.get(i).charAt(j)))
							pangram = false;
					if(pangram)
					{
						hasPangram = true;
						break;
					}	
				}
				if(hasPangram)
				{
					confirmInput = true;
					letters = tempLetters;
				}
				else
					System.out.println("These letters do not make a pangram!\n");
			}
		}
		fillPossibleWordList();
	}
	
	//Switches a boolean flag to determine if the user will enter their own letters
	public void updateGameMode()
	{
		System.out.printf("Would you like to choose your own set of letters or play from preset games?%n");
		System.out.printf("1. Choose your own letters!%n2. Play from presets!%n");
		
		int num = getInputInteger(1, 2);
		
		if(num==1)
			usePresets = false;
		else
			usePresets = true;
	}	
	
	//Transfers game info text from files to Strings
	public void setIntroStrings() throws FileNotFoundException
	{
		introFiles = new Scanner(new File("C:/Users/Robert J/VS Code/JavaWorks/rules.txt"));
		rules = "";
		
		while(introFiles.hasNextLine())
			rules+=introFiles.nextLine()+"\n";
		
		introFiles = new Scanner(new File("C:/Users/Robert J/VS Code/JavaWorks/commands.txt"));
		commands = "";
		
		while(introFiles.hasNextLine())
			commands+=introFiles.nextLine()+"\n";
	}
	
	//Selects a random set of 7 letters for the game
	public void setLetters()
	{
		int index = (int)(Math.random()*games.size());
		String str= games.remove(index);
		
		for(int i=0;i<str.length();i++)
			letters.add(str.charAt(i));
	}
	
	//When and "!" is entered, this method will run the corresponding command
	public void getMenuOption(String str)
	{
		ArrayList<String> options = new ArrayList<String>(Arrays.asList("!info","!shuffle","!list","!rank","!quit"));
		
		if(options.indexOf(str)<0)
		{
			System.out.println("I'm sorry but this is not a menu option.\n");
			return;
		}
			
		int num = options.indexOf(str);
		
		if(num==0)
			menuInfo();
		else if(num==1)
			menuShuffle();
		else if(num==2)
			menuList();
		else if(num==3)
			menuRank();
		else if(num==4)
			menuQuit();
	}
	
	//Prints the rules and commands
	public void menuInfo() //prints the rules
	{
		System.out.println(rules);
		System.out.println(commands);
		// add rules
		System.out.println("Please press enter to resume the game.");
		sc.nextLine();
	}

	//shuffles order of letters
	public void menuShuffle() 
	{
		//must-use letter will be the first value in the ArrayList
		for(int i=1; i<letters.size(); i++)
		{
			int n = 1 + 6*(int)Math.random();
			char temp = letters.get(i);
			
			letters.set(i, letters.get(n));
			letters.set(n, temp);
		}
		
	}
	
	
	//prints the list of already found words
	public void menuList()
	{
		if(alreadyGuessed.size()==0)
		{
			System.out.println("You haven't found any words yet! Keep Looking!\n");
			return;
		}
		
		System.out.println("You have found the following words:");
		Collections.sort(alreadyGuessed);
		
		for(String str : alreadyGuessed)
			System.out.println(str);
		
		System.out.println("\nPlease press enter to resume the game.");
		sc.nextLine();
	}

	//prints all rankings and needed points
	public void menuRank()
	{
		
		for(int i=0; i<ranks.length; i++)
		{
			double n = maxScore*rankRatios[i];
			
			System.out.print(ranks[i]);
			
			if(n%1!=0)
				n = (int)n + 1;
			
			System.out.println( "(" + (int)n + ")" );
		}
		
		// System.out.println("\nPlease press enter to resume the game.");
		// sc.nextLine();
		
	}
		
	//quits the game and prompts the user to play again.
	public void menuQuit()
	{
		System.out.printf("Would you like to see the answer list?%n1. Yes%n2. No%n");

		int num = getInputInteger(1,2);
		
		if(num == 1)
			printPossibleWords(); //prints Possible word list with stars next to found words and Pangram labeled
	
		System.out.printf("Would you like to play again?%n1. Yes%n2. No%n");
		num = getInputInteger(1,2);
		
		if(num==1)
		{
			resetInstanceVariables();
			// setLetters();
			System.out.println(rules);
			System.out.println(commands);
			updateGameMode();
			
			if(!usePresets)
				enterLetters();
			else
			{
				setLetters();
				fillPossibleWordList();
			}
		}
		else
		{
			System.out.println("Goodbye! Play again soon!");
			isRunning = false;
		} 
		
	}
	
	//Prints out the users current rank
	public void printCurrentRank() 
	{
		double n = score/(double)maxScore;
		boolean printed = false;
		
		for(int i=0; i<ranks.length-1; i++)
		{
			if(n>=rankRatios[i] && n<rankRatios[i+1])
			{
				System.out.println("Rank: "+ ranks[i]);
				printed = true;
				break;
			}
		}
		if(!printed)
			System.out.println("Rank: Queen Bee");
		
		
		
		// if(isPangram(str))
		// 	score += 7;
		// score += str.length()-3;
		
	}
	
	
	//Prints the users score
	public void printScore()
	{
		System.out.println("Score: " + score);
	}
		
	//Prints the 7 current letters in a hexagonal shape
	public void printLetters()
	{
		String s6 = "    ", s3 = "  ";
		
		System.out.println("\n" + s3 + (char)(letters.get(1)-32) + s6 + (char)(letters.get(2)-32) + "\n");
		System.out.println((char)(letters.get(3)-32) + s6 + (char)(letters.get(0)-32) + s6 + (char)(letters.get(4)-32) + "\n");
		System.out.println(s3 + (char)(letters.get(5)-32) + s6 + (char)(letters.get(6)-32) + "\n");
	}
	
	//updates score 
	public void makeGuess(String str) 
	{
		//adds to alreadyGuessed list.
		if(alreadyGuessed.contains(str))
		{
			System.out.println("You have already guessed this word.\n");
			return;
		}
		
		if(possibleWordList.contains(str))
		{
			int temp = score;
			alreadyGuessed.add(str);
			
			if(isPangram(str))
				score += 7;
			score += str.length()-3;
			System.out.printf("+ %d points%n%n",score-temp);
		}
	}
	
	//checks if the input word is a pangram
	public boolean isPangram(String str)
	{
		boolean pangram = true;
		
		for(int i=0;i<letters.size();i++)
			if(str.indexOf(letters.get(i))<0)
				pangram = false;
		
		for(int i=0;i<str.length();i++)
			if(!letters.contains(str.charAt(i)))
				pangram = false;

		return pangram;
	
	}
	
	//Prints the instance variable possibleWordList
	public void printPossibleWords()
	{
		System.out.println("These were all the possible words:");
		
		for(String str : possibleWordList)
		{
			if(alreadyGuessed.contains(str))
				System.out.print("* ");
			System.out.print(str);
			
			if(isPangram(str))
				System.out.print("\t\tPANGRAM");
			System.out.println();
		}
		System.out.println();
	}
	
	//ask the user if they want to play
	public void confirmGameStart()
	{
		System.out.printf("Would you like to play? %n1. Yes%n2. No%n");
		int num = getInputInteger(1,2);
		
		if(num==1)
			isRunning = true;
		else
		{
			System.out.println("Come play later!");
			isRunning = false;
		}
		
	}
	
	//checks if a word contains the root and proper letters
	public boolean isWord(String str)
	{
		boolean correctLetters = true;
		boolean hasRoot = str.indexOf(letters.get(0)) > -1;
		
		for(int i=0; i<str.length(); i++)
			if(letters.indexOf(str.charAt(i))<0)
				correctLetters = false;
		
		if(str.length()==0)
		{
			System.out.println("You forgot to enter a word!\n");
			return false;
		}
		
		if(correctLetters && hasRoot && possibleWordList.contains(str) && alreadyGuessed.contains(str)==false)
			System.out.println("You found a word!");
		else if(!correctLetters)
			System.out.println("I'm sorry, but this word contains letters or characters that were not provided.\n");
		else if(!hasRoot) 
			System.out.printf("I'm sorry, but this word does not use the center letter. (%s)%n%n",(char)(letters.get(0)-32)+"");
		else if(!possibleWordList.contains(str))
			System.out.println("I'm sorry, but this is not an accepted word.\n");
		
		return correctLetters && hasRoot;
	}
	
	//fill the instance variable possibleWordList
	public void fillPossibleWordList()
	{
		for(String str : dictionary)
		{
			boolean correctLetters = true;
			boolean hasRoot = str.indexOf(letters.get(0)) > -1;
		
			for(int i=0; i<str.length(); i++)
				if(letters.indexOf(str.charAt(i))<0)
					correctLetters = false;
			
			if(correctLetters&&hasRoot&&possibleWordList.contains(str)==false)
			{
				possibleWordList.add(str);
				//maxScore+= str.length()-3;
				if(isPangram(str))
					maxScore+=7;
				maxScore += str.length()-3;
			}
		}
		Collections.sort(possibleWordList);
	}
	
	//resets some of the instance variables
	public void resetInstanceVariables()
	{
		possibleWordList = new ArrayList<String>();
		score = 0;
		maxScore = 0;
		alreadyGuessed = new ArrayList<String>();
		letters = new ArrayList<Character>();

		possibleWordList.clear();
	}
	
	//Prompts the user to enter an integer between the given parameter
	public int getInputInteger(int lowerLimit, int upperLimit)
	{
		// System.out.printf("Please enter a numerical value between %d and %d inclusive.%n",lowerLimit,upperLimit);
		boolean keepRunning = true;
		
		while(keepRunning)
		{
			try
			{
				int num = Integer.parseInt(sc.nextLine());
				
				if(num>=lowerLimit && num<=upperLimit)
				{
					System.out.println();
					return num;
				}
				System.out.printf("%nI'm sorry but the input data is not in the correct format.%n"
							+ "Please enter a numerical value between %d and %d inclusive.%n%n",lowerLimit,upperLimit);
			}
			catch(Exception e)
			{
				System.out.printf("%nI'm sorry but the input data is not in the correct format.%n"
							+ "Please enter a numerical value between %d and %d inclusive.%n%n",lowerLimit,upperLimit);
			}
		}
		
		return -1;	 
	
	}
}