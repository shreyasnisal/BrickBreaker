
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Driver {
	
	public static void main(String args[]) {
		JFrame mFrame = new JFrame("Brick Breaker");

		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");

		mFrame.getContentPane().setCursor(blankCursor);

		mFrame.add(new Game());

		mFrame.setSize(800, 600);
		mFrame.setMaximizedBounds(new Rectangle(800, 600));
		mFrame.setVisible(true);
	}

}