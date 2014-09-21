import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>{
    private class Node {
        Node next;
        Node prev;
        Item data;

        Node(Item initial) {
            data = initial;
        }
    }

    private Node _header = new Node(null);
    private int _size = 0;

    public Deque() {
        _header.next = _header.prev = _header;
        _header.data = null;
    }

    public boolean isEmpty() {
        return _size == 0;
    }

    public int size() {
        return _size;
    }

    private void addAfter(Node node, Item item) {
        if (item == null) throw new NullPointerException();

        ++_size;
        Node newNode = new Node(item);
        newNode.next = node.next;
        newNode.next.prev = newNode;
        newNode.prev = node;
        newNode.prev.next = newNode;
    }

    public void addFirst(Item item) {
        addAfter(_header, item);
    }

    public void addLast(Item item) {
        addAfter(_header.prev, item);
    }

    private Item removeAt(Node removed) {
        if (removed == _header) throw new NoSuchElementException();

        --_size;
        removed.prev.next = removed.next;
        removed.next.prev = removed.prev;
        return removed.data;
    }

    public Item removeFirst() {
        return removeAt(_header.next);
    }

    public Item removeLast() {
        return removeAt(_header.prev);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node _current = _header.next;

        public boolean hasNext() {
            return _current != _header;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item ret = _current.data;
            _current = _current.next;
            return ret;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();

        for (String arg: args) {
            if (arg.equals("F")) StdOut.println(deque.removeFirst());
            else if (arg.equals("L")) StdOut.println(deque.removeLast());
            else if (arg.equals("D")) {
                for (int e: deque) {
                    StdOut.println("dump:" + Integer.toString(e));
                }
            }
            else {
                int num = Integer.parseInt(arg);
                if (num > 0) deque.addLast(num);
                else deque.addFirst(num);
            }
        }
    }
}
