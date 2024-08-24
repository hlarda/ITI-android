class Main {
    public static void main(String[] args) {
        int rows = 5;

        for (int i = 1; i <= rows; ++i) {
            for (int j = 1; j <=rows + i; ++j) {
                if (j <= i) {
                    System.out.print("*");
                } else if (j <= rows) {
                    System.out.print("  ");
                } else {
                    System.out.print(" *");
                }
            }
            System.out.println();
        }
    }
}


