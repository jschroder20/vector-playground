/*
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


package acm.graphics;

import acm.util.ErrorException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D.Double;

public class Vector extends GObject implements GFillable {
    private VertexList vertices;
    private boolean complete;
    private boolean isFilled;
    private Color fillColor;
    static final long serialVersionUID = 21L;
    int xLocation = 100;
    int yLocation = 100;
    int width = 3;
    int var1;
    int var3;
    double r;


    public GPolygon() {
        this.vertices = new VertexList();
        this.clear();
    }


    public Vector(int var2, int var3) {
        this();
        this.var2 = var2;
        this.y = ;
        this.r = Math.sqrt(x*x+y*y);
        this.vertices.add(points);
        this.markAsComplete();

        GPoint[] var1 = {new GPoint(xLocation, yLocation), new GPoint(xLocation + r -5, yLocation),
                new GPoint(xLocation + r -5, yLocation -3), new GPoint(xLocation + r -5, yLocation +(width/2)),
                new GPoint(xLocation + r -5, yLocation +width+3), new GPoint(xLocation + r -5, yLocation +width),
                new GPoint(xLocation, yLocation +width)};
    }



    public GPoint getCurrentPoint() {
        return this.vertices.getCurrentPoint();
    }

    public void setFilled(boolean var1) {
        this.isFilled = var1;
        this.repaint();
    }

    public boolean isFilled() {
        return this.isFilled;
    }

    public void setFillColor(Color var1) {
        this.fillColor = var1;
        this.repaint();
    }

    public Color getFillColor() {
        return this.fillColor == null ? this.getColor() : this.fillColor;
    }

    public GRectangle getBounds() {
        GRectangle var1 = this.vertices.getBounds(this.getMatrix());
        return new GRectangle(var1.getX() + this.getX(), var1.getY() + this.getY(), var1.getWidth(), var1.getHeight());
    }

    public boolean contains(double var1, double var3) {
        return this.vertices.contains(var1 - this.getX(), var3 - this.getY(), this.getMatrix());
    }

    protected void paint2d(Graphics2D var1) {
        int var2 = this.vertices.size();
        Double var3 = new Double(0);
        var3.moveTo(this.vertices.get(0).getX(), this.vertices.get(0).getY());

        for(int var4 = 0; var4 < var2; ++var4) {
            var3.lineTo(this.vertices.get(var4).getX(), this.vertices.get(var4).getY());
        }

        var3.lineTo(this.vertices.get(0).getX(), this.vertices.get(0).getY());
        if (this.isFilled()) {
            var1.setColor(this.getFillColor());
            var1.fill(var3);
            var1.setColor(this.getColor());
        }

        var1.draw(var3);
    }

    public void recenter() {
        this.vertices.recenter();
    }

    public Object clone() {
        try {
            GPolygon var1 = (GPolygon)super.clone();
            var1.vertices = new VertexList(var1.vertices);
            return var1;
        } catch (Exception var2) {
            throw new ErrorException("Impossible exception");
        }
    }

    protected void markAsComplete() {
        this.complete = true;
    }

    protected void clear() {
        if (this.complete) {
            throw new ErrorException("You can't clear a GPolygon that has been marked as complete.");
        } else {
            this.vertices.clear();
        }
    }
}
*/