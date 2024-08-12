class Hello{
    public static void main(String[] args){
        if (args.length == 0){
            System.out.println("Usage: java Hello <name>");
        }else{
            System.out.println("Hello " + args[0]);
        }
    }
}