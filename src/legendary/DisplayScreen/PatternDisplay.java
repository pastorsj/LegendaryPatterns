package legendary.DisplayScreen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PatternDisplay extends JPanel {

	public PatternDisplay() {
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File(
					"/Users/SamPastoriza/Documents/Programming/Java Development/LegendaryPatterns/input_output/GraphVizoutput.png"));
			Image dimg = myPicture.getScaledInstance(900, 700, Image.SCALE_SMOOTH);
			JLabel picLabel = new JLabel(new ImageIcon(dimg));
			this.add(picLabel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
