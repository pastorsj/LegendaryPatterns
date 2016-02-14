package legendary.mainScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import legendary.Interfaces.ICommand;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPatternDetector;
import legendary.client.DisplayDriver;
import legendary.commands.DetectorCommand;
import legendary.commands.GVGenCommand;
import legendary.commands.ModelGenCommand;
import legendary.detectors.AdapterDetector;
import legendary.detectors.CompositeDetector;
import legendary.detectors.DecoratorDetector;
import legendary.detectors.SingletonDetector;

public class LegendaryProperties {

	private static LegendaryProperties instance;
	private Properties properties;
	private Map<String, String> propertyMap;
	private IPatternDetector detector;
	private IModel model;
	private static Map<String, ICommand> commandMap = new HashMap<>();
	private File file;

	static {
		commandMap.put("Singleton-Detection", new DetectorCommand(
				SingletonDetector.class));
		commandMap.put("Adapter-Detection", new DetectorCommand(
				AdapterDetector.class));
		commandMap.put("Decorator-Detection", new DetectorCommand(
				DecoratorDetector.class));
		commandMap.put("Composite-Detection", new DetectorCommand(
				CompositeDetector.class));
		commandMap.put("Class-Loading", new ModelGenCommand());
		commandMap.put("DOT-Generation", new GVGenCommand());
	}

	private LegendaryProperties() {
		this.propertyMap = new HashMap<>();
		this.properties = new Properties();
		this.file = null;
	}

	public static LegendaryProperties getInstance() {
		if (instance == null)
			instance = new LegendaryProperties();
		return instance;
	}

	public Map<String, String> getPropertyMap() {
		return this.propertyMap;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getCurrentFilename() {
		return this.file.getName();
	}

	public File getFile() {
		return this.file;
	}

	public String getDotPath() {
		return this.propertyMap.get("dotPath");
	}

	public String getOutputDirectory() {
		return this.propertyMap.get("outputDirectory");
	}

	public void readProperties() {
		InputStream reader = null;
		try {
			reader = new FileInputStream(this.file);
			properties.load(reader);
			this.propertyMap.put("dirLevels",
					properties.getProperty("Directory-Levels"));
			this.propertyMap.put("inputFolder",
					properties.getProperty("Input-Folder"));
			this.propertyMap.put("inputClasses",
					properties.getProperty("Input-Classes"));
			this.propertyMap.put("outputDirectory",
					properties.getProperty("Output-Directory"));
			this.propertyMap.put("dotPath", properties.getProperty("Dot-Path"));
			this.propertyMap.put("phases", properties.getProperty("Phases"));
			this.propertyMap.put("adapterMethodDelegation",
					properties.getProperty("Adapter-MethodDelegation", "1"));
			this.propertyMap.put("decoratorMethodDelegation",
					properties.getProperty("Decorator-MethodDelegation", "1"));
			this.propertyMap.put("singletonGetInstance", properties
					.getProperty("Singleton-RequireGetInstance", "true"));
			this.propertyMap.put("maxCompositeDistance",
					properties.getProperty("Max-Composite-Distance", "2"));
			this.propertyMap.put("compositeGetChildren",
					properties.getProperty("Composite-Get-Children", "false"));
			this.propertyMap.put("compositeGetChildrenName", properties
					.getProperty("Composite-Get-Children-Name", "getChildren"));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void analyse() {
		String[] phaseList = propertyMap.get("phases").split(", ");
		String[] stageList = new String[phaseList.length];
		for(int i = 0; i < phaseList.length; i++){
			stageList[i] = commandMap.get(phaseList[i]).name();
		}
		LegendaryProgressBar.getInstance().begin(stageList);
		Thread t = new Thread() {
			public void run() {
				for (String s : phaseList) {
					commandMap.get(s).execute();
					LegendaryProgressBar.getInstance().finishTask();
					LegendaryProgressBar.getInstance().incrementBy(1);
				}
				DisplayDriver.go();
			}
		};
		t.start();
	}

	public void updateDetector(Class<? extends IPatternDetector> detector) {
		IPatternDetector temp;
		try {
			temp = detector.newInstance();
			if (this.detector != null)
				temp.addDetector(this.detector);
			this.detector = temp;
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setModel(IModel legendaryModel) {
		this.model = legendaryModel;
	}

	public IModel getModel() {
		return this.model;
	}

	public IPatternDetector getDetector() {
		return this.detector;
	}
}
