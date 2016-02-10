package legendary.phases;

import java.util.List;

import javax.swing.SwingWorker;

import legendary.Interfaces.IModel;
import legendary.Interfaces.IPatternDetector;
import legendary.mainScreen.ProgressBarPanel;

public class DetectorTask extends SwingWorker<List<String>, String> {

	private Class<? extends IPatternDetector> detector;
	private IModel m;

	public DetectorTask(Class<? extends IPatternDetector> detector) {
		this.detector = detector;
		this.m = ProgressBarPanel.getInstance().model;
	}

	@Override
	protected List<String> doInBackground() throws Exception {
		
	}

}
