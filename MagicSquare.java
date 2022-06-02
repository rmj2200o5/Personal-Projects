package JavaWorks.Projects;

import java.util.ArrayList;

public class MagicSquare {
	
	private static int[][] square;
	private int startingNumber;
	private int size;
	private int increment;
	
	public MagicSquare(int s) {
		startingNumber = 1;
		size = s;
		increment = 1;
		if(size%2==1){
			square = createMagicSquareOdd(startingNumber,size,increment);
		}else if(size%4==0){
			square = createMagicSquareDoublyEven(startingNumber,s,increment);
		}else {
			square= creatMagicSquareSinglyEven(startingNumber,s,increment);
		}
	}
	public MagicSquare(int startNum, int s, int inc) {
		startingNumber = startNum;
		size = s;
		increment = inc;
		if(size%2==1){
			square = createMagicSquareOdd(startingNumber,size,increment);
		}else if(size%4==0){
			square = createMagicSquareDoublyEven(startNum,s,inc);
		}else {
			square= creatMagicSquareSinglyEven(startNum,s,inc);
		}
	}
	public int getStartingNumber() {
		return startingNumber;
	}
	public int getSize() {
		return size;
	}
	public int getIncrement() {
		return increment;
	}
	public int[][] getSquare(){
		return square;
	}
	public boolean isMagicSquare() {
		int downDiag = 0;
		for(int i=0;i<square.length;i++) {
			downDiag+=square[i][i];
		}
		for(int i=0;i<square.length;i++) {
			int row = 0;
			int col= 0;
			for(int j=0;j<square.length;j++) {
				row+=square[i][j];
				col+=square[j][i];
			}
			if(row!=downDiag||col!=downDiag) {
				return false;
			}
		}
		int upDiag = 0;
		for(int i=0;i<square.length;i++) {
			upDiag+= square[i][square.length-i-1];
		}
		return upDiag==downDiag;
	}
	public boolean isFilled(int[][] mat) {
		for(int[] arr: mat) 
			for(int num: arr) 
				if(num==0)
					return false;
		return true;
	}
	
	public int[][] createMagicSquareOdd(int startingNum, int size, int increment) {
		int[][] grid = new int[size][size];
		int num = startingNum;
		int currentI = 0;
		int currentJ = size/2;
		while(!isFilled(grid)) {
			int currentNum=grid[currentI][currentJ];
			if(currentNum==0) {
				grid[currentI][currentJ]=num;
				num+=increment;
				currentI--;
				currentJ++;
			}else {
				currentI++;
				currentJ--;
				currentI++;
			}
			if(currentI==size)
				currentI=0;
			if(currentI==size+1)
				currentI=1;
			if(currentI<0)
				currentI=size-1;
			if(currentJ==size)
				currentJ=0;
			if(currentJ<0)
				currentJ=size-1;
		}
		return grid;
	}
	
	public int[][] createMagicSquareDoublyEven(int startingNum, int size, int increment){
		int[][] grid = new int[size][size];
		int squares = size/4;
		ArrayList<Integer> skipped = new ArrayList<Integer>();
		for(int x=0;x<squares;x++) {
			for(int y=0;y<squares;y++) {
				int minI=4*x; int minJ=4*y;int maxI=4*x+3;int maxJ=4*y+3;
				for(int i=minI;i<=maxI;i++) {
					for(int j=minJ;j<=maxJ;j++) {
						int relativeI = i-minI;
						int relativeJ = j-minJ;
						if(relativeI==relativeJ||relativeI==4-relativeJ-1) {
							skipped.add(i*size*increment+increment*(j+1));
						}else {
							grid[i][j]=i*size*increment+increment*(j+1);
						}
					}
				}
			}
		}
		skipped.sort(null);
		for(int i=size-1;i>=0;i--) {
			for(int j=size-1;j>=0;j--) {
				if(grid[i][j]==0) {
					grid[i][j]=skipped.remove(0);
				}
			}
		}
		return grid;
	}
	
	public int[][] creatMagicSquareSinglyEven(int startingNum,int size, int increment){
		int[][] grid = new int[size][size];
		int maximum = (size*size*increment-increment)+startingNum;
		
		int[][] grid1 = createMagicSquareOdd(startingNum,size/2,increment);
		for(int i=0;i<size/2;i++) {
			for(int j=0;j<size/2;j++) {
				grid[i][j]=grid1[i][j];
			}
		}
		
		int[][] grid2 = createMagicSquareOdd(maximum/4+1,size/2,increment);
		for(int i=size/2;i<size;i++) {
			for(int j=size/2;j<size;j++) {
				grid[i][j]=grid2[i-size/2][j-size/2];
			}
		}
		
		int[][] grid3 = createMagicSquareOdd(maximum/2+1,size/2,increment);
		for(int i=0;i<size/2;i++) {
			for(int j=size/2;j<size;j++) {
				grid[i][j]=grid3[i][j-size/2];
			}
		}
		
		int[][] grid4 = createMagicSquareOdd(maximum-(maximum/4-1),size/2,increment);
		for(int i=size/2;i<size;i++) {
			for(int j=0;j<size/2;j++) {
				grid[i][j]=grid4[i-size/2][j];
			}
		}
		
		for(int i=0;i<size/2;i++) {
			for(int j=0;j<=size/4;j++) {
				boolean condition1 = !(i==size/4 && j==0);
				boolean condition2 = !(j==size/4 && i!=size/4);
				if(condition1&&condition2) {
					int temp = grid[i][j];
					grid[i][j] =grid[i+size/2][j];
					grid[i+size/2][j]= temp;
				}
			}
		}
		int numColsRight = size/2 - size/4 - 2;
		for(int i=size-numColsRight;i<size;i++) {
			for(int j=0;j<size/2;j++) {
				int temp = grid[j][i];
				grid[j][i] = grid[j+size/2][i];
				grid[j+size/2][i] = temp;
			}
		}
		return grid;
	}
	/*
    * Maximum size square for neat formatting is size 31    
    */
	@Override
	public String toString() {
		String out = "";
		out+=String.format("Size: %d%nStarting Number: %d%nIncrement: %d%n%n",size,startingNumber,increment);
		for(int[] arr: square) {
			for(int num: arr) {
				out+=String.format("%-4d ",num);
			}
			out+="\n";
		}
		return out;
	}
}


