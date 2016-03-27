import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;

public class StartMenu extends JPanel{
	
	JButton tetrisButton, ticTacButton, exitButton;
	JPanel startPanel, parentPanel;
	JFrame frameParam;
	
	//every new panel will need a CardLayout parameter and a JPanel parameter
	CardLayout cardLayoutParam;
	
	final static String key = "Start Menu Panel";

	public StartMenu(CardLayout cardParam, JPanel parent, JFrame frame)
	{
		frameParam = frame;
		parentPanel = parent;
		cardLayoutParam = cardParam;
		startPanel = new JPanel();
		StartMenuButtonListener startMenuListener = new StartMenuButtonListener();
		
		setPreferredSize(new Dimension(500, 500));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createRigidArea(new Dimension(0, 120)));
		
		//tic tac toe button
		ticTacButton = new JButton("Play Tic-Tac-Toe!");
		ticTacButton.setMaximumSize(new Dimension(170, 70));
		ticTacButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		ticTacButton.addActionListener(startMenuListener);
		add(ticTacButton);
		
		add(Box.createRigidArea(new Dimension(0, 30)));
		
		//tetris button
		tetrisButton = new JButton("Play Tetris!");
		tetrisButton.setMaximumSize(new Dimension(170, 70));
		tetrisButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		tetrisButton.addActionListener(startMenuListener);
		add(tetrisButton);
		
		add(Box.createRigidArea(new Dimension(0, 30)));
		
		//exit button
		exitButton = new JButton("Exit");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setMaximumSize(new Dimension(80, 40));
		exitButton.addActionListener(startMenuListener);
		add(exitButton);
		
		
	}
	
	public void makeInvisible()
	{
		startPanel.setVisible(false);
	}
	
	public String getKey()
	{
		return key;
	}
	
	private class StartMenuButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == ticTacButton)
			{
				cardLayoutParam.show(parentPanel, TTTMenu.key);
			}else if(event.getSource() == tetrisButton)
			{
				cardLayoutParam.show(parentPanel, TetrisMenu.key);
			}else if(event.getSource() == exitButton)
			{
				frameParam.setVisible(false);
				frameParam.dispose();
			}
			
			
		}
	}

}
