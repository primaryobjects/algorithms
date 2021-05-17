import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
    private Node<Item> _head, _tail;
    private int count = 0;

    // construct an empty deque
    public Deque()
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

    // is the deque empty?
    public boolean isEmpty()
    {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        if (isEmpty())
        {
            _head = _tail = new Node(item);
        }
        else
        {
            Node<Item> node = new Node(item, _head);
            _head.prev = node;
            _head = node;
        }

        count++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        if (isEmpty())
        {
            _head = _tail = new Node(item);
        }
        else
        {
            Node node = new Node(item, null, _tail);
            _tail.next = node;
            _tail = _tail.next;
        }

        count++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        Node<Item> node = _head;
        _head = _head.next;
        
        count--;
        
        if (isEmpty())
        {
            _head = _tail = null;
        }

        return node.data;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        Node<Item> node = _tail;
        _tail = _tail.prev;

        count--;
        
        if (!isEmpty())
        {
            _tail.next = null;
        }
        else
        {
            _head = _tail = null;
        }

        return node.data;
    }

    // return an iterator over items in order from front to back
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

        System.out.println("\n---");
    }

    // unit testing (required)
    public static void main(String[] args)
    {        
        Deque<Integer> deque;
        
        deque = new Deque();
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(1);
        deque.addFirst(9);
        deque.removeFirst();
        deque.print();

        deque = new Deque();
        deque.addFirst(1);
        deque.print();

        deque = new Deque();
        deque.addFirst(1);
        deque.removeFirst();
        deque.print();

        deque = new Deque();
        deque.addFirst(1);
        deque.removeLast();
        deque.print();

        deque = new Deque();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.removeFirst();
        deque.removeLast();
        deque.print();

        deque = new Deque();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.removeLast();
        deque.removeFirst();
        deque.print();

        deque = new Deque();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeLast();
        deque.removeFirst();
        deque.print();

        deque = new Deque();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeFirst();
        deque.removeLast();
        System.out.println("isEmpty: " + deque.isEmpty());
        deque.print();

        deque = new Deque();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeFirst();
        deque.removeLast();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        System.out.println("Size: " + deque.size());
        deque.print();

        deque = new Deque();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addLast(3);
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext())
        {
            int i = iterator.next();
            System.out.print(i);
        }
        System.out.println("\n---");
    }
}