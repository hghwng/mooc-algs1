public class Percolation {
	private boolean _isOpen[];
    private WeightedQuickUnionUF _union_full;
    private WeightedQuickUnionUF _union_prec;
	private int _N;
    private int _size;

	private int posToIndex(int i, int j) {
		if (i < 1 || i > _N
			|| j < 1 || j > _N)
			throw new IndexOutOfBoundsException();
        return (i - 1) * _N +  (j - 1);
	}

    private void connect(int src, int other) {
        if (_isOpen[other]) {
            _union_full.union(src, other);
            _union_prec.union(src, other);
        }
    }

	public Percolation(int N) {
		if (N <= 0)  throw new java.lang.IllegalArgumentException();
		_N = N;
        _size = N * N;
        _union_full = new WeightedQuickUnionUF(_size + 1);
        _union_prec = new WeightedQuickUnionUF(_size + 2);

        _isOpen = new boolean[_size + 2];
        _isOpen[_size] = _isOpen[_size + 1] =true;
	}

	public void open(int i, int j) {
		int idx = posToIndex(i, j);
        _isOpen[idx] = true;

        if (i != 1) {
            connect(idx, idx - _N);
        } else {
            // top
            connect(idx, _size);
        }

        if (i != _N) {
            connect(idx, idx + _N);
        } else {
            // bottom
            _union_prec.union(idx, _size + 1);
        }

        if (j != 1)  connect(idx, idx - 1);
        if (j != _N)  connect(idx, idx + 1);
	}

	public boolean isOpen(int i, int j) {
        return _isOpen[posToIndex(i, j)];
	}

	public boolean isFull(int i, int j) {
		return _union_full.connected(_size, posToIndex(i, j));
	}

	public boolean percolates() {
        return _union_prec.connected(_size, _size + 1);
	}
}
