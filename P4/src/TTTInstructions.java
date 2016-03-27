import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class TTTInstructions extends JPanel{

	private JLabel label;
	private JPanel TTTInstructionsPanel, parentPanel, backPanel;
	private JButton back;
	final static String key = "Tic-Tac-Toe Instructions";
	CardLayout cardLayout;
	ButtonListener listener;
	
	public TTTInstructions(CardLayout cardLayoutParam, JPanel parent){
		
		cardLayout = cardLayoutParam;
		parentPanel = parent;
		
		listener = new ButtonListener();
		
		TTTInstructionsPanel = new JPanel();
		setLayout(new BorderLayout());
		
		backPanel = new JPanel();
		
		back = new JButton("Back");
		back.addActionListener(listener);
		
		
		label = new JLabel("<html>When the game starts, a blank board will appear.<br><br>"
				+ "1) The first person to click on a piece will be named Player X.<br><br>"
				+ "2) The other person will be named Player O.<br><br>"
				+ "3) Player X's amount of wins can be seen on the left side of the window,<br><br>"
				+ "and Player O's amount of wins can be seen on the right.<br><br>"
				+ "4) The players can see whose turn it is by looking on their side of the screen.<br><br>"
				+ "5) The phrase \"It's your turn!\" will be on the side of the player who is to go next.<br><br>"
				+ "6) The first person to get three of their letters in a row wins!<html>");
		
		add(label, BorderLayout.NORTH);
		backPanel.add(back);
		add(backPanel, BorderLayout.SOUTH);
		
	}
	
	public String getKey(){
		return key;
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(parentPanel, TTTMenu.key);
		}
	}
}
