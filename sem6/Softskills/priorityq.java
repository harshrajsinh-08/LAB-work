public class priorityq {
    class PriorityQNode<T> {
        T data;
        int pr;
        PriorityQNode<T> prev;
        PriorityQNode<T> next;

        public PriorityQNode(T data, int priority) {
            this.data = data;
            this.pr = priority;
        }
    }

    // Priority Queue class
    class PriorityQ<T> {
        PriorityQNode<T> head;
        PriorityQNode<T> tail;

        public PriorityQ() {
            head = null;
            tail = null;
        }

        public void insert(T data, int priority) {
            PriorityQNode<T> newNode = new PriorityQNode<>(data, priority);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else if (priority < head.pr) {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            } else {
                PriorityQNode<T> current = head;
                while (current.next != null && current.next.pr <= priority) {
                    current = current.next;
                }
                newNode.prev = current;
                newNode.next = current.next;

                if (current.next != null) {
                    current.next.prev = newNode;
                } else {
                    tail = newNode;
                }

                current.next = newNode;
            }
        }

        public T delete() {
            if (head == null) {
                return null;
            }
            T data = head.data;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
            return data;
        }

        public T peek() {
            if (head == null) {
                return null;
            }
            return head.data;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    // Main class to test the Priority Queue
    public static void main(String[] args) {
        priorityq example = new priorityq();
        PriorityQ<String> priorityQueue = example.new PriorityQ<>();

        priorityQueue.insert("Task A", 3);
        priorityQueue.insert("Task B", 1);
        priorityQueue.insert("Task C", 2);

        System.out.println("Highest-priority task: " + priorityQueue.peek());

        while (!priorityQueue.isEmpty()) {
            System.out.println("Executing: " + priorityQueue.delete());
        }
    }
}