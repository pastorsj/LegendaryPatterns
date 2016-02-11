package legendary.mainScreen;

import javax.swing.JProgressBar;

public class LegendaryProgressBar {
	private static LegendaryProgressBar instance;
	
	private JProgressBar progressBar;
	private int value;
	
	private LegendaryProgressBar() {
		this.progressBar = new JProgressBar();
		this.progressBar.setStringPainted(true);
		this.progressBar.setMaximum(100);
		this.value = 0;
	}
	
	public static LegendaryProgressBar getInstance() {
		if(instance == null) {
			instance = new LegendaryProgressBar();
		}
		return instance;
	}
	
	public JProgressBar getProgressBar() {
		return this.progressBar;
	}
	
	public void setValue(int value) {
		this.value = value;
		this.progressBar.setValue(this.value);
	}
	
	public void incrementBy(int inc) {
		this.value += inc;
		this.progressBar.setValue(this.value);
	}
}
