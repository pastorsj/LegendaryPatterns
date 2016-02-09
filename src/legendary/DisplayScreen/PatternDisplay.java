package legendary.DisplayScreen;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PatternDisplay extends JPanel {

	public PatternDisplay() {
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File(
					"/Users/SamPastoriza/Documents/Programming/Java Development/LegendaryPatterns/input_output/GraphVizoutput.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			JScrollPane pane = new JScrollPane(picLabel);
			pane.setPreferredSize(new Dimension(950, 700));
			pane.setViewportView(picLabel);
			this.add(pane);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
