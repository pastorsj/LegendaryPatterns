package legendary.DisplayScreen;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DisplayFrame {

	private PatternDisplay patternDisplay;
	private PatternSelector patternSelector;
	private static JFrame frame;
	public static boolean changedFocus;
	public static boolean scrollBarAdjusted;

	public void createDisplay() {
		frame = new DropdownMenuPanel();
		frame.addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				System.out.println("In Focus");
				changedFocus = true;
			}
		});
		changedFocus = false;
		frame.setBackground(Color.WHITE);
		frame.setSize(1280, 720);
		frame.setTitle("Design Parser");

		JPanel rows = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rows.setBackground(Color.WHITE);
		this.patternSelector = new PatternSelector();
		this.patternSelector.addListeners();
		patternSelector.setBackground(Color.WHITE);
		this.patternDisplay = PatternDisplay.getInstance();
		this.patternDisplay.addListeners();
		patternDisplay.setBackground(Color.WHITE);

		rows.add(patternSelector);
		rows.add(patternDisplay);

		frame.add(rows);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		addMouseListeners();
	}

	private void addMouseListeners() {
		// TODO Auto-generated method stub
		frame.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Click!");
				PatternDisplay.scrolled = false;
			}
		});
	}

	public static void stop() {
		frame.dispose();
	}

}
