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

	Map<IPatternDetector, Map<IClass, Set<IClass>>> patternInformation;
	Map<IPatternDetector, List<TreeNode>> checkBoxPatternArrays;
	List<CheckBoxNode> rootList;
	JPanel panel;

	public CheckBoxTree() {
		this.patternInformation = this.getPatternInformation();
		this.checkBoxPatternArrays = new HashMap<>();
		this.rootList = new ArrayList<>();
		this.initializeCheckBoxPatternArrays();
		this.initializeRootVector();
	}

	private Map<IPatternDetector, Map<IClass, Set<IClass>>> getPatternInformation() {
		// Need to get the pattern information
		// It is a map of pattern detectors to a list of the pattern sets...
		Map<IPatternDetector, Map<IClass, Set<IClass>>> detectors = new HashMap<>();
		IPatternDetector detector = ClassParser.getInstance().getDetector();
		while(detector != null){
			detectors.put(detector, detector.getKeyMap());
			detector = detector.getDecorated();
		}
		return detectors;
	}
	
	private void initializeCheckBoxPatternArrays() {
		for (IPatternDetector p : this.patternInformation.keySet()) {
			List<TreeNode> cbNodes = new ArrayList<>();
			for (IClass patternName : this.patternInformation.get(p).keySet()) {
				CheckBoxNode t = new CheckBoxNode(patternName.getClassName(), false, new ArrayList<>());
				cbNodes.add(t);
			}
			this.checkBoxPatternArrays.put(p, cbNodes);
		}
	}


	private void initializeRootVector() {
		for (IPatternDetector p : this.checkBoxPatternArrays.keySet()) {
			String patternName = p.getPatternName();
			CheckBoxNode t = new CheckBoxNode(patternName, false, this.checkBoxPatternArrays.get(p));
			this.rootList.add(t);
		}
	}

	public void createTree() {
		JTree tree = new JTree(this.rootList.toArray());
		System.out.println(this.rootList.size());
		
		CheckBoxRenderer renderer = new CheckBoxRenderer();
		tree.setCellRenderer(renderer);

		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(250, 600));
		scrollPane.setBorder(null);
		this.add(scrollPane);
	}
}