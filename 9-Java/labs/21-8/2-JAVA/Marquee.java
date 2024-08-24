import java.applet.Applet;
import java.awt.Graphics;
public class Marquee extends Applet implements Runnable{
    Thread thread;
    int x;

    public void init(){
        thread = new Thread(this);
        thread.start();
        x = 0;

    }
    public void paint(Graphics g){        
        g.drawString("JAVA", x, getHeight() / 2);
    }
    public void run(){
        while (true) {
            try{
                repaint();
                Thread.sleep(50);
                x += 5;
                if (x > getWidth()){
                    x = 0;
                }
            }catch(InterruptedException i){
                i.printStackTrace();
            }
        }
    }
}
