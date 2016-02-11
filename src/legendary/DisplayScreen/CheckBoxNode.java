package legendary.DisplayScreen;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import legendary.Interfaces.IClass;

@SuppressWarnings("serial")
public class CheckBoxNode extends DefaultMutableTreeNode {
	String text;
	private CheckBoxModel model;
	private Set<IClass> classes;

	boolean selected;

	public CheckBoxNode(String text, CheckBoxModel checkBoxModel,
			boolean selected) {
		this.text = text;
		this.model = checkBoxModel;
		this.selected = selected;
	}

	public CheckBoxNode(String name, Set<IClass> classes,
			CheckBoxModel checkBoxModel, boolean selected2) {
		this.classes = classes;
		this.selected = selected2;
		this.model = checkBoxModel;
		this.text = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean newValue) {
		selected = newValue;
		model.regen();
	}

	public String getText() {
		return text;
	}

	public void setText(String newValue) {
		text = newValue;
	}

	public String toString() {
		return getClass().getName() + "[" + text + "/" + selected + "]";
	}

	public Set<IClass> getClasses() {
		return this.classes;
	}
}
