package legendary.client;

import legendary.DisplayScreen.DisplayFrame;

public class DisplayDriver {

	public static void go() {
		DisplayFrame displayScreen = new DisplayFrame();
		displayScreen.createDisplay();
	}
}
