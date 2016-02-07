package legendary.mainScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LegendaryProperties {

	private static LegendaryProperties instance = new LegendaryProperties();
	private Properties properties;
	private Map<String, String> propertyMap;
	
	private LegendaryProperties() {
		this.propertyMap = new HashMap<>();
		this.properties = new Properties();
	}
	
	public static LegendaryProperties getInstance() {
		return instance;
	}
	
	public void readProperties(File file) {
		InputStream reader = null;
		try {
			reader = new FileInputStream(file);
			properties.load(reader);
			this.propertyMap.put("inputFolder", properties.getProperty("Input-Folder"));
			this.propertyMap.put("inputClasses", properties.getProperty("Input-Classes"));
			this.propertyMap.put("outputDirectory", properties.getProperty("Output-Directory"));
			this.propertyMap.put("dotPath", properties.getProperty("Dot-Path"));
			this.propertyMap.put("phases", properties.getProperty("Phases"));
			this.propertyMap.put("adapterMethodDelegation", properties.getProperty("Adapter-MethodDelegation", "1"));
			this.propertyMap.put("decoratorMethodDelegation", properties.getProperty("Decorator-MethodDelegation", "1"));
			this.propertyMap.put("singletonGetInstance", properties.getProperty("Singleton-RequireGetInstance", "true"));
			this.propertyMap.put("maxCompositeDistance", properties.getProperty("Max-Composite-Distance", "2"));
			this.propertyMap.put("compositeGetChildren", properties.getProperty("Composite-Get-Children", "false"));
			this.propertyMap.put("compositeGetChildrenName", properties.getProperty("Composite-Get-Children-Name", "getChildren"));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void analyse(File file) {
		
	}
}
