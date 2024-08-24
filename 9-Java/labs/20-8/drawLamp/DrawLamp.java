import java.awt.Graphics;
import java.applet.Applet;
import java.awt.Color;

public class DrawLamp extends Applet{
    public void paint(Graphics g){
        int x = 50;
        int y = 50;

        // Draw the top oval
        g.setColor(Color.yellow);
        g.fillOval(x, y, 100, 20);
        // Draw two lines to connect the top oval to the bottom oval
        g.setColor(Color.black);
        g.drawLine(x, 60 ,0, y + 100);
        g.drawLine(x+100, 60 ,200, y + 100);
        // Drow bottom arc
        //drawArc(int x, int y, int length, int width, int startAngle, int arcAngle)
        g.drawArc(0, y + 83, 200, 35, 180, 180); 
        // Draw ovals inside
        g.setColor(Color.yellow);
        g.fillOval(x+35, y+25, 30, 75);
        g.fillOval(x,    y+35, 20, 50);
        g.fillOval(x+80,y+35, 20, 50);
        //two lines buttom
        g.setColor(Color.black);
        g.drawLine(x+35,       y + 83+35,    x+35-15,      y + 100+ 83);
        g.drawLine(x+35+30,   y + 83+35,    x+35+30+15,    y + 100+ 83);
        //Rectangle
        g.drawRect(x-25, y + 100+ 83, 100+50, 15);

        

    }
}
