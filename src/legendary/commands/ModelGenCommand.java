package legendary.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import legendary.Classes.LegendaryModel;
import legendary.Interfaces.ICommand;
import legendary.Interfaces.IModel;
import legendary.ParsingUtil.GeneralUtil;
import legendary.asm.DesignParser;
import legendary.mainScreen.LegendaryProperties;

public class ModelGenCommand implements ICommand {

	@Override
	public void execute() {
		List<String> classes = new ArrayList<String>();
		IModel legendaryModel = new LegendaryModel();
		String dir = LegendaryProperties.getInstance().getPropertyMap().get("inputFolder");
		GeneralUtil.packageName = dir.substring(dir.lastIndexOf("/") + 1);
		int levels = Integer.parseInt(LegendaryProperties.getInstance()
				.getPropertyMap().get("dirLevels"));
		classes.addAll(GeneralUtil.getClassesFromDir(new File(dir), levels));
		for (String className : classes) {
			try {
				DesignParser.executeASM(className, legendaryModel, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		legendaryModel.convertToGraph();
		LegendaryProperties.getInstance().setModel(legendaryModel);
	}

	@Override
	public String name() {
		return "Reading files and generating model...";
	}
}
