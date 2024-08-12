package pkg;

class Hello{
    public static void main(String[] args){
        System.out.println("Hello");
    }
}

class Hi{}

/*
 * 1. Compile the program using javac -d . addPkg.java
 * 2. Run the program using java pkg.Hello
 * 3. Output: Hello with creating a directory named pkg with compiled classes inside.
 */