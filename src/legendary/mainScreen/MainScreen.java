package legendary.mainScreen;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainScreen {
	
	public static JFrame frame;

	public void createScreen() {
		frame = new JFrame();
		frame.setSize(640, 480);
		frame.setTitle("Design Parser");

		JPanel rows = new JPanel(new GridLayout(3, 1));
		JPanel buttonPanel = new ButtonPanel();
		JPanel dndPanel = new DragAndDropPanel();
		JPanel pBarPanel = LegendaryProgressBar.getInstance();

		buttonPanel.setPreferredSize(new Dimension(100, 200));
		pBarPanel.setPreferredSize(new Dimension(300, 50));
		dndPanel.setPreferredSize(new Dimension(100, 100));

		rows.add(buttonPanel);
		rows.add(pBarPanel);
		rows.add(dndPanel);

		frame.add(rows);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
