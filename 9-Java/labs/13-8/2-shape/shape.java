abstract class Shape {
    private int x;

    public Shape() {
        System.out.println("Shape default");
    }

    public Shape(int x) {
        this.x = x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    abstract public double calculateArea();
}

class Circle extends Shape {
    private int r;

    public Circle() {
        System.out.println("Circle default");
    }

    public Circle(int r) {
        System.out.println("Circle para");
        this.r = r;
    }

    @Override
    public double calculateArea() {
        return 3.14 * r * r;
    }
}

class Rectangle extends Shape {
    private int x, y;

    public Rectangle() {
        System.out.println("Rectangle default");
    }

    public Rectangle(int x, int y) {
        super(); 
        System.out.println("Rectangle para");
        this.x = x;
        this.y = y;
    }

    public double calculateArea() {
        return x * y;
    }
}

class Main {
     public static double sumShapes(Shape s1, Shape s2){
        return (s1.calculateArea() + s2.calculateArea());
    }
    public static void main(String[] args) {
      
        Shape s1 = new Circle(5);
        Shape s2 = new Rectangle(5, 10);

       System.out.println(sumShapes(s1, s2));
    }
}

