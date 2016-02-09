package legendary.DisplayScreen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import legendary.Interfaces.IPatternDetector;
import legendary.detectors.AdapterDetector;
import legendary.detectors.CompositeDetector;
import legendary.detectors.DecoratorDetector;
import legendary.detectors.SingletonDetector;

@SuppressWarnings("serial")
public class CheckBoxTree extends JPanel {

	Map<IPatternDetector, List<String>> patternInformation;
	Map<IPatternDetector, List<CheckBoxNode>> checkBoxPatternArrays;
	int numberOfPatterns;
	Vector<?> rootVector;
	JPanel panel;

	public CheckBoxTree() {
		this.patternInformation = this.getPatternInformation();
		this.checkBoxPatternArrays = new HashMap<>();
		this.initializeCheckBoxPatternArrays();
		this.numberOfPatterns = this.getNumberOfGeneralPatternsDetected();
		this.initializeRootVector();
	}

	private void initializeCheckBoxPatternArrays() {
		for (IPatternDetector p : this.patternInformation.keySet()) {
			List<CheckBoxNode> cbNodes = new ArrayList<>();
			for (String patternName : this.patternInformation.get(p)) {
				cbNodes.add(new CheckBoxNode(patternName, false));
			}
			this.checkBoxPatternArrays.put(p, cbNodes);
		}
	}

	private Map<IPatternDetector, List<String>> getPatternInformation() {
		// Need to get the pattern information
		// It is a map of pattern detectors to a list of the pattern sets...
		Map<IPatternDetector, List<String>> detectors = new HashMap<>();
		detectors.put(new AdapterDetector(), new ArrayList<>());
		detectors.put(new DecoratorDetector(), new ArrayList<>());
		detectors.put(new SingletonDetector(), new ArrayList<>());
		detectors.put(new CompositeDetector(), new ArrayList<>());
		return detectors;
	}

	private void initializeRootVector() {
		List<Vector<?>> nodes = new ArrayList<>();
		for (IPatternDetector p : this.checkBoxPatternArrays.keySet()) {
			// TODO: Replace with actual pattern being detected
			Vector<?> v = new NamedVector("Pattern P", this.checkBoxPatternArrays.get(p).toArray());
			nodes.add(v);
		}
		this.rootVector = new NamedVector("Patterns Detected", nodes.toArray());
	}

	private int getNumberOfGeneralPatternsDetected() {
		// Get the number of general patterns detected
		// Currently, we have max 4 patterns, Adapter, Decorator, Composite and
		// Singleton
		return this.patternInformation.keySet().size();
	}

	public void createTree() {
		JTree tree = new JTree(this.rootVector);

		CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);

		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(300, 700));
		scrollPane.setBorder(null);
		this.add(scrollPane);
	}

}

class CheckBoxNodeRenderer implements TreeCellRenderer {
	private JCheckBox leafRenderer = new JCheckBox();

	private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();

	Color selectionForeground, selectionBackground, textForeground, textBackground;

	protected JCheckBox getLeafRenderer() {
		return leafRenderer;
	}

	public CheckBoxNodeRenderer() {
		Font fontValue;
		fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) {
			leafRenderer.setFont(fontValue);
		}
		
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {

		Component returnValue;
		if (leaf) {

			String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, false);
			leafRenderer.setText(stringValue);
			leafRenderer.setSelected(false);

			leafRenderer.setEnabled(tree.isEnabled());

			if (selected) {
				leafRenderer.setForeground(selectionForeground);
				leafRenderer.setBackground(selectionBackground);
			} else {
				leafRenderer.setForeground(textForeground);
				leafRenderer.setBackground(textBackground);
			}

			if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
				Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
				if (userObject instanceof CheckBoxNode) {
					CheckBoxNode node = (CheckBoxNode) userObject;
					leafRenderer.setText(node.getText());
					leafRenderer.setSelected(node.isSelected());
				}
			}
			returnValue = leafRenderer;
		} else {
			returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row,
					hasFocus);
		}
		return returnValue;
	}
}