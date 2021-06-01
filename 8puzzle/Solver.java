import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {
    private final Stack<Board> solutionBoards;
    private boolean isSolvable;

    public Solver(Board initial) 
    {
        if (initial == null) 
        {
            throw new IllegalArgumentException();
        }

        // Initialize our starting state.
        isSolvable = false;
        solutionBoards = new Stack<>();

        // Use a minimum priority queue to store potential board moves.
        MinPQ<SearchNode> searchNodes = new MinPQ<>();

        // Insert the starting board position and a second board with one tile swapped.
        searchNodes.insert(new SearchNode(initial, null));
        searchNodes.insert(new SearchNode(initial.twin(), null));

        while (!searchNodes.min().board.isGoal())
        {
            // Get the next least-cost board move.
            SearchNode searchNode = searchNodes.delMin();

            // Find all possible next moves.
            for (Board board : searchNode.board.neighbors())
            {
                // Avoid duplicate board.
                if (searchNode.prevNode == null || (searchNode.prevNode != null && !searchNode.prevNode.board.equals(board)))
                {
                    // We have a new board move, insert it into the priority queue.
                    searchNodes.insert(new SearchNode(board, searchNode));
                }
            }
        }

        // We've arrived at a goal from either our initial starting board or from its twin.
        // Find the goal state, which will be the lowest cost node.
        SearchNode current = searchNodes.min();

        // Walk backwards from the goal state to the initial board.
        while (current.prevNode != null)
        {
            solutionBoards.push(current.board);
            current = current.prevNode;
        }

        // Finally, add the last board (our starting board).
        solutionBoards.push(current.board);

        // If we've arrived back at our starting board, this game is solvable. Otherwise, it's the twin.
        if (current.board.equals(initial))
        {
            isSolvable = true;
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prevNode;
        private int moves;
        private int manhattan;

        public SearchNode(Board board, SearchNode prevNode) {
            this.board = board;
            this.prevNode = prevNode;

            // Calculate the cost using the manhattan distance.
            this.manhattan = board.manhattan();

            if (prevNode != null)
            {
                // Bump the penalty cost +1 for each move from the beginning.
                moves = prevNode.moves + 1;
            }
            else
            {
                // Initialize cost penalty to 0.
                moves = 0;
            }
        }

        @Override
        public int compareTo(SearchNode that) {
            // A* = Estimated cost to goal (manhattan) plus the cost from start to current state (this.moves).
            int priorityDiff = (this.manhattan + this.moves) - (that.manhattan + that.moves);

            // If the two boards are equal cost, break the tie by ignoring the cost so far and just using the pure manhattan distance.
            return  priorityDiff == 0 ? this.manhattan - that.manhattan : priorityDiff;
        }
    }

    public int moves() {
        int result = -1;

        if (isSolvable())
        {
            result = solutionBoards.size() - 1;
        }

        return result;
    }

    public Iterable<Board> solution() {
        Iterable<Board> result = null;

        if (isSolvable())
        {
            result = solutionBoards;
        }

        return result;
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public static void main(String[] args) {
        // create initial board from file
        //In in = new In("8puzzle/puzzle3x3-unsolvable1.txt");
        //int n = in.readInt();
        int[][] tiles = new int[3][3];
        tiles[0] = new int[]{0, 1, 3};
        tiles[1] = new int[]{4, 2, 5};
        tiles[2] = new int[]{7, 8, 6};

        /*for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();*/
        //Board initial = new Board(blocks);
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}