package legendary.mainScreen;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class LegendaryProgressBar extends JPanel {
	private static LegendaryProgressBar instance;

	private JProgressBar progressBar;
	private int value;
	private int taskInd = 0;

	private String[] tasks;

	private LegendaryProgressBar() {
		this.progressBar = new JProgressBar();
		this.progressBar.setStringPainted(true);
		this.progressBar.setString("");
//		this.progressBar.setVisible(false);
		this.progressBar.setMaximum(100);
		this.progressBar.setPreferredSize(new Dimension(300, 50));
		this.add(progressBar);
		this.value = 0;
	}

	public static LegendaryProgressBar getInstance() {
		if (instance == null) {
			instance = new LegendaryProgressBar();
		}
		return instance;
	}

	public void setMax(int max) {
		this.progressBar.setMaximum(max);
	}

	public void begin(String[] tasks) {
		this.progressBar.setValue(0);
		this.setMax(tasks.length);
		this.tasks = tasks;
		this.progressBar.setVisible(true);
		this.progressBar.setString(tasks[0]);
		this.setVisible(true);
		this.validate();
		this.repaint();
	}

	public JProgressBar getProgressBar() {
		return this.progressBar;
	}

	public void finishTask() {
		taskInd++;
		if (taskInd >= progressBar.getMaximum())
			progressBar.setString("Finished!");
		else
			progressBar.setString(tasks[taskInd]);
	}

	public void setValue(int value) {
		this.value = value;
		if (this.value <= progressBar.getMaximum())
			this.progressBar.setValue(this.value);
		else
			progressBar.setValue(progressBar.getMaximum());
	}

	public void incrementBy(int inc) {
		this.value += inc;
		if (this.value <= progressBar.getMaximum())
			this.progressBar.setValue(this.value);
		else
			progressBar.setValue(progressBar.getMaximum());
	}
}
