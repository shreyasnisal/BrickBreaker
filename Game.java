
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JPanel {
	
	private float ballX;
	private float ballY;

	private float paddleX;

	private float ballXSpeed, ballYSpeed;

	private Point[] brickPositions;

	private static final int FPS = 60;
	private static final int BALL_DIAMETER = 15;
	private static final int PADDLE_WIDTH = 80;
	private static final int PADDLE_HEIGHT = 10;

	public Game() {
		super();
		ballX = 200;
		ballY = 200;
		ballXSpeed = 2;
		ballYSpeed = 2;

		brickPositions = new Point[36];

		int brickY = 50;
		for (int i = 0; i < 3; i++) {
			int brickX = 10;
			for (int j = 0; j < 12; j++) {
				// g.fillRect(brickX, brickY, 60, 20);
				brickPositions[12*i + j] = new Point(brickX, brickY);
				brickX += 64;
			}
			brickY += 24;
		}

		Thread ballMove = new Thread() {
			public void run() {
				while (true) {
					ballX += ballXSpeed;
					ballY += ballYSpeed;


					// Paddle-Ball Collision
					if (((ballY + BALL_DIAMETER) >= 500) && ((ballX + BALL_DIAMETER/2) < (paddleX + PADDLE_WIDTH)) && ((ballX + BALL_DIAMETER/2) > paddleX)) {
						ballYSpeed = -ballYSpeed;
					}

					// Border Collisions
					if ((ballX + BALL_DIAMETER*2) >= 800) ballXSpeed = -ballXSpeed;
					if (ballX == 0) ballXSpeed = -ballXSpeed;
					if (ballY == 0) ballYSpeed = -ballYSpeed;

					//Brick Collisions
					for (int i = 0; i < 36; i++) {
						if ((ballX + BALL_DIAMETER/2 < brickPositions[i].x + 60) && (ballX + BALL_DIAMETER/2 > brickPositions[i].x) && (ballY + BALL_DIAMETER/2 < brickPositions[i].y + 20) && (ballY + BALL_DIAMETER/2 > brickPositions[i].y)) {
							brickPositions[i] = new Point(-50, -50);
							ballYSpeed = -ballYSpeed;
						}
					}

	               repaint();

	               try {
	                  Thread.sleep(1000 / FPS);  // milliseconds
	               } catch (InterruptedException ex) { }
				}
			}
		};

		ballMove.start();


		//Paddle
		paddleX = 100;

		addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e)
            {
                paddleX = e.getPoint().x;
                repaint();
            }
        });

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.setBackground(Color.WHITE);

		g.setColor(Color.RED);
		g.fillOval((int)ballX, (int)ballY, BALL_DIAMETER, BALL_DIAMETER);

		g.setColor(Color.BLUE);
		g.fillRect((int)paddleX, 500, PADDLE_WIDTH, PADDLE_HEIGHT);

		g.setColor(Color.GREEN);

		for (int i = 0; i < 36; i++) {
			g.fillRect(brickPositions[i].x, brickPositions[i].y, 60, 20);
		}

	}
}