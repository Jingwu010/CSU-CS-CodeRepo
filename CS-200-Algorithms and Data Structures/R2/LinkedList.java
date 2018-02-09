public class LinkedList {

    private Node head;
    private int size;

    public LinkedList() {
            head = null;
            size = 0;
    }

    public void clear() {
            head = null;
            size = 0;
    }

    public void add(Object item) {
            add(size, item);
    }

    public void add(int index, Object item) {
            if (index < 0 || index > size)
                    throw new IndexOutOfBoundsException(
                                    "List index out of bounds on add");
            if (index == 0) {
                    head = new Node(item, head);
            } else { // find predecessor
                    Node curr = head;
                    for (int i = 0; i < index - 1; i++) {
                            curr = curr.getNext();
                    }
                    // append after predecessor
                    curr.setNext(new Node(item, curr.getNext()));
            }
            size++;
    }
    public Object get(int index){
   	    Object res = null;
        if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException(
                                "List index out of bounds on remove");
        if (index == 0) {
        	    res = head.getItem();
        } else { // locate node
                Node curr = head;
                for (int i = 0; i < index; i++)
                        curr = curr.getNext();
                res =curr.getItem();
        }
        return res;
    }
    
    public Object remove(int index) {
    	    Object res = null;
            if (index < 0 || index >= size)
                    throw new IndexOutOfBoundsException(
                                    "List index out of bounds on remove");
            if (index == 0) {
            	    res = head.getItem();
                    head = head.getNext();
            } else { // locate predecessor of node to be removed
                    Node curr = head;
                    for (int i = 0; i < index - 1; i++)
                            curr = curr.getNext();
                    res =curr.getNext().getItem();
                    curr.setNext(curr.getNext().getNext());
            }
            size--;
            return res;
    }

 
    public int size() {
            return size;
    }

    public boolean isEmpty() {
            return size == 0;
    }

    public String toString() {
            String res = "[ " ;
            for (Node current = head; current != null; current = current.getNext())
                    res += current.getItem().toString() + " ";
            return res + "]";
    }

    public static void main(String[] args) {

            LinkedList list = new LinkedList();
            list.add("Jane");
            System.out.println(list);
            list.add("John");
            System.out.println(list);
            list.add("Jess");
            System.out.println(list);

            list.add(0, "AAAA");
            System.out.println(list);
            list.add(4, "ZZZZ");
            System.out.println(list);
            list.add(2, "BBBB");
            System.out.println(list);


            System.out.println("item 0: " + list.get(0));
            list.remove(0);
            System.out.println("removing at index 0");
            System.out.println(list);
            System.out.println("item 1: " + list.get(1));
            list.remove(1);
            System.out.println("removing at index 1");
            System.out.println(list);
            System.out.println("item 3: " + list.get(3));
            list.remove(3);
            System.out.println("removing at index 3");
            System.out.println(list);
          
    }
}