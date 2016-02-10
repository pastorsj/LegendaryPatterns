package legendary.DisplayScreen;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayFrame implements ActionListener{

	public void createDisplay() {
		JFrame frame = new DropdownMenuPanel();
		frame.setBackground(Color.WHITE);
		frame.setSize(1280, 720);
		frame.setTitle("Design Parser");
		
		JPanel rows = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rows.setBackground(Color.WHITE);
		JPanel patternSelector = new PatternSelector();
		patternSelector.setBackground(Color.WHITE);
		JPanel patternDisplay = new PatternDisplay();
		patternDisplay.setBackground(Color.WHITE);
		
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
