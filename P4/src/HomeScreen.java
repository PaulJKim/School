import javax.swing.*;

import java.awt.*;

public class HomeScreen extends JPanel {
	
	JPanel startFramePanel;
	JFrame mainFrame;
	
	//cardlayout used to hold each panel in the the program
	CardLayout cardLayout = new CardLayout();
	TTT ticTacBoard;
	
	public HomeScreen(JFrame param){
		
		mainFrame = param;
		
		startFramePanel = new JPanel();
		startFramePanel.setLayout(cardLayout);
		
		StartMenu startMenu = new StartMenu(cardLayout, startFramePanel, mainFrame);
		TTTMenu ticTacMenu = new TTTMenu(cardLayout, startFramePanel);
		
		//every panel is added to the card layout in this class except for
		//the Tetris and TTT classes
		
		TetrisInstructions tetrisInstructions = new TetrisInstructions(cardLayout, startFramePanel);
		TTTInstructions ticTacInstructions = new TTTInstructions(cardLayout, startFramePanel);
		//Tetris tetrisBoard = new Tetris(cardLayout, startFramePanel, tBoardInit);
		TetrisMenu tetrisMenu = new TetrisMenu(cardLayout, startFramePanel);
		
		startFramePanel.add(startMenu);
		startFramePanel.add(ticTacMenu);
		startFramePanel.add(tetrisMenu);
		startFramePanel.add(tetrisInstructions);
		startFramePanel.add(ticTacInstructions);
		//startFramePanel.add(tetrisBoard);
		
		cardLayout.addLayoutComponent(startMenu, startMenu.getKey());
		cardLayout.addLayoutComponent(ticTacMenu, ticTacMenu.getKey());
		cardLayout.addLayoutComponent(tetrisMenu, tetrisMenu.getKey());
		cardLayout.addLayoutComponent(tetrisInstructions, tetrisInstructions.getKey());
		cardLayout.addLayoutComponent(ticTacInstructions, ticTacInstructions.getKey());
		//cardLayout.addLayoutComponent(tetrisBoard, tetrisBoard.getKey());
		
		cardLayout.first(startFramePanel);
		
	}
	

}
