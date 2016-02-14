package legendary.DisplayScreen;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import legendary.mainScreen.LegendaryProperties;

@SuppressWarnings("serial")
public class PatternDisplay extends JPanel {

	private static PatternDisplay instance;
	private JScrollPane pane;
	private JPanel contentPane;

	private PatternDisplay() {
	}

	public static PatternDisplay getInstance() {
		if (instance == null) {
			instance = new PatternDisplay();
		}
		return instance;
	}
	
	public void loadImage() {
		// Use JLabel to show the image
		if(this.pane != null) {
			this.pane.setVisible(false);
		}
		System.out.println("Loading");
		LegendaryProperties properties = LegendaryProperties.getInstance();
		Icon gvIcon = new ImageProxy(properties.getOutputDirectory()
				+ "GraphVizOutput.png");
		this.pane = new JScrollPane(new JLabel(gvIcon));
		this.add(this.pane, BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}

	public void update() {
		System.out.println("Updating");
		this.loadImage();
	}
}
