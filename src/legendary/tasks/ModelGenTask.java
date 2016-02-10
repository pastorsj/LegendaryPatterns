package legendary.tasks;

import java.util.List;

import javax.swing.SwingWorker;

import legendary.Interfaces.IClass;
import legendary.asm.DesignParser;
import legendary.mainScreen.ProgressBarPanel;

public class ModelGenTask extends SwingWorker<List<IClass>, IClass> {

	@Override
	protected List<IClass> doInBackground() throws Exception {
		int i = 0;
		for(String s : ProgressBarPanel.getInstance().classes){
			i++;
			DesignParser.executeASM(s, ProgressBarPanel.getInstance().model, true);
			firePropertyChange("Classes", "", i);
		}
		return null;
	}

}
