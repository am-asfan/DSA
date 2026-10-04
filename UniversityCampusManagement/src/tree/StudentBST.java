package tree;

import model.Student;

/**
 * Custom Binary Search Tree that organises students by Student ID.
 *
 *              IT050
 *             /     \
 *         IT025     IT075
 *
 * Rule: left subtree IDs  <  node ID  <  right subtree IDs
 * IDs are compared alphabetically with compareToIgnoreCase().
 * An in-order traversal (Left, Node, Right) therefore visits students in sorted ID order.
 */
public class StudentBST {

    private BSTNode root;
    private int size;

    public StudentBST() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    // ------------------------------------------------------------------ insert

    /**
     * Inserts a student into the correct position of the tree.
     * @return false if the Student ID already exists (duplicates are not allowed)
     */
    public boolean insert(Student student) {
        if (student == null || search(student.getStudentId()) != null) {
            return false;
        }
        root = insertRecursive(root, student);
        size++;
        return true;
    }

    private BSTNode insertRecursive(BSTNode node, Student student) {
        if (node == null) {
            return new BSTNode(student); // found the empty spot
        }
        int comparison = student.getStudentId().compareToIgnoreCase(node.getKey());
        if (comparison < 0) {
            node.setLeft(insertRecursive(node.getLeft(), student));
        } else {
            node.setRight(insertRecursive(node.getRight(), student));
        }
        return node;
    }

    // ------------------------------------------------------------------ search

    /**
     * Searches by moving left or right at each node - O(height of tree).
     * @return the student, or null if not found
     */
    public Student search(String studentId) {
        BSTNode current = root;
        while (current != null) {
            int comparison = studentId.compareToIgnoreCase(current.getKey());
            if (comparison == 0) {
                return current.getStudent();
            }
            current = (comparison < 0) ? current.getLeft() : current.getRight();
        }
        return null;
    }

    // ------------------------------------------------------------------ delete

    /**
     * Deletes the student with the given ID.
     * @return false if the tree is empty or the student is not found
     */
    public boolean delete(String studentId) {
        if (search(studentId) == null) {
            return false;
        }
        root = deleteRecursive(root, studentId);
        size--;
        return true;
    }

    private BSTNode deleteRecursive(BSTNode node, String studentId) {
        if (node == null) {
            return null;
        }

        int comparison = studentId.compareToIgnoreCase(node.getKey());
        if (comparison < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), studentId));
        } else if (comparison > 0) {
            node.setRight(deleteRecursive(node.getRight(), studentId));
        } else {
            // Found the node to delete.
            // Case 1 & 2: zero or one child -> replace node with its child
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            // Case 3: two children -> copy the in-order successor
            // (smallest node in the right subtree), then delete that successor.
            BSTNode successor = findMinimum(node.getRight());
            node.setStudent(successor.getStudent());
            node.setRight(deleteRecursive(node.getRight(), successor.getKey()));
        }
        return node;
    }

    private BSTNode findMinimum(BSTNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // --------------------------------------------------------------- traversal

    /** Prints every student in sorted Student ID order (Left -> Node -> Right). */
    public void inOrderTraversal() {
        inOrderRecursive(root);
    }

    private void inOrderRecursive(BSTNode node) {
        if (node == null) {
            return;
        }
        inOrderRecursive(node.getLeft());
        node.getStudent().printTableRow();
        inOrderRecursive(node.getRight());
    }

    /** Displays the sorted student table followed by the shape of the tree. */
    public void display() {
        if (isEmpty()) {
            System.out.println("The BST is empty. No students to display.");
            return;
        }

        System.out.println("Students sorted by Student ID (in-order traversal):");
        System.out.println();
        Student.printTableHeader();
        inOrderTraversal();
        System.out.println(Student.TABLE_LINE);
        System.out.println("Total students in BST: " + size);

        System.out.println();
        System.out.println("BST structure (rotated 90 degrees: root on the left,");
        System.out.println("right children above, left children below):");
        System.out.println();
        printTree(root, 0);
    }

    /** Prints the tree sideways: right subtree first, then node, then left subtree. */
    private void printTree(BSTNode node, int depth) {
        if (node == null) {
            return;
        }
        printTree(node.getRight(), depth + 1);
        System.out.println("        ".repeat(depth) + node.getKey());
        printTree(node.getLeft(), depth + 1);
    }
}
