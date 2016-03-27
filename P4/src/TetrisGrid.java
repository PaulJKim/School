import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;


public class TetrisGrid extends JPanel{

	private JPanel tetrisGrid;
	private int[] coordArray = new int[8];
	private JLabel[][] gridArray = new JLabel[20][10];
	private int[][] boardArray = new int[20][10], pieceArray;
	private PieceQueue pieceQueue = new PieceQueue();
	private TimerListener timerListener = new TimerListener();
	private int row = 0, column = 3, timerDelay = 500, floor = 19, shifts = 0, coordTemp = 0, score = 0;
	private Timer timer;
	private boolean rightTrue = false, leftTrue = false, rotateTrue = false;
	private int shiftTracker = 0;
	
	private KeyboardListener keyListener = new KeyboardListener();
	
	//constructor creates the grid for the pieces using JLabels that change color according to an 
	//underlying 2d grid
	public TetrisGrid(){
		tetrisGrid = new JPanel();
		tetrisGrid.setBackground(Color.BLACK);
		tetrisGrid.setLayout(new GridLayout(20, 10));
		addKeyListener(keyListener);
		this.requestFocusInWindow();
		setFocusable(true);
		timer = new Timer(timerDelay, timerListener);
		pieceArray = pieceQueue.getQueue().peek();
		
		for(int x = 0; x < 20; x++){
			for(int y = 0; y < 10; y++){
				
				gridArray[x][y] = new JLabel("        ");
				gridArray[x][y].setOpaque(true);
				add(gridArray[x][y]);
				gridArray[x][y].setBackground(new Color(220, 220, 220));
			}
		}
		
		
		timer.start();
	}
	
	public void startGame(){
		addKeyListener(keyListener);
		requestFocusInWindow();
		setFocusable(true);
		if(pieceQueue.getQueue().isEmpty()){
			JOptionPane.showMessageDialog(null, "You have used all the pieces!");
		}
//		if (floor < 5){
//			timer.stop();
//			JOptionPane.showMessageDialog(null, "You lost!");
//		}
		for(int xxx = 0; xxx < 4; xxx ++){
			for(int yyy = 0; yyy < 4; yyy++){
				if(pieceArray[xxx][yyy] == 1){
					coordArray[coordTemp] = xxx;
					coordTemp ++;
					coordArray[coordTemp] = yyy;
					coordTemp ++;
				}
			}
		}
		//System.out.print(Arrays.toString(coordArray));
		for(int x = 0, y = 1; y < 8; x +=2, y+=2){
			boardArray[row + coordArray[x]][column + coordArray[y] + shifts] = 1;
		}
		paintBoard();
		
		//shifts pieces, also checks if piece are to either side so that pieces don't overlap
		if(leftTrue && checkSides()){
			shifts -- ;
			leftTrue = false;
		}
		if(rightTrue && checkSides()){
			shifts ++;
			rightTrue = false;
		}
		if(rotateTrue && checkSides()){
			rotatePiece();
			rotateTrue = false;
		}
		
		
		
		
		//System.out.println("_________________");
			 
	}
	
	//removes the previous location of the piece giving the effect that the piece is moving down the board
	public void removeShadow(){
		for(int x = 0; x < row+3; x++){
			for(int y = 0; y < 10; y++){
				if(boardArray[x][y] == 1)
				{
					boardArray[x][y] = 0;
				}
			}
		}
		
	}
	
	//changes the color of the JLabels in the grid according to the values of the elements in the underlying 2d array
	public void paintBoard(){
		
		for(int xx = 0; xx < 20; xx++){
			for(int yy = 0; yy < 10; yy++){
				if(boardArray[xx][yy] == 1 || boardArray[xx][yy] == 2){
					gridArray[xx][yy].setBackground(Color.BLUE);
				}else{
					gridArray[xx][yy].setBackground(new Color(220, 220, 220));
				}

				
			}
		}
	}
	
	
	public void setTimer(int x){
		timer.setDelay(x);
	}
	
	public void stopTimer(){
		timer.stop();
	}
	
	public void startTimer(){
		timer.start();
	}
	
	//algorithm for rotating the pieces
	public void rotatePiece(){
		int[][] tempArray = new int[4][4];
		for(int x = 0, y = 3; x < 4; x++, y--){
			for(int z = 0; z < 4; z++){
				tempArray[x][z] = pieceArray[z][y];
			}
		}
	//	System.out.println(Arrays.deepToString(pieceArray));
		pieceArray = tempArray;
		//System.out.println(Arrays.deepToString(tempArray));
		coordTemp = 0;
		for(int xxx = 0; xxx < 4; xxx ++){
			for(int yyy = 0; yyy < 4; yyy++){
				if(pieceArray[xxx][yyy] == 1){
					coordArray[coordTemp] = xxx;
					coordTemp ++;
					coordArray[coordTemp] = yyy;
					coordTemp ++;
				}
			}
		}
	}
	
	public void clearBoard(){
		for(int x = 0; x < floor; x++){
			for(int y = 0; y < 10; y++){
				boardArray[x][y] = 0;
				
			}
		}
	}
	
	public int clearWhiteSpace(){
		int count = 0;
		for(int temp = 0; temp <4; temp++){
		if(pieceArray[3][temp] == 0){
			count++;
		}
		}
		if(count == 4){
			count = 1;
			return count;
		}else{
			return 0;
		}
			
	}
	
	public int getHeights(){

		int height = 0;
		boolean hasPiece = false;
		for(int temp = 0; temp <4; temp++){
			if(pieceArray[0][temp] == 1){
				hasPiece = true;
			}
		}
		if(hasPiece){
			height ++;
			hasPiece = false;
		}
		for(int temp = 0; temp <4; temp++){
			if(pieceArray[1][temp] == 1){
				hasPiece = true;

			}
		}
		if(hasPiece){
			height ++;
			hasPiece = false;
		}
		for(int temp = 0; temp <4; temp++){
			if(pieceArray[2][temp] == 1){
				hasPiece = true;

			}
		}
		if(hasPiece){
			height ++;
			hasPiece = false;
		}
		for(int temp = 0; temp <4; temp++){
			if(pieceArray[3][temp] == 1){
				hasPiece = true;

			}
		}
		if(hasPiece){
			height ++;
			hasPiece = false;
		}
		return height;
	}
	
	public boolean pieceBelow(){
		int tempRow = row;
		if(tempRow > 18){
			tempRow = 18;
		}
		
		
		for(int y = 3; y < 7; y++){
			
//			System.out.println(tempRow+1 + "\t" + (y + shifts) );
			if(boardArray[tempRow+1][(y + shifts)] == 1){
				return true;
			}
			
		}
		return false;
	}
	
	
//	public void printGrid(){
//		for(int x = 0; x < 20; x++){
//			for(int y = 0; y < 10; y++){
//				System.out.print(boardArray[x][y] + " ");
//			}
//			System.out.println("");
//		}
//	}
	

	//checks if there is a line of 10 blocks that are filled horizontally
	public boolean checkFilled(int rowNumber)
	{
		boolean trigger = false;
			for(int y = 0; y < 10; y++){
				if(boardArray[rowNumber][y] == 0)
				{
					trigger = false;
					break;
				}if(boardArray[rowNumber][y] == 2 || boardArray[rowNumber][y] == 1)
				{
					trigger = true;
				}
			}
			return trigger;
	}
	
	//checks if a piece is on either side
	public boolean checkSides()
	{
		boolean sidesNotFilled = true;
		for(int x = 0; x < 20; x++){
			for(int y = 0; y < 10; y++){
				if(boardArray[x][y] == 1 && (y != 0) && (y != 9) && (boardArray[x][y+1] == 2 || boardArray[x][y-1] == 2))
				{
					sidesNotFilled = false;
				}
			}
		}
		return sidesNotFilled;
	}
	
	public int returnScore()
	{
		return score;
	}
	
	//keeps game updated
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			
			removeShadow();
			startGame();
			coordTemp = 0;
			//paintBoard();
			//printGrid();
				
			
			//this if block is for if the piece reaches the bottom of the board
			if(row == 16){
				
				row = -1;
				
				shifts = 0;
				
				floor = floor - getHeights();
				
				for(int x = 0; x < 20; x++){
					for(int y = 0; y < 10; y++){
						if(boardArray[x][y] == 1)
						{
							boardArray[x][y] = 2;
						}
						
					}
				}
				
				for(int x = 0; x < 20; x++)
				{
					if(checkFilled(x))
					{
						score = score + 1000;
						Tetris.updateScore(score);
						for(int y = 0; y < 10; y++)
						{
							boardArray[x][y] = 0;
						}
						for(int row = x; row > 1; row--){
							for(int column = 0; column < 10; column++){
								if(boardArray[row][column] == 2 || boardArray[row - 1][column] == 2)
								{
									boardArray[row + 1][column] = 2;
									boardArray[row][column] = 0;
								}
								
							}
						}
					}
				}
			
				pieceQueue.getQueue().remove();
				pieceArray = pieceQueue.getQueue().peek();
			}else 
			{
				for(int x = 0; x < 20; x++){
					for(int y = 0; y < 10; y++){
						if(boardArray[x][y] == 1 && boardArray[x+1][y] == 2)
						{
							row = -1;
							
							shifts = 0;
							
							floor = floor - getHeights();
							for(int row = 0; row < 20; row++){
								for(int column = 0; column < 10; column++){
									if(boardArray[row][column] == 1)
									{
										boardArray[row][column] = 2;
									}
									
								}
							}
							
							for(int row = 0; row < 20; row++)
							{
								if(checkFilled(row))
								{
									for(int col = 0; col < 10; col++)
									{
										boardArray[row][col] = 0;
									}
									for(int row2 = x; row2 > 1; row2--){
										for(int column = 0; column < 10; column++){
											if(boardArray[row2][column] == 2 || boardArray[row2 - 1][column] == 2)
											{
												boardArray[row2 + 1][column] = 2;
												boardArray[row2][column] = 0;
											}
											
										}
									}
								}
							}
							pieceQueue.getQueue().remove();
							pieceArray = pieceQueue.getQueue().peek();
						}
						
					}
				}
			}
			//row -= 3;
			row ++;
			
		}
	}
	
	
//key listener for moving the pieces
	private class KeyboardListener implements KeyListener{
		public void keyTyped(KeyEvent e) {
			
		}

		
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				if(shifts > -3){
					leftTrue = true;
				}
				
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				if(shifts < 3){
					rightTrue = true;
				}
				
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				setTimer(150);
				
			}
			if(e.getKeyCode() == KeyEvent.VK_UP){
				rotateTrue = true;
			}
		}

		
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				setTimer(1000);
				
			}
			
		}
		
	
	}
	
}
