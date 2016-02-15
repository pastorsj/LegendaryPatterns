package legendary.DisplayScreen;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImageProxy implements Icon {
	ImageIcon imageIcon;
	String pathToImage;
	Thread retrievalThread;
	boolean retrieving = false;
	public static final String waitOnMe = "";
	public JPanel contentPane;
	public boolean checkImage;

	public ImageProxy(String path) {
		pathToImage = path;
	}

	public int getIconWidth() {
		if (imageIcon != null) {
			return imageIcon.getIconWidth();
		} else {
			return 800;
		}
	}

	public int getIconHeight() {
		if (imageIcon != null) {
			return imageIcon.getIconHeight();
		} else {
			return 600;
		}
	}

	public void paintIcon(final Component c, Graphics g, int x, int y) {
		System.out.println("test");
		if (imageIcon != null) {
			imageIcon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading image, please wait...", x + 300, y + 190);
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							synchronized (waitOnMe) {
								waitOnMe.wait();
							}
							System.out.println("staring load");
							imageIcon = new ImageIcon(pathToImage, "Graph Viz Output");
							System.out.println(imageIcon);
							// NOTE: Do both revalidate() and repaint() on the
							// parent component
							c.revalidate();
							c.repaint();
							// contentPane.revalidate();
							// contentPane.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
						retrieving = false;
					}
				});
				retrievalThread.start();
			}
		}
	}

	public void update() {
		if (this.imageIcon != null) {
			System.out.println(imageIcon);
			imageIcon.getImage().flush();
			this.imageIcon = null;
		}
	}

	public void clear() {
		if (this.imageIcon != null)
			imageIcon.getImage().flush();
	}
}
