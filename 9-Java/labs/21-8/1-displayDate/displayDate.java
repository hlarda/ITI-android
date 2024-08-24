import java.util.Date;
import java.applet.Applet;
import java.awt.Graphics;

public class displayDate extends Applet implements Runnable{
    Thread thread;

    public void init(){
        thread = new Thread(this);
        thread.start();
    }
    public void paint(Graphics g){
        Date date = new Date();
        g.drawString(date.toString(), getWidth()/2, getHeight()/2);
    }
    public void run(){
        while (true) {
            try{
                repaint();
                Thread.sleep(1000);
            }catch(InterruptedException i){
                i.printStackTrace();
            }
        }

    }
}

