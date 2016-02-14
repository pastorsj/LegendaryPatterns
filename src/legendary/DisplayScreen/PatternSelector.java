package legendary.DisplayScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PatternSelector extends JPanel {

	public PatternSelector() {
		CheckBoxTree checkBoxTree = new CheckBoxTree();
		JScrollPane pane = new JScrollPane(checkBoxTree);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(300, 650));
		checkBoxTree.createTree();
		checkBoxTree.setBackground(Color.WHITE);
		pane.setViewportView(checkBoxTree);
		this.add(pane);
		pane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				DisplayFrame.scrollBarAdjusted = true;
			}
		});
	}
}
