/**
 * Create an applet that displays the list of available fonts in the 
underlying platform.
• Each font should be written in its own font.
• If you encounter any deprecated method|(s), follow the 
compiler instructions to re-compile and detect which method is 
deprecated. Afterwards, use the help (documentation) to see 
the proper replacement for the deprecated method(s).
 */

import java.awt.Toolkit;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Font;

public class FontApplet extends Applet{
    public void paint(Graphics g){
        int i = 20;  
        String[] fontList = Toolkit.getDefaultToolkit().getFontList();
        for (String fontName : fontList) {
            Font fontObj = new Font(fontName, Font.PLAIN, 12);
            g.setFont(fontObj);
            g.drawString(fontName, 10, i);
            i += 15;  
        }
    }
}
