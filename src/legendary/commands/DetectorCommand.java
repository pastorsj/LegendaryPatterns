package legendary.commands;

import legendary.Interfaces.ICommand;
import legendary.Interfaces.IPatternDetector;
import legendary.detectors.AbstractPatternDetector;
import legendary.mainScreen.LegendaryProperties;

public class DetectorCommand implements ICommand{
	private Class<? extends IPatternDetector> detector;
	
	public DetectorCommand(Class<? extends AbstractPatternDetector> detector){
		this.detector = detector;
	}
	
	@Override
	public void execute()
	{
		LegendaryProperties.getInstance().updateDetector(this.detector);
	}
}
