package legendary.DisplayScreen;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import legendary.mainScreen.LegendaryProperties;

@SuppressWarnings("serial")
public class PatternDisplay extends JPanel {

	private static PatternDisplay instance;
	private JScrollPane pane;
	private JLabel picture;

	private PatternDisplay() {
	}

	public static PatternDisplay getInstance() {
		if (instance == null) {
			instance = new PatternDisplay();
		}
		return instance;
	}

	public void createPane() {
		this.pane = new JScrollPane(this.picture);
		pane.setPreferredSize(new Dimension(950, 650));
		pane.setViewportView(this.picture);
		this.add(pane);
	}

	public void getImage() {
		LegendaryProperties properties = LegendaryProperties.getInstance();
		this.picture = new JLabel(new ImageIcon(properties.getOutputDirectory() + "GraphVizOutput.png"));
	}

	public void update() {
		this.getImage();
		LegendaryProperties properties = LegendaryProperties.getInstance();
		try {
			Desktop.getDesktop().open(new File(properties.getOutputDirectory() + "GraphVizOutput.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
