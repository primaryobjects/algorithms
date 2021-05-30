import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private int _n;
    private int[][] _tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        if (tiles == null)
        {
            throw new java.lang.IllegalArgumentException();
        }
    
        if (tiles.length != tiles[0].length)
        {
            throw new java.lang.IllegalArgumentException();
        }

        _n = tiles.length;
        _tiles = new int[_n][_n];

        for (int y=0; y<tiles.length; y++)
        {
            for (int x=0; x<tiles.length; x++)
            {
                _tiles[y][x] = tiles[y][x];
            }
        }
    }

	private int[] toXY(int i) {
		int [] xy = new int[2];
		
        xy[0] = i % _n; // col
		xy[1] = i / _n; // row

		return xy;
	}

    // string representation of this board
    public String toString()
    {
        String result = _n + "\n";

        for (int y=0; y<_tiles.length; y++)
        {
            for (int x=0; x<_tiles.length; x++)
            {
                result += _tiles[y][x] + " ";
            }

            result += "\n";
        }

        return result;
    }

    // board dimension n
    public int dimension()
    {
        return _n;
    }

    // number of tiles out of place
    public int hamming()
    {
        int count = 0;

        for (int y=0; y<_tiles.length; y++)
        {
            for (int x=0; x<_tiles.length; x++)
            {
                int correct = (y * _n) + x + 1;
                if (_tiles[y][x] != correct)
                {
                    count++;
                }
            }
        }

        return count - 1; // -1 for blank space
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        int count = 0;

        for (int y=0; y<_tiles.length; y++)
        {
            for (int x=0; x<_tiles.length; x++)
            {
                if (_tiles[y][x] != 0)
                {
                    //int correct = (y * _n) + x + 1;
                    int[] location1 = toXY(_tiles[y][x] - 1);
                    int[] location2 = new int[]{x, y};

                    int distance = (Math.abs(location1[0] - location2[0]) + Math.abs(location1[1] - location2[1]));
                    //System.out.println("Tile: " + _tiles[y][x] + ", Should Equal: " + correct + ", location1: " + location1[0] + "," + location1[1] + ", location2: " + location2[0] + "," + location2[1] + ", Difference: " + distance);
                    count += distance;
                }
            }
        }

        return count;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == this)
        {
            return true;
        }
        else if (y == null || y.getClass() != this.getClass())
        {
            return false;
        }

        String board1 = toString();
        String board2 = ((Board)y).toString();

        return board1.equals(board2);
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }

    private int[][] swap(int i1, int j1, int i2, int j2) {
        int[][] copy = copy(_tiles);
        int temp = copy[i1][j1];
        copy[i1][j1] = copy[i2][j2];
        copy[i2][j2] = temp;
        return copy;
    }

    private int blankPosition() {
        for (int i = 0; i < _tiles.length; i++)
            for (int j = 0; j < _tiles.length; j++)
                if (_tiles[i][j] == 0)
                    return j + i * dimension();
        return -1;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        Stack<Board> neighbours = new Stack<>();
        int position = blankPosition();
        int i = position / dimension();
        int j = position % dimension();
        if (i > 0)
            neighbours.push(new Board(swap(i, j, i - 1, j)));
        if (i < _tiles.length - 1)
            neighbours.push(new Board(swap(i, j, i + 1, j)));
        if (j > 0)
            neighbours.push(new Board(swap(i, j, i, j - 1)));
        if (j < _tiles.length - 1)
            neighbours.push(new Board(swap(i, j, i, j + 1)));

        return neighbours;
    }

    // a board that is obtained by exchanging any pair of tiles (except blank space 0).
    public Board twin()
    {
        int[] swapTile1 = null;
        int[] swapTile2 = null;
        int[][] tiles = new int[_n][_n];

        for (int y=0; y<_tiles.length; y++)
        {
            for (int x=0; x<_tiles.length; x++)
            {
                // Copy the tile.
                tiles[y][x] = _tiles[y][x];

                // Try and mark this tile as a swap.
                int tile = _tiles[y][x];
                if (tile != 0)
                {
                    // Found a tile to swap.
                    if (swapTile1 == null)
                    {
                        swapTile1 = new int[]{y, x};
                    }
                    else if (swapTile2 == null)
                    {
                        swapTile2 = new int[]{y, x};
                    }
                }
            }
        }

        // Swap the two tiles.
        int temp = tiles[swapTile1[0]][swapTile1[1]];
        tiles[swapTile1[0]][swapTile1[1]] = tiles[swapTile2[0]][swapTile2[1]];
        tiles[swapTile2[0]][swapTile2[1]] = temp;

        return new Board(tiles);
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {
        int[][] tiles = new int[3][3];
        tiles[0] = new int[]{0, 1, 3};
        tiles[1] = new int[]{4, 2, 5};
        tiles[2] = new int[]{7, 8, 6};

        Board board1 = new Board(tiles);
        StdOut.println(board1.toString());
        StdOut.println(board1.hamming());

        // Test equality true.
        Board board2 = new Board(tiles);
        StdOut.println(board1.equals(board2));

        tiles[0][1] = 3;
        tiles[0][2] = 1;

        // Test equality false.
        board2 = new Board(tiles);
        StdOut.println(board1.equals(board2));

        // Test twin.
        board2 = board1.twin();
        StdOut.println(board2.toString());
        StdOut.println(board2.hamming());
        StdOut.println(board1.equals(board2));

        // Test manhatten
        tiles[0] = new int[]{8, 1, 3};
        tiles[1] = new int[]{4, 0, 2};
        tiles[2] = new int[]{7, 6, 5};
        board1 = new Board(tiles);
        StdOut.println(board1.toString());
        StdOut.println("Hamming: " + board1.hamming());
        StdOut.println("Manhatten: " + board1.manhattan());

        for (Board board : board1.neighbors())
        {
            StdOut.println(board.toString());
        }
    }
}