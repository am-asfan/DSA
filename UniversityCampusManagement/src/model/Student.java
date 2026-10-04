package model;

/**
 * Represents one student record.
 * The Student ID uniquely identifies a student in every data structure
 * (Linked List, BST and Hash Table).
 */
public class Student {

    // Shared table layout so every structure prints students the same way
    public static final String TABLE_LINE =
            "----------------------------------------------------------------------";
    private static final String ROW_FORMAT = "%-12s%-22s%-22s%-6s%n";

    private final String studentId;   // ID never changes after creation (it is the key)
    private String name;
    private String programme;
    private int marks;

    public Student(String studentId, String name, String programme, int marks) {
        this.studentId = studentId;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    /** Prints the column headings used by all student tables. */
    public static void printTableHeader() {
        System.out.println(TABLE_LINE);
        System.out.printf(ROW_FORMAT, "ID", "Name", "Programme", "Marks");
        System.out.println(TABLE_LINE);
    }

    /** Prints this student as one row of a table. */
    public void printTableRow() {
        System.out.printf(ROW_FORMAT, studentId, name, programme, marks);
    }

    /** Prints this student as a labelled block (used for single search results). */
    public void printDetails() {
        System.out.println("Student ID : " + studentId);
        System.out.println("Name       : " + name);
        System.out.println("Programme  : " + programme);
        System.out.println("Marks      : " + marks);
    }

    @Override
    public String toString() {
        return studentId + " - " + name + " - " + programme + " - " + marks;
    }
}
