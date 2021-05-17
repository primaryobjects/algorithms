import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            throw new IllegalArgumentException("Missing argument for number of results.");
        }

        int n = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
        {
            String string = StdIn.readString();
            queue.enqueue(string);
        }

        for (int i = 0; i < n; i++)
        {
            StdOut.println(queue.dequeue());
        }
    }
 }