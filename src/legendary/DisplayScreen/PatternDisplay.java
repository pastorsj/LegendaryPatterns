package legendary.DisplayScreen;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import legendary.mainScreen.LegendaryProperties;

@SuppressWarnings("serial")
public class PatternDisplay extends JPanel {

	public PatternDisplay() {
		LegendaryProperties properties = LegendaryProperties.getInstance();
		JLabel picLabel = new JLabel(new ImageIcon(
				properties.getOutputDirectory() + "GraphVizoutput.png"));
		try {
			Thread.sleep(2000);
			System.out.println("Waiting on image to load");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JScrollPane pane = new JScrollPane(picLabel);
		pane.setPreferredSize(new Dimension(950, 650));
		pane.setViewportView(picLabel);
		this.add(pane);
	}
}
