import java.applet.Applet;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Label;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//import java.awt.BasicStroke;
//import java.awt.Graphics2D;
//import java.awt.Stroke;

enum ShapeType{ LINE,   OVAL,   RECTANGLE,  FREEHAND }

public class Paint extends Applet{
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Color tmpColor   = Color.RED;
    private boolean isFilled = false;
    //private boolean isDashed = false;
    private Shape tmpShape   = new Line(isFilled,tmpColor);
    private int shapeCounter = 0;


    private int indexBeforeClear   = 0;
    private ShapeType currentShape = ShapeType.LINE;

    private boolean isPressed = false, isDragged = false;

    public void init(){
        setBackground(Color.WHITE);

        Label colorLabel        = new Label("Color:");
        CheckboxGroup colorGroup= new CheckboxGroup();
        Checkbox redChk         = new Checkbox("Red", colorGroup, true);
        Checkbox greenChk       = new Checkbox("Green", colorGroup, false);
        Checkbox blueChk        = new Checkbox("Blue", colorGroup, false);
        Checkbox whiteChk        = new Checkbox("White", colorGroup, false);

        Label shapeLabel        = new Label("Shape:");
        CheckboxGroup shapeGroup= new CheckboxGroup();
        Checkbox lineChk        = new Checkbox("Line", shapeGroup, true);
        Checkbox ovalChk        = new Checkbox("Oval", shapeGroup, false);
        Checkbox rectChk        = new Checkbox("Rectangle", shapeGroup, false);
        Checkbox freehandChk    = new Checkbox("Freehand", shapeGroup, false);

        Label styleLabel        = new Label("Style:");
        Checkbox fillChk        = new Checkbox("Fill");
        //CheckboxGroup styleGroup= new CheckboxGroup();
        //Checkbox fillChk        = new Checkbox("Fill",styleGroup,false);
        //Checkbox dashChk      = new Checkbox("Dash",styleGroup,false);

        redChk.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                tmpShape.color = tmpColor = Color.RED;
            }
        });
        greenChk.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                tmpShape.color = tmpColor = Color.GREEN;
            }
        });
        blueChk.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                tmpShape.color = tmpColor = Color.BLUE;
            }
        });
        lineChk.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                tmpShape     = new Line(isFilled,tmpColor);
                currentShape = ShapeType.LINE;
            }
        });
        ovalChk.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                tmpShape     = new Oval(isFilled,tmpColor);
                currentShape = ShapeType.OVAL;
            }
        });
        rectChk.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                tmpShape     = new Rectangle(isFilled,tmpColor);
                currentShape = ShapeType.RECTANGLE;
            }
        });
        freehandChk.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                tmpShape     = new Freehand(isFilled,tmpColor);
                currentShape = ShapeType.FREEHAND;
            }
        });
       fillChk.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                tmpShape.isFilled = isFilled = fillChk.getState();
                tmpShape.color = tmpColor;
            }
        });
        //dashChk.addItemListener(new ItemListener(){
        //    public void itemStateChanged(ItemEvent e){
        //        isDashed = dashChk.getState();
        //    }
        //});
        Button undoBtn = new Button("Undo");
        undoBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(indexBeforeClear != 0){
                    shapeCounter = indexBeforeClear;
                    indexBeforeClear = 0;
                }else if(shapeCounter > 0){
                    shapeCounter--;
                }
                repaint();
            }
        });
        Button clearAllBtn = new Button("Clear All");
        clearAllBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(indexBeforeClear == 0){
                    indexBeforeClear = shapeCounter;
                    shapeCounter = 0;
                }
                repaint();
            }
        });
        Button eraseBtn = new Button("Erase");
        eraseBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tmpColor = Color.WHITE;
                tmpShape.color = tmpColor;
                whiteChk.setState(true);
            }
        });
        colorLabel.setBackground(Color.GRAY);
        colorLabel.setForeground(Color.WHITE);
        add(colorLabel);    
        add(redChk);
        add(greenChk);  
        add(blueChk);

        shapeLabel.setBackground(Color.GRAY);
        shapeLabel.setForeground(Color.WHITE);
        add(shapeLabel);    
        add(lineChk);   
        add(ovalChk);   
        add(rectChk);   
        add(freehandChk);   
        
        styleLabel.setBackground(Color.GRAY);
        styleLabel.setForeground(Color.WHITE);
        add(styleLabel); 
        add(fillChk);  
        //add(dashChk);
        
        add(undoBtn);   
        add(clearAllBtn);
        add(eraseBtn);

        MouseActions mouseActions = new MouseActions();
        addMouseListener(mouseActions);
        addMouseMotionListener(mouseActions);
    }
    class MouseActions extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            if((!isPressed) && (!isDragged)){
                isPressed = true;
                if(currentShape == ShapeType.FREEHAND){
                    tmpShape = new Freehand(isFilled,tmpColor);
                } else{
                    tmpShape.setStartPoint(e.getX(), e.getY());
                }
            }
        }
        public void mouseDragged(MouseEvent e){
            if(isPressed){
                isDragged = true;
                if(currentShape == ShapeType.FREEHAND){
                    ((Freehand) tmpShape).addPoint(e.getX(), e.getY());
                } else{
                    tmpShape.setEndPoint(e.getX(), e.getY());
                }
                repaint();
            }
        }
        public void mouseReleased(MouseEvent e){
            if(isPressed && isDragged){
                shapes.add(shapeCounter,tmpShape);
                switch (currentShape){
                    case RECTANGLE:
                        tmpShape = new Rectangle(isFilled,tmpColor);
                        break;
                    case OVAL:
                        tmpShape = new Oval(isFilled,tmpColor);
                        break;
                    case LINE:
                        tmpShape = new Line(isFilled,tmpColor);
                        break;
                    case FREEHAND:
                        tmpShape = new Freehand(isFilled,tmpColor);
                        break;
                    default:                        break;
                }
                isPressed = isDragged = false;
                shapeCounter++;
                repaint();
            }
        }
    }
    public void paint(Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
        for(int i = 0; i < shapeCounter; i++){
            shapes.get(i).draw(g);
        }
        tmpShape.draw(g);        
    }
}
/*TODO: Add dashed style */
/*
    Graphics2D g2d = (Graphics2D) g.create();
    Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    g2d.setStroke(dashed);

    for(int i = 0; i < shapeCounter; i++){
        shapes.get(i).draw(g2d);
    }
    tmpShape.draw(g2d);
    g2d.dispose();
 */