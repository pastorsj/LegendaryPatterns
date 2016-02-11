package legendary.DisplayScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import legendary.Classes.ClassParser;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IPatternDetector;

public class CheckBoxModel {

	private TreeModel model;
	private DefaultMutableTreeNode root;
	private Map<IPatternDetector, Map<IClass, Set<IClass>>> patternInformation;

	public CheckBoxModel() {
		this.root = new DefaultMutableTreeNode();
		this.patternInformation = this.getPatternInformation();
		this.initializeCheckBoxPatternArrays();
		this.model = new DefaultTreeModel(this.root);
	}

	public TreeModel getModel() {
		return this.model;
	}

	private Map<IPatternDetector, Map<IClass, Set<IClass>>> getPatternInformation() {
		// Need to get the pattern information
		// It is a map of pattern detectors to a list of the pattern sets...
		Map<IPatternDetector, Map<IClass, Set<IClass>>> detectors = new HashMap<>();
		IPatternDetector detector = ClassParser.getInstance().getDetector();
		while (detector != null) {
			detectors.put(detector, detector.getKeyMap());
			detector = detector.getDecorated();
		}
		return detectors;
	}

	public void regen() {
		List<CheckBoxNode> nodes = new ArrayList<>();
		CheckBoxNode leaf = (CheckBoxNode) root.getFirstLeaf();
		while (leaf != null) {
			nodes.add(leaf);
			leaf = (CheckBoxNode) leaf.getNextSibling();
		}
		for (CheckBoxNode n : nodes) {
			for (IClass c : n.getClasses()) {
				c.setDrawable(false);
			}
		}
		for (CheckBoxNode n : nodes) {
			if (n.isSelected()) {
				for (IClass c : n.getClasses()) {
					System.out.println(c.getClassName());
					c.setDrawable(true);
				}
			}
		}
		ClassParser.getInstance().regenGV();
	}

	private void initializeCheckBoxPatternArrays() {
		for (IPatternDetector p : this.patternInformation.keySet()) {
			if (this.patternInformation.get(p).isEmpty()) {
				continue;
			}
			CheckBoxNode node = new CheckBoxNode(p.getPatternName(), this,
					false);
			for (IClass patternName : this.patternInformation.get(p).keySet()) {
				CheckBoxNode t = new CheckBoxNode(patternName.getClassName(),
						patternInformation.get(p).get(patternName), this, false);
				node.add(t);
			}
			this.root.add(node);
		}
	}
}
