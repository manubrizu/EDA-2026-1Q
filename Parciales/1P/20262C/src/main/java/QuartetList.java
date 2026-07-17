
public class QuartetList {
    private Node head;
    private Node tail;

    class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    public void append(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void dump() {
        Node curr = head;
        while (curr != null) {
            System.out.print("[" + curr.value + "] -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }


    public void processQuartets() {
        Node prev = null;
        Node current = head;
        while (current != null && current.next != null && current.next.next != null && current.next.next.next != null) {
            Node n1 = current;
            Node n2 = current.next;
            Node n3 = current.next.next;
            Node n4 = current.next.next.next;
            int sumaExterna = n1.value + n4.value;
            int sumaInterna = n2.value + n3.value;
            if (sumaExterna > sumaInterna) {
                /// N1 -> N3 -> N2 -> N4
                n1.next = n3;
                n3.next = n2;
                n2.next = n4;
                prev = n4;
                current = n4.next;
            } else if (sumaExterna < sumaInterna) {
                /// N4 -> N2 -> N3 -> N1
                n1.next = n4.next;
                n4.next = n2;
                n3.next = n1;
                if (prev == null) {
                    head = n4;
                } else {
                    prev.next = n4;
                }
                prev = n1;
                current = n1.next;
            }
            else {  /// CASO QUE LA SUMA DE IGUAL
                prev = n4;
                current = n4.next;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("MAIN 1");
        main1(args);
        System.out.println("MAIN 2");
        main2(args);
        System.out.println("MAIN 3");
        main3(args);
    }

    public static void main1(String[] args) {
        QuartetList list = new QuartetList();
        list.append(10); list.append(1); list.append(2); list.append(10);

        list.processQuartets();
        list.dump();
    }

    public static void main2(String[] args) {
        QuartetList list = new QuartetList();
        list.append(5); list.append(1); list.append(2); list.append(5);
        list.append(2); list.append(8); list.append(9); list.append(3);

        list.processQuartets();
        list.dump();
    }

    public static void main3(String[] args) {
        QuartetList list = new QuartetList();
        list.append(5); list.append(1); list.append(2); list.append(5);
        list.append(2); list.append(8);

        list.processQuartets();
        list.dump();
    }

}
