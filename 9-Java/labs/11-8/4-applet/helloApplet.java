import java.applet.Applet;
import java.awt.Graphics;

public class helloApplet extends Applet{
    public void paint(Graphics g){
        g.drawString("Hello", 100, 100);
    }
}

/*
 * Compile: javac helloApplet.java
 * Run: appletviewer applet.html
 */