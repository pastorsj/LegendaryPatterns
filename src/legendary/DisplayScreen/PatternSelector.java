package legendary.DisplayScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class PatternSelector extends JPanel {
	
	private AdjustmentListener adjustmentListener;
	private JScrollPane pane;
	private CheckBoxTree checkBoxTree;
	private int oldVPos = 0;
    private int oldHPos = 0;
	
	public PatternSelector() {
		checkBoxTree = new CheckBoxTree();
		pane = new JScrollPane(checkBoxTree);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(300, 650));
		checkBoxTree.createTree();
		checkBoxTree.setBackground(Color.WHITE);
		pane.setViewportView(checkBoxTree);
		this.add(pane);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Click!");
				PatternDisplay.scrolled = false;
			}
		});
	}
	
	public void addListeners() {
		this.adjustmentListener = new AdjustmentListener() {
	        @Override
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            int vPos = pane.getVerticalScrollBar().getValue(), 
	                hPos = pane.getHorizontalScrollBar().getValue();
	 
	            if (e.getSource().equals(pane.getVerticalScrollBar()) 
	                    && vPos != oldVPos) {
	                oldVPos = vPos;
	                PatternDisplay.scrolled = true;
	            }
	            if (e.getSource().equals(pane.getHorizontalScrollBar())
	                    && hPos != oldHPos) {
	            	System.out.println("??????????????????????????");
	                oldHPos = hPos;
	                PatternDisplay.scrolled = true;
	            }
	        }
	    };
	    pane.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
		pane.getHorizontalScrollBar().addAdjustmentListener(adjustmentListener);
	}
}
