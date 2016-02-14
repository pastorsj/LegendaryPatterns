package legendary.client;

import javax.swing.SwingUtilities;

import legendary.mainScreen.MainScreen;

public class Driver {
	
	public static void main(String[] args) {
		go();
	}
	
	public static void go(){
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainScreen mainScreen = new MainScreen();
				mainScreen.createScreen();
			}
		});
	}
}
