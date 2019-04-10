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
	public static final double DELAY = 1000.0 / 60.0;

	// Number of turns 
	public static final int NTURNS = 3;

	Color BURGUNDY = new Color(99, 3, 28);
	Color DARK_GREEN = new Color(0, 74, 34);

	public void mouseMoved(MouseEvent e) {
		double moveX = e.getX();

		paddle.setLocation(moveX-(PADDLE_WIDTH/2), getHeight()-PADDLE_Y_OFFSET);
		add(paddle);

	}

	public void run() {
		//setBackground(bg);
		addMouseListeners();
		setTitle("BREAKOUT!1!11!!! sksjskssjsksjk");

		// Set the canvas size.  In your code, remember to ALWAYS use getWidth()
		// and getHeight() to get the screen dimensions, not these constants!
		setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);

		for(int j=0; j<NBRICK_COLUMNS; j++){
			for(int k=0; k<NBRICK_ROWS; k++){
				brick = new GRect((k+1)*BRICK_SEP+k*BRICK_WIDTH,BRICK_Y_OFFSET+j*BRICK_HEIGHT+j*BRICK_SEP,BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				if(j==0 || j==1){
					brick.setColor(Color.RED);
				} else if(j==2 || j==3){
					brick.setColor(Color.ORANGE);
				} else if(j==4 || j==5){
					brick.setColor(Color.YELLOW);
				} else if(j==6 || j==7){
					brick.setColor(Color.GREEN);
				} else if(j==8 || j==9) {
					brick.setColor(Color.CYAN);
				}
				add(brick);
			}
		}
		add(ball);
		moveBall();

		if(ball.getX()>brick.getX() && ball.getRightX()<brick.getRightX() && ball.getBottomY()==brick.getY()) {
			remove(brick);
		}
	}

	public GOval drawBall(){
		GOval ball = new GOval(0, 0, BALL_RADIUS, BALL_RADIUS);
		ball.setColor(Color.BLACK);
		ball.setFilled(true);
		return(ball);
	}
	public void moveBall(){
		int dx = 1;
		int dy = 1;

		while(true){
			pause(3);
			ball.move(dx, dy);

			if((ball.getBottomY() > getHeight()) || (ball.getY() < 0)){
				dy *= -1;
			}
			if(ball.getRightX() > getWidth() || (ball.getX() < 0)){
				dx *= -1;
			}
			if(ball.getX()>paddle.getX() && ball.getRightX()<paddle.getRightX() && ball.getBottomY()==paddle.getY()) {
				dy *= -1;
			}


		}
	}

	public GRect drawPaddle() {
		GRect paddle = new GRect (getWidth()/2-PADDLE_WIDTH/2, getHeight()-PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		return(paddle);
	}


}
