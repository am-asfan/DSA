package queue;

import model.ServiceRequest;

/**
 * Custom queue (FIFO) built from linked nodes.
 * Holds student service requests waiting to be processed.
 *
 *   front -> [SR001] -> [SR002] -> [SR003] <- rear
 *
 * enqueue() adds at the REAR, dequeue() removes from the FRONT,
 * so the first request added is the first request processed.
 */
public class ServiceQueue {

    /** One node of the queue. */
    private static class RequestNode {
        private final ServiceRequest request;
        private RequestNode next;

        RequestNode(ServiceRequest request) {
            this.request = request;
        }
    }

    private RequestNode front;
    private RequestNode rear;
    private int size;

    public ServiceQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /** Adds a request to the rear of the queue. */
    public void enqueue(ServiceRequest request) {
        RequestNode newNode = new RequestNode(request);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the request at the front of the queue.
     * @return the request, or null if the queue is empty
     */
    public ServiceRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        ServiceRequest request = front.request;
        front = front.next;
        if (front == null) {
            rear = null; // queue became empty
        }
        size--;
        return request;
    }

    /**
     * Returns the front request without removing it.
     * @return the request, or null if the queue is empty
     */
    public ServiceRequest peek() {
        if (isEmpty()) {
            return null;
        }
        return front.request;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    /** Checks whether a request ID is already waiting in the queue. */
    public boolean containsRequestId(String requestId) {
        RequestNode current = front;
        while (current != null) {
            if (current.request.getRequestId().equalsIgnoreCase(requestId)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /** Displays all requests from front (next to be processed) to rear. */
    public void display() {
        if (isEmpty()) {
            System.out.println("No service requests available.");
            return;
        }

        int position = 1;
        RequestNode current = front;
        while (current != null) {
            String marker = (current == front) ? "  <-- FRONT (next to process)" : "";
            System.out.println(position + ". " + current.request + marker);
            current = current.next;
            position++;
        }
        System.out.println("Total requests waiting: " + size);
    }
}
