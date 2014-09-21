public class Palindrome {
    public static void main(String[] args) {
        Deque<Character> deque = new Deque<Character>();
        while (StdIn.hasNextChar())  deque.addLast(StdIn.readChar());

        boolean pass = true;
        while (!deque.isEmpty()) {
            char first = deque.removeFirst();
            if (deque.isEmpty()) {
                pass = false;
                break;
            }

            char last = deque.removeLast();
            if (first != last) {
                pass = false;
                break;
            }
        }

        if (pass) StdOut.println("true");
        else StdOut.println("false");
    }
}
