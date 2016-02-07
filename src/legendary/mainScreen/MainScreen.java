package legendary.mainScreen;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainScreen{

	public void createScreen() {
		JFrame frame = new JFrame();
		JPanel buttonPanel = new ButtonPanel();
		
		frame.setSize(640, 480);
		frame.setTitle("Design Parser");
		
		frame.setLayout(new BorderLayout());
		frame.add(buttonPanel, BorderLayout.CENTER);
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
}
