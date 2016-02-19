package legendary.DisplayScreen;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import legendary.mainScreen.LegendaryProperties;

@SuppressWarnings("serial")
public class PatternDisplay extends JPanel {

	private static PatternDisplay instance;
	private JScrollPane pane;
	private JLabel label;
	private ImageProxy icon;
	private int oldVPos = 0;
    private int oldHPos = 0;
	private AdjustmentListener adjustmentListener;
	public static boolean scrolled;

	private PatternDisplay() {
		
	}

	public static PatternDisplay getInstance() {
		if (instance == null) {
			instance = new PatternDisplay();
		}
		return instance;
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
	                scrolled = true;
	            }
	            if (e.getSource().equals(pane.getHorizontalScrollBar())
	                    && hPos != oldHPos) {
	                oldHPos = hPos;
	                scrolled = true;
	            }
	        }
	    };
	}
	
	public void loadImage() {
		// Use JLabel to show the image
		if (this.pane != null) {
			this.remove(this.pane);
			this.pane.setVisible(false);
		}
		System.out.println("Loading");
		LegendaryProperties properties = LegendaryProperties.getInstance();
		if (icon != null)
			icon.clear();
		icon = new ImageProxy(properties.getOutputDirectory() + "GraphVizOutput.png");
		this.label = new JLabel(icon);
		this.pane = new JScrollPane(this.label);
		this.pane.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
		this.pane.getHorizontalScrollBar().addAdjustmentListener(adjustmentListener);
		this.pane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.err.println("Click!");
				PatternDisplay.scrolled = false;
			}
		});
		this.add(this.pane, BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}

	public void update() {
		System.out.println("Updating");
		this.loadImage();

	}
}
