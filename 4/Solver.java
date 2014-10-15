import java.util.ArrayDeque;

public class Solver {
    private class State implements Comparable<State>{
        private int move;
        private int priority;
        public State previous;
        public Board board;

        public int move() {
            return this.move;
        }

        public State(State previous, Board board) {
            this.previous = previous;
            this.board = board;

            if (previous == null) {
                this.move = 0;
            } else {
                this.move = previous.move + 1;
            }

            this.priority =  this.move + this.board.manhattan();
        }

        public int compareTo(State that) {
            return this.priority - that.priority;
        }
    }
    private State result;

    private void addNeighbors(MinPQ<State> queue) {
        State srcState = queue.delMin();
        for (Board dst: srcState.board.neighbors()) {
            if (srcState.previous != null && srcState.previous.board.equals(dst)) continue;
            queue.insert(new State(srcState, dst));
        }
    }

    public Solver(Board board) {
        MinPQ<State> self = new MinPQ<State>();
        MinPQ<State> twin = new MinPQ<State>();

        self.insert(new State(null, board));
        twin.insert(new State(null, board.twin()));

        for (;;) {
            if (self.min().board.isGoal()) {
                result = self.min();
                return;
            } else if (twin.min().board.isGoal()) {
                return;
            }
            addNeighbors(self);
            addNeighbors(twin);
        }
    }

    public boolean isSolvable() {
        return result != null;
    }

    public int moves() {
        if (!isSolvable()) return -1;
        return result.move();
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        ArrayDeque<Board> ret = new ArrayDeque<Board>();
        for (State state = result; state != null; state = state.previous) {
            ret.addFirst(state.board);
        }
        return ret;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
