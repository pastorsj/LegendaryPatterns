package legendary.DisplayScreen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.JTree.DynamicUtilTreeNode;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxRenderer implements TreeCellRenderer {
	private JCheckBox checkBox = new JCheckBox();

	Color selectionForeground, selectionBackground, textForeground, textBackground;

	public CheckBoxRenderer() {
		Font fontValue;
		fontValue = UIManager.getFont("Tree.font");
		if (fontValue != null) {
			checkBox.setFont(fontValue);
		}

		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");
		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
	}

	protected JCheckBox getLeafRenderer() {
		// JCheckBox res = (JCheckBox) checkBox.clone();
		// return res;
		return this.checkBox;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {

		String stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, false);
		checkBox.setText(stringValue);
//		checkBox.setSelected(false);

		checkBox.setEnabled(tree.isEnabled());

		if (selected) {
			checkBox.setForeground(selectionForeground);
			checkBox.setBackground(selectionBackground);
		} else {
			checkBox.setForeground(textForeground);
			checkBox.setBackground(textBackground);
		}

//		System.out.println(this);
		value = ((DynamicUtilTreeNode) value).getUserObject();
		if ((value != null) && (value instanceof CheckBoxNode)) {
			CheckBoxNode node = (CheckBoxNode) value;
			checkBox.setText(node.getText());
			if (selected) {
				node.setSelected(!node.selected);
			}
			checkBox.setSelected(node.isSelected());
		}
		return checkBox;
	}
}
