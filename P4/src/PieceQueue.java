import java.util.*;


public class PieceQueue{

	
	private Queue<int[][]> pieceQueue;
	
	//int 2d arrays that contain each piece
	int[][] arrayI = 
							{{0, 1, 0, 0},
							{0, 1, 0, 0},
							{0, 1, 0, 0},
							{0, 1, 0, 0},
						};
	
	int[][] arrayJ = 
							{{0, 0, 0, 0},
							{0, 0, 1, 0},
							{0, 0, 1, 0},
							{0, 1, 1, 0},
						};
	
	int[][] arrayL = 
							{{0, 0, 0, 0},
							{0, 1, 0, 0},
							{0, 1, 0, 0},
							{0, 1, 1, 0},
						};
	
	int[][] arrayO = 
							{{0, 0, 0, 0},
							{0, 0, 0, 0},
							{0, 1, 1, 0},
							{0, 1, 1, 0},
						};
	
	int[][] arrayS = 
							{{0, 0, 0, 0},
							{0, 0, 0, 0},
							{0, 0, 1, 1},
							{0, 1, 1, 0},
						};
	
	int[][] arrayT = 
							{{0, 0, 0, 0},
							{0, 0, 0, 0},
							{1, 1, 1, 0},
							{0, 1, 0, 0},
						};
	
	int[][] arrayZ = 
							{{0, 0, 0, 0},
							{0, 0, 0, 0},
							{1, 1, 0, 0},
							{0, 1, 1, 0},
						};
	
	
	public PieceQueue()
	{
		pieceQueue = new LinkedList<int[][]>();
		
		//generates 200 pieces randomly and adds them to the queue
		Random generator = new Random();
		int arrayNum = 0;
		for(int i = 0; i < 200; i++)
		{
			arrayNum = generator.nextInt(7);
			if(arrayNum == 0)
			{
				pieceQueue.add(arrayI);
			}
			if(arrayNum == 1)
			{
				pieceQueue.add(arrayJ);
			}
			if(arrayNum == 2)
			{
				pieceQueue.add(arrayL);
			}
			if(arrayNum == 3)
			{
				pieceQueue.add(arrayO);
			}
			if(arrayNum == 4)
			{
				pieceQueue.add(arrayS);
			}
			if(arrayNum == 5)
			{
				pieceQueue.add(arrayT);
			}
			if(arrayNum == 6)
			{
				pieceQueue.add(arrayZ);
			}
			
		}
	}
		
	//pulls 5 pieces and returns a 3d array containing the pieces
	public int[][][] pullPiece()
		{
			int[][][] pullArray = new int[5][][];
			for(int i = 0; i < 5; i++)
			{
				pullArray[i] = pieceQueue.poll();
			}
			return pullArray;
		}
		
	public Queue<int [][]> getQueue(){
		return pieceQueue;
	}
		
	}