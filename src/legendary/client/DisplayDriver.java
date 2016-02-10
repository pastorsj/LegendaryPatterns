package legendary.client;

import javax.swing.SwingUtilities;

import legendary.DisplayScreen.DisplayFrame;

public class DisplayDriver {

	public static void go() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				DisplayFrame displayScreen = new DisplayFrame();
				displayScreen.createDisplay();
			}
		});
	}
}
