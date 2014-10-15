import java.util.*;

public class Board {
    private int[][] blocks;

    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][];
        for (int row = 0; row < blocks.length; ++row) this.blocks[row] = blocks[row].clone();
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int dist = 0;

        for (int r = 0; r < blocks.length; ++r) {
            for (int c = 0; c < blocks.length; ++c) {
                int val = blocks[r][c];
                if (val != 0 && val != r * blocks.length + c + 1) ++dist;
            }
        }

        return dist;
    }

    public int manhattan() {
        int dist = 0;

        for (int r = 0; r < blocks.length; ++r) {
            for (int c = 0; c < blocks.length; ++c) {
                int val = blocks[r][c];
                if (val != 0) {
                    --val;
                    int diff_r = val / blocks.length - r;
                    int diff_c = val % blocks.length - c;
                    dist += Math.abs(diff_c) + Math.abs(diff_r);
                }
            }
        }

        return dist;
    }

    public boolean isGoal() {
        int i = 1;
        for (int r = 0; r < blocks.length; ++r) {
            for (int c = 0; c < blocks.length; ++c) {
                int val = blocks[r][c];
                if (val != i && val != 0) return false;
                ++i;
            }
        }
        return true;
    }

    private boolean swap(int r0, int c0, int r1, int c1) {
        if (r1 < 0 || r1 >= blocks.length) return false;
        if (c1 < 0 || c1 >= blocks.length) return false;

        int tmp = blocks[r0][c0];
        blocks[r0][c0] = blocks[r1][c1];
        blocks[r1][c1] = tmp;
        return true;
    }

    private void swapAndEnqueue(ArrayList<Board> queue, int r0, int c0, int r1, int c1) {
        Board copy = new Board(blocks);
        if (copy.swap(r0, c0, r1, c1)) queue.add(copy);
    }

    private boolean swapWithNonZero(int r0, int c0, int r1, int c1) {
        if (r1 < 0 || r1 >= blocks.length) return false;
        if (c1 < 0 || c1 >= blocks.length) return false;
        if (blocks[r1][c1] == 0) return false;

        int tmp = blocks[r0][c0];
        blocks[r0][c0] = blocks[r1][c1];
        blocks[r1][c1] = tmp;
        return true;
    }

    public Board twin() {
        Board twin = new Board(blocks);
        for (int r = 0; r < blocks.length; ++r) {
            for (int c = 0; c < blocks.length; ++c) {
                if (twin.blocks[r][c] != 0) {
                    if (twin.swapWithNonZero(r, c, r, c - 1)) return twin;
                    if (twin.swapWithNonZero(r, c, r, c + 1)) return twin;
                }
            }
        }
        throw new IllegalArgumentException("Invalid board");
    }

    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        Board that = (Board)y;
        return java.util.Arrays.deepEquals(this.blocks, that.blocks);
    }

    public Iterable<Board> neighbors() {
        int r;
        int c = -1;

        found:
        for (r = 0; r < blocks.length; ++r) {
            for (c = 0; c < blocks.length; ++c) {
                if (blocks[r][c] == 0) break found;
            }
        }

        ArrayList<Board> queue = new ArrayList<Board>();
        swapAndEnqueue(queue, r, c, r - 1, c);
        swapAndEnqueue(queue, r, c, r + 1, c);
        swapAndEnqueue(queue, r, c, r, c - 1);
        swapAndEnqueue(queue, r, c, r, c + 1);
        return queue;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(blocks.length + "\n");
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

    }
}
