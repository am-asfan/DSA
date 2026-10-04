# Testing Checklist

Run the program (`java -cp out Main`) and tick each test once the actual result matches the expected result.

## Student (Linked List)

| # | Test | Steps / Input | Expected Result | Pass |
| - | ---- | ------------- | --------------- | ---- |
| S1 | Display empty list | Option 4 at start | `No student records found. The list is empty.` | [ ] |
| S2 | Add valid student | Option 1: `IT001`, `Student A`, `HNDIT`, `85` | `Student added successfully.` | [ ] |
| S3 | Add duplicate student | Option 1: `IT001` | `A student with ID IT001 already exists...` | [ ] |
| S4 | Case-insensitive ID | Option 1: `it001` | Rejected as duplicate of IT001 | [ ] |
| S5 | Invalid marks | Marks: `-10`, `101`, `abc` | `Invalid marks...` and the prompt repeats | [ ] |
| S6 | Empty fields | Press Enter at ID / Name / Programme | `... cannot be empty` and the prompt repeats | [ ] |
| S7 | Display students | Option 4 | Table of all students in insertion order | [ ] |
| S8 | Update existing | Option 2: `IT002`, new values | `Student updated successfully.` and new values shown in options 4, 9, 10 | [ ] |
| S9 | Update missing | Option 2: `IT999` | `Student not found.` | [ ] |
| S10 | Delete existing | Option 3: `IT003` | `Student deleted successfully.` and IT003 gone from options 4, 9, 10 | [ ] |
| S11 | Delete missing | Option 3: `IT003` again | `Student not found.` | [ ] |

## Stack (Recent Actions)

| # | Test | Steps / Input | Expected Result | Pass |
| - | ---- | ------------- | --------------- | ---- |
| K1 | Empty stack | Option 8 at start | `No recent actions recorded.` | [ ] |
| K2 | Push action | Add / update / delete a student | An action is recorded each time | [ ] |
| K3 | Display order | Option 8 | Newest action is number 1 | [ ] |
| K4 | Other actions | Add/process request, add/remove location, add/remove road | All appear in option 8 | [ ] |

## Queue (Service Requests)

| # | Test | Steps / Input | Expected Result | Pass |
| - | ---- | ------------- | --------------- | ---- |
| Q1 | Empty queue | Option 6 or 7 at start | `No service requests available.` | [ ] |
| Q2 | Add request | Option 5: `SR001`, `IT001`, `Transcript Request` | Added, position 1 | [ ] |
| Q3 | Duplicate request ID | Option 5: `SR001` | Rejected | [ ] |
| Q4 | Missing student | Option 5: `SR009`, `IT999` | `Student not found...` | [ ] |
| Q5 | FIFO | Add SR001, SR002, SR003, then option 6 | SR001 is processed first; option 7 shows SR002 at the front | [ ] |

## BST

| # | Test | Steps / Input | Expected Result | Pass |
| - | ---- | ------------- | --------------- | ---- |
| B1 | Empty tree | Option 9 at start | `The BST is empty...` | [ ] |
| B2 | Insert / in-order | Add IT050, IT025, IT075, IT010, IT040, then option 9 | Rows sorted IT010, IT025, IT040, IT050, IT075; tree shape shown | [ ] |
| B3 | Duplicate | Add an existing ID | Rejected (checked before insert) | [ ] |
| B4 | Search found | Option 9 → `Y` → `IT040` | Details displayed | [ ] |
| B5 | Search missing | Option 9 → `Y` → `IT999` | `Student not found.` | [ ] |
| B6 | Delete leaf | Delete IT010, then option 9 | Still sorted, IT010 gone | [ ] |
| B7 | Delete with two children | Delete IT050 (the root), then option 9 | Still sorted; root replaced by its successor | [ ] |

## Hash Table

| # | Test | Steps / Input | Expected Result | Pass |
| - | ---- | ------------- | --------------- | ---- |
| H1 | Empty table | Option 10 at start | `...hash table is empty.` | [ ] |
| H2 | Insert / search | Add IT002, option 10: `IT002` | `Student found using Hashing:` + bucket 6 | [ ] |
| H3 | Collision | Add IT001 and IT010, option 10 | Bucket 5 shows two students, marked `collision` | [ ] |
| H4 | Search in chain | Option 10: `IT001` (after H3) | Found correctly | [ ] |
| H5 | Missing | Option 10: `IT404` | `Student not found.` | [ ] |
| H6 | Delete | Delete IT010, then option 10 | IT010 removed from its chain | [ ] |

## Graph, BFS, DFS

| # | Test | Steps / Input | Expected Result | Pass |
| - | ---- | ------------- | --------------- | ---- |
| G1 | Empty graph | Options 15 / 16 / 17 at start | Empty-graph message, no crash | [ ] |
| G2 | Add location | Option 11: `Library` | Added successfully | [ ] |
| G3 | Duplicate location | Option 11: `library` | `...already exists...` | [ ] |
| G4 | Add connection | Option 13: `Library`, `Cafeteria` | Added; option 15 shows it in both lists | [ ] |
| G5 | Duplicate connection | Option 13: `Cafeteria`, `Library` | `...already exists...` | [ ] |
| G6 | Missing location | Option 13: `Library`, `Nowhere` | `Location 'Nowhere' not found.` | [ ] |
| G7 | Self connection | Option 13: `Library`, `Library` | `A location cannot be connected to itself.` | [ ] |
| G8 | Remove connection | Option 14: `Library`, `Cafeteria` | Removed from both lists | [ ] |
| G9 | Missing connection | Option 14 on the same pair again | `...does not exist.` | [ ] |
| G10 | Remove location | Option 12: `Library` | Location and all its roads removed (check option 15) | [ ] |
| G11 | BFS | Option 19, then option 16: `Library` | Level 0: Library; level 1: Main Gate, Lecture Hall, Computer Lab; ... | [ ] |
| G12 | DFS | Option 17: `Library` | Goes deep along one path before backtracking | [ ] |
| G13 | Invalid start | Option 16 / 17: `Atlantis` | `Invalid starting location...` | [ ] |
| G14 | Disconnected graph | Remove `Library`, then DFS from `Main Gate` | Lists the unreachable locations | [ ] |

## Menu / General

| # | Test | Steps / Input | Expected Result | Pass |
| - | ---- | ------------- | --------------- | ---- |
| M1 | Text input | `abc` | `Invalid choice. Please enter a valid menu number.` | [ ] |
| M2 | Out of range | `999`, `-1`, `0` | Same message | [ ] |
| M3 | Exit | `18` | Goodbye message, program ends | [ ] |
| M4 | Sample data twice | Option 19 twice | Second run adds 0 locations and 0 roads; nothing duplicated | [ ] |
