package linkedlist;

import model.Student;

/**
 * One node of the singly linked list: holds a student and a link to the next node.
 */
public class StudentNode {

    private Student student;
    private StudentNode next;

    public StudentNode(Student student) {
        this.student = student;
        this.next = null;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentNode getNext() {
        return next;
    }

    public void setNext(StudentNode next) {
        this.next = next;
    }
}
