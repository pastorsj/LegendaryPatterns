package legendary.mainScreen;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.iharder.dnd.FileDrop;

public class DragAndDropPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LegendaryProperties properties = LegendaryProperties.getInstance();
	
	public DragAndDropPanel() {
		final JTextArea text = new JTextArea();
		text.setPreferredSize(new Dimension(300, 75));
		if(properties.getFile() == null) {
			text.setText("Drag and drop a .properties type file here");			
		} else {
			text.setText(properties.getCurrentFilename());
		}
		this.add(new JScrollPane(text));

		new FileDrop(null, text, /* dragBorder, */ new FileDrop.Listener() {
			public void filesDropped(File[] files) {
				File file = files[0];
				if(file.getName().endsWith(".properties")) {
					properties.setFile(file);
					text.setText(file.getName());
				} else {
					text.setText("Please drop a .properties type file");
				}
			}
		});
	}
}
