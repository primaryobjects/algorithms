public class HelloGoodbye {
    public static void main(String[] args) {
        // Prints "Hello name1 name2" to the terminal window.
        // Prints "Goodbye name2 name1" to the terminal window.
        if (args.length >= 2) {
            System.out.println("Hello " + args[0] + " and " + args[1] + ".");
            System.out.println("Goodbye " + args[1] + " and " + args[0] + ".");
        }
    }
}