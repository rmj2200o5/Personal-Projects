package JavaWorks.Projects;

import java.util.*;
public class TowerOfHanoi {
	private static ArrayList<Integer> stack1;
	private static ArrayList<Integer> stack2;
	private static ArrayList<Integer> stack3;
	private static int height;
	private static int moveCount=0;
	
	public TowerOfHanoi(int size) {
		//to show the solution to the puzzle, type "TowerOfHanoi = new TowerOfHanoi(#);" in a different class.
		//replace the "#" with any number you desire.
		height = size;
		stack1 = new ArrayList<Integer>();
		stack2 = new ArrayList<Integer>();
		stack3 = new ArrayList<Integer>();
		for(int i=0;i<size;i++) {
			stack1.add(size-i);
		}
		printArrays();
		recursiveSolve(height,stack1,stack3,stack2);
	}

	public static void recursiveSolve(int size, ArrayList<Integer> startingStack, ArrayList<Integer> endingStack, ArrayList<Integer> extraStack) {
		if(size==1) {
			movePieceFromTo(startingStack,endingStack);
			moveCount++;
			printArrays();
		}
		else {
			recursiveSolve(size-1,startingStack,extraStack,endingStack);
			movePieceFromTo(startingStack,endingStack);
			moveCount++;
			printArrays();
			recursiveSolve(size-1,extraStack,endingStack,startingStack);
		}
	}
	public static void movePieceFromTo(ArrayList<Integer> startingStack, ArrayList<Integer> endingStack) {
		endingStack.add(startingStack.remove(startingStack.size()-1));
	}
	public static void printNumMoves() {
		System.out.println(moveCount);
	}	
	public static void printArrays() {
		for(int i=height-1;i>=0;i--) {
			System.out.print(i<stack1.size() ? stack1.get(i)+"  " : 0+"  ");
			System.out.print(i<stack2.size() ? stack2.get(i)+"  " : 0+"  ");
			System.out.print(i<stack3.size() ? stack3.get(i)+"  " : 0+"  ");	
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	
}

