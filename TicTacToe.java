package JavaWorks.Projects;

import java.util.*;
public class TicTacToe {
	
	private static String[][] gameBoard;
	public static int[][] board;
	
	//private boolean p1Turn;
	private static boolean isRunning;
	
	private static Scanner sc;
	
	static GameState currentState;
	static AImode difficulty;
	
	
	public TicTacToe() {
		
		//to initiate a game, run the line "TicTactToe = new TicTacToe();" in a different class.
		sc = new Scanner(System.in);
		//p1Turn = true;
		gameBoard = new String[23][24];
		board = new int[3][3];
		isRunning = true;
		fillBoard(gameBoard);
		
		System.out.println("Do you want to play a Person or an AI\n"
				+ "Type \"Person\" to play a person.\n"
				+ "Type \"AI\" to face an artificial intelligence.");
		boolean correctFormat = false;
		while(!correctFormat) {
			String str = sc.nextLine().toLowerCase();
			if(str.equals("person")) {
				currentState = GameState.PERSON;
				correctFormat = true;
			} else if(str.equals("ai")) {
				currentState = GameState.AI;
				correctFormat = true;
			}else {
				System.out.println("I'm sorry the input data was not in the correct format.\n"
						+ "Please type either \"Person\" or \"AI\".");
			}
		}
		
		switch(currentState) {
			case PERSON:
				while(isRunning) {
					playerOne();
					if(isRunning)
						playerTwo();
				}
				break;
			case AI:
				System.out.println("Choose your difficulty.\n"
						+ "Type \"easy\" or \"hard\" to select your difficulty!");
				correctFormat = false;
				while(!correctFormat) {
					String str = sc.nextLine().toLowerCase();
					if(str.equals("easy")) {
						difficulty = AImode.EASY;
						correctFormat = true;
					} else if(str.equals("hard")) {
						difficulty = AImode.HARD;
						correctFormat = true;
					}else {
						System.out.println("I'm sorry the input data was not in the correct format.\n"
								+ "Please type either \"easy\" or \"hard\".");
					}
				}
				while(isRunning) {
					playerOne();
					if(isRunning) {
						switch(difficulty) {
							case EASY:
								aiMoveEasy();
								break;
							case HARD:
								aiMoveHard();
								break;
						}
					}
				}
				break;
		}
		
	}
	enum GameState{
		PERSON,AI
	}
	enum AImode{
		EASY,HARD
	}
	
	public static void aiMoveHard() {
		boolean madeMove = false;
		//this for-loop covers all possible "close calls" wins
		for(int i=0;i<3;i++) {
			if(board[i][0]==board[i][1]&&board[i][1]==2&&board[i][2]==0) {
				char letter = 'a';
				if(i==1) letter ='b';
				if(i==2) letter ='c';
				drawO(letter,3,gameBoard);
				madeMove = true;
				break;
			}
			if(board[i][1]==board[i][2]&&board[i][2]==2&&board[i][0]==0) {
				char letter = 'a';
				if(i==1) letter ='b';
				if(i==2) letter ='c';
				drawO(letter,1,gameBoard);
				madeMove = true;
				break;
			}
			if(board[0][i]==board[1][i]&&board[1][i]==2&&board[2][i]==0) {
				drawO('c',i+1,gameBoard);
				madeMove = true;
				break;
			}
			if(board[1][i]==board[2][i]&&board[2][i]==2&&board[0][i]==0) {
				drawO('a',i+1,gameBoard);
				madeMove = true;
				break;
			}
			if(board[i][0]==board[i][2]&&board[i][2]==2&&board[i][1]==0) {
				char letter = 'a';
				if(i==1) letter ='b';
				if(i==2) letter ='c';
				drawO(letter,2,gameBoard);
				madeMove = true;
				break;
			}
			if(board[0][i]==board[2][i]&&board[2][i]==2&&board[1][i]==0) {
				drawO('b',i+1,gameBoard);
				madeMove = true;
				break;
			}
		}
		//diagonal checks
		if(!madeMove) {
			if(board[0][0]==board[2][2]&&board[2][2]==2&&board[1][1]==0) {
				drawO('b',2,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[1][1]==board[2][2]&&board[2][2]==2&&board[0][0]==0) {
				drawO('a',1,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[0][0]==board[1][1]&&board[1][1]==2&&board[2][2]==0) {
				drawO('c',3,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[0][2]==board[2][0]&&board[2][0]==2&&board[1][1]==0) {
				drawO('b',2,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[0][2]==board[1][1]&&board[1][1]==2&&board[2][0]==0) {
				drawO('c',1,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[1][1]==board[2][0]&&board[2][0]==2&&board[0][2]==0) {
				drawO('a',3,gameBoard);
				madeMove = true;
			}
		}
		//this for-loop covers all possible "close calls" losses
		if(!madeMove) {
			for(int i=0;i<3;i++) {
				if(board[i][0]==board[i][1]&&board[i][1]==1&&board[i][2]==0) {
					char letter = 'a';
					if(i==1) letter ='b';
					if(i==2) letter ='c';
					drawO(letter,3,gameBoard);
					madeMove = true;
					break;
				}
				if(board[i][1]==board[i][2]&&board[i][2]==1&&board[i][0]==0) {
					char letter = 'a';
					if(i==1) letter ='b';
					if(i==2) letter ='c';
					drawO(letter,1,gameBoard);
					madeMove = true;
					break;
				}
				if(board[0][i]==board[1][i]&&board[1][i]==1&&board[2][i]==0) {
					drawO('c',i+1,gameBoard);
					madeMove = true;
					break;
				}
				if(board[1][i]==board[2][i]&&board[2][i]==1&&board[0][i]==0) {
					drawO('a',i+1,gameBoard);
					madeMove = true;
					break;
				}
				if(board[i][0]==board[i][2]&&board[i][2]==1&&board[i][1]==0) {
					char letter = 'a';
					if(i==1) letter ='b';
					if(i==2) letter ='c';
					drawO(letter,2,gameBoard);
					madeMove = true;
					break;
				}
				if(board[0][i]==board[2][i]&&board[2][i]==1&&board[1][i]==0) {
					drawO('b',i+1,gameBoard);
					madeMove = true;
					break;
				}
			}
		}
		//diagonal checks
				if(!madeMove) {
					if(board[0][0]==board[2][2]&&board[2][2]==1&&board[1][1]==0) {
						drawO('b',2,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[1][1]==board[2][2]&&board[2][2]==1&&board[0][0]==0) {
						drawO('a',1,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[0][0]==board[1][1]&&board[1][1]==1&&board[2][2]==0) {
						drawO('c',3,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[0][2]==board[2][0]&&board[2][0]==1&&board[1][1]==0) {
						drawO('b',2,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[0][2]==board[1][1]&&board[1][1]==1&&board[2][0]==0) {
						drawO('c',1,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[1][1]==board[2][0]&&board[2][0]==1&&board[0][2]==0) {
						drawO('a',3,gameBoard);
						madeMove = true;
					}
				}
		if(!madeMove) {
			while(!madeMove) {
				int num1= (int)(Math.random()*3+1)-1;
				int num2= (int)(Math.random()*3+1)-1;
				if(cornerAvailable()) {
					if(isCorner(num1,num2)) {
						if(isOpen(num1,num2)) {
							char letter = 'a';
							if(num1==1) letter ='b';
							if(num1==2) letter ='c';
							drawO(letter,num2+1,gameBoard);
							madeMove = true;
						}
					}
				}else {
					if(isOpen(num1,num2)) {
						char letter = 'a';
						if(num1==1) letter ='b';
						if(num1==2) letter ='c';
						drawO(letter,num2+1,gameBoard);
						madeMove = true;
					}
				}
			}
		}
		String out = "";
		for(int i=0; i<gameBoard.length;i++) {
			for(int j=0;j<gameBoard[0].length;j++) {
				out+= gameBoard[i][j];
			}
			out+="\n";
		}
		System.out.println(out);
		System.out.println("\nThe AI has made its move!");
		isOverCheck();
	}
	public static void aiMoveEasy() {
		boolean madeMove = false;
		//this for-loop covers all possible "close calls" wins
		for(int i=0;i<3;i++) {
			if(board[i][0]==board[i][1]&&board[i][1]==2&&board[i][2]==0) {
				char letter = 'a';
				if(i==1) letter ='b';
				if(i==2) letter ='c';
				drawO(letter,3,gameBoard);
				madeMove = true;
				break;
			}
			if(board[i][1]==board[i][2]&&board[i][2]==2&&board[i][0]==0) {
				char letter = 'a';
				if(i==1) letter ='b';
				if(i==2) letter ='c';
				drawO(letter,1,gameBoard);
				madeMove = true;
				break;
			}
			if(board[0][i]==board[1][i]&&board[1][i]==2&&board[2][i]==0) {
				drawO('c',i+1,gameBoard);
				madeMove = true;
				break;
			}
			if(board[1][i]==board[2][i]&&board[2][i]==2&&board[0][i]==0) {
				drawO('a',i+1,gameBoard);
				madeMove = true;
				break;
			}
			if(board[i][0]==board[i][2]&&board[i][2]==2&&board[i][1]==0) {
				char letter = 'a';
				if(i==1) letter ='b';
				if(i==2) letter ='c';
				drawO(letter,2,gameBoard);
				madeMove = true;
				break;
			}
			if(board[0][i]==board[2][i]&&board[2][i]==2&&board[1][i]==0) {
				drawO('b',i+1,gameBoard);
				madeMove = true;
				break;
			}
		}
		//diagonal checks
		if(!madeMove) {
			if(board[0][0]==board[2][2]&&board[2][2]==2&&board[1][1]==0) {
				drawO('b',2,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[1][1]==board[2][2]&&board[2][2]==2&&board[0][0]==0) {
				drawO('a',1,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[0][0]==board[1][1]&&board[1][1]==2&&board[2][2]==0) {
				drawO('c',3,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[0][2]==board[2][0]&&board[2][0]==2&&board[1][1]==0) {
				drawO('b',2,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[0][2]==board[1][1]&&board[1][1]==2&&board[2][0]==0) {
				drawO('c',1,gameBoard);
				madeMove = true;
			}
		}
		if(!madeMove) {
			if(board[1][1]==board[2][0]&&board[2][0]==2&&board[0][2]==0) {
				drawO('a',3,gameBoard);
				madeMove = true;
			}
		}
		//this for-loop covers all possible "close calls" losses
		if(!madeMove) {
			for(int i=0;i<3;i++) {
				if(board[i][0]==board[i][1]&&board[i][1]==1&&board[i][2]==0) {
					char letter = 'a';
					if(i==1) letter ='b';
					if(i==2) letter ='c';
					drawO(letter,3,gameBoard);
					madeMove = true;
					break;
				}
				if(board[i][1]==board[i][2]&&board[i][2]==1&&board[i][0]==0) {
					char letter = 'a';
					if(i==1) letter ='b';
					if(i==2) letter ='c';
					drawO(letter,1,gameBoard);
					madeMove = true;
					break;
				}
				if(board[0][i]==board[1][i]&&board[1][i]==1&&board[2][i]==0) {
					drawO('c',i+1,gameBoard);
					madeMove = true;
					break;
				}
				if(board[1][i]==board[2][i]&&board[2][i]==1&&board[0][i]==0) {
					drawO('a',i+1,gameBoard);
					madeMove = true;
					break;
				}
				if(board[i][0]==board[i][2]&&board[i][2]==1&&board[i][1]==0) {
					char letter = 'a';
					if(i==1) letter ='b';
					if(i==2) letter ='c';
					drawO(letter,2,gameBoard);
					madeMove = true;
					break;
				}
				if(board[0][i]==board[2][i]&&board[2][i]==1&&board[1][i]==0) {
					drawO('b',i+1,gameBoard);
					madeMove = true;
					break;
				}
			}
		}
		//diagonal checks
				if(!madeMove) {
					if(board[0][0]==board[2][2]&&board[2][2]==1&&board[1][1]==0) {
						drawO('b',2,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[1][1]==board[2][2]&&board[2][2]==1&&board[0][0]==0) {
						drawO('a',1,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[0][0]==board[1][1]&&board[1][1]==1&&board[2][2]==0) {
						drawO('c',3,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[0][2]==board[2][0]&&board[2][0]==1&&board[1][1]==0) {
						drawO('b',2,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[0][2]==board[1][1]&&board[1][1]==1&&board[2][0]==0) {
						drawO('c',1,gameBoard);
						madeMove = true;
					}
				}
				if(!madeMove) {
					if(board[1][1]==board[2][0]&&board[2][0]==1&&board[0][2]==0) {
						drawO('a',3,gameBoard);
						madeMove = true;
					}
				}
		if(!madeMove) {
			while(!madeMove) {
				int num1= (int)(Math.random()*3+1)-1;
				int num2= (int)(Math.random()*3+1)-1;
				if(isOpen(num1,num2)) {
					char letter = 'a';
					if(num1==1) letter ='b';
					if(num1==2) letter ='c';
					drawO(letter,num2+1,gameBoard);
					madeMove = true;
				}
			}
		}
		String out = "";
		for(int i=0; i<gameBoard.length;i++) {
			for(int j=0;j<gameBoard[0].length;j++) {
				out+= gameBoard[i][j];
			}
			out+="\n";
		}
		System.out.println(out);
		System.out.println("\nThe AI has made its move!");
		isOverCheck();
	}
	public static boolean isCorner(int n1, int n2) {
		boolean out = false;
		if(n1==0&&n2==0) out = true;
		if(n1==0&&n2==2) out = true;
		if(n1==2&&n2==0) out = true;
		if(n1==1&&n2==1) out = true;
		if(n1==2&&n2==2) out = true;
		return out;
	}
	public static boolean cornerAvailable() {
		boolean out = false;
		if(board[0][0]==0) out = true;
		if(board[2][0]==0) out = true;
		if(board[0][2]==0) out = true;
		if(board[1][1]==0) out = true;
		if(board[2][2]==0) out = true;
		return out;
	}
	
	public static void playerOne() {
		boolean correctFormat = false;
		String response = "";
		while(!correctFormat) {
			playerOneText();
			String str = sc.nextLine();
			String abc = "abc";
			String numbers = "123";
			if(str.length()>1&&abc.indexOf(str.charAt(0))>-1&&numbers.indexOf(str.charAt(1))>-1&&board[str.charAt(0)-97][Character.getNumericValue(str.charAt(1))-1]==0) {
				correctFormat = true;
				response = str;
			}else {
				System.out.println("I'm sorry the input data was not in the correct format."
						+ "\nPlease try again. Make sure you are typing only the letter "
						+ "and number \nwithout any space!\n");
			}
		}
		drawX(response.charAt(0),Character.getNumericValue(response.charAt(1)),gameBoard);
		String out = "";
		for(int i=0; i<gameBoard.length;i++) {
			for(int j=0;j<gameBoard[0].length;j++) {
				out+= gameBoard[i][j];
			}
			out+="\n";
		}
		System.out.println(out);
		isOverCheck();
	}
	public static void playerTwo() {
		boolean correctFormat = false;
		String response = "";
		while(!correctFormat) {
			playerTwoText();
			String str = sc.nextLine();
			String abc = "abc";
			String numbers = "123";
			if(str.length()>1&&abc.indexOf(str.charAt(0))>-1&&numbers.indexOf(str.charAt(1))>-1) {
				correctFormat = true;
				response = str;
			}else {
				System.out.println("I'm sorry the input data was not in the correct format."
						+ "\nPlease try again. Make sure you are typing only the letter "
						+ "and number without any space!");
			}
		}
		drawO(response.charAt(0),Character.getNumericValue(response.charAt(1)),gameBoard);
		String out = "";
		for(int i=0; i<gameBoard.length;i++) {
			for(int j=0;j<gameBoard[0].length;j++) {
				out+= gameBoard[i][j];
			}
			out+="\n";
		}
		System.out.println(out);
		isOverCheck();
	}
	public static void playerOneText() {
		System.out.println("It's player one's turn! To make your turn type the "
				+ "location of the row and \ncolumn you want to place an \"O\" in.");
		System.out.println("For example, \"a1\" is the top left space and "
				+ "\"c3\" is the bottom right space.");
	}
	public static void playerTwoText() {
		System.out.println("It's player two's turn! To make your turn type the "
				+ "location of the row and \ncolumn you want to place an \"X\" in.");
		System.out.println("For example, \"a1\" is the top left space and "
				+ "\"c3\" is the bottom right space.");
	}
	public static boolean isOpen(int num1,int num2) {
		return board[num1][num2]==0;
	}
	
	public static void isOverCheck() {
		switch(currentState) {
			case PERSON:
				boolean isTie = true;
				for(int i=0;i<3;i++) {
					if(board[i][0]==board[i][1]&&board[i][1]==board[i][2]&&board[i][0]!=0) {
						isRunning = false;
						System.out.println(board[i][0]==1 ? "Congradulations Player One! You have won!":"Congradulations Player Two! You have won!");
					}
					if(board[0][i]==board[1][i]&&board[1][i]==board[2][i]&&board[0][i]!=0) {
						isRunning = false;
						System.out.println(board[0][i]==1 ? "Congradulations Player One! You have won!":"Congradulations Player Two! You have won!");
					}
					for(int j=0;j<3;j++) {
						if(board[i][j]==0)
							isTie = false;
					}
				}
				if(board[0][0]==board[1][1]&&board[1][1]==board[2][2]&&board[0][0]!=0) {
					isRunning = false;
					System.out.println(board[0][0]==1 ? "Congradulations Player One! You have won!":"Congradulations Player Two! You have won!");
				}
				if(board[0][2]==board[1][1]&&board[1][1]==board[2][0]&&board[1][1]!=0) {
					isRunning = false;
					System.out.println(board[2][0]==1 ? "Congradulations Player One! You have won!":"Congradulations Player Two! You have won!");
				}
				if(isTie) {
					isRunning = false;
					System.out.println("The game has ended in a tie!");
				}
				break;
			case AI:
				boolean istie = true;
				for(int i=0;i<3;i++) {
					if(board[i][0]==board[i][1]&&board[i][1]==board[i][2]&&board[i][0]!=0) {
						isRunning = false;
						System.out.println(board[i][0]==1 ? "Congradulations Player One! You have won!":"Oh No! The AI has defeated you.\nBetter luck next time!");
					}
					if(board[0][i]==board[1][i]&&board[1][i]==board[2][i]&&board[0][i]!=0) {
						isRunning = false;
						System.out.println(board[0][i]==1 ? "Congradulations Player One! You have won!":"Oh No! The AI has defeated you.\nBetter luck next time!");
					}
					for(int j=0;j<3;j++) {
						if(board[i][j]==0)
							istie = false;
					}
				}
				if(board[0][0]==board[1][1]&&board[1][1]==board[2][2]&&board[0][0]!=0) {
					isRunning = false;
					System.out.println(board[0][0]==1 ? "Congradulations Player One! You have won!":"Oh No! The AI has defeated you.\nBetter luck next time!");
				}
				if(board[0][2]==board[1][1]&&board[1][1]==board[2][0]&&board[1][1]!=0) {
					isRunning = false;
					System.out.println(board[2][0]==1 ? "Congradulations Player One! You have won!":"Oh No! The AI has defeated you.\\nBetter luck next time!");
				}
				if(istie) {
					isRunning = false;
					System.out.println("The game has ended in a tie!");
				}
				break;
		}
	}
	public static void fillBoard(String[][] gB){
		for(String[] arr: gB) {
			for(int i=0;i<arr.length;i++) {
				arr[i]=" ";
			}
		}
		for(int i=0;i<gB.length;i++) {
			gB[i][7]="|";
		}
		for(int i=0;i<gB.length;i++) {
			gB[i][15]="|";
		}
		for(int i=0;i<gB[0].length;i++) {
			gB[7][i] = "-";
		}
		for(int i=0;i<gB[0].length;i++) {
			gB[15][i] = "-";
		}
	}
	public static void drawX(char r, int c, String[][] gameBoard) {
		int row = 0;
		if(r=='b') row = 1;
		if(r=='c') row = 2;
		int column = --c;
		board[row][column]=1;
		for(int i=0;i<7;i++) {
			gameBoard[row*8+i][column*8+i]= "\\";
		}
		for(int i=0;i<7;i++) {
			gameBoard[row*8+i][column*8+6-i]= "/";
		}
		gameBoard[row*8+3][column*8+6-3]="X";
	}
	public static void drawO(char r, int c, String[][] gameBoard) {
		int row = 0;
		if(r=='b') row = 1;
		if(r=='c') row = 2;
		int column = --c;
		board[row][column]=2;
		for(int i=1;i<6;i++) {
			gameBoard[row*8][column*8+i]= "-";
		}
		for(int i=1;i<6;i++) {
			gameBoard[row*8+i][column*8+6]= "|";
		}
		for(int i=1;i<6;i++) {
			gameBoard[row*8+6][column*8+i]= "-";
		}
		for(int i=1;i<6;i++) {
			gameBoard[row*8+i][column*8]= "|";
		}
		
	
	}
	public String toString() {
		String out = "";
		for(int i=0; i<gameBoard.length;i++) {
			for(int j=0;j<gameBoard[0].length;j++) {
				out+= gameBoard[i][j];
			}
			out+="\n";
		}
		return out;
	}
}
