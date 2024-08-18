class Split {
    public static void main(String[] args) {
        if (args.length != 0) {
            String str = args[0];
            String[] arrTokens = str.split("\\.");
            for (String i : arrTokens) {
                System.out.println(i);
            }
        } else {
            System.out.println("Invalid input, Enter IP address.");
        }
    }
}

