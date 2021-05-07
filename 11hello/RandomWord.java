import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        // Prints a random word from an input of words using Knuth's method (no array, no storage of terms).
        // On the terminal, use Ctrl-D to end input.
        String word = "";
        int i=0;
        while (!StdIn.isEmpty())
        {
            String tempWord = StdIn.readString();
            if (++i == 1 || StdRandom.bernoulli(1.0/i)) {
                word = tempWord;
            }
        }

        StdOut.println(word);
    }
}