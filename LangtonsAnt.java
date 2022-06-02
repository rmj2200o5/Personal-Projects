package JavaWorks.Projects;

import java.util.*;

public class LangtonsAnt {
	private String[][] grid;
	private int rows;
	private int columns;
	private int numSteps;
	private int stepsPerRefresh;
	
	private static boolean isRunning;
	private Scanner sc;
	
	private Ant mainAnt;
	
	public LangtonsAnt() {
		sc = new Scanner(System.in);
		confirmGameStart();
		while(isRunning) {
			askRowsAndCols();
			askSteps();
			askStepsPerRefresh();
			askAntStartingSpot();
			fillArr(grid);
			int steps = numSteps;
			System.out.printf("STEP %d%n",0);
			printGrid(grid);
			moveAnt();
			steps--;
			while(steps-->0) {
				if((numSteps-steps-1)%stepsPerRefresh==0) {
					System.out.printf("STEP %d%n",numSteps-steps-1);
					printGrid(grid);
				}
				moveAnt();
			}
			System.out.printf("STEP %d%n",numSteps-steps-1);
			printGrid(grid);
			updateIsRunning();
		}
		
		
		
	}
	/*This method asks the user if they want to start the Program*/
	public void confirmGameStart() {
		boolean correctFormat = false;
		while(!correctFormat) {
			System.out.printf("1. Start Langston's Ant Program%n"
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
	public void fillArr(String[][] arr) {
		for(int i=0; i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++) {
				arr[i][j]= " ";
			}
		}
	}
	public void printGrid(String[][] arr) {
		//Prints the instance variable grid
		//Prints an "*" at the location of the ant
		int row = mainAnt.getRow();
		int col = mainAnt.getCol();
		for(int i=0;i<arr[0].length+2;i++) {
			System.out.print("-");
		}
		System.out.println();
		for(int i=0; i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++) {
				if(j==0)
					System.out.print("|");
				
				if(i==row&&j==col) {
					System.out.print("*");
				}else {
					System.out.print(arr[i][j]);
				}
				
				if(j==arr[0].length-1)
					System.out.print("|");
			}
			System.out.println();
		}
		for(int i=0;i<arr[0].length+2;i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	public void moveAnt() {
		/*Calls on either the method whiteSpaceMove or blackSpaceMove 
		 *from the Ant Class.
		 *This method also adjust the ants location to avoid out of bounds 
		 *errors and changes the color of the space the ant was previous located at.
		 *This method also uses the modifying methods setRow() and setCol() from the Ant Class*/
		int row = mainAnt.getRow();
		int col = mainAnt.getCol();
		if(grid[row][col].equals(" ")){
			mainAnt.whiteSpaceMove();
			if(mainAnt.getRow()==grid.length)
				mainAnt.setRow(0);
			if(mainAnt.getRow()<0)
				mainAnt.setRow(grid.length-1);
			if(mainAnt.getCol()==grid[0].length)
				mainAnt.setCol(0);
			if(mainAnt.getCol()<0)
				mainAnt.setCol(grid[0].length-1);
			grid[row][col]= "#";
		}else {
			mainAnt.blackSpaceMove();
			if(mainAnt.getRow()==grid.length)
				mainAnt.setRow(0);
			if(mainAnt.getRow()<0)
				mainAnt.setRow(grid.length-1);
			if(mainAnt.getCol()==grid[0].length)
				mainAnt.setCol(0);
			if(mainAnt.getCol()<0)
				mainAnt.setCol(grid[0].length-1);
			grid[row][col]= " ";
		}
	}
	
	/*This method ask the size of the board. Once the number of rows and columns have been
	 * successfully entered, the instance variable "grid" is instantiated.
	 * 
	 * The instance variables "rows" and "columns" are also instantiated in this method.*/
	public void askRowsAndCols() {
		boolean correctFormat = false;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("Please enter the number of rows for the board. (1-150)%n");
			String temp = sc.nextLine();
			if(checkInputInteger(1,150,temp)) {
				rows = Integer.parseInt(temp);
				correctFormat = true;
			}
		}
		
		correctFormat = false;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("Please enter the number of columns for the board.(1-150)%n");
			String temp = sc.nextLine();
			if(checkInputInteger(1,150,temp)) {
				columns = Integer.parseInt(temp);
				correctFormat = true;
			}
		}
		
		grid = new String[rows][columns];
		
	}
	/*This method ask how many steps will be calculated per screen refresh
	 * 
	 * The instance variable stepsPerRefresh is instantiated.*/
	public void askStepsPerRefresh() {
		boolean correctFormat = false;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("Please enter the number of steps per refresh (1-%d).%n",numSteps);
			String temp = sc.nextLine();
			if(checkInputInteger(1,numSteps,temp)) {
				stepsPerRefresh = Integer.parseInt(temp);
				correctFormat = true;
			}
		}
		
	}

	/*This method asks how many steps will occur in the current run.
	 * 
	 * The instance variable "numSteps" is instantiated*/
	public void askSteps() {
		boolean correctFormat = false;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("Please enter the number of steps for this run (1-40000).%n");
			String temp = sc.nextLine();
			if(checkInputInteger(1,40000,temp)) {
				numSteps = Integer.parseInt(temp);
				correctFormat = true;
			}
		}
	}
	/*This method ask for the starting position of the Ant.
	 * 
	 * The input row and column are used to instantiate the instance variable "mainAnt.*/
	public void askAntStartingSpot() {
		boolean correctFormat = false;
		int row = -1;
		int column = -1;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("Please enter the starting row of the ant (1-%d).%n",grid.length);
			String temp = sc.nextLine();
			if(checkInputInteger(1,grid.length,temp)) {
				row = Integer.parseInt(temp)-1;
				correctFormat = true;
			}
		}
		
		correctFormat = false;
		//The while loop continues asking for input until a number within the appropriate range is given
		while(!correctFormat) {
			System.out.printf("Please enter the starting column of the ant (1-%d).%n",grid[0].length);
			String temp = sc.nextLine();
			if(checkInputInteger(1,grid[0].length,temp)) {
				column = Integer.parseInt(temp)-1;
				correctFormat = true;
			}
		}
		mainAnt = new Ant(row,column);
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
					+ "Please ensure you are entering a numerical value between 1 and %d inclusive.%n%n",lowerLimit,upperLimit);
		}
		return lowerLimit<=temp && temp<=upperLimit; 
	}
}

