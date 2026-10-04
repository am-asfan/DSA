package tree;

import model.Student;

/**
 * One node of the Binary Search Tree.
 * Smaller Student IDs go to the left, larger Student IDs go to the right.
 */
public class BSTNode {

    private Student student;
    private BSTNode left;
    private BSTNode right;

    public BSTNode(Student student) {
        this.student = student;
        this.left = null;
        this.right = null;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getKey() {
        return student.getStudentId();
    }

    public BSTNode getLeft() {
        return left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }
}
