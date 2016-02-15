package legendary.DisplayScreen;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import legendary.ParsingUtil.GeneralUtil;
import legendary.mainScreen.LegendaryProperties;

@SuppressWarnings("serial")
public class PatternDisplay extends JPanel {

	private static PatternDisplay instance;
	private JScrollPane pane;
	private JLabel label;

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
		if (this.pane != null) {
			this.remove(this.pane);
			this.pane.setVisible(false);
		}
		System.out.println("Loading");
		LegendaryProperties properties = LegendaryProperties.getInstance();
		Icon gvIcon = new ImageProxy(properties.getOutputDirectory() + "GraphVizOutput" + GeneralUtil.fileNum + ".png");
		checkAndDeleteFiles();
		this.label = new JLabel(gvIcon);
		this.pane = new JScrollPane(this.label);
		this.add(this.pane, BorderLayout.CENTER);

		this.revalidate();
		this.repaint();
	}

	private void checkAndDeleteFiles() {
		// TODO Auto-generated method stub
		LegendaryProperties properties = LegendaryProperties.getInstance();
		Path path = FileSystems.getDefault().getPath(properties.getOutputDirectory(),
				"GraphVizOutput" + GeneralUtil.fileNum + ".png");
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		System.out.println("Updating");
		this.loadImage();

	}
}
