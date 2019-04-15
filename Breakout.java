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
	boolean againn;


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

    public void mouseClicked(MouseEvent e) {

        GObject object = getElementAt(e.getX(), e.getY());
        if (object != null) {
            againn = true;
        }

	    double moveX = e.getX();

        paddle.setLocation(moveX-(PADDLE_WIDTH/2), getHeight()-PADDLE_Y_OFFSET);
        add(paddle);

    }
    public void run() {
	    playgame();

	    if (deaths<3){
	        winScreen();
        } else {
	        deathScreen();
	    }
	    if (againn){
	        playgame();
        }
    }

	public void playgame() {
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
					brick.setFillColor(Color.RED);
				} else if(j==2 || j==3){
					brick.setFillColor(Color.ORANGE);
				} else if(j==4 || j==5){
					brick.setFillColor(Color.YELLOW);
				} else if(j==6 || j==7){
					brick.setFillColor(Color.GREEN);
				} else if(j==8 || j==9) {
					brick.setFillColor(Color.CYAN);
				}
				add(brick);
			}
		}
		GLabel deathCount = new GLabel("Deaths: "+deaths,10,10);
		deathCount.setColor(Color.BLACK);
		add(deathCount);
		add(ball);
		int dx = 1;
		int dy = 1;

		while(deaths<3) {
			pause(3);
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

            if (deaths<3 && (ball.getBottomY() > getHeight())) {
                deathCount.setLabel("Deaths: "+deaths);
                pause(1000);
                ball.setLocation(300,300);
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

			GObject object = getElementAt(ball.getX()+BALL_RADIUS, ball.getBottomY());
			if (object != null) {
				dy *= -1;
				if (object.getY() < getCanvasHeight()/2) {
					remove(object);
					bricks++;
				}
			}
            System.out.println(bricks);
		}
	}

	public void deathScreen(){
        addMouseListeners();
        setBackground(Color.red);
	    removeAll();
	    remove(paddle);
	    GLabel lost = new GLabel("You Lost.");
	    lost.setLocation( (CANVAS_WIDTH-lost.getWidth())/2, (CANVAS_HEIGHT-lost.getHeight())/2);
	    add(lost);

        GLabel again = new GLabel("Play again?");
        again.setLocation( (CANVAS_WIDTH-lost.getWidth())/2, (CANVAS_HEIGHT-lost.getHeight())/2+50);
        add(again);

    }

    public void winScreen(){

    }

	public GOval drawBall(){
		GOval ball = new GOval(300, 300, BALL_RADIUS, BALL_RADIUS);
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

			//bounce off bottom & top
			if((ball.getBottomY() > getHeight()) || (ball.getY() < 0)){
				dy *= -1;
			}
			//bounce off right & left
			if(ball.getRightX() > getWidth() || (ball.getX() < 0)){
				dx *= -1;
			}

			GObject object = getElementAt(ball.getX(), ball.getY());
			if (object != null) {
				if (object == brick) {
					remove(brick);
				}
				dy*=1;
			}

			/*if(ball.getX()>paddle.getX() && ball.getRightX()<paddle.getRightX() && ball.getBottomY()==paddle.getY()) {
				dy *= -1;
			}
			if(ball.getRightX()==paddle.getX() && ball.getY()+BALL_RADIUS>paddle.getY() && ball.getY()-BALL_RADIUS<paddle.getBottomY()) {
				dx *= -1;
			}
			if(ball.getX()==paddle.getRightX() && ball.getY()+BALL_RADIUS>paddle.getY() && ball.getY()-BALL_RADIUS<paddle.getBottomY()) {
				dx *= -1;
			}*/



		}
	}

	public GRect drawPaddle() {
		GRect paddle = new GRect (getWidth()/2-PADDLE_WIDTH/2, getHeight()-PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		return(paddle);
	}


}
