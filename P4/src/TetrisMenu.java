import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisMenu extends JPanel{
	
	JPanel tetrisMenuPanel, parentPanel;
	JButton startButton, instructionsButton, backButton, scoreBoardButton;
	JComboBox tetrisComboBox;
	CardLayout cardLayout;
	ComboBoxItem zero;
	ComboBoxItem one;
	ComboBoxItem four;
	ComboBoxItem prompt; 
	
	Tetris tetrisScreen;
	TetrisBoard tetrisBoard;
	TetrisGrid tetrisGrid;
	
	final static String key = "Tetris Menu";
	
	public TetrisMenu(CardLayout cardLayoutParam, JPanel parent)
	{
		cardLayout = cardLayoutParam;
		parentPanel = parent;
		tetrisMenuPanel = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(500, 500));
		TetrisMenuListener tetrisListener = new TetrisMenuListener();
		
		add(Box.createRigidArea(new Dimension(0, 70)));
		
		tetrisComboBox = new JComboBox();
		zero = new ComboBoxItem("0", 0);
		one = new ComboBoxItem("1", 1);
		four = new ComboBoxItem("4", 4);
		prompt = new ComboBoxItem("Select shown Pieces", 0);
		tetrisComboBox.addItem(prompt);
		tetrisComboBox.addItem(zero);
		tetrisComboBox.addItem(one);
		tetrisComboBox.addItem(four);
		tetrisComboBox.setMaximumSize(new Dimension(170, 20));
		tetrisComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(tetrisComboBox);
		ComboBoxListener comboListener = new ComboBoxListener();
		tetrisComboBox.addActionListener(comboListener);
		
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		startButton = new JButton("Start");
		startButton.setMaximumSize(new Dimension(170, 70));
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(tetrisListener);
		add(startButton);
		
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		instructionsButton = new JButton("Instructions");
		instructionsButton.setMaximumSize(new Dimension(170, 70));
		instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructionsButton.addActionListener(tetrisListener);
		add(instructionsButton);
		
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		backButton = new JButton("Back");
		backButton.setMaximumSize(new Dimension(170, 70));
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(tetrisListener);
		add(backButton);
		
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		scoreBoardButton = new JButton("Score Board");
		scoreBoardButton.setMaximumSize(new Dimension(170, 50));
		scoreBoardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreBoardButton.addActionListener(tetrisListener);
		add(scoreBoardButton);
		
		
	}

	public String getKey()
	{
		return key;
	}
	
	private class TetrisMenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			int x = 0;
			if(event.getSource() == startButton)
			{
				if(tetrisComboBox.getSelectedItem().equals(prompt))
				{
					x = JOptionPane.showOptionDialog(null, "Select a number of shown tetriminos!", null, JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Return"}, "default");
					if(x == JOptionPane.YES_OPTION)
					{
						cardLayout.show(parentPanel, TetrisMenu.key);
					}
				}else if(!tetrisComboBox.getSelectedItem().equals(prompt))
				{
					tetrisBoard = new TetrisBoard();
					tetrisGrid = new TetrisGrid();
//					tetrisScreen = new Tetris(cardLayout, parentPanel, tetrisBoard);
					tetrisScreen = new Tetris(cardLayout, parentPanel, tetrisGrid);
					parentPanel.add(tetrisScreen);
					cardLayout.addLayoutComponent(tetrisScreen, Tetris.key);
					tetrisBoard.startTimer();
					cardLayout.show(parentPanel, Tetris.key);
				}
			}else if(event.getSource() == instructionsButton)
			{
				cardLayout.show(parentPanel, TetrisInstructions.key);
			}else if(event.getSource() == backButton)
			{
				cardLayout.show(parentPanel, StartMenu.key);
			}else if(event.getSource() == scoreBoardButton)
			{
				System.out.println("Score Board");
			}
			
		}
				
	}
	
	private class ComboBoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent E)
		{
			if(tetrisComboBox.getSelectedItem().equals(zero))
			{
				//System.out.println(zero.getValue());
				//tetrisBoard.setShownTetriminos(zero.getValue());
			}else if(tetrisComboBox.getSelectedItem().equals(one))
			{
				//System.out.println(one.getValue());
				//tetrisBoard.setShownTetriminos(one.getValue());
			}else if(tetrisComboBox.getSelectedItem().equals(four))
			{
				//System.out.println(four.getValue());
				//tetrisBoard.setShownTetriminos(four.getValue());
			}
				
		}
	}
	
	private class ComboBoxItem
	{
		private String name;
		private int value;
		
		public ComboBoxItem(String itemName, int itemValue)
		{
			name = itemName;
			value = itemValue;
		}
		
		public String getName()
		{
			return name;
		}
		
		public int getValue()
		{
			return value;
		}
		
		public String toString()
		{
			return name;
		}
		
	}
	
}