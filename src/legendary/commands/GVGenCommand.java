package legendary.commands;

import java.io.IOException;

import legendary.Classes.ClassParser;
import legendary.Interfaces.ICommand;
import legendary.Interfaces.IModel;
import legendary.ParsingUtil.GeneralUtil;
import legendary.mainScreen.LegendaryProperties;

public class GVGenCommand implements ICommand {

	@Override
	public void execute() {
		IModel m = LegendaryProperties.getInstance().getModel();
		ClassParser.getInstance().addDetector(LegendaryProperties.getInstance().getDetector());
		StringBuilder builder = new StringBuilder();
		try {
			ClassParser.getInstance().makeGraphViz(m, builder);
			GeneralUtil.writeAndExecGraphViz(builder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
