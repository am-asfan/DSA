import java.util.Scanner;

import graph.CampusGraph;
import hashing.StudentHashTable;
import linkedlist.StudentLinkedList;
import model.ServiceRequest;
import model.Student;
import queue.ServiceQueue;
import stack.ActionStack;
import tree.StudentBST;
import utils.InputValidator;

/**
 * CIT300 Data Structures & Algorithms - Graded Practical Assignment 1
 * University Student Record and Campus Route Management System
 *
 * Main only shows menus, reads input and calls the data structures.
 *
 *   Student records   -> StudentLinkedList (main storage)
 *   Recent actions    -> ActionStack
 *   Service requests  -> ServiceQueue
 *   Sorted students   -> StudentBST
 *   Fast ID search    -> StudentHashTable
 *   Campus network    -> CampusGraph (BFS / DFS)
 *
 * The Linked List, BST and Hash Table all hold references to the SAME
 * Student objects, and every add / update / delete is applied to all three
 * so they always stay synchronised.
 */
public class Main {

    private static final String DOUBLE_LINE = "==================================================";
    private static final int EXIT_OPTION = 18;
    private static final int SAMPLE_DATA_OPTION = 19;

    private static final StudentLinkedList studentList = new StudentLinkedList();
    private static final ActionStack actionStack = new ActionStack();
    private static final ServiceQueue serviceQueue = new ServiceQueue();
    private static final StudentBST studentTree = new StudentBST();
    private static final StudentHashTable studentHashTable = new StudentHashTable();
    private static final CampusGraph campusGraph = new CampusGraph();

    private static InputValidator input;

    public static void main(String[] args) {
        input = new InputValidator(new Scanner(System.in));

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = input.readMenuChoice("Enter your choice: ", 1, SAMPLE_DATA_OPTION);
            System.out.println();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> updateStudent();
                case 3 -> deleteStudent();
                case 4 -> displayAllStudents();
                case 5 -> addServiceRequest();
                case 6 -> processServiceRequest();
                case 7 -> displayServiceQueue();
                case 8 -> displayRecentActions();
                case 9 -> displayStudentsUsingBST();
                case 10 -> searchStudentUsingHashing();
                case 11 -> addCampusLocation();
                case 12 -> removeCampusLocation();
                case 13 -> addCampusConnection();
                case 14 -> removeCampusConnection();
                case 15 -> displayCampusConnections();
                case 16 -> bfsTraversal();
                case 17 -> dfsTraversal();
                case EXIT_OPTION -> running = false;
                case SAMPLE_DATA_OPTION -> loadSampleCampusData();
                default -> System.out.println("Invalid choice. Please enter a valid menu number.");
            }

            if (running) {
                input.readLine("\nPress Enter to return to the menu...");
            }
        }

        System.out.println("Thank you for using the system. Goodbye!");
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println(DOUBLE_LINE);
        System.out.println(" UNIVERSITY STUDENT & CAMPUS MANAGEMENT SYSTEM");
        System.out.println(DOUBLE_LINE);
        System.out.println();
        System.out.println("STUDENT MANAGEMENT");
        System.out.println("1. Add Student Record");
        System.out.println("2. Update Student Record");
        System.out.println("3. Delete Student Record");
        System.out.println("4. Display All Records");
        System.out.println();
        System.out.println("SERVICE REQUESTS");
        System.out.println("5. Add Service Request");
        System.out.println("6. Process Next Service Request");
        System.out.println("7. Display Service Queue");
        System.out.println();
        System.out.println("ACTION HISTORY");
        System.out.println("8. Display Recent Actions");
        System.out.println();
        System.out.println("STUDENT SEARCH");
        System.out.println("9. Display Students using BST");
        System.out.println("10. Search Student using Hashing");
        System.out.println();
        System.out.println("CAMPUS MANAGEMENT");
        System.out.println("11. Add Campus Location");
        System.out.println("12. Remove Campus Location");
        System.out.println("13. Add Campus Connection/Road");
        System.out.println("14. Remove Campus Connection/Road");
        System.out.println("15. Display Campus Connections");
        System.out.println("16. BFS Campus Traversal");
        System.out.println("17. DFS Campus Traversal");
        System.out.println();
        System.out.println("18. Exit");
        System.out.println();
        System.out.println("OPTIONAL");
        System.out.println("19. Load Sample Campus Data (demo)");
        System.out.println(DOUBLE_LINE);
        System.out.println();
    }

    private static void printTitle(String title) {
        System.out.println(DOUBLE_LINE);
        System.out.println(title);
        System.out.println(DOUBLE_LINE);
        System.out.println();
    }

    // =================================================== STUDENT MANAGEMENT

    private static void addStudent() {
        printTitle("ADD STUDENT RECORD");

        String studentId = input.readId("Enter Student ID: ", "Student ID");
        // 1. Duplicate check (Linked List is the main storage)
        if (studentList.containsStudentId(studentId)) {
            System.out.println("A student with ID " + studentId + " already exists. Duplicate IDs are not allowed.");
            return;
        }
        String name = input.readNonEmpty("Enter Student Name: ", "Student name");
        String programme = input.readNonEmpty("Enter Programme: ", "Programme");
        int marks = input.readMarks("Enter Marks: ");

        // 2. Create the object, then 3-5. store it in every structure
        Student student = new Student(studentId, name, programme, marks);
        studentList.addStudent(student);
        studentTree.insert(student);
        studentHashTable.insert(student);

        // 6. Record the action
        actionStack.push("ADD STUDENT - " + studentId);

        System.out.println();
        System.out.println("Student added successfully.");
    }

    private static void updateStudent() {
        printTitle("UPDATE STUDENT RECORD");

        if (studentList.isEmpty()) {
            System.out.println("No student records found. The list is empty.");
            return;
        }

        String studentId = input.readId("Enter Student ID: ", "Student ID");
        Student existing = studentHashTable.search(studentId);
        if (existing == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println();
        System.out.println("Current details:");
        existing.printDetails();
        System.out.println();

        String newName = input.readNonEmpty("Enter new name: ", "Student name");
        String newProgramme = input.readNonEmpty("Enter new programme: ", "Programme");
        int newMarks = input.readMarks("Enter new marks: ");

        // Update the main record in the Linked List.
        studentList.updateStudent(studentId, newName, newProgramme, newMarks);
        // The BST and Hash Table point to the same Student object, so they already
        // see the new values. The Student ID (their key) never changes, so the BST
        // keeps its shape; we still refresh the hash table entry explicitly.
        studentHashTable.update(studentList.searchStudent(studentId));

        actionStack.push("UPDATE STUDENT - " + studentId);

        System.out.println();
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        printTitle("DELETE STUDENT RECORD");

        if (studentList.isEmpty()) {
            System.out.println("No student records found. The list is empty.");
            return;
        }

        String studentId = input.readId("Enter Student ID: ", "Student ID");
        if (!studentList.containsStudentId(studentId)) {
            System.out.println("Student not found.");
            return;
        }

        studentList.deleteStudent(studentId);
        studentTree.delete(studentId);
        studentHashTable.delete(studentId);

        actionStack.push("DELETE STUDENT - " + studentId);

        System.out.println("Student deleted successfully.");
    }

    private static void displayAllStudents() {
        printTitle("ALL STUDENT RECORDS (LINKED LIST)");
        studentList.displayStudents();
    }

    // ===================================================== SERVICE REQUESTS

    private static void addServiceRequest() {
        printTitle("ADD SERVICE REQUEST");

        String requestId = input.readId("Enter Request ID: ", "Request ID");
        if (serviceQueue.containsRequestId(requestId)) {
            System.out.println("Request ID " + requestId + " is already in the queue. Duplicate IDs are not allowed.");
            return;
        }

        String studentId = input.readId("Enter Student ID: ", "Student ID");
        if (studentHashTable.search(studentId) == null) {
            System.out.println("Student not found. A request can only be made for a registered student.");
            return;
        }

        String description = input.readNonEmpty("Enter Request Description: ", "Request description");

        serviceQueue.enqueue(new ServiceRequest(requestId, studentId, description));
        actionStack.push("ADD SERVICE REQUEST - " + requestId);

        System.out.println();
        System.out.println("Service request added to the queue. Position: " + serviceQueue.size());
    }

    private static void processServiceRequest() {
        printTitle("PROCESS NEXT SERVICE REQUEST");

        ServiceRequest request = serviceQueue.dequeue();
        if (request == null) {
            System.out.println("No service requests available.");
            return;
        }

        System.out.println("Processing Request:");
        System.out.println();
        request.printDetails();
        System.out.println();
        System.out.println("Request processed successfully.");
        System.out.println("Requests remaining in queue: " + serviceQueue.size());

        actionStack.push("PROCESS SERVICE REQUEST - " + request.getRequestId());
    }

    private static void displayServiceQueue() {
        printTitle("SERVICE REQUEST QUEUE (FIFO)");
        serviceQueue.display();
    }

    // ======================================================= ACTION HISTORY

    private static void displayRecentActions() {
        printTitle("RECENT ACTIONS");
        actionStack.display();
    }

    // ======================================================= STUDENT SEARCH

    private static void displayStudentsUsingBST() {
        printTitle("STUDENTS USING BINARY SEARCH TREE");
        studentTree.display();

        if (!studentTree.isEmpty() && input.readYesNo("\nSearch for a student in the BST? (Y/N): ")) {
            String studentId = input.readId("Enter Student ID: ", "Student ID");
            Student student = studentTree.search(studentId);
            System.out.println();
            if (student == null) {
                System.out.println("Student not found.");
            } else {
                System.out.println("Student found using BST:");
                System.out.println();
                student.printDetails();
            }
        }
    }

    private static void searchStudentUsingHashing() {
        printTitle("SEARCH STUDENT USING HASHING");

        if (studentHashTable.isEmpty()) {
            System.out.println("No student records found. The hash table is empty.");
            return;
        }

        String studentId = input.readId("Enter Student ID: ", "Student ID");
        int bucket = studentHashTable.hash(studentId);
        Student student = studentHashTable.search(studentId);

        System.out.println();
        System.out.println("hash(" + studentId + ") = bucket " + bucket);
        System.out.println();
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("Student found using Hashing:");
            System.out.println();
            student.printDetails();
        }

        System.out.println();
        studentHashTable.display();
    }

    // ==================================================== CAMPUS MANAGEMENT

    private static void addCampusLocation() {
        printTitle("ADD CAMPUS LOCATION");

        String name = input.readNonEmpty("Enter Location Name: ", "Location name");
        if (!campusGraph.addLocation(name)) {
            System.out.println("Location '" + campusGraph.getStoredName(name) + "' already exists. Duplicate locations are not allowed.");
            return;
        }
        actionStack.push("ADD LOCATION - " + name);
        System.out.println("Location '" + name + "' added successfully.");
    }

    private static void removeCampusLocation() {
        printTitle("REMOVE CAMPUS LOCATION");

        if (campusGraph.isEmpty()) {
            System.out.println("The campus graph is empty. No locations added yet.");
            return;
        }

        String name = input.readNonEmpty("Enter Location Name: ", "Location name");
        String storedName = campusGraph.getStoredName(name);
        if (!campusGraph.removeLocation(name)) {
            System.out.println("Location '" + name + "' not found.");
            return;
        }
        actionStack.push("REMOVE LOCATION - " + storedName);
        System.out.println("Location '" + storedName + "' and all of its connections removed successfully.");
    }

    private static void addCampusConnection() {
        printTitle("ADD CAMPUS CONNECTION / ROAD");

        if (campusGraph.getLocationCount() < 2) {
            System.out.println("At least two campus locations are needed to add a connection.");
            return;
        }

        String from = input.readNonEmpty("Enter First Location: ", "Location name");
        String to = input.readNonEmpty("Enter Second Location: ", "Location name");

        if (!validateLocationPair(from, to)) {
            return;
        }
        from = campusGraph.getStoredName(from);
        to = campusGraph.getStoredName(to);

        if (!campusGraph.addConnection(from, to)) {
            System.out.println("Connection " + from + " <-> " + to + " already exists. Duplicate connections are not allowed.");
            return;
        }
        actionStack.push("ADD CONNECTION - " + from + " <-> " + to);
        System.out.println("Connection added successfully: " + from + " <-> " + to);
    }

    private static void removeCampusConnection() {
        printTitle("REMOVE CAMPUS CONNECTION / ROAD");

        if (campusGraph.isEmpty()) {
            System.out.println("The campus graph is empty. No locations added yet.");
            return;
        }

        String from = input.readNonEmpty("Enter First Location: ", "Location name");
        String to = input.readNonEmpty("Enter Second Location: ", "Location name");

        if (!validateLocationPair(from, to)) {
            return;
        }
        from = campusGraph.getStoredName(from);
        to = campusGraph.getStoredName(to);

        if (!campusGraph.removeConnection(from, to)) {
            System.out.println("Connection " + from + " <-> " + to + " does not exist.");
            return;
        }
        actionStack.push("REMOVE CONNECTION - " + from + " <-> " + to);
        System.out.println("Connection removed successfully (both directions): " + from + " <-> " + to);
    }

    /** Checks both locations exist and are different. Prints the reason if not. */
    private static boolean validateLocationPair(String from, String to) {
        if (!campusGraph.hasLocation(from)) {
            System.out.println("Location '" + from + "' not found.");
            return false;
        }
        if (!campusGraph.hasLocation(to)) {
            System.out.println("Location '" + to + "' not found.");
            return false;
        }
        if (from.trim().equalsIgnoreCase(to.trim())) {
            System.out.println("A location cannot be connected to itself.");
            return false;
        }
        return true;
    }

    private static void displayCampusConnections() {
        printTitle("CAMPUS CONNECTIONS (ADJACENCY LIST)");
        campusGraph.displayConnections();
    }

    private static void bfsTraversal() {
        printTitle("BFS CAMPUS TRAVERSAL");
        String start = readStartLocation();
        if (start != null && !campusGraph.bfs(start)) {
            System.out.println("Invalid starting location. '" + start + "' does not exist.");
        }
    }

    private static void dfsTraversal() {
        printTitle("DFS CAMPUS TRAVERSAL");
        String start = readStartLocation();
        if (start != null && !campusGraph.dfs(start)) {
            System.out.println("Invalid starting location. '" + start + "' does not exist.");
        }
    }

    /** Returns the start location typed by the user, or null if the graph is empty. */
    private static String readStartLocation() {
        if (campusGraph.isEmpty()) {
            System.out.println("The campus graph is empty. Add locations before running a traversal.");
            return null;
        }
        String start = input.readNonEmpty("Enter Starting Location: ", "Starting location");
        System.out.println();
        return start;
    }

    // ========================================================== SAMPLE DATA

    /**
     * Optional demo data. Only adds locations/roads that do not exist yet,
     * so it never overwrites anything the user has entered.
     */
    private static void loadSampleCampusData() {
        printTitle("LOAD SAMPLE CAMPUS DATA");

        String[] sampleLocations = {
                "Main Gate", "Library", "Lecture Hall", "Computer Lab",
                "Cafeteria", "Hostel", "Administration", "Sports Ground"
        };
        String[][] sampleConnections = {
                {"Main Gate", "Library"},
                {"Main Gate", "Administration"},
                {"Library", "Lecture Hall"},
                {"Library", "Computer Lab"},
                {"Lecture Hall", "Cafeteria"},
                {"Computer Lab", "Administration"},
                {"Cafeteria", "Hostel"},
                {"Administration", "Sports Ground"}
        };

        int locationsAdded = 0;
        for (String location : sampleLocations) {
            if (campusGraph.addLocation(location)) {
                locationsAdded++;
            }
        }

        int connectionsAdded = 0;
        for (String[] road : sampleConnections) {
            if (campusGraph.addConnection(road[0], road[1])) {
                connectionsAdded++;
            }
        }

        actionStack.push("LOAD SAMPLE CAMPUS DATA");
        System.out.println("Sample data loaded: " + locationsAdded + " location(s) and "
                + connectionsAdded + " connection(s) added.");
        System.out.println("(Existing locations and connections were kept unchanged.)");
        System.out.println();
        campusGraph.displayConnections();
    }
}
