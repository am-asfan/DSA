package stack;

/**
 * Custom stack (LIFO) built from linked nodes.
 * Stores the recent actions performed in the system.
 *
 *   top -> [DELETE STUDENT - IT002] -> [ADD STUDENT - IT002] -> [ADD STUDENT - IT001] -> null
 *
 * The newest action is always on top, so it is displayed first.
 */
public class ActionStack {

    /** One node of the stack. */
    private static class ActionNode {
        private final String action;
        private ActionNode next;

        ActionNode(String action) {
            this.action = action;
        }
    }

    private ActionNode top;
    private int size;

    public ActionStack() {
        top = null;
        size = 0;
    }

    /** Places a new action on top of the stack. */
    public void push(String action) {
        ActionNode newNode = new ActionNode(action);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the top (most recent) action.
     * @return the action, or null if the stack is empty
     */
    public String pop() {
        if (isEmpty()) {
            return null;
        }
        String action = top.action;
        top = top.next;
        size--;
        return action;
    }

    /**
     * Returns the top action without removing it.
     * @return the action, or null if the stack is empty
     */
    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return top.action;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    /** Displays all actions from newest (top) to oldest (bottom). */
    public void display() {
        if (isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }

        int number = 1;
        ActionNode current = top;
        while (current != null) {
            System.out.println(number + ". " + current.action);
            current = current.next;
            number++;
        }
    }
}
