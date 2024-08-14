import java.util.Scanner;

class Complex{
    private int real;
    private int img;

    public Complex (){
        img=0;
        real=0;
    }
    public Complex (int real, int img){
        this.real = real;        
        this.img  = img;
    }
    public int getReal(){
        return real;
    }
    public int getImg(){
        return img;
    }
    public void setReal( int real ){
        this.real = real;
    }
    public void setImg( int img ){
        this.img = img;
    }
    public Complex sum (Complex cmplx){
        Complex sumResult = new Complex( (this.real+cmplx.real) , (this.img+cmplx.img) );
        return sumResult;
    }
    public Complex subtract (Complex cmplx){
        Complex sumResult = new Complex( (this.real-cmplx.real) , (this.img-cmplx.img) );
        return sumResult;
    }
    public void print(){
        if (img >= 0){
            System.out.println(real + "+" + img + "i");
        }else{
            System.out.println(real + "" + img + "i");
        }
    }
}

class Main{
    public static void main(String[] args){
        int usrInput;
        Scanner input = new Scanner(System.in);
        Complex cmplx1 = new Complex();
        Complex cmplx2 = new Complex();
        Complex result = new Complex();
        
        System.out.println("Enter two complex numbers");
        System.out.println("First Number---------------");
        System.out.print("Real: ");
        cmplx1.setReal(input.nextInt());
        System.out.print("Imaginary: ");
        cmplx1.setImg(input.nextInt());
        System.out.println("Second Number---------------");
        System.out.print("Real: ");
        cmplx2.setReal(input.nextInt());
        System.out.print("Imaginary: ");
        cmplx2.setImg(input.nextInt());

        System.out.println("Choose which operation to perform on them");
        System.out.println("1. Add          2. Subtract         3. Exit");
        usrInput = input.nextInt();
        if(usrInput == 1){
            result = cmplx1.sum(cmplx2);
        }else if (usrInput == 2){
            result = cmplx1.subtract(cmplx2);
        }else if (usrInput == 3){
            System.exit(0);
        }else{
            System.out.println("Invalid Choice");
        }
        System.out.print("Result: ");
        result.print();
        
    }
}
