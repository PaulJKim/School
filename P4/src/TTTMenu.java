import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class TTTMenu extends JPanel{
	
	JPanel menuPanel, parentPanel;
	JButton start, instructions, back;
	ButtonListener listener;
	CardLayout cardLayout;
	
	final static String key = "Tic Tac Toe Menu Panel";
		
	public TTTMenu(CardLayout cardLayoutParam, JPanel parent){
		
		cardLayout = cardLayoutParam;
		parentPanel = parent;
		
		menuPanel = new JPanel();
		setPreferredSize(new Dimension(500, 500));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createRigidArea(new Dimension(0, 120)));
		
		start = new JButton("Start");
		start.setMaximumSize(new Dimension(170, 70));
		listener = new ButtonListener();
		start.addActionListener(listener);
		add(start);
		
		add(Box.createRigidArea(new Dimension(0, 30)));
		
		instructions = new JButton("Instructions");
		instructions.setMaximumSize(new Dimension(170, 70));
		instructions.addActionListener(listener);
		add(instructions);
		
		add(Box.createRigidArea(new Dimension(0, 30)));
		
		back = new JButton("Back");
		back.setMaximumSize(new Dimension(170, 70));
		back.addActionListener(listener);
		add(back);
		
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		back.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		
		
	}
	
	public String getKey()
	{
		return key;
	}
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == start){
				TTT ticTacGame = new TTT(cardLayout, parentPanel);
				parentPanel.add(ticTacGame);
				cardLayout.addLayoutComponent(ticTacGame, TTT.key);
				cardLayout.show(parentPanel, TTT.key);
			}else if(event.getSource() == instructions){
				cardLayout.show(parentPanel, TTTInstructions.key);
			}else if(event.getSource() == back){
				cardLayout.show(parentPanel, StartMenu.key);
			}
		}
	}
}
