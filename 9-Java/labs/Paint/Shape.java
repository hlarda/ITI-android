import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Graphics2D;

abstract class Shape{
    private int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
    public boolean isFilled = false;
    Color color;

    public Shape(){
        color = Color.WHITE;
    }
    public Shape(boolean isFilled, Color color){
        this.isFilled = isFilled;
        this.color = color;
    }
    public int getX1()    { return x1;      }
    public int getX2()    { return x2;      }
    public int getY1()    { return y1;      }
    public int getY2()    { return y2;      }
    public int getWidth (){ return x2 - x1; }
    public int getHeight(){ return y2 - y1; }

    public void setX1(int x1){ this.x1 = x1; }
    public void setX2(int x2){ this.x2 = x2; }
    public void setY1(int y1){ this.y1 = y1; }
    public void setY2(int y2){ this.y2 = y2; }
    public void setStartPoint(int x, int y){
        x1=x;   
        y1=y;
    }
    public void setEndPoint(int x, int y){
        x2=x;   
        y2=y;
    }
    public abstract void draw(Graphics g);
    public abstract void draw(Graphics2D g2d);
}
class Rectangle extends Shape{
    public Rectangle(){
        super();
    }
    public Rectangle(boolean isFilled, Color color){
        super(isFilled, color);
    }
    public void draw(Graphics g){
        g.setColor(color);
        if(isFilled){
            g.fillRect(getX1(), getY1(), getWidth(), getHeight());
        } else{
            g.drawRect(getX1(), getY1(), getWidth(), getHeight());
        }
    }
    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        if(isFilled){
            g2d.fillRect(getX1(), getY1(), getWidth(), getHeight());
        } else{
            g2d.drawRect(getX1(), getY1(), getWidth(), getHeight());
        }
    }
}
class Oval extends Shape{
    public Oval(){
        super();
    }
    public Oval(boolean isFilled, Color color){
        super(isFilled, color);
    }
    public void draw(Graphics g){
        g.setColor(color);
        if(isFilled){
            g.fillOval(getX1(), getY1(), getWidth(), getHeight());
        } else{
            g.drawOval(getX1(), getY1(), getWidth(), getHeight());
        }
    }
    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        if(isFilled){
            g2d.fillOval(getX1(), getY1(), getWidth(), getHeight());
        } else{
            g2d.drawOval(getX1(), getY1(), getWidth(), getHeight());
        }
    }
}
class Line extends Shape{
    public Line(){
        super();
    }
    public Line(boolean isFilled, Color color){
        super(isFilled, color);
    }
    public void draw(Graphics g){
        g.setColor(color);
        g.drawLine(getX1(), getY1(), getX2(), getY2());
    }
    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        g2d.drawLine(getX1(), getY1(), getX2(), getY2());
    }
}
class Freehand extends Shape{
    private ArrayList<Integer> xPoints = new ArrayList<>();
    private ArrayList<Integer> yPoints = new ArrayList<>();
    public Freehand(){
        super();
    }
    public Freehand(boolean isFilled, Color color){
        super(isFilled, color);
    }
    public void addPoint(int x, int y){
        xPoints.add(x);
        yPoints.add(y);
    }
    public void draw(Graphics g){
        g.setColor(color);
        for (int i = 1; i < xPoints.size(); i++){
            g.drawLine(xPoints.get(i - 1), yPoints.get(i - 1), xPoints.get(i), yPoints.get(i));
        }
    }
    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        for (int i = 1; i < xPoints.size(); i++){
            g2d.drawLine(xPoints.get(i - 1), yPoints.get(i - 1), xPoints.get(i), yPoints.get(i));
        }
    }
}
