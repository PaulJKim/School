import javax.swing.*;

import java.awt.*;

public class P4Arcade {

	
	
	public static void main(String[] args) {
		
		
		JFrame startFrame = new JFrame("P4 Arcade!");
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JFrame param to be passed to StartMenu for exit function
		HomeScreen homeScreen = new HomeScreen(startFrame);
		
		startFrame.getContentPane().add(homeScreen.startFramePanel);
		startFrame.pack();
		startFrame.setVisible(true);
		
	

	}

}
