public class MergeSorted
{
    private Node head, tail;

    public MergeSorted()
    {
        // Initialize a virtual node to serve as the head and tail.
        head = new Node(-1);
        tail = head;
    }

    private class Node
    {
        public int data;
        public Node next;

        public Node(int data)
        {
            this.data = data;
        }

        public Node(int data, Node next)
        {
            this(data);
            this.next = next;
        }
    }

    public Node merge(Node a, Node b)
    {
        // Go through all elements in both a and b.
        while (a != null || b != null)
        {
            if (a == null)
            {
                // No more items left in a, take everything from b and we're done.
                tail.next = b;
                b = null;
            }
            else if (b == null)
            {
                // No more items left in b, take everything from a and we're done.
                tail.next = a;
                a = null;
            }
            else
            {
                // Compare the current values in a and b.
                Node node;

                if (a.data < b.data)
                {
                    // Take the value currently in a.
                    node = new Node(a.data);

                    // Move to the next value in a.
                    a = a.next;
                }
                else
                {
                    // Take the value currently in b.
                    node = new Node(b.data);

                    // Move to the next value in b.
                    b = b.next;
                }

                // Append the value to our result;
                tail.next = node;
                
                // Update the tail.
                tail = node;
            }
        }

        // Skip the virtual node and return the rest.
        return head.next;
    }

    public void print(Node h)
    {
        Node node = h;

        if (node != null)
        {
            while (node.next != null)
            {
                // Print each node in the linked list.
                System.out.print(node.data + ",");
                node = node.next;
            }

            System.out.print(node.data);
        }
    }

    public Node generate(int[] values)
    {
        Node h = null, t = null;

        // Generate a linked list from an array of values.
        if (values.length > 0)
        {
            for (int value : values)
            {
                if (h == null)
                {
                    // Initialize the head and tail node.
                    h = new Node(value);
                    t = h;
                }    
                else
                {
                    // Append a node.
                    t.next = new Node(value);
                    t = t.next;
                }
            }
        }

        return h;
    }
    public static void main(String[] args)
    {
        MergeSorted mergeSorted = new MergeSorted();
        
        Node a = mergeSorted.generate(new int[]{1, 3, 20});
        Node b = mergeSorted.generate(new int[]{2, 5, 10, 15});
        
        System.out.println("Merging lists:");
        mergeSorted.print(a);
        System.out.println();
        mergeSorted.print(b);

        System.out.println("\nResult:");
        Node result = mergeSorted.merge(a, b);
        mergeSorted.print(result);

        mergeSorted = new MergeSorted();
        a = mergeSorted.generate(new int[]{1});
        result = mergeSorted.merge(a, null);
        System.out.println();
        mergeSorted.print(result);

        mergeSorted = new MergeSorted();
        a = mergeSorted.generate(new int[]{1});
        result = mergeSorted.merge(null, a);
        System.out.println();
        mergeSorted.print(result);

        mergeSorted = new MergeSorted();
        result = mergeSorted.merge(null, null);
        System.out.println();
        mergeSorted.print(result);

        mergeSorted = new MergeSorted();
        a = mergeSorted.generate(new int[]{1, 2, 3, 5, 7, 9});
        b = mergeSorted.generate(new int[]{3, 4, 6, 8, 10});
        result = mergeSorted.merge(a, b);
        System.out.println();
        mergeSorted.print(result);
    }
}