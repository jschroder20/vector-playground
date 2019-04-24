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

	//initalize variables & objects

	int deaths = 0;
	int bricks = 0;
	boolean againn = false;
	boolean play = false;
	boolean loop = true;

	GRect paddle = drawPaddle();
	GOval ball = drawBall();
	GRect brick;
	GRect button = drawButton();
	GLabel playAgainButton = drawPlayAgainButton();
	GOval life = drawLife();

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


	//method to move the paddle with cursor
	public void mouseMoved(MouseEvent e) {
		double moveX = e.getX();
		if(e.getX() >= paddle.getWidth()/2 && e.getX() <= getCanvasWidth()-(paddle.getWidth()/2)) {
			paddle.setLocation(moveX - (PADDLE_WIDTH / 2), getHeight() - PADDLE_Y_OFFSET);
		}
		add(paddle);

	}

	//method for play again + start buttons
	public void mouseClicked(MouseEvent e) {
		GObject object = getElementAt(e.getX(), e.getY());
		if (object != null && object.getY() >= 327) {
			againn = true;
		}
		if (object != null && object.getY() <327){
			play = true;
		}


	}

	//run method
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

	//method that plays game
	private void playgame() {
		//initialization
		clearCanvas();
		setBackground(Color.white);
		addMouseListeners();
		paddle.setColor(Color.black);
		ball.setLocation(300,300);
		bricks = 0;
		deaths = 0;
		againn = false;
		play = true;

		//creates brick grid
		Color[] colors = new Color[]{new Color(58, 255, 151), new Color(27,215,187), new Color(20,201,203), new Color(15,190,216), new Color(8,179,229)};
		for (int j = 0; j < NBRICK_COLUMNS; j++) {
			for (int k = 0; k < NBRICK_ROWS; k++) {
				brick = new GRect((k + 1) * BRICK_SEP + k * BRICK_WIDTH, BRICK_Y_OFFSET + j * BRICK_HEIGHT + j * BRICK_SEP, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				brick.setColor(colors[j/2]);
				add(brick);
			}
		}

		//creates deathcounter
		GLabel deathCount = new GLabel("Deaths: " + deaths);
		deathCount.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		deathCount.setLocation(10,20);
		deathCount.setColor(Color.BLACK);
		add(deathCount);

		//add ball + set initial movement
		add(ball);
		int dx = 1;
		int dy = 1;

		int turn = 0;

		while (play) {
			//move ball
			pause(DELAY - 5 - 1 * (bricks / 10));
			ball.move(dx, dy);

			//bounce off top
			if (ball.getY() < 0) {
				dy *= -1;
			}
			//bounce off right & left
			if (ball.getX() < 0 || ball.getRightX() > getWidth()) {
				dx *= -1;
			}

			//increase deaths if ball is at bottom
			if (ball.getBottomY() > getHeight()) {
				deaths++;
			}

			//reset ball if # of deaths is less than three
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

			//end game if # of deaths is three
			if (deaths == NTURNS) {
				play = false;
			}
			//end game if all bricks are destroyed
			if (bricks == 5) {
				play = false;
			}

			//in-progress code for a life power-up
			if (bricks == 1) {
				if (turn == 0) {
					life.setLocation(ball.getX(), ball.getY());
					System.out.println("NEW LIFE");
				}
				turn++;
				//add(life);
			}

			//check if ball is touching brick + remove brick if so, increase "bricks" variable
			GObject object = getElementAt(ball.getX() + BALL_RADIUS, ball.getBottomY());
			if (object != null) {
				dy *= -1;
				if (object.getY() < getCanvasHeight() / 2) {
					remove(object);
					bricks++;
				}
			}
		}

	}

	//method displaying death screen
	public void deathScreen() {
		//add objects/set screen
		addMouseListeners();
		setBackground(new Color (255, 70, 97));
		paddle.setColor(new Color (255, 70, 97));
		clearCanvas();
		GLabel lost = new GLabel("You Lost");
		lost.setFont(new Font("Comic Sans MS", Font.BOLD, 60));

		lost.setLocation((getCanvasWidth()-lost.getWidth())/2, 200);
		add(lost);

		add(button);
		add(playAgainButton);

		//keep checking to see if the player clicked the play again button
		while (!againn) {
			pause(10);
		}
	}

	//method displaying win screen
	public void winScreen() {
		//add objects/set screen
		clearCanvas();
		setBackground(new Color(44, 191, 81));
		paddle.setColor(new Color(44, 191, 81));

		GLabel win = new GLabel("You Won!");
		win.setFont(new Font("Comic Sans MS", Font.BOLD, 60));

		win.setLocation((getCanvasWidth()-win.getWidth())/2, 200);
		add(win);

		add(button);
		add(playAgainButton);

		//keep checking to see if the player clicked the play again button
		while (!againn) {
			pause(10);
		}
	}

	//start screen
	public void startScreen(){
		//add objects/set screen
		clearCanvas();
		setBackground(new Color(15,190,216));
		paddle.setColor(new Color(15,190,216));
		GLabel brickbreaker = new GLabel("BRICKBREAKER");
		brickbreaker.setFont(new Font("Comic Sans MS", Font.BOLD, 40));

		brickbreaker.setLocation((getCanvasWidth()-brickbreaker.getWidth())/2, 100);
		add(brickbreaker);

		GLabel playy = new GLabel ("PLAY?");
		playy.setFont(new Font("Comic Sans MS", Font.BOLD, 60));

		playy.setLocation((getCanvasWidth()-playy.getWidth())/2, 300);
		add(playy);

		//keep checking to see if the player has pressed the "play" button
		while(!play){
			pause(10);
		}

	}

	//method that draws the ball
	public GOval drawBall(){
		GOval ball = new GOval(300, 300, BALL_RADIUS, BALL_RADIUS);
		ball.setColor(Color.BLACK);
		ball.setFilled(true);
		return(ball);
	}

	//method that draws the paddle
	public GRect drawPaddle() {
		GRect paddle = new GRect (getWidth()/2-PADDLE_WIDTH/2, getHeight()-PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		return(paddle);
	}

	//method that draws play again button
	public GRect drawButton() {
	    GRect button = new GRect(120, 310, 180, 42);
        button.setColor(new Color(255, 255, 255));

        return(button);
    }

    //method that draws the play again button text
    public GLabel drawPlayAgainButton() {
        playAgainButton = new GLabel("Play again?");
		playAgainButton.setFont(new Font("Comic Sans MS", Font.BOLD, 30));

		playAgainButton.setLocation(130, 340);
        playAgainButton.setColor(Color.WHITE);
        return(playAgainButton);
    }

    public GOval drawLife(){
		life = new GOval(10,10);
		life.setFilled(true);

		life.setColor(Color.PINK);
		return(life);
	}
}
