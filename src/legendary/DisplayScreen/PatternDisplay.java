package legendary.DisplayScreen;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PatternDisplay extends JPanel {

	public PatternDisplay() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(
				"C:/Users/Administrator/Documents/GitHub/LegendaryPatterns/input_output/GraphVizoutput.png"));
		JScrollPane pane = new JScrollPane(picLabel);
		pane.setPreferredSize(new Dimension(950, 650));
		pane.setViewportView(picLabel);
		this.add(pane);
		this.setVisible(true);
		this.validate();
	}
}
