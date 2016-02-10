package legendary.phases;

import javax.swing.SwingWorker;

import legendary.Interfaces.IPatternDetector;
import legendary.Interfaces.Phase;

public class DetectorPhase implements Phase {

	private Class<? extends IPatternDetector> detector;

	public DetectorPhase(Class<? extends IPatternDetector> detector) {
		this.detector = detector;
	}

	@Override
	public String getPhaseName() {
		return "PatternDetection";
	}

	@Override
	public String formatString() {
		return "Analyzed %d classes";
	}

	@Override
	public SwingWorker<?, ?> getTask() {
		return new DetectorTask(detector);
	}

}
