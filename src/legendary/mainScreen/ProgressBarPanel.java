package legendary.mainScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class ProgressBarPanel extends JPanel implements ActionListener,
		PropertyChangeListener {

	private JProgressBar overallPBar;
	private JProgressBar taskPBar;
	private LegendaryProperties properties = LegendaryProperties.getInstance();
	private SwingWorker<List<String>, String> dirtask = new GetDirTask(properties
			.getPropertyMap().get("inputFolder"), properties.getPropertyMap()
			.get("dirLevels"));

	public ProgressBarPanel() {
		overallPBar = new JProgressBar(0, 3);
		overallPBar.setValue(0);
		overallPBar.setStringPainted(true);
		overallPBar.setString("");
		taskPBar = new JProgressBar();
		taskPBar.setIndeterminate(true);
		taskPBar.setStringPainted(true);
		taskPBar.setString("");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (overallPBar.getValue() == 0) {
			taskPBar.setString(String.format("Found %d classes",
					evt.getNewValue()));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ButtonPanel.analyseFile)) {
			ButtonPanel.analyseFile.setEnabled(false);
			overallPBar.setString("Finding classes...");
			taskPBar.setString("Found 0 classes");
			dirtask.addPropertyChangeListener(this);
		}
	}

}
