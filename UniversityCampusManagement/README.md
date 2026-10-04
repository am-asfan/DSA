# University Student Record and Campus Route Management System

## Module

CIT300 – Data Structures and Algorithms

## Assignment

Graded Practical Assignment 1

## Technologies

```text
Java (JDK 17 or newer)
Console Application
```

Standard Java only. No GUI, database, web framework or external libraries.

---

## Project Structure

```text
UniversityCampusManagement/
│
├── src/
│   ├── Main.java                     Menu, input handling, calls the data structures
│   ├── model/
│   │   ├── Student.java              studentId, name, programme, marks
│   │   ├── ServiceRequest.java       requestId, studentId, requestDescription
│   │   └── CampusLocation.java       graph vertex + its neighbour list
│   ├── linkedlist/
│   │   ├── StudentNode.java
│   │   └── StudentLinkedList.java    main student storage
│   ├── stack/
│   │   └── ActionStack.java          recent actions (LIFO)
│   ├── queue/
│   │   └── ServiceQueue.java         service requests (FIFO)
│   ├── tree/
│   │   ├── BSTNode.java
│   │   └── StudentBST.java           students ordered by Student ID
│   ├── hashing/
│   │   └── StudentHashTable.java     fast Student ID lookup
│   ├── graph/
│   │   └── CampusGraph.java          campus map, BFS and DFS
│   └── utils/
│       └── InputValidator.java       validates all user input
│
├── README.md
├── TESTING.md                        detailed test checklist
├── .gitignore
└── demo/
    └── demo-video-link.txt
```

---

## Data Structures

| Feature | Data Structure | Class |
| ------- | -------------- | ----- |
| Student records | Singly Linked List | `StudentLinkedList` |
| Recent actions | Stack | `ActionStack` |
| Service requests | Queue | `ServiceQueue` |
| Sorted students / search | Binary Search Tree | `StudentBST` |
| Fast student search | Hash Table | `StudentHashTable` |
| Campus network | Graph (adjacency list) | `CampusGraph` |
| Campus traversal | BFS / DFS | `CampusGraph.bfs()` / `CampusGraph.dfs()` |

### Linked List

A custom singly linked list (`head -> node -> node -> null`) and the main storage for student records. New students are added at the tail. It supports `addStudent()`, `updateStudent()`, `deleteStudent()`, `searchStudent()`, `displayStudents()` and `containsStudentId()`. Deleting a node re-links the previous node so it skips the deleted one. Search is linear, **O(n)**.

### Stack

A custom linked stack, **Last In First Out**. Every important operation pushes a message like `ADD STUDENT - IT001` onto the stack. Because the newest action is always on top, `display()` lists actions from newest to oldest. Operations: `push()`, `pop()`, `peek()`, `isEmpty()`, `display()`.

### Queue

A custom linked queue, **First In First Out**, with `front` and `rear` pointers. `enqueue()` adds a service request at the rear and `dequeue()` removes the one at the front, so the first request added is the first processed. Operations: `enqueue()`, `dequeue()`, `peek()`, `isEmpty()`, `display()`.

### Binary Search Tree (BST)

Students are placed in the tree using **Student ID** as the key: smaller IDs go left, larger IDs go right. **In-order traversal** (Left → Node → Right) visits students in sorted ID order. Deletion covers all three cases: a leaf, a node with one child, and a node with two children (replaced by its in-order successor). Operations: `insert()`, `search()`, `delete()`, `inOrderTraversal()`, `display()`. Duplicate IDs are rejected.

> IDs are compared alphabetically, so use the same number of digits for every ID (`IT001`, `IT010`, `IT100`). Otherwise `IT10` will sort before `IT2`.

### Hash Table

A custom hash table built on an **array of 11 buckets**.

```text
hash(studentId) = (sum of character codes of studentId) % 11
Example: "IT001" -> 73 + 84 + 48 + 48 + 49 = 302 -> 302 % 11 = 5
```

Collisions are handled with **separate chaining**: each bucket holds a small linked list. `IT001` and `IT010` contain the same characters, so they get the same hash and land in the same bucket. This is an easy way to show a collision during the demo. A search only checks one bucket, so on average it is **O(1)**. Operations: `insert()`, `search()`, `update()`, `delete()`, `display()`.

### Graph

The campus is an **undirected graph** stored as an **adjacency list**. Each `CampusLocation` (vertex) keeps a list of the locations it is connected to. Adding a road `A – B` stores `B` in `A`'s list and `A` in `B`'s list. Removing a location also removes it from every neighbour's list. Duplicate locations, duplicate roads, roads to missing locations and self-loops are rejected. Location names are case-insensitive.

### BFS (Breadth-First Search)

Uses a **queue**. It visits the start location, then all locations one road away (level 1), then two roads away (level 2), and so on. The output shows each location's level, which proves the search goes level by level.

### DFS (Depth-First Search)

Uses **recursion** (the Java call stack acts as the stack). It follows one road as deep as possible before backtracking. The visiting order depends on the order of the adjacency lists.

Both traversals also list any locations that **cannot be reached** from the start.

### Keeping structures synchronised

The Linked List, BST and Hash Table all reference the **same `Student` objects**:

| Operation | Linked List | BST | Hash Table | Stack |
| --------- | ----------- | --- | ---------- | ----- |
| Add | `addStudent()` | `insert()` | `insert()` | push `ADD STUDENT` |
| Update | `updateStudent()` | same object, key unchanged | `update()` | push `UPDATE STUDENT` |
| Delete | `deleteStudent()` | `delete()` | `delete()` | push `DELETE STUDENT` |

---

## Features

**Student management**
- Add a student, with validation and a duplicate-ID check
- Update a student's name, programme and marks
- Delete a student from all structures
- Display all students in a table (Linked List)

**Service requests**
- Add a request (the student must exist, and the request ID must be unique in the queue)
- Process the next request in FIFO order
- Display the queue, with the front request marked

**Action history**
- Display recent actions, newest first (Stack)

**Student search**
- Display students sorted by ID, plus the tree shape (BST), with an optional BST search
- Search by ID using hashing, showing the bucket index and the bucket/chain layout

**Campus management**
- Add or remove a location (removing one also deletes all its roads)
- Add or remove a two-way road
- Display the adjacency list
- BFS and DFS traversal from any location
- Optional sample campus data (menu option 19). It only adds what is missing and never overwrites user data.

**Input validation**
- Invalid menu choices (`abc`, `999`, `-1`)
- Empty IDs, names, programmes, descriptions and location names
- Marks must be whole numbers from 0 to 100
- Duplicate and missing students, requests, locations and connections
- Student IDs are case-insensitive (`it001` is stored as `IT001`)

---

## How to Run

Requires **JDK 17 or newer** (the code uses `switch` arrow syntax and `String.repeat`).

The classes are in packages, so compile with `-sourcepath src`. Run these from the `UniversityCampusManagement` folder:

```bash
javac -d out -sourcepath src src/Main.java
java -cp out Main
```

This works the same in Windows Command Prompt, PowerShell, Git Bash, macOS and Linux. Compiled `.class` files go into `out/`, which Git ignores.

Alternative, compiling inside `src`:

```bash
cd src
javac Main.java
java Main
```

(This leaves `.class` files next to the source files. `.gitignore` ignores them.)

---

## Recommended Demonstration Flow

1. **Add students** (option 1): `IT001 - Student A - HNDIT - 85`, `IT002 - Student B - HNDIT - 78`, `IT003 - Student C - BICT - 91`
2. **Display Linked List** (option 4)
3. **Update** IT002 (option 2)
4. **Delete** IT003 (option 3)
5. **Display Stack** actions (option 8). The newest is shown first.
6. **Add three service requests** (option 5): SR001, SR002, SR003
7. **Process one request** (option 6). SR001 comes out first (FIFO). Show the queue with option 7.
8. **Display BST** (option 9). Rows are sorted, and the tree shape is shown.
9. **Search IT002 using hashing** (option 10). Tip: add `IT010` first to show a collision with `IT001`.
10. **Add campus locations** (option 11), or load sample data (option 19)
11. **Add campus connections** (option 13)
12. **Display the adjacency list** (option 15)
13. **Run BFS** (option 16) from `Library` and point out the levels
14. **Run DFS** (option 17) from `Library`
15. **Show invalid input handling**: menu `abc` / `999` / `-1`, marks `-10` / `101` / `abc`, a duplicate ID, a missing student, a duplicate location, a missing location, and an invalid BFS start

---

## Testing Checklist

See [TESTING.md](TESTING.md) for each test case with its input and expected result.

| Area | Tests |
| ---- | ----- |
| Student | add valid, add duplicate, update existing, update missing, delete existing, delete missing, display empty list, invalid marks, empty fields |
| Stack | push on every action, display newest first, empty stack |
| Queue | add request, process request, FIFO order, empty queue, duplicate request ID, request for missing student |
| BST | insert, search, duplicate rejected, delete (leaf / one child / two children), in-order traversal sorted |
| Hash Table | insert, search, delete, collision (IT001 + IT010), missing student |
| Graph | add location, duplicate location, remove location (with its roads), add connection, duplicate connection, remove connection, missing location, missing connection, BFS, DFS, invalid start, empty graph |

---

## Team Members

| No | Name        | Student ID | Responsibility  | Individual Contribution |
| -- | ----------- | ---------- | --------------- | ----------------------- |
| 1  | MEMBER NAME | STUDENT ID | Linked List     | DESCRIPTION             |
| 2  | MEMBER NAME | STUDENT ID | Stack & Queue   | DESCRIPTION             |
| 3  | MEMBER NAME | STUDENT ID | BST & Hashing   | DESCRIPTION             |
| 4  | MEMBER NAME | STUDENT ID | Graph & BFS/DFS | DESCRIPTION             |

> Replace the placeholders with real member details before submission.

### Individual Contribution

Each member should describe the classes they built, the methods they wrote, the problems they solved and the tests they ran. For example, member 1 would cover `StudentNode`, `StudentLinkedList` and the add/update/delete student flow.

---

## GitHub Collaboration

### Branches

```text
main                          stable, working code only
feature/student-linked-list   Student model, StudentNode, StudentLinkedList
feature/stack-queue           ActionStack, ServiceRequest, ServiceQueue
feature/bst-hashing           BSTNode, StudentBST, StudentHashTable
feature/graph                 CampusLocation, CampusGraph, BFS, DFS
feature/integration           Main.java menu, InputValidator, README
```

Each component is in its own package, so members can work in parallel with very few merge conflicts.

### Commits

- Commit small, working steps with clear messages, e.g. `Add deleteStudent to StudentLinkedList` or `Implement BFS with level output`
- Each member commits their own work from their own GitHub account, so contributions are visible in the history
- Make sure the project compiles before every commit

### Pull Requests

1. Push your feature branch: `git push -u origin feature/<name>`
2. Open a Pull Request into `main` (or into `feature/integration`)
3. At least one other member reviews the code and tests it
4. Fix any review comments, then merge

### Integration

- `feature/integration` connects all the structures through `Main.java`
- After each merge, pull the latest `main` and re-run the full demo flow

### Testing

- Before each PR, run the checklist items for your component (see `TESTING.md`)
- After integration, run the full checklist on `main`

> The team must create the repository, commits and pull requests itself. Nothing in this project fakes that history.

---

## Demo Video

The link is in [demo/demo-video-link.txt](demo/demo-video-link.txt).
