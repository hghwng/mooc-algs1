import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] _arr;
    private int _length = 0;

    private void resize(int newLength) {
        if (newLength > _arr.length) newLength = 2 * _arr.length;
        else if (newLength < _arr.length / 4) newLength = _arr.length / 2;
        else return;

        Item[] newArr = (Item[])(new Object[newLength]);
        for (int i = 0; i < _length; ++i) {
            newArr[i] = _arr[i];
        }

        _arr = newArr;
    }

    public RandomizedQueue() {
        _arr = (Item[])(new Object[1]);
    }

    public boolean isEmpty() {
        return _length == 0;
    }

    public int size() {
        return _length;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        resize(_length + 1);
        _arr[_length] = item;
        ++_length;
    }

    public Item dequeue() {
        if (_length == 0) throw new NoSuchElementException();

        int idx = StdRandom.uniform(_length);
        Item ret = _arr[idx];
        _arr[idx] = _arr[_length - 1];
        _arr[_length - 1] = null;

        --_length;
        resize(_length);
        return ret;
    }

    public Item sample() {
        if (_length == 0) throw new NoSuchElementException();
        return _arr[StdRandom.uniform(_length)];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] _state;
        int _current = 0;

        public RandomizedQueueIterator() {
            _state = (Item[])(new Object[_length]);
            for (int i = 0; i < _length; ++i) {
                _state[i] = _arr[i];
            }
            StdRandom.shuffle(_state);
        }

        public boolean hasNext() {
            return _current != _state.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return _state[_current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; ++i) {
            queue.enqueue(i);
        }
        for (int e: queue) {
            StdOut.println(e);
        }
    }
}
