package legendary.DisplayScreen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import legendary.client.Driver;
import legendary.mainScreen.LegendaryProperties;

@SuppressWarnings("serial")
public class DropdownMenuPanel extends JFrame implements ActionListener {

	private JMenuBar menuBar;
	private JMenu fileMenu, helpMenu;
	private JMenuItem loadNewConfig, exportGraph, howTo, about;

	public DropdownMenuPanel() {

		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.helpMenu = new JMenu("Help");
		this.loadNewConfig = fileMenu.add("Load New Config");
		this.exportGraph = fileMenu.add("Export Graph");
		this.howTo = this.helpMenu.add("How to use the program");
		this.about = this.helpMenu.add("About Information");
		this.menuBar.add(this.fileMenu);
		this.menuBar.add(this.helpMenu);

		this.loadNewConfig.addActionListener(this);
		this.exportGraph.addActionListener(this);
		this.howTo.addActionListener(this);
		this.about.addActionListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(this.menuBar);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.loadNewConfig)) {
			DisplayFrame.stop();
			Driver.go();
		} else if (e.getSource().equals(this.exportGraph)) {
			JFileChooser fc = new JFileChooser();
			int retVal = fc.showSaveDialog(this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				try {
					Files.copy(new File(LegendaryProperties.getInstance()
							.getOutputDirectory() + "GraphVizOutput.png")
							.toPath(), fc.getSelectedFile().toPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource().equals(this.howTo)) {
			readAndDisplayTxt("./docs/howTo.txt");
		} else if (e.getSource().equals(this.about)) {
			readAndDisplayTxt("./docs/about.txt");
		} else {
			System.err.println("This source does not exist");
		}
	}
	
	private void readAndDisplayTxt(String filename){
		JFrame aboutFrame = new JFrame();
		aboutFrame.setLocationRelativeTo(null);
		JLabel label = new JLabel();
		label.setFont(new Font("Arial", 0, 20));
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					filename));
			String line = br.readLine();
			List<String> listOfStrings = new ArrayList<>();
			while (line != null) {
				listOfStrings.add(line + "  <br>  ");
				line = br.readLine();
			}
			label.setText("<html>ABOUT:<br>");
			for(String s : listOfStrings){
				label.setText(label.getText() + s);
			}
			label.setText(label.getText() + "</html>");
			aboutFrame.getContentPane().add(label);
			aboutFrame.pack();
			aboutFrame.setVisible(true);
			br.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
