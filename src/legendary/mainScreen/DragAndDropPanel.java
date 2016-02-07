package legendary.mainScreen;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DragAndDropPanel extends JPanel {

	public DragAndDropPanel() {
		final JTextArea text = new JTextArea();
		text.setPreferredSize(new Dimension(200, 200));
		text.setText("Drag and Drop a \n.properties file here");
		this.add(new JScrollPane(text));

		FileDrop fd = new FileDrop(null, text, /* dragBorder, */ new FileDrop.Listener() {
			public void filesDropped(File[] files) {
				File file = files[0];
				text.setText(file.getName());
			}
		});
	}
}
