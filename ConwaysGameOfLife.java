package JavaWorks.Projects;

import java.util.*;
public class ConwaysGameOfLife {
	private String[][] grid;
	private String[][] gridCopy;
	private int rows;
	private int cols;
	private int generations;
	private int generationsPerRefresh;
	private int randomizationKey;
	private int timeDelay;
	
	private static boolean isRunning;
	private Scanner sc;
	
	static GameStates gametype;
	static StillLifes stillLife;
	static Oscillators oscillator;
	
	boolean viewStillLife;
	
	public ConwaysGameOfLife() throws InterruptedException {
		sc = new Scanner(System.in);
		confirmGameStart();
		while(isRunning) {
			setGameState();
			switch(gametype) {
				case INPUT:
					setRowsAndCols(25,50);
					askGenerations();
					askGenerationsPerRefresh();
					fillGrid(grid);
					askRandomizationKey();
					setStartingColonist();
					updateTimeDelay();
					int numGenerations = generations;
					while(numGenerations-->0) {
						updateBoard();
						if((generations-numGenerations-1)%generationsPerRefresh==0) {
							Thread.sleep(timeDelay);
							printGrid(grid);
						}
						
					}
					updateIsRunning();
					break;
				case PRESET:
					askPreset();
					if(viewStillLife) {
						changeStillLife();
						printStillLife();
					}else {
						changeOscillator();
						printOscillator();
					}
					updateIsRunning();
					break;
			}
		}
	}
	enum GameStates{
		INPUT, PRESET;
	}
	enum StillLifes{
		Block, Beehive, Loaf, Boat, Tub;
	}
	enum Oscillators{
		Blinker, Toad, Beacon, Pulsar, PentDecathlon; 
	}
	public void printOscillator() throws InterruptedException {
		switch(oscillator) {
		case Blinker:
			setRowsAndCols(5,5);
			fillGrid(grid);
			generations = 20;
			generationsPerRefresh = 1;
			updateTimeDelay();
			for(int i=1;i<4;i++) {
				grid[2][i]="x";
			}
			int numGenerations = generations;
			while(numGenerations-->0) {
				updateBoard();
				if((generations-numGenerations-1)%generationsPerRefresh==0) {
					Thread.sleep(timeDelay);
					printGrid(grid);
				}
				
			}
			break;
		case Toad:
			setRowsAndCols(6,6);
			fillGrid(grid);
			generations = 10;
			generationsPerRefresh = 1;
			updateTimeDelay();
			for (int i = 2; i <= 4; i++) {
 				grid[2][i] = "x";
				}
				for (int j = 1; j <= 3; j++) {
 				grid[3][j] = "x";
				}
				numGenerations = generations;
			while(numGenerations-->0) {
				updateBoard();
				if((generations-numGenerations-1)%generationsPerRefresh==0) {
					Thread.sleep(timeDelay);
					printGrid(grid);
				}
				
			}
			break;
		case Beacon:
			setRowsAndCols(8,8);
			fillGrid(grid);
			generations = 20;
			generationsPerRefresh = 1;
			updateTimeDelay();
			for(int i=2;i<4;i++) {
				for(int j=2;j<4;j++) {
					grid[i][j]="x";
				}
			}
			for(int i=4;i<6;i++) {
				for(int j=4;j<6;j++) {
					grid[i][j]="x";
				}
			}
			numGenerations = generations;
			while(numGenerations-->0) {
				updateBoard();
				if((generations-numGenerations-1)%generationsPerRefresh==0) {
					Thread.sleep(timeDelay);
					printGrid(grid);
				}
				
			}
			break;
		case Pulsar:
			setRowsAndCols(17,17);
			generations = 40;
			generationsPerRefresh = 1;
			updateTimeDelay();
			fillGrid(grid);
			for(int i=0;i<3;i++) {
				grid[4+i][2]="x";
				grid[10+i][2]="x";
				grid[4+i][7]="x";
				grid[10+i][7]="x";
				grid[4+i][14]="x";
				grid[10+i][14]="x";
				grid[4+i][9]="x";
				grid[10+i][9]="x";
				grid[2][4+i]="x";
				grid[2][10+i]="x";
				grid[7][4+i]="x";
				grid[7][10+i]="x";
				grid[14][4+i]="x";
				grid[14][10+i]="x";
				grid[9][4+i]="x";
				grid[9][10+i]="x";
			}
			numGenerations = generations;
			while(numGenerations-->0) {
				updateBoard();
				if((generations-numGenerations-1)%generationsPerRefresh==0) {
					Thread.sleep(timeDelay);
					printGrid(grid);
				}
				
			}
			break;
		case PentDecathlon:
			setRowsAndCols(18,11);
			fillGrid(grid);
			generations = 30;
				generationsPerRefresh = 1;
				updateTimeDelay();
			for (int i = 6; i <= 11; i++) {
 				grid[i][3] = "x";
 				grid[i][7] = "x";
			}
			  grid[4][5] = "x";
				grid[5][4] = "x";
				grid[5][6] = "x";
				grid[12][4] = "x";
				grid[13][5] = "x";
				grid[12][6] = "x";
			numGenerations = generations;
			while(numGenerations-->0) {
				updateBoard();
				if((generations-numGenerations-1)%generationsPerRefresh==0) {
					Thread.sleep(timeDelay);
					printGrid(grid);
				}
				
			}
			break;
	}
	}
	public void printStillLife() {
		switch(stillLife) {
		case Block:
			setRowsAndCols(6,6);
			fillGrid(grid);
			for(int i=2;i<4;i++) {
				for(int j=2;j<4;j++) {
					grid[i][j] = "x";
				}
			}
			printGrid(grid);
			break;
		case Beehive:
			setRowsAndCols(7,8);
			fillGrid(grid);
			for(int i=3;i<5;i++) {
				grid[2][i]="x";
				grid[4][i]="x";
			}
			grid[3][2]="x";
			grid[3][5]="x";
			printGrid(grid);
			break;
		case Loaf:
			setRowsAndCols(8,8);
			fillGrid(grid);
			for(int i=3;i<5;i++) {
				grid[2][i]="x";
				grid[i][5]="x";
			}
			int tempRow = 3;
			int tempCol = 2;
			for(int i=0;i<3;i++) {
				grid[tempRow][tempCol]="x";
				tempRow++;
				tempCol++;
			}
			printGrid(grid);
			break;
		case Boat:
			setRowsAndCols(7,7);
			fillGrid(grid);
			grid[2][2] = "x";
			grid[2][3] = "x";
			grid[3][2] = "x";
			grid[3][4] = "x";
			grid[4][3] = "x";
			printGrid(grid);
			break;
		case Tub:
			setRowsAndCols(7,7);
			fillGrid(grid);
			grid[2][3] = "x";
			grid[3][2] = "x";
			grid[3][4] = "x";
			grid[4][3] = "x";
			printGrid(grid);
			break;
	}
	}
	/*This method adjust the time delay so that items will print slower when 
	 * there are fewer generations that need to be printed.*/
	public void updateTimeDelay() {
		timeDelay = 6000/(generations/generationsPerRefresh);
	}
	/*This method instantiates 3 instance variables*/
	public void setRowsAndCols(int row, int col) {
		rows = row;
		cols = col;
		grid = new String[rows][cols];
	}
	/*This method applies the rules of Conway's Game of Life.
	 * Cell are created, die, or survive.*/
	public void updateBoard() {
		gridCopy = new String[rows][cols];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				gridCopy[i][j] = grid[i][j];
			}
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				int aliveCount = 0;
				for(int k=Math.max(0, i-1);k<=Math.min(i+1, rows-1);k++) {
					for(int l = Math.max(0, j-1);l<=Math.min(j+1, cols-1);l++) {
						if(grid[k][l].equals("x"))
							aliveCount++;
					}
				}
				if(grid[i][j].equals("x"))
					aliveCount--;
				if(aliveCount<=1) {
					gridCopy[i][j]=" ";
				}else if(aliveCount>=4) {
					gridCopy[i][j] = " ";
				}else if(aliveCount==3)
					gridCopy[i][j] = "x";
					
			}
		}
		grid = gridCopy;
		
	}
	/*This method user the randomizationKey to randomly assign positions to alive.
	 * The results are different for each value of randomizationKey*/
	public void setStartingColonist() {
		for(int i=0; i<rows;i++) {
			for(int j=0;j<cols;j++) {
				int num = (randomizationKey/(i+1)*(j+1))%50;
				if(num<25) {
					grid[i][j] = "x";
				}
			}
		}
	}
	/*This method ask what kind of preset the use wants to view*/
	public void askPreset() {
		boolean correctFormat = false;
		while(!correctFormat) {
			System.out.printf("Please Select your Preset.%n1. Still Lifes%n2. Oscillators%n");
			String temp = sc.nextLine();
			if(checkInputInteger(1,2,temp)) {
				correctFormat = true;
				int num = Integer.parseInt(temp);
				if(num==1)
					viewStillLife = true;
				else if (num==2)
					viewStillLife = false;
			}
		}
	}
	/*This method takes a four digit user input integer and stores it
	 * in the instance variable "randomizationKey"*/
	public void askRandomizationKey() {
		boolean correctFormat = false;
		while(!correctFormat) {
			System.out.printf("Please enter a 4 digit Randomization Key. (0000-9999).%n");
			String temp = sc.nextLine();
			if(checkInputInteger(0,9999,temp)&&temp.length()==4) {
				randomizationKey = Integer.parseInt(temp)+1234;
				correctFormat = true;
			}
		}
	}
	/*This method changes the value of the instance variable 
	 * "stillLife" so that the correct pattern is shown*/
	public void changeOscillator() {
		boolean correctFormat = false;
		while(!correctFormat) {
			System.out.printf("Choose one of the following options!%n");
			System.out.printf("1. Blinker %n2. Toad%n3. Beacon%n4. Pulsar%n5. PentDecathlon%n");
			String temp = sc.nextLine();
			if(checkInputInteger(1,5,temp)) {
				int num = Integer.parseInt(temp);
				correctFormat = true;
				if(num==1)
					oscillator = Oscillators.Blinker;
				if(num==2)
					oscillator = Oscillators.Toad;
				if(num==3)
					oscillator = Oscillators.Beacon;
				if(num==4)
					oscillator = Oscillators.Pulsar;
				if(num==5)
					oscillator = Oscillators.PentDecathlon;
			}
		}
	}
	/*This method changes the value of the instance variable 
	 * "stillLife" so that the correct pattern is shown*/
	public void changeStillLife() {
		boolean correctFormat = false;
		while(!correctFormat) {
			System.out.printf("Choose one of the following options!%n");
			System.out.printf("1. Block %n2. Beehive%n3. Loaf%n4. Boat%n5. Tub%n");
			String temp = sc.nextLine();
			if(checkInputInteger(1,5,temp)) {
				int num = Integer.parseInt(temp);
				correctFormat = true;
				if(num==1)
					stillLife = StillLifes.Block;
				if(num==2)
					stillLife = StillLifes.Beehive;
				if(num==3)
					stillLife = StillLifes.Loaf;
				if(num==4)
					stillLife = StillLifes.Boat;
				if(num==5)
					stillLife = StillLifes.Tub;
			}
		}
	}
	/*This method confirms whether the user is creating their own pattern 
	 * or looking at a preset pattern.*/
	public void setGameState() {
		boolean correctFormat = false;
		while(!correctFormat) {
			System.out.printf("1. Create your own pattern.%n"
					+ "2. Look at preset patterns.%n");
			String temp = sc.nextLine();			
			if(checkInputInteger(1,2,temp)) {
				int num = Integer.parseInt(temp);
				correctFormat = true;
				if(num==1)
					gametype = GameStates.INPUT;
				if(num==2)
					gametype = GameStates.PRESET;
			}
		}
	}
	public void printGrid(String[][] arr) {
		//Prints the instance variable grid
		for(int i=0;i<arr[0].length+1;i++) {
			System.out.print("--");
		}
		System.out.println();
		for(int i=0; i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++) {
				if(j==0)
					System.out.print("|");
				System.out.print(arr[i][j]+" ");
				if(j==arr[0].length-1)
					System.out.print("|");
			}
			System.out.println();
		}
		for(int i=0;i<arr[0].length+1;i++) {
			System.out.print("--");
		}
		System.out.println();
	}
	/*This method asks the user if they want to start the Program*/
	public void confirmGameStart() {
		boolean correctFormat = false;
		while(!correctFormat) {
			System.out.printf("1. Start Conway's Game of Life Simulation%n"
					+ "2. Quit%n");
			String temp = sc.nextLine();			
			if(checkInputInteger(1,2,temp)) {
				int num = Integer.parseInt(temp);
				correctFormat = true;
				if(num==1)
					isRunning = true;
			}
		}
	}
	/*This method fills the instance variable "grid" with spaces*/
	public void fillGrid(String[][] arr) {
		for(int i=0; i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++) {
				arr[i][j]= " ";
			}
		}
	}
	/*This method ask how many steps will be calculated per screen refresh
	 * 
	 * The instance variable stepsPerRefresh is instantiated.*/
	public void askGenerationsPerRefresh() {
		boolean correctFormat = false;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("Please enter the number of generations per refresh. (1-%d).%n",generations);
			String temp = sc.nextLine();
			if(checkInputInteger(1,generations,temp)) {
				generationsPerRefresh = Integer.parseInt(temp);
				correctFormat = true;
			}
		}
		
	}
	/*This method asks how many steps will occur in the current run.
	 * 
	 * The instance variable "numSteps" is instantiated*/
	public void askGenerations() {
		boolean correctFormat = false;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("Please enter the number of generations for this run. (1-250).%n");
			String temp = sc.nextLine();
			if(checkInputInteger(1,250,temp)) {
				generations = Integer.parseInt(temp);
				correctFormat = true;
			}
		}
	}
	/*Check if the user wants to play again.*/
	public void updateIsRunning() {
		boolean correctFormat = false;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("1. Play Again!%n"
					+ "2. Quit%n%n");
			String temp = sc.nextLine();
			if(checkInputInteger(1,2,temp)) {
				int num = Integer.parseInt(temp);
				if (num==2)
					isRunning = false;
				correctFormat = true;
			}
			
		}
	}
	/*This method has two functions. The first function is to check if every character within a given string is a number.
	 * If any character is not a number, the method returns false.
	 * 
	 * The second function is to check if the input integer is within a required limit.
	 * The limits are input as parameters. If the input is not within the limits, the method returns false.
	 * 
	 * Additionally, this methods prints a line stating the input data is not in the correct format and 
	 * prompts the user to enter the required data again.*/
	public boolean checkInputInteger(int lowerLimit, int upperLimit, String input) {
		if(input.length()==0) return false;
		ArrayList<Character> arr= new ArrayList<Character>(Arrays.asList('1','2','3','4','5','6','7','8','9','0'));
		for(int i=0;i<input.length();i++) {
			if(!arr.contains(input.charAt(i))){
				System.out.printf("I'm sorry but the input data is not in the correct format.%n"
						+ "Please ensure you are entering a numerical value between %d and %d inclusive.%n%n",lowerLimit,upperLimit);
				return false;
			}
		}
		int temp = Integer.parseInt(input);
		if(!(lowerLimit<=temp && temp<=upperLimit)) {
			System.out.printf("I'm sorry but the input data is not in the correct format.%n"
					+ "Please ensure you are entering a numerical value between %d and %d inclusive.%n%n",lowerLimit,upperLimit);
		}
		return lowerLimit<=temp && temp<=upperLimit; 
	}
}

