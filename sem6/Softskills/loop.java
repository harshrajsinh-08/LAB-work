class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class loop {

    // Function to detect and remove a loop in the linked list
    public static void detectAndRemoveLoop(Node head) {
        if (head == null || head.next == null) return;

        Node slow = head;
        Node fast = head;

        // Step 1: Detect the loop
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            // Loop detected
            if (slow == fast) {
                removeLoop(head, slow);
                return;
            }
        }
    }

    // Function to remove the loop
    private static void removeLoop(Node head, Node loopNode) {
        Node ptr1 = head;
        Node ptr2 = loopNode;

        // Step 2: Find the start of the loop
        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        // Step 3: Find the last node in the loop and break the loop
        Node lastNode = ptr2;
        while (lastNode.next != ptr2) {
            lastNode = lastNode.next;
        }
        lastNode.next = null; // Break the loop
    }

    // Utility function to print the linked list
    public static void printList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // Main function to test the loop removal
    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        // Create a loop for testing
        head.next.next.next.next.next = head.next.next;

        detectAndRemoveLoop(head);

        System.out.println("Linked List after removing loop:");
        printList(head);
    }
}