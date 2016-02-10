package legendary.mainScreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import legendary.client.DisplayDriver;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements ActionListener{
	
	private JButton chooseFile, analyseFile;
	private JFileChooser fileChooser;
	private File file;
	
	public ButtonPanel() {
		initializeButtons();
		this.setSize(new Dimension(100, 100));
		this.file = null;
	}
	
	private void initializeButtons() {
		JPanel layout = new JPanel(new BorderLayout());
		
		this.chooseFile = new JButton("Choose a Config File");
		this.analyseFile = new JButton("Analyse");
		this.fileChooser = new JFileChooser();
		
		this.chooseFile.addActionListener(this);
		this.analyseFile.addActionListener(this);
		
		layout.add(chooseFile, BorderLayout.NORTH);
		layout.add(analyseFile, BorderLayout.SOUTH);
		
		this.add(layout);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		LegendaryProperties properties = LegendaryProperties.getInstance();
		if(e.getSource().equals(this.chooseFile)) {
	      int returnVal = this.fileChooser.showOpenDialog(ButtonPanel.this);

	      if (returnVal == JFileChooser.APPROVE_OPTION) {
	        this.file = this.fileChooser.getSelectedFile();
	        System.out.println(this.file.getName());
	        if(this.file.getPath().endsWith(".properties")) {
	        	//Do stuff with the file
	        	properties.setFile(this.file);
	        	properties.readProperties();
	        } else {
	        	System.err.println("This is not a legit file");
	        }
	      }
		} else if(e.getSource().equals(this.analyseFile)) {
			if(properties.getFile() == null) {
				System.err.println("Print to panel: You have not chosen a file");
			} else {
				System.out.println(properties.getCurrentFilename());
				properties.analyse();
				System.out.println("Finished Analysis");
				//DisplayDriver.go();
			}
		}
	}
}
