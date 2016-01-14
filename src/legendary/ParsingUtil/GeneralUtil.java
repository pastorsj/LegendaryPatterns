package legendary.ParsingUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import legendary.asm.DesignParser;

public class GeneralUtil {
	public static List<String> getClassesFromDir(File dir) {
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> res2 = new ArrayList<String>();
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles();
			for (int i = 0; i < dirFiles.length; i++) {
				res2.addAll(getClassesFromDir(dirFiles[i]));
			}
			for (String r : res2) {
				r = r.substring(r.lastIndexOf(DesignParser.packageName),
						(r.contains("java") ? r.lastIndexOf("java") - 1 : r.length())).replace("\\", ".");
				res.add(r);
			}

		} else
			res.add(dir.toString());
		return res;
	}
}
