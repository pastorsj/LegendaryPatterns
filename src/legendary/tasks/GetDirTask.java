package legendary.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

public class GetDirTask extends SwingWorker<List<String>, String> {

	private String dir;
	private int levels;
	public static String packageName;

	public GetDirTask(String string, String string2) {
		this.dir = string;
		this.levels = Integer.parseInt(string2);
	}

	@Override
	protected List<String> doInBackground() throws Exception {
		return getClassesFromDir(new File(dir), levels);
	}

	@Override
	protected void process(List<String> chunks) {
		firePropertyChange("files", 0, chunks.size());
	}
	
	/**
	 * Gets the classes from given directory.
	 *
	 * @param dir
	 *            the directory
	 * @return paths to the classes in the directory
	 */
	public List<String> getClassesFromDir(File dir, int dirlevels) {
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> res2 = new ArrayList<String>();
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles();
			for (int i = 0; i < dirFiles.length; i++) {
				res2.addAll(getClassesFromDir(dirFiles[i], dirlevels));
			}
			for (String r : res2) {
				r = r.replace("\\", ".");
				int start = r.lastIndexOf(packageName);
				r = r.substring(
						start,
						(r.contains(".java") ? r.lastIndexOf(".java") : r
								.length())).replace("\\", ".");
				r = r.substring(
						r.lastIndexOf(packageName),
						(r.contains(".class") ? r.lastIndexOf(".class") : r
								.length())).replace("\\", ".");
				int levels = r.length() - r.replace(".", "").length();
				if (dirlevels >= 0 && levels >= dirlevels)
					continue;
				res.add(r);
			}

		} else if (dir.toString().endsWith(".java")
				|| dir.toString().endsWith(".class")) {
			publish(dir.toString());
			res.add(dir.toString());
		}
		return res;
	}
}
