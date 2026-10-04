package hashing;

import model.Student;

/**
 * Custom hash table for fast Student ID lookup.
 *
 * - Internally uses an ARRAY of buckets.
 * - Collisions are handled with SEPARATE CHAINING: every bucket is a small
 *   linked list, so several students can share the same bucket index.
 *
 *   bucket[5] -> [IT010] -> [IT001] -> null     (collision: same hash value)
 *   bucket[6] -> [IT002] -> null
 *   bucket[7] -> null
 */
public class StudentHashTable {

    /** One entry in a bucket's chain. */
    private static class HashNode {
        private Student student;
        private HashNode next;

        HashNode(Student student) {
            this.student = student;
        }
    }

    private static final int DEFAULT_CAPACITY = 11; // a prime number spreads keys better

    private final HashNode[] buckets;
    private int size;

    public StudentHashTable() {
        this(DEFAULT_CAPACITY);
    }

    public StudentHashTable(int capacity) {
        buckets = new HashNode[capacity];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Simple hash function:
     *   1. Add up the character codes of the (upper-case) Student ID.
     *   2. Take the remainder after dividing by the number of buckets.
     *
     * Example: "IT001" -> 73+84+48+48+49 = 302 -> 302 % 11 = 5
     */
    public int hash(String studentId) {
        String key = studentId.toUpperCase();
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += key.charAt(i);
        }
        return sum % buckets.length;
    }

    /**
     * Inserts a student at the front of its bucket's chain.
     * @return false if the Student ID already exists
     */
    public boolean insert(Student student) {
        if (student == null || search(student.getStudentId()) != null) {
            return false;
        }
        int index = hash(student.getStudentId());
        HashNode newNode = new HashNode(student);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        return true;
    }

    /**
     * Replaces the stored record for an existing Student ID (used after an update).
     * @return false if the Student ID is not in the table
     */
    public boolean update(Student updatedStudent) {
        HashNode node = findNode(updatedStudent.getStudentId());
        if (node == null) {
            return false;
        }
        node.student = updatedStudent;
        return true;
    }

    /**
     * Searches only ONE bucket (the one the hash function points to).
     * @return the student, or null if not found
     */
    public Student search(String studentId) {
        HashNode node = findNode(studentId);
        return (node == null) ? null : node.student;
    }

    private HashNode findNode(String studentId) {
        HashNode current = buckets[hash(studentId)];
        while (current != null) {
            if (current.student.getStudentId().equalsIgnoreCase(studentId)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Removes a student from its bucket's chain.
     * @return false if the Student ID is not in the table
     */
    public boolean delete(String studentId) {
        int index = hash(studentId);
        HashNode current = buckets[index];
        HashNode previous = null;

        while (current != null) {
            if (current.student.getStudentId().equalsIgnoreCase(studentId)) {
                if (previous == null) {
                    buckets[index] = current.next; // removing the first node in the chain
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    /** Displays every bucket and its chain, marking buckets with collisions. */
    public void display() {
        System.out.println("Hash table: " + buckets.length + " buckets, "
                + size + " student(s), separate chaining");
        for (int i = 0; i < buckets.length; i++) {
            StringBuilder line = new StringBuilder();
            line.append(String.format("Bucket [%2d] : ", i));

            int chainLength = 0;
            HashNode current = buckets[i];
            while (current != null) {
                line.append("[").append(current.student.getStudentId()).append("] -> ");
                chainLength++;
                current = current.next;
            }
            line.append("null");
            if (chainLength > 1) {
                line.append("   (collision: ").append(chainLength).append(" students)");
            }
            System.out.println(line);
        }
    }
}
