class Main {
    public static void main(String[] args) {
        int result = 0; 

        if ((args.length == 3) && (args[1].equals("+") || args[1].equals("-") || args[1].equals("x") || args[1].equals("/"))) {
            switch (args[1]) {
                case "+":
                    result = Integer.valueOf(args[0]) + Integer.valueOf(args[2]);
                    break;
                case "-":
                    result = Integer.valueOf(args[0]) - Integer.valueOf(args[2]);
                    break;
                case "x":
                    result = Integer.valueOf(args[0]) * Integer.valueOf(args[2]);
                    break;
                case "/":
                    if (Integer.valueOf(args[2]) == 0) {
                        System.out.println("DIVISION BY ZERO!");
                        System.exit(0);
                    } else {
                        result = Integer.valueOf(args[0]) / Integer.valueOf(args[2]);
                    }
                    break;
            }
            System.out.println(result);
        } else {
            System.out.println("You should have entered only two operands separated by (+, -, x, or /) ");
            System.exit(0);
        }
    }
}

