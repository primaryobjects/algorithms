import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Item[] _items;
    private int _count = 0;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        _items = (Item[])new Object[2];
    }

    // #region Private Members
    private class RandomizedIterator implements Iterator<Item>
    {
        private final Item[] _items;
        private int _index;

        public RandomizedIterator(Item[] items, int count)
        {
            // Copy the array of items.
            _items = resize(items, count);

            // Shuffle the items.
            StdRandom.shuffle(_items);
        }

        public boolean hasNext()
        {
            return _index < _items.length;
        }

        public Item next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            return _items[_index++];
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    private void enlarge()
    {
        // If we've reached capacity in the current array, enlarge it to 2X.
        if (_items.length == _count)
        {
            _items = resize(_items, _count, _items.length * 2);
        }
    }

    private void shrink()
    {
        // If we only have 25% capacity items in the array, shrink it by 50%.
        if (_count > 0 && _items.length / 4 == _count)
        {
            _items = resize(_items, _count, _items.length / 2);
        }
    }

    private Item[] resize(Item[] arr, int currentSize, int newSize)
    {
        if (newSize < currentSize)
        {
            throw new IllegalArgumentException();
        }

        // Copy the array into the new sized array.
        Item[] arr2 = (Item[])new Object[newSize];
        for (int i = 0; i < _count; i++)
        {
            arr2[i] = arr[i];
        }

        return arr2;
    }

    private Item[] resize(Item[] arr, int currentSize)
    {
        return resize(arr, currentSize, currentSize);
    }
    // #endregion

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return _count;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        // Auto-enlarge the array, if needed.
        enlarge();

        // Add the item to the array.
        _items[_count++] = item;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        // Choose a random item to remove.
        int index = StdRandom.uniform(size());
        Item item = _items[index];

        // Replace the selected item with the last item in the array.
        _items[index] = _items[_count-1];
        
        // Trim the last item off.
        _items[_count-1] = null;

        _count--;

        // Auto-shrink the array, if needed.
        shrink();

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        // Choose a random item to remove.
        int index = StdRandom.uniform(size());
        return _items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomizedIterator(_items, _count);
    }

    private void print()
    {
        for (Item item : this)
        {
            System.out.print(item);
        }

        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> queue;

        for (int i = 0; i < 10; i++)
        {
            queue = new RandomizedQueue<Integer>();
            queue.enqueue(1);
            queue.enqueue(2);
            queue.enqueue(3);
            queue.print();
        }

        queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        for (int i = 0; i < 10; i++)
        {
            StdOut.print(queue.sample());
        }
        StdOut.println();

        queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        StdOut.println("Size=" + queue.size() + ", " + queue.dequeue());
        StdOut.println("Size=" + queue.size() + ", " + queue.dequeue());
        StdOut.println("Size=" + queue.size() + ", " + queue.dequeue());
        StdOut.println("Size=" + queue.size());
    }
}