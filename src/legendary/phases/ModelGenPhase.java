package legendary.phases;

import javax.swing.SwingWorker;

import legendary.Interfaces.Phase;
import legendary.mainScreen.ProgressBarPanel;
import legendary.tasks.ModelGenTask;

public class ModelGenPhase implements Phase {

	@Override
	public String getPhaseName() {
		return "ModelGeneration";
	}

	@Override
	public String formatString() {
		return "Parsed %d of " + ProgressBarPanel.getInstance().classes.size()
				+ " classes";
	}

	@Override
	public SwingWorker<?, ?> getTask() {
		return new ModelGenTask();
	}

}
