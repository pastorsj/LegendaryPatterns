package legendary.DisplayScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		if(e.getSource().equals(this.loadNewConfig)) {
			System.out.println("Load New Config");
		} else if(e.getSource().equals(this.exportGraph)) {
			System.out.println("Export Graph");
		} else if(e.getSource().equals(this.howTo)) {
			System.out.println("How to run the program");
		} else if(e.getSource().equals(this.about)) {
			System.out.println("About the program");
		} else {
			System.err.println("This source does not exist");
		}
	}
}
