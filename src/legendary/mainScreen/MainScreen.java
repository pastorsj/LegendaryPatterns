package legendary.mainScreen;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainScreen{
	
	public void createScreen() {
		JFrame frame = new JFrame();
		frame.setSize(640, 480);
		frame.setTitle("Design Parser");
		
		JPanel rows = new JPanel(new GridLayout(3, 1));
		JPanel buttonPanel = new ButtonPanel();
		JPanel dndPanel = new DragAndDropPanel();
		JPanel pbarPanel = new ProgressBarPanel();
		
		buttonPanel.setPreferredSize(new Dimension(100, 200));
		dndPanel.setPreferredSize(new Dimension(100, 100));
		
		rows.add(buttonPanel);
		rows.add(dndPanel);
		
		frame.add(rows);
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	

	}
}
