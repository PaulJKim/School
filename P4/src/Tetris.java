import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Tetris extends JPanel{
	
	private static JLabel score;
	private JLabel nextPiece;
	private static int scoreInt = 0;
	private JPanel parent, tetrisGame, scorePanel, 
								infoPanel, pausePanel, piecePanel;
	private CardLayout cardLayout;
	private JButton pauseButton;
	private static int shownTetriminos;
	final static String key = "tetris game board";
	private TetrisBoard tetrisBoard;
	//
	private TetrisGrid tetrisGrid;
	//
	
	private Stack <Component> tetriminos;
	
	public Tetris(CardLayout cardLayoutParam, JPanel parentPanel, TetrisGrid tGrid)//TetrisBoard tBoard)
	{
		//tetrisBoard = tBoard;
		cardLayout = cardLayoutParam;
		parent = parentPanel;
		//
		tetrisGrid = tGrid;
		
		GridBagConstraints c = new GridBagConstraints();
		
		tetrisGame = new JPanel();
		tetrisGame.setPreferredSize(new Dimension(500, 500));
		tetrisGame.setLayout(new GridBagLayout());
		
//		tetrisBoard.setPreferredSize(new Dimension(300, 500));
//		tetrisBoard.setBackground(Color.cyan);
		tetrisGrid.setPreferredSize(new Dimension(300, 500));
		tetrisGrid.setBackground(Color.WHITE);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		//add(tetrisBoard, c);
		add(tetrisGrid, c);
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(170, 500));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Color.gray);
		score = new JLabel("Score: " + scoreInt);
		nextPiece = new JLabel("Next Piece: ");
		infoPanel.add(score);
		infoPanel.add(nextPiece);
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		add(infoPanel, c);
		
		piecePanel = new JPanel();
		piecePanel.setPreferredSize(new Dimension(150, 200));
		piecePanel.setLayout(new BoxLayout(piecePanel, BoxLayout.Y_AXIS));
		piecePanel.setBackground(Color.cyan);
		JLabel random = new JLabel(" ");
		piecePanel.add(random);
		infoPanel.add(piecePanel);
		
		//separate panel for pause button
		pausePanel = new JPanel();
		pausePanel.setPreferredSize(new Dimension(150, 40));
		pausePanel.setLayout(new BoxLayout(pausePanel, BoxLayout.Y_AXIS));
		pausePanel.setBackground(Color.gray);
		pauseButton = new JButton("Pause");
		pauseButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		PauseButtonListener pauseListener = new PauseButtonListener();
		pauseButton.addActionListener(pauseListener);
		pausePanel.add(pauseButton);
		infoPanel.add(pausePanel);
		
		
	}
	
	public void restartGame()
	{
		tetrisGame.remove(tetrisBoard);
	}
	
	//public void paintComponent(Graphics g)
	//{
		//super.paintComponent(g);
		//g.setColor(Color.BLUE);
		//g.drawRect(160, 10, 35, 35);
		//g.fillRect(160, 10, 35, 35);
	//}
	
	public String getKey()
	{
		return key;
	}
	
	public void setShownTetriminos(int x)
	{
		shownTetriminos = x;
	}
	
	public static void updateScore(int x)
	{
		score.setText("Score: " + x);
	}
	
	
	
	
	private class PauseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			int x;
			if(event.getSource() == pauseButton)
			{
				tetrisGrid.stopTimer();
				x = JOptionPane.showOptionDialog(null, "Paused", null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Return", "Exit"}, "default");
				if(x == JOptionPane.NO_OPTION)
				{
					cardLayout.show(parent, TetrisMenu.key);
				}else if(x == JOptionPane.YES_OPTION)
				{
					tetrisGrid.startTimer();
					cardLayout.show(parent, Tetris.key);
				}
			}
		}
	}

}
