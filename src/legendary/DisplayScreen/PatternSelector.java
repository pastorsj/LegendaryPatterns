package legendary.DisplayScreen;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PatternSelector extends JPanel {

	public PatternSelector() {
		CheckBoxTree checkBoxTree = new CheckBoxTree();
		System.out.println("Creating a new check box tree");
		checkBoxTree.setPreferredSize(new Dimension(300, 700));
		checkBoxTree.createTree();
		this.add(checkBoxTree);
	}
}
