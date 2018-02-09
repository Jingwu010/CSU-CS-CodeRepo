public class Node {
    private Object item;
    private Node next;

    public Node(Object item) {
        this.item = item;
        this.next = null;
    }
    public Node(Object item, Node next) {
        this.item = item;
        this.next = next;
    }
    public void setNext(Node nextNode) {
        next = nextNode;
    }
    public Node getNext() {
        return next;
    }
    public Object getItem() {
        return item;
    }
    
    public String toString(){
        return item.toString();
    }
    
    public static void main(String[] args) {
        Node jess = new Node("Jess");
        Node john = new Node("John", jess);
        Node jane = new Node("Jane", john);
        //jess.setNext(jane);
         
        System.out.println("First sweep:");
        for (Node current = jane; current != null; current = current.getNext())
            System.out.println(current);
         
        jane.setNext(jess);
         
        System.out.println("\nSecond sweep:");
        for (Node current = jane; current != null; current = current.getNext())
                 System.out.println(current);
        
        
        // Careful with that axe, Eugene...
        // Node temp = jess.getNext().getNext();
    }
        
}