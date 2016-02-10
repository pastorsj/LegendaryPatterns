package legendary.mainScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import legendary.Interfaces.IModel;
import legendary.Interfaces.Phase;
import legendary.phases.GetDirPhase;

@SuppressWarnings("serial")
public class ProgressBarPanel extends JPanel implements ActionListener,
		PropertyChangeListener {

	private JProgressBar overallPBar;
	private JProgressBar taskPBar;
	private Phase phase;
	private Phase[] phases;
	private SwingWorker<?, ?> task;
	private static ProgressBarPanel instance;
	public IModel model;
	public List<String> classes;

	private ProgressBarPanel() {
		taskPBar = new JProgressBar();
		taskPBar.setIndeterminate(true);
		taskPBar.setStringPainted(true);
		taskPBar.setString("");
		phase = new GetDirPhase();
	}

	public static ProgressBarPanel getInstance() {
		if (instance == null) {
			instance = new ProgressBarPanel();
		}
		return instance;
	}

	public void setPhases(Phase[] phases) {
		this.phases = phases;
		overallPBar = new JProgressBar(0, phases.length + 1);
		overallPBar.setValue(0);
		overallPBar.setStringPainted(true);
		overallPBar.setString("");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		taskPBar.setString(String.format(phase.formatString(),
				evt.getNewValue()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ButtonPanel.analyseFile)) {
			ButtonPanel.analyseFile.setEnabled(false);
			for (Phase p : phases) {
				phase = p;
				overallPBar.setString(p.getPhaseName());
				taskPBar.setString(String.format(p.formatString(), 0));
				this.task = phase.getTask();
				this.taskPBar.addPropertyChangeListener(this);
				this.task.execute();
				overallPBar.setValue(overallPBar.getValue() + 1);
			}
		}
	}

}
