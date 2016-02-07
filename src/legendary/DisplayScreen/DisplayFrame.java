package legendary.DisplayScreen;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayFrame implements ActionListener{

	public void createDisplay() {
		JFrame frame = new DropdownMenuPanel();
		frame.setSize(1280, 720);
		frame.setTitle("Design Parser");
		
		JPanel rows = new JPanel(new FlowLayout());
		JPanel patternSelector = new PatternSelector();
		JPanel patternDisplay = new PatternDisplay();
		
		patternSelector.setPreferredSize(new Dimension(300, 700));
		patternDisplay.setPreferredSize(new Dimension(900, 700));
		
		rows.add(patternSelector);
		rows.add(patternDisplay);
		
		frame.add(rows);
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
