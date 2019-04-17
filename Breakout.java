/*
 * File: Breakout.java
 * -------------------
 * Name:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Breakout extends GraphicsProgram {
	Color bg = new Color(4, 0, 44);

	GRect paddle = drawPaddle();
	GOval ball = drawBall();
	GRect brick;
	int deaths = 0;
	int bricks = 0;
	boolean againn = false;
	boolean play = false;
	GRect button = drawButton();
	GLabel playAgainButton = drawPlayAgainButton();
	boolean loop = true;


	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
	}

	// Dimensions of the canvas, in pixels
	// These should be used when setting up the initial size of the game,
	// but in later calculations you should use getWidth() and getHeight()
	// rather than these constants for accurate size information.
	public static final double CANVAS_WIDTH = 420;
	public static final double CANVAS_HEIGHT = 600;

	// Number of bricks in each row
	public static final int NBRICK_COLUMNS = 10;

	// Number of rows of bricks
	public static final int NBRICK_ROWS = 10;

	// Separation between neighboring bricks, in pixels
	public static final double BRICK_SEP = 4;

	// Width of each brick, in pixels
	public static final double BRICK_WIDTH = Math.floor(
			(CANVAS_WIDTH - (NBRICK_COLUMNS + 1.0) * BRICK_SEP) / NBRICK_COLUMNS);

	// Height of each brick, in pixels
	public static final double BRICK_HEIGHT = 8;

	// Offset of the top brick row from the top, in pixels
	public static final double BRICK_Y_OFFSET = 70;

	// Dimensions of the paddle
	public static final double PADDLE_WIDTH = 60;
	public static final double PADDLE_HEIGHT = 10;

	// Offset of the paddle up from the bottom 
	public static final double PADDLE_Y_OFFSET = 30;

	// Radius of the ball in pixels
	public static final double BALL_RADIUS = 10;

	// The ball's vertical velocity.
	public static final double VELOCITY_Y = 3.0;

	// The ball's minimum and maximum horizontal velocity; the bounds of the
	// initial random velocity that you should choose (randomly +/-).
	public static final double VELOCITY_X_MIN = 1.0;
	public static final double VELOCITY_X_MAX = 3.0;

	// Animation delay or pause time between ball moves (ms)
	double DELAY = 1000.0 / 60.0;

	// Number of turns 
	public static final int NTURNS = 3;

	Color one = new Color(42, 245, 24);
	Color two = new Color(27,215,187);
	Color three = new Color(20,201,203);
	Color four = new Color(15,190,216);
	Color five = new Color(8,179,229);

	public void mouseMoved(MouseEvent e) {
		double moveX = e.getX();

		/*if (moveX < PADDLE_WIDTH/2){
			moveX = 30;
		} else if (moveX > CANVAS_WIDTH- PADDLE_WIDTH/2){
			moveX = CANVAS_WIDTH-PADDLE_WIDTH;
		}
*/
			paddle.setLocation(moveX - (PADDLE_WIDTH / 2), getHeight() - PADDLE_Y_OFFSET);

		add(paddle);

	}

	public void mouseClicked(MouseEvent e) {

		GObject object = getElementAt(e.getX(), e.getY());
		if (object != null && object.getY() >= 327) {
			againn = true;
		}
		if (object != null && object.getY() <250){
			play = true;
		}
	}


	public void run() {
		setTitle("BREAKOUT!1!11!!! sksjskssjsksjk");
		setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);


		startScreen();

		while (loop) {
			playgame();

			if (deaths < 3) {
				winScreen();
			} else {
				deathScreen();
			}
			clearCanvas();

			if (!againn) {
				loop = false;
			}
		}
	}

	public void playgame() {
		clearCanvas();
		setBackground(Color.white);
		addMouseListeners();
		paddle.setColor(Color.black);
		ball.setLocation(300,300);
		bricks = 0;
		deaths = 0;
		againn = false;
		play = true;

		for (int j = 0; j < NBRICK_COLUMNS; j++) {
			for (int k = 0; k < NBRICK_ROWS; k++) {
				brick = new GRect((k + 1) * BRICK_SEP + k * BRICK_WIDTH, BRICK_Y_OFFSET + j * BRICK_HEIGHT + j * BRICK_SEP, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				if (j == 0 || j == 1) {
					brick.setFillColor(one);
				} else if (j == 2 || j == 3) {
					brick.setFillColor(two);
				} else if (j == 4 || j == 5) {
					brick.setFillColor(three);
				} else if (j == 6 || j == 7) {
					brick.setFillColor(four);
				} else if (j == 8 || j == 9) {
					brick.setFillColor(five);
				}
				add(brick);
			}
		}
		GLabel deathCount = new GLabel("Deaths: " + deaths, 10, 10);
		deathCount.setColor(Color.BLACK);
		add(deathCount);
		add(ball);
		int dx = 1;
		int dy = 1;

		while (play) {
			pause(DELAY - 5 - 1 * (bricks / 10));
			ball.move(dx, dy);

			//bounce off bottom & top
			if (ball.getY() < 0) {
				dy *= -1;
			}
			//bounce off right & left
			if (ball.getX() < 0 || ball.getRightX() > getWidth()) {
				dx *= -1;
			}
			if (ball.getBottomY() > getHeight()) {
				deaths++;
			}

			if (deaths < NTURNS && (ball.getBottomY() > getHeight())) {
				deathCount.setLabel("Deaths: " + deaths);
				pause(1000);
				ball.setLocation(300, 300);
				pause(200);
				remove(ball);
				pause(200);
				add(ball);
				pause(200);
				remove(ball);
				pause(200);
				add(ball);
				dy = 1;
				dx = 1;
			}


			if (deaths == NTURNS) {
				play = false;
			}
			if (bricks == 100) {
				play = false;
			}

			GObject object = getElementAt(ball.getX() + BALL_RADIUS, ball.getBottomY());
			if (object != null) {
				dy *= -1;
				if (object.getY() < getCanvasHeight() / 2) {
					remove(object);
					bricks++;
				}
			}
			System.out.println(bricks);
		}
	}

	public void deathScreen() {
		addMouseListeners();
		setBackground(Color.red);
		paddle.setColor(Color.red);
		clearCanvas();
		GLabel lost = new GLabel("You Lost.");
		lost.setLocation((CANVAS_WIDTH - lost.getWidth()) / 2, (CANVAS_HEIGHT - lost.getHeight()) / 2);
		add(lost);


		add(button);
		add(playAgainButton);

		while (!againn) {
			pause(1000);
		}
	}

	public void winScreen() {
		clearCanvas();
		setBackground(Color.green);
		paddle.setColor(Color.green);

		GLabel win = new GLabel("Congrats! You Won!");
		win.setLocation((CANVAS_WIDTH - win.getWidth()) / 2, (CANVAS_HEIGHT - win.getHeight()) / 2);
		add(win);

		add(button);
		add(playAgainButton);

		while (!againn) {
			pause(1000);
		}

	}

	public void startScreen(){
		clearCanvas();
		setBackground(Color.cyan);
		paddle.setColor(Color.CYAN);
		GLabel brickbreaker = new GLabel("BRICKBREAKER", 100, 100);
		add(brickbreaker);

		GLabel playy = new GLabel ("PLAY?", 100, 200);
		add(playy);

		while(!play){
			pause(100);
		}

	}

	public GOval drawBall(){
		GOval ball = new GOval(300, 300, BALL_RADIUS, BALL_RADIUS);
		ball.setColor(Color.BLACK);
		ball.setFilled(true);
		return(ball);
	}


	public GRect drawPaddle() {
		GRect paddle = new GRect (getWidth()/2-PADDLE_WIDTH/2, getHeight()-PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		return(paddle);
	}

	public GRect drawButton() {
	    GRect button = new GRect(181.5, 327.0, 68.0, 21.0);
        button.setColor(Color.BLUE);
        button.setFilled(true);
        return(button);
    }

    public GLabel drawPlayAgainButton() {
        playAgainButton = new GLabel("Play again?");
        playAgainButton.setLocation(184, 340);
        playAgainButton.setColor(Color.WHITE);
        return(playAgainButton);
    }


}
