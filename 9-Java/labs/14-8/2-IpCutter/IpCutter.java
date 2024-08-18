class IPCutter {
    public static void main(String[] args) {
        if(args.length != 0){
            int dotIndex = args[0].indexOf('.');
            int firstIndex = 0;

            while (dotIndex != -1) {
            System.out.println(args[0].substring(firstIndex, dotIndex));
            firstIndex = dotIndex + 1;
            dotIndex = args[0].indexOf('.', firstIndex);
            }

        System.out.println(args[0].substring(firstIndex));
        }else{
            System.out.println("Invalid input, Enter Ip address.");
        }
    }
}

