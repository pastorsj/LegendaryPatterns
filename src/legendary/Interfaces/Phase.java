package legendary.Interfaces;

import javax.swing.SwingWorker;

public interface Phase {
	public String getPhaseName();
	public String formatString();
	public SwingWorker<?, ?> getTask();
}
