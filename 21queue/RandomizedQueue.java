import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> _head;
    private int _count = 0;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
    }

    //#region Private classes
    private class Node<T>
    {
        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(T item)
        {
            data = item;
        }

        public Node(T item, Node<T> n)
        {
            this(item);
            next = n;
        }

        public Node(T item, Node<T> n, Node<T> p)
        {
            this(item, n);
            prev = p;
        }
    }

    private class NodeIterator<T> implements Iterator<T>
    {
        private Node<T> _current;

        public NodeIterator(Node<T> node)
        {
            // Initialize cursor.
            _current = node;
        }

        public boolean hasNext() {
            return _current != null;
        }

        public T next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            T data = _current.data;
            _current = _current.next;
            
            return data;
        }
    }
    //#endregion

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

        if (isEmpty())
        {
            Node<Item> node = new Node<Item>(item);
            _head = node;
        }
        else
        {
            // Choose a random index to insert at.
            int index = StdRandom.uniform(size() + 1);

            // Get a pointer to the head.
            Node<Item> current = _head;
            Node<Item> parent = null;

            // Move current to the desired index.
            for (int i = 0; i < index; i++)
            {
                parent = current;
                current = current.next;
            }

            // Now that we're in position, insert a new node using the parent and next.
            Node<Item> node = new Node<Item>(item, current, parent);

            // Store a pointer to the old child. Unless we are inserting the first node, in which case there is no child.
            Node<Item> temp = null;
            if (parent != null)
            {
                temp = parent.next;

                // Connect the parent to the new node, effectively inserting it into position.
                parent.next = node;
            }

            // Connect the old child's previous node to the new node, effectively inserting it into position. Unless we are inserting at the end of the list, in which case there is no old child.
            if (temp != null)
            {
                temp.prev = node;
            }

            if (index == 0)
            {
                // Update the head.
                _head = node;
            }
        }

        _count++;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        // Return the first element, since we're already in random order.
        Node<Item> node = _head;
        _head = _head.next;
        
        _count--;
        
        if (isEmpty())
        {
            _head = null;
        }

        return node.data;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        return null;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new NodeIterator<Item>(_head);
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