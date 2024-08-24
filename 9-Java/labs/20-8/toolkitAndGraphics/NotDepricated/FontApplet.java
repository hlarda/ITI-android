import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class FontApplet extends Applet {

    String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    public void paint(Graphics g) {
        int y = 20;

        for (String fontName : fontList) {
			Font font = new Font(fontName, Font.PLAIN, 12);
            g.setFont(font); 
            g.drawString(fontName, 10, y);
            y += 20;
        }
    }
}