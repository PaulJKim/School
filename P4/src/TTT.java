import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


public class TTT extends JPanel {

	private JPanel TTTBoard, parentPanel, backButtonPanel, undoPanel;
	private JButton topLeft, topMiddle, topRight, middleLeft, middleMiddle, middleRight, bottomLeft, bottomMiddle, bottomRight, backButton, undo;
	private JLabel xTurn, oTurn, xWins, oWins;
	private ButtonListener listener;
	private int clicks, playerXScore, playerOScore;
	private JButton[] buttonArray = new JButton[10];
	private boolean playerWon, xWon;
	private CardLayout cardLayout;
	private Stack<JButton> moveStack;
	private String undoString = "";
	final static String key = "Tic-Tac-Toe Board";
	
    private static final String UNDOKEY = "Z";
    private Action undoKey = new AbstractAction(UNDOKEY) {
        public void actionPerformed(ActionEvent e) {
           if(clicks > 0){
        	   undoMethod();
           }
        }
    };
	public TTT(CardLayout cardLayoutParam, JPanel parent){
		
		cardLayout = cardLayoutParam;
		parentPanel = parent;
		moveStack = new Stack<JButton>();
		
		xTurn = new JLabel(" It's Player X's turn!");
		oTurn = new JLabel(" It's Player O's turn!");
		oTurn.setVisible(false);
		xWins = new JLabel(" Wins: " + playerXScore);
		oWins = new JLabel(" Wins: " + playerOScore);

		listener = new ButtonListener();
		
		TTTBoard = new JPanel();
	
		backButtonPanel = new JPanel();
		undoPanel = new JPanel();
		backButton = new JButton("Back");
		backButton.addActionListener(listener);
		backButtonPanel.add(backButton);
		undo = new JButton("Undo Move");
		undo.setFocusable(false);
		undo.addActionListener(listener);
		undo.setEnabled(false);
		
		undoPanel.add(undo);
		
		
		
		setLayout(new GridLayout(3,5));
		
		clicks = 0;
		playerWon = false;
		xWon = false;
		playerXScore = 0;
		playerOScore = 0;
		
		
		topLeft = new JButton("");
		topLeft.setFocusable(false);
		buttonArray[0] = topLeft;
		topLeft.addActionListener(listener);
		
		topMiddle = new JButton(" ");
		topMiddle.setFocusable(false);
		buttonArray[1] = topMiddle;
		topMiddle.addActionListener(listener);
		
		topRight = new JButton("  ");
		topRight.setFocusable(false);
		buttonArray[2] = topRight;
		topRight.addActionListener(listener);
		
		middleLeft = new JButton("   ");
		middleLeft.setFocusable(false);
		buttonArray[3] = middleLeft;
		middleLeft.addActionListener(listener);
		
		middleMiddle = new JButton("     ");
		middleMiddle.setFocusable(false);
		buttonArray[4] = middleMiddle;
		middleMiddle.addActionListener(listener);
		
		middleRight = new JButton("      ");
		middleRight.setFocusable(false);
		buttonArray[5] = middleRight;
		middleRight.addActionListener(listener);
		
		bottomLeft = new JButton("       ");
		bottomLeft.setFocusable(false);
		buttonArray[6] = bottomLeft;
		bottomLeft.addActionListener(listener);
		
		bottomMiddle = new JButton("        ");
		bottomMiddle.setFocusable(false);
		buttonArray[7] = bottomMiddle;
		bottomMiddle.addActionListener(listener);
		
		bottomRight = new JButton("          ");
		bottomRight.setFocusable(false);
		buttonArray[8] = bottomRight;
		bottomRight.addActionListener(listener);
		
		add(xWins);
		add(topLeft);
		add(topMiddle);
		add(topRight);
		add(oWins);
		add(xTurn);
		add(middleLeft);
		add(middleMiddle);
		add(middleRight);
		add(oTurn);
		add(backButtonPanel);
		add(bottomLeft);
		add(bottomMiddle);
		add(bottomRight);
		add(undoPanel);
		
		this.getInputMap().put(
                KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), UNDOKEY);
        this.getActionMap().put(UNDOKEY, undoKey);
        
        
	}
	
	public boolean checkWin(){
		if(topLeft.getText().equals(middleLeft.getText()) && middleLeft.getText().equals(bottomLeft.getText())){//left vertical
			playerWon = true;
			if(topLeft.getText().equals("X")){
				xWon = true;
			}
		}else if(topMiddle.getText().equals(middleMiddle.getText()) && middleMiddle.getText().equals(bottomMiddle.getText())){//middle vertical
			playerWon = true;
			if(topMiddle.getText().equals("X")){
				xWon = true;
			}
		}else if(topRight.getText().equals(middleRight.getText()) && middleRight.getText().equals(bottomRight.getText())){//right vertical
			playerWon = true;
			if(topRight.getText().equals("X")){
				xWon = true;
			}
		}else if(bottomLeft.getText().equals(bottomMiddle.getText()) && bottomLeft.getText().equals(bottomRight.getText())){//bottom horizontal
			playerWon = true;
			if(bottomLeft.getText().equals("X")){
				xWon = true;
			}
		}else if(middleLeft.getText().equals(middleMiddle.getText()) && middleMiddle.getText().equals(middleRight.getText())){//middle horizontal
			playerWon = true;
			if(middleLeft.getText().equals("X")){
				xWon = true;
			}
		}else if(topLeft.getText().equals(topMiddle.getText()) && topRight.getText().equals(topMiddle.getText())){//top horizontal
			playerWon = true;
			if(topLeft.getText().equals("X")){
				xWon = true;
			}
		}else if(topLeft.getText().equals(middleMiddle.getText()) && middleMiddle.getText().equals(bottomRight.getText())){//backslash diagonal
			playerWon = true;
			if(topLeft.getText().equals("X")){
				xWon = true;
			}
		}else if(topRight.getText().equals(middleMiddle.getText()) && middleMiddle.getText().equals(bottomLeft.getText())){//forward slash diagonal
			playerWon = true;
			if(topRight.getText().equals("X")){
				xWon = true;
			}
		}else{
			playerWon = false;
		}
		return playerWon;
	}
	
	public void checkStatus(){
		if((clicks > 8) || (checkWin() == true)){
			String result;
			removeActionListener();
			if(checkWin() == true){
				if(xWon == true){
					result = "Player X won!";
					playerXScore ++;
				}else{
					result = "Player O won";
					playerOScore ++;
				}
			}else{
				result = "It was a tie!";
			}
			xWins.setText(" Wins: " + playerXScore);
			oWins.setText(" Wins: " + playerOScore);
			result = result + "\nWould you like to play again?";
			int again = JOptionPane.showConfirmDialog(null, result, null, JOptionPane.YES_NO_OPTION);
			
			clearButtons();
			clicks = 0;
			playerWon = false;
			enableActionListener();
			xWon = false;
			xTurn.setVisible(true);
			oTurn.setVisible(false);
			undo.setEnabled(false);
			while(moveStack.isEmpty() == false){
				moveStack.pop();
			}
			if(again == JOptionPane.NO_OPTION){
				cardLayout.show(parentPanel, TTTMenu.key);
			}
				
		}
	}
	
	public void removeActionListener(){
		for(int temp = 0; temp < 9; temp++){
		buttonArray[temp].setEnabled(false);
		}
	}
	
	public void enableActionListener(){
		for(int temp = 0; temp < 9; temp++){
			buttonArray[temp].setEnabled(true);
			}
	}
	
	public void clearButtons(){
		String temp2 = "";
		for(int temp = 0; temp < 9; temp++){
			buttonArray[temp].setText(temp2);
			buttonArray[temp].setFont(new Font("sansserif",Font.BOLD,1));
			temp2 = temp2 + " ";
			}

	}
	
	public String getKey(){
		return key;
	}
	
	public void undoMethod(){
		moveStack.peek().setEnabled(true);
		moveStack.peek().setFont(new Font("sansserif", Font.BOLD, 1));
		moveStack.pop().setText(undoString);
		undoString += " ";
		
		if(moveStack.isEmpty() == true){
			undo.setEnabled(false);
		}
		if(clicks%2 == 0){
			oTurn.setVisible(true);
			xTurn.setVisible(false);
		}else{
			xTurn.setVisible(true);
			oTurn.setVisible(false);
		}
		clicks --;
	}

	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			requestFocus();
			if(backButton == e.getSource()){
				cardLayout.show(parentPanel, TTTMenu.key);
			}
			if(undo == e.getSource()){
				undoMethod();
			}
			for(int temp = 0; temp < 9; temp++){
				
				if(buttonArray[temp] == e.getSource()){
					if(buttonArray[temp].getText().equals("X") || buttonArray[temp].getText().equals("O")){
						JOptionPane.showMessageDialog(null, "That tile has already been chosen! Pick another!");
						break;
					}
					undo.setEnabled(true);
					moveStack.push(buttonArray[temp]);
					if(clicks%2 == 0){
						oTurn.setVisible(true);
						xTurn.setVisible(false);
						buttonArray[temp].setText("X");
						buttonArray[temp].setFont(new Font("sansserif",Font.BOLD,80));
					}else{		
						xTurn.setVisible(true);
						oTurn.setVisible(false);
						buttonArray[temp].setText("O");
						buttonArray[temp].setFont(new Font("sansserif",Font.BOLD,80));
					}
					clicks ++;
				}
			}
			
			
			
			checkStatus();
		}	
	}
	
	
	

	
	
}
