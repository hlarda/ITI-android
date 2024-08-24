import java.applet.Applet;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppletCounter extends Applet {
    private int counter;
    Button incrementBtn, decrementBtn;

    public void init(){
        incrementBtn = new Button("+");
        decrementBtn = new Button("-");

        incrementBtn.addActionListener(new incrementBtnListener());

        decrementBtn.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    counter --;
                    repaint() ;
                }
            });

        add(incrementBtn);
        add(decrementBtn);
    }

    public void paint(Graphics g){
        g.drawString( "Counter equals:  " + counter, getWidth()/2-50, getHeight()/2);
    }

    class incrementBtnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            counter++;
            repaint();
        }
    }
}
