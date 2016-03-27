import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class TetrisBoard extends JPanel{

	private int pieceX = 150, pieceY = 0;
	int numberOfPieces = 0;
	JPanel tetrisBoard;
	TimerListener timerListener = new TimerListener();
	Timer tetrisTimer = new Timer(500, timerListener);
	
	public TetrisBoard()
	{
		tetrisBoard = new JPanel();
		tetrisBoard.setBackground(Color.cyan);
		
	}
	
	public void paint(Graphics g)
		{
		if(numberOfPieces == 0)
		{
			super.paint(g);
			g.setColor(Color.RED);
			g.drawRect(pieceX, pieceY, 25, 25);
			g.fillRect(pieceX, pieceY, 25, 25);
			g.drawRect(pieceX, pieceY+25, 25, 25);
			g.fillRect(pieceX, pieceY+25, 25, 25);
			g.drawRect(pieceX, pieceY+50, 25, 25);
			g.fillRect(pieceX, pieceY+50, 25, 25);
			g.drawRect(pieceX, pieceY+75, 25, 25);
			g.fillRect(pieceX, pieceY+75, 25, 25);
		}	
		if(numberOfPieces == 1)
		{
			super.paint(g);
			g.setColor(Color.RED);
			g.drawRect(pieceX, pieceY, 25, 25);
			g.fillRect(pieceX, pieceY, 25, 25);

		}
	
			
			//g.drawRect(125, 26, 25, 25);
			//g.fillRect(125, 26, 25, 25);
			
			//for(int x = 0; x < 300; x++)
			//{for(int y = 0; y < 500; y = y + 25)
				//{
					//tetrisBoard.
				//}
			//}
		}
	
	
	public void startTimer()
	{
		tetrisTimer.start();
	}
	
	public void stopTimer()
	{
		tetrisTimer.stop();
	}
	
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			//System.out.println("count" + pieceY);
			pieceY = pieceY + 25;
			repaint();
			if(pieceY > 474)
			{
				pieceY = 0;
				numberOfPieces++;
			}
		}
	}
}
