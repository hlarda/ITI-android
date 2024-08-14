abstract class Parent{
	private int num1;
	private int num2;
	
	public void setNum1(int num){
	    num1 = num;
	}
	public void setNum2(int num){
        num2 = num; 
    }
    public int getNum1(){
        return num1;
    }
    public int getNum2(){
        return num2;
    }
    
    public Parent(){}
    public Parent(int num1, int num2){
        this.num1 = num1;
        this.num2 = num2;
    }
    public int sum(int num1, int num2){
        return num1 + num2;
    }
}

class Child extends Parent{
    private int num3;

    public void setNum3 (int num){
        num3 = num;
    }
    public int getNum3 (){
        return num3;    
    }
    public int sum (int num1, int num2, int num3){
        return (num1 + super.sum(num2, num3));
    }
}

class Main{
    public static void main(String[] args) {
        Child childd = new Child();
        System.out.println(childd.sum(1,2,3));
    }
}

