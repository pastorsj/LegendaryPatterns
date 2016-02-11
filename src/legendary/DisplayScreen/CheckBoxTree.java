package legendary.DisplayScreen;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import legendary.Classes.ClassParser;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IPatternDetector;
import legendary.detectors.AdapterDetector;
import legendary.detectors.CompositeDetector;
import legendary.detectors.DecoratorDetector;
import legendary.detectors.SingletonDetector;

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