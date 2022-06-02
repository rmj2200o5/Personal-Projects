package JavaWorks.Projects;


public class Ant {
	private int row;
	private int column;
	private int orientation;
	
	public Ant(int startingRow, int startingColumn) {
		row = startingRow;
		column = startingColumn;
		orientation = 3;
	}
	
	public void blackSpaceMove() {
		orientation--;
		if(orientation<1) orientation = 4;
		if(orientation == 1) row--;
		if(orientation == 2) column++;
		if(orientation == 3) row++;
		if(orientation == 4) column--;
	}
	public void whiteSpaceMove() {
		orientation++;
		if(orientation>4) orientation = 1;
		if(orientation == 1) row--;
		if(orientation == 2) column++;
		if(orientation == 3) row++;
		if(orientation == 4) column--;
	}
	public void updateOrientation(int num) {
		if(num==1) orientation++;
		if(num==-1) orientation--;
		
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return column;
	}
	public void setRow(int r) {
		row = r;
	}
	public void setCol(int c) {
		column = c;
	}
}

