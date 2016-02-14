package legendary.DisplayScreen;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import legendary.ParsingUtil.GeneralUtil;

class ImageProxy implements Icon {
	ImageIcon imageIcon;
	String pathToImage;
	Thread retrievalThread;
	boolean retrieving = false;

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
							while(GeneralUtil.isGenning);
							System.out.println("staring load");
							imageIcon = new ImageIcon(pathToImage, "Graph Viz Output");

							// NOTE: Do both revalidate() and repaint() on the
							// parent component
							c.revalidate();
							c.repaint();
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
	
	public void update(){
		this.imageIcon = null;
	}
}
