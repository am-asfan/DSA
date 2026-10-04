package linkedlist;

import model.Student;

/**
 * Custom singly linked list - the MAIN storage for student records.
 *
 *   head -> [IT001] -> [IT002] -> [IT003] -> null
 *
 * New students are added at the end (tail) so records keep their insertion order.
 */
public class StudentLinkedList {

    private StudentNode head;
    private StudentNode tail;
    private int size;

    public StudentLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    /**
     * Adds a student to the end of the list.
     * @return false if a student with the same ID already exists
     */
    public boolean addStudent(Student student) {
        if (student == null || containsStudentId(student.getStudentId())) {
            return false;
        }

        StudentNode newNode = new StudentNode(student);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        return true;
    }

    /**
     * Linear search: walk from the head until the ID matches.
     * @return the student, or null if not found
     */
    public Student searchStudent(String studentId) {
        StudentNode current = head;
        while (current != null) {
            if (current.getStudent().getStudentId().equalsIgnoreCase(studentId)) {
                return current.getStudent();
            }
            current = current.getNext();
        }
        return null;
    }

    public boolean containsStudentId(String studentId) {
        return searchStudent(studentId) != null;
    }

    /**
     * Updates name, programme and marks of an existing student.
     * @return false if the student does not exist
     */
    public boolean updateStudent(String studentId, String newName, String newProgramme, int newMarks) {
        Student student = searchStudent(studentId);
        if (student == null) {
            return false;
        }
        student.setName(newName);
        student.setProgramme(newProgramme);
        student.setMarks(newMarks);
        return true;
    }

    /**
     * Removes the student with the given ID by re-linking the previous node.
     * @return false if the list is empty or the student does not exist
     */
    public boolean deleteStudent(String studentId) {
        if (isEmpty()) {
            return false;
        }

        // Case 1: the node to delete is the head
        if (head.getStudent().getStudentId().equalsIgnoreCase(studentId)) {
            head = head.getNext();
            if (head == null) {
                tail = null; // list became empty
            }
            size--;
            return true;
        }

        // Case 2: find the node BEFORE the one to delete
        StudentNode previous = head;
        while (previous.getNext() != null) {
            StudentNode current = previous.getNext();
            if (current.getStudent().getStudentId().equalsIgnoreCase(studentId)) {
                previous.setNext(current.getNext()); // skip over the deleted node
                if (current == tail) {
                    tail = previous;
                }
                size--;
                return true;
            }
            previous = current;
        }
        return false;
    }

    /** Displays every student from head to tail in a table. */
    public void displayStudents() {
        if (isEmpty()) {
            System.out.println("No student records found. The list is empty.");
            return;
        }

        Student.printTableHeader();
        StudentNode current = head;
        while (current != null) {
            current.getStudent().printTableRow();
            current = current.getNext();
        }
        System.out.println(Student.TABLE_LINE);
        System.out.println("Total students: " + size);
    }
}
