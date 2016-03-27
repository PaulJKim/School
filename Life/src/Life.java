/* 
* Life.java 
* Author: Paul Kim
* Submission Date: 10/5/2013
* 
* Purpose: This programs runs the game of Life where each cell is given a random value
* that determines whether the cell is alive or dead. Then, depending on the number of 
* cells adjacent to the cell that are alive, the cell either remains alive or dead in the
* next generation. This continues for an infinite amount of generations.
* 
* Statement of Academic Honesty: 
* 
* The following code represents my own work. I have neither 
* received nor given inappropriate assistance. I have not copied 
* or modified code from any source other than the course webpage 
* or the course textbook. I recognize that any unauthorized 
* assistance or plagiarism will be handled in accordance with 
* the University of Georgia's Academic Honesty Policy and the 
* policies of this course. I recognize that my work is based 
* on an assignment created by the Department of Computer 
* Science at the University of Georgia. Any publishing 
* or posting of source code for this project is strictly 
* prohibited unless you have written consent from the Department 
* of Computer Science at the University of Georgia. 
*/
import java.util.Random;
public class Life {

	public static void main(String[] args) {
		
		//determines the size of the grid and each cell
		
		int gridSize = 200;
		int cellSize = 3;
		
		//creates a new grid object
		
		Grid grid = new Grid(gridSize, cellSize, "The Game of Life");
		grid.setDelay(10);
		
		int aliveColor = 1;
		int deadColor = 0;
		
		int row = 10;
		
		//this section is just a test grid that is then cleared
		
		for (int col = 25; col >= 25 && col <= 75; col++){
			grid.setPos(row, col, aliveColor);
		
		}
		
		grid.update();
		grid.clearGrid();
		
		//initializes the rows and columns as zero
		
		row = 0;
		int col = 0;
		
		//random number used to determine whether each cell is dead or alive
		
		Random r = new Random();
		
		//assign random dead or alive value to each cell
	
		for (row = 0; row < (gridSize - 1); row++){
			for (col = 0; col < (gridSize - 1); col++){
				r = new Random();
				if (r.nextInt(100) > 49 )
					grid.setPos(row, col, aliveColor);
				else
					grid.setPos(row, col, deadColor);
			}
			r = new Random();
			if (r.nextInt(100) > 49 )
				grid.setPos(row, col, aliveColor);
			else
				grid.setPos(row, col, deadColor);
		}
		grid.update();
		grid.initialize();
		
		//to make the loop infinite
		
		int y = 0;
		
		while (y < 1){
			
		//rules for determining whether a cell is alive or dead in the next generation
			
		for (row = 0; row < (gridSize - 1); row++){
			for (col = 0; col < (gridSize - 1); col++){
			if (grid.getPos(row, col) == 1 && (grid.matchingNeighbors(row, col, aliveColor) == 2) || grid.matchingNeighbors(row, col, aliveColor) == 3 )
				grid.setPos(row, col, aliveColor);
			
			else if (grid.getPos(row, col) == 0 && grid.matchingNeighbors(row, col, aliveColor) == 3)
				grid.setPos(row, col, aliveColor);
			
			else 
				grid.setPos(row, col, deadColor);
			
		}
		if (grid.getPos(row, col) == 1 && (grid.matchingNeighbors(row, col, aliveColor) == 2) || grid.matchingNeighbors(row, col, aliveColor) == 3 )
			grid.setPos(row, col, aliveColor);
			
		else if (grid.getPos(row, col) == 0 && grid.matchingNeighbors(row, col, aliveColor) == 3)
			grid.setPos(row, col, aliveColor);
		
		else 
			grid.setPos(row, col, deadColor);
		}
		grid.update();
		}
	}
		
}