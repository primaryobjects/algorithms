import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Deque<Item> _queue;
    private Item _selectedItem;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        _queue = new Deque<Item>();
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return _queue.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return _queue.size();
    }

    // add the item
    public void enqueue(Item item)
    {
        // Randomly add the item to the front or back of the queue.
        if (StdRandom.bernoulli())
        {
            _queue.addFirst(item);         
        }
        else
        {
            _queue.addLast(item);
        }

        // Randomly select this item as the current selection.
        if (_selectedItem == null || StdRandom.bernoulli(1.0/size()))
        {
            _selectedItem = item;
        }
    }

    // remove and return a random item
    public Item dequeue()
    {
        Item item = _queue.removeLast();

        // If we just removed the selected item, choose a new one.
        if (_selectedItem == item)
        {
            Iterator<Item> iterator = _queue.iterator();
            if (iterator.hasNext())
            {
                _selectedItem = iterator.next();
            }
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        Item item = null;

        int i = 1;
        Iterator<Item> iterator = _queue.iterator();
        while (iterator.hasNext())
        {
            item = iterator.next();
            if (StdRandom.bernoulli(1.0/i++))
            {
                break;
            }
        }

        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return _queue.iterator();
    }

    private void print()
    {
        for (Item item : _queue)
        {
            System.out.print(item);
        }

        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> queue;

        for (int i=0; i<10; i++)
        {
            queue = new RandomizedQueue<Integer>();
            queue.enqueue(1);
            queue.enqueue(2);
            queue.enqueue(3);
            queue.print();
        }
    }
}