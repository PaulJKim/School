import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisInstructions extends JPanel{
	
	JPanel tetrisInstructionsPanel, parentPanel;
	JButton backButton;
	CardLayout cardLayout;
	JLabel firstLabel, secondLabel, thirdLabel, fourthLabel;
	final static String key = "tetris instructions";
	
	
	public TetrisInstructions(CardLayout cardLayoutParam, JPanel parent)
	{
		cardLayout = cardLayoutParam;
		parentPanel = parent;
		BackButtonListener backListener = new BackButtonListener();
		
		tetrisInstructionsPanel = new JPanel();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(500, 500));
		
		firstLabel = new JLabel("<html>1. Use the left and right arrows to move pieces to laterally <br><br><html>"
				+ "<html>2. Use the up arrow key to turn pieces clockwise and the down arrow key<br> to turn pieces counterclockwise<br><br><html>"
				+ "<html>3. When a row is completely filled with ten blocks, it will disappear<br><br><html>"
				+ "<html>4. The game ends either when all of the pieces are generated or the lines <br>reach the top of the board.<html>");
		
		add(firstLabel, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		backButton = new JButton("Back");
		backButton.setMaximumSize(new Dimension(170, 40));
		backButton.addActionListener(backListener);
		buttonPanel.add(backButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		
	}
	
	public String getKey()
	{
		return key;
	}
	
	private class BackButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == backButton)
			{
				cardLayout.show(parentPanel, TetrisMenu.key);
			}
		}
	}

}
