import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Circle {
    int size, x, y;
    boolean directionX = true, directionY = true;

    public Circle(int size, int x, int y) {
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public void move(int width, int height) {
        if (directionX) {   x += 2; } 
        else            {   x -= 2; }

        if (directionY) {   y += 5; } 
        else            {   y -= 5; }

        if (x > width - size || x < 0) {
            directionX = !directionX;
        }
        if (y > height - size || y < 0) {
            directionY = !directionY;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x, y, size, size);
    }
}

public class PlayAndPause extends Applet implements Runnable {
    Circle circle = new Circle(20, 200, 200);
    Button startBtn, pauseBtn;
    Thread thread;
    boolean startFlag = true;

    public void init() {
        thread = new Thread(this);
        startBtn = new Button("Start");
        pauseBtn = new Button("Pause");

        startBtn.addActionListener(new startBtnListener());
        pauseBtn.addActionListener(new pauseBtnListener());

        add(startBtn);
        add(pauseBtn);
    }

    public void paint(Graphics g) {
        circle.draw(g);
    }

    public void run() {
        while (true) {
            try {
                circle.move(getWidth(), getHeight());
                repaint();
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
    class startBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(startFlag){
                thread.start();
                startFlag = false;
            }else{
                thread.resume();
            }
        }
    }
    class pauseBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            thread.suspend();
        }
    }


}