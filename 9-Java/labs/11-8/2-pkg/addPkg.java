package pkg;

class Hello{
    public static void main(String[] args){
        System.out.println("Hello");
    }
}

class Hi{}

/*
    $ jar cef pkg.Hello jarjor.jar pkg/*
    $ java -jar jarjor.jar 
    Hello 
*/