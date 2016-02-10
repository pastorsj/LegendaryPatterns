package legendary.phases;

import javax.swing.SwingWorker;

import legendary.Interfaces.Phase;
import legendary.mainScreen.LegendaryProperties;
import legendary.tasks.GetDirTask;

public class GetDirPhase implements Phase {

	@Override
	public String formatString() {
		return "Found %d classes";
	}

	@Override
	public String getPhaseName() {
		return "GetDir";
	}

	@Override
	public SwingWorker<?, ?> getTask() {
		LegendaryProperties properties = LegendaryProperties.getInstance();
		return new GetDirTask(properties.getPropertyMap().get("inputFolder"),
				properties.getPropertyMap().get("dirLevels"));
	}

}
