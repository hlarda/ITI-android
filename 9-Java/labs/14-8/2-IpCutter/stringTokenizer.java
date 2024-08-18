import java.util.StringTokenizer;

class Tokenizer{
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a ip as an argument.");
            return;
        }

        StringTokenizer tokenizer = new StringTokenizer(args[0], ".");

        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
        }
    }
}