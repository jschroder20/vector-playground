import acm.program.GraphicsProgram;
import acm.graphics.*;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * MouseDrag -- implement simple mouse drag in a window.
 */
public class Playground extends GraphicsProgram {
	private static final double CANVAS_WIDTH = 600;
	private static final double CANVAS_HEIGHT = 400;
	private double x1 = -60;
	private double y1 = -50;

	private double x2 = -50;
	private double y2 = 100;

	private int width = 2;
	private GPoint origin = new GPoint(CANVAS_WIDTH/2, (CANVAS_HEIGHT/2)-1);



	private GPolygon vectorOne = drawVectorOne();
	private GPolygon vectorTwo = drawVectorTwo();

	private GPolygon vectorOneShadow = drawVectorOneShadow();
	private GPolygon vectorTwoShadow = drawVectorTwoShadow();

	private boolean add = false;

	public void mouseClicked(MouseEvent e) {
		GObject object = getElementAt(e.getX(), e.getY());
		if (object != null && object.getY() <= 10) {
			add = true;
			System.out.println("CLICK");
		}


	}

	public void run(){
		setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);

		drawPlane();
		drawAddVectorsButton();

		add(vectorOne);
		add(vectorTwo);

		GLabel vectorOneLabel = new GLabel("<"+x1+","+y1+">", 30,30);
		vectorOneLabel.setColor(Color.green);
		add(vectorOneLabel);

		GLabel vectorTwoLabel = new GLabel("<"+x2+","+y2+">", 30,50);
		vectorTwoLabel.setColor(Color.blue);
		add(vectorTwoLabel);



		while(true) {
			if (add) {
				System.out.println("ADD");
				add(vectorOneShadow);
				add(vectorTwoShadow);
				addVectors();
				GLabel addVectorsLabel = new GLabel("<"+(x1+x2)+","+(y1+y2)+">", 30,70);
				addVectorsLabel.setColor(Color.red);
				add(addVectorsLabel);
			}
		}





	}



	public GPolygon drawVectorOne(){
		double theta;
		double r = Math.sqrt(x1*x1+y1*y1);
		if (x1 == 0 && y1 >0) {
			theta = 90;
		} else if(x1==0 && y1 <0){
			theta = 270;
		} else if(x1==0 && y1==0){
			theta = 0;
		}  else if (x1<0){
			theta = (360*Math.atan((y1)/x1)/6.28)+180;
		} else {
			theta = 360*Math.atan(y1/x1)/6.28;
		}
		GPoint[] points = {new GPoint(0, 0), new GPoint( r -5, 0),
				new GPoint(r -5,  -3), new GPoint(r, (width/2)),
				new GPoint(r -5,width+3), new GPoint(r -5, width),
				new GPoint(0, width)};
		GPolygon vector = new GPolygon(points);
		vector.rotate(theta);
		vector.setFilled(true);
		vector.setColor(Color.green);
		vector.setLocation(origin);
		return vector;
	}

	public GPolygon drawVectorOneShadow(){

		double theta;
		double r = Math.sqrt(x1*x1+y1*y1);
		if (x1 == 0 && y1 >0) {
			theta = 90;
		} else if(x1==0 && y1 <0){
			theta = 270;
		} else if(x1==0 && y1==0){
			theta = 0;
		}  else if (x1<0){
			theta = (360*Math.atan((y1)/x1)/6.28)+180;
		} else {
			theta = 360*Math.atan(y1/x1)/6.28;
		}
		GPoint[] points = {new GPoint(0, 0), new GPoint( r -5, 0),
				new GPoint(r -5,  -3), new GPoint(r, (width/2)),
				new GPoint(r -5,width+3), new GPoint(r -5, width),
				new GPoint(0, width)};
		GPolygon vector = new GPolygon(points);
		vector.rotate(theta);
		vector.setFilled(true);
		vector.setColor(new Color (104, 212, 117));
		vector.setLocation(origin.getX()+x2, origin.getY()+(y2*-1));
		return vector;
	}


	public GPolygon drawVectorTwo(){
		double theta;
		double r = Math.sqrt(x2*x2+y2*y2);
		if (x2 == 0 && y2 >0) {
			theta = 90;
		} else if(x2==0 && y2 <0){
			theta = 270;
		} else if(x2==0 && y2==0){
			theta = 0;
		}  else if (x2<0){
			theta = (360*Math.atan((y2)/x2)/6.28)+180;
		} else {
			theta = 360*Math.atan(y2/x2)/6.28;
		}
		GPoint[] points = {new GPoint(0, 0), new GPoint( r -5, 0),
				new GPoint(r -5,  -3), new GPoint(r, (width/2)),
				new GPoint(r -5,width+3), new GPoint(r -5, width),
				new GPoint(0, width)};
		GPolygon vector = new GPolygon(points);
		vector.rotate(theta);
		vector.setFilled(true);
		vector.setColor(Color.blue);
		vector.setLocation(origin);
		return vector;
	}

	public GPolygon drawVectorTwoShadow(){
		double theta;
		double r = Math.sqrt(x2*x2+y2*y2);
		if (x2 == 0 && y2 >0) {
			theta = 90;
		} else if(x2==0 && y2 <0){
			theta = 270;
		} else if(x2==0 && y2==0){
			theta = 0;
		}  else if (x2<0){
			theta = (360*Math.atan((y2)/x2)/6.28)+180;
		} else {
			theta = 360*Math.atan(y2/x2)/6.28;
		}
		GPoint[] points = {new GPoint(0, 0), new GPoint( r -5, 0),
				new GPoint(r -5,  -3), new GPoint(r, (width/2)),
				new GPoint(r -5,width+3), new GPoint(r -5, width),
				new GPoint(0, width)};
		GPolygon vector = new GPolygon(points);
		vector.rotate(theta);
		vector.setFilled(true);
		vector.setColor(new Color(103, 115, 187));
		vector.setLocation(origin.getX()+x1, origin.getY()+(y1*-1));
		return vector;
	}


	public void drawPlane(){
		int lineLength = (int)(.85*getHeight());
		int lineWidth = 2;
		GRect yaxis = new GRect((getWidth()-lineWidth)/2,(getHeight()-lineLength)/2,lineWidth,lineLength);
		yaxis.setFilled(true);
		add(yaxis);

		GRect xaxis = new GRect((getWidth()-lineLength)/2, (getHeight()-lineWidth)/2, lineLength, lineWidth);
		xaxis.setFilled(true);
		add(xaxis);
	}

	public void addVectors(){
		double x = x1+x2;
		double y = y1+y2;

		double r = Math.sqrt(x*x+y*y);
		double theta;
		if (x == 0 && y >0) {
			theta = 90;
		} else if(x==0 && y <0){
			theta = 270;
		} else if(x==0 && y==0){
			theta = 0;
		} else if(x<0){
			theta = ((360 * Math.atan(y/(x)))/6.28)+180;
		} else {
			theta = (360*Math.atan(y/x))/6.28;
		}

		GPoint[] points = {new GPoint(0, 0), new GPoint( r -5, 0),
				new GPoint(r -5,  -3), new GPoint(r, (width/2)),
				new GPoint(r -5,width+3), new GPoint(r -5, width),
				new GPoint(0, width)};
		GPolygon vector = new GPolygon(points);
		vector.rotate(theta);
		vector.setFilled(true);
		vector.setColor(Color.red);
		vector.setLocation(origin);
		add(vector);

	}

	public void drawAddVectorsButton(){

		GRect button = new GRect(500,10,80,30);
		button.setFilled(true);
		button.setFillColor(new Color(143, 143, 143));


		add(button);
	}

}