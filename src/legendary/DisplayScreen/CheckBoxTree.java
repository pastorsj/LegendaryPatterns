package legendary.DisplayScreen;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

@SuppressWarnings("serial")
public class CheckBoxTree extends JPanel {

	JPanel panel;

	public void createTree() {
		CheckBoxModel model = new CheckBoxModel();
		JTree tree = new JTree(model.getModel());
		
		CheckBoxRenderer renderer = new CheckBoxRenderer();
		tree.setCellRenderer(renderer);

		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(250, 600));
		scrollPane.setBorder(null);
		this.add(scrollPane);
	}
}