/*
 * Draw only three lines
 */

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

class Line{
    private int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

    public void setStartPoint(int x1, int y1){
        this.x1 = x1;
        this.y1 = y1;
    }
    public void setEndPoint(int x2, int y2){
        this.x2 = x2;
        this.y2 = y2;
    }
    public void setPoints(int x1, int y1, int x2, int y2){
        setStartPoint(x1, y1);
        setEndPoint  (x2, y2);
    }
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawLine(x1, y1, x2, y2);
    }
}

public class DrawLine extends Applet{
    private int max = 3, lineCounter = 0;
    private boolean isPressed = false, isDragged = false;
    private Line tmpLine = new Line();
    private Line[] lines = new Line[max];

    public void init(){
        addMouseListener(new MouseActions());
        addMouseMotionListener(new MouseActions());
    }

    class MouseActions extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            if ((lineCounter < max) && (!isPressed) && (!isDragged)){
                isPressed = true;
                tmpLine.setStartPoint(e.getX(), e.getY());
            }
        }
        public void mouseDragged(MouseEvent e){
            if (isPressed && lineCounter < max){
                isDragged = true;
                tmpLine.setEndPoint(e.getX(), e.getY());
                repaint();
            }
        }
        public void mouseReleased(MouseEvent e){
            if (isPressed && isDragged && lineCounter < max){
                lines[lineCounter] = new Line();
                lines[lineCounter] = tmpLine;
                tmpLine = new Line();
                isPressed = isDragged = false;
                lineCounter++;
                repaint();
            }
        }
    }

    public void paint(Graphics g){
        if (isPressed && isDragged){
            tmpLine.draw(g);
        }
        for (int i = 0; i < lineCounter; i++){
            lines[i].draw(g);
        }
    }
}