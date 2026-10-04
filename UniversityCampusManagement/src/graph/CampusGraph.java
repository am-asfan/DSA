package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import model.CampusLocation;

/**
 * Undirected campus graph stored as an ADJACENCY LIST.
 *
 *   Vertices = campus locations
 *   Edges    = roads between two locations
 *
 *   Library    -> Main Gate, Lecture Hall, Computer Lab
 *   Main Gate  -> Library, Administration
 *   ...
 *
 * Every CampusLocation keeps its own list of neighbours. Because roads are
 * two-way, each connection is stored in BOTH locations' lists.
 * Location names are matched without caring about upper/lower case.
 */
public class CampusGraph {

    private final ArrayList<CampusLocation> locations;

    public CampusGraph() {
        locations = new ArrayList<>();
    }

    public boolean isEmpty() {
        return locations.isEmpty();
    }

    public int getLocationCount() {
        return locations.size();
    }

    /** Finds a location by name (case-insensitive). Returns null if missing. */
    private CampusLocation findLocation(String name) {
        for (CampusLocation location : locations) {
            if (location.getName().equalsIgnoreCase(name.trim())) {
                return location;
            }
        }
        return null;
    }

    public boolean hasLocation(String name) {
        return findLocation(name) != null;
    }

    /** Returns the stored spelling of a location name (e.g. "library" -> "Library"). */
    public String getStoredName(String name) {
        CampusLocation location = findLocation(name);
        return (location == null) ? name : location.getName();
    }

    // ------------------------------------------------------------- locations

    /**
     * Adds a new vertex with an empty neighbour list.
     * @return false if the location already exists
     */
    public boolean addLocation(String name) {
        if (hasLocation(name)) {
            return false;
        }
        locations.add(new CampusLocation(name.trim()));
        return true;
    }

    /**
     * Removes a vertex AND every edge that touches it.
     * @return false if the location does not exist
     */
    public boolean removeLocation(String name) {
        CampusLocation target = findLocation(name);
        if (target == null) {
            return false;
        }
        // Remove target from every neighbour's list, so no road points to it any more
        for (CampusLocation neighbour : target.getNeighbours()) {
            neighbour.removeNeighbour(target);
        }
        locations.remove(target);
        return true;
    }

    // ----------------------------------------------------------- connections

    public boolean hasConnection(String from, String to) {
        CampusLocation a = findLocation(from);
        CampusLocation b = findLocation(to);
        return a != null && b != null && a.isConnectedTo(b);
    }

    /**
     * Adds a two-way road: from -> to AND to -> from.
     * @return false if a location is missing, both names are the same,
     *         or the connection already exists
     */
    public boolean addConnection(String from, String to) {
        CampusLocation a = findLocation(from);
        CampusLocation b = findLocation(to);
        if (a == null || b == null || a == b || a.isConnectedTo(b)) {
            return false;
        }
        a.addNeighbour(b);
        b.addNeighbour(a);
        return true;
    }

    /**
     * Removes a two-way road in both directions.
     * @return false if a location is missing or the connection does not exist
     */
    public boolean removeConnection(String from, String to) {
        if (!hasConnection(from, to)) {
            return false;
        }
        CampusLocation a = findLocation(from);
        CampusLocation b = findLocation(to);
        a.removeNeighbour(b);
        b.removeNeighbour(a);
        return true;
    }

    /** Prints the adjacency list: each location followed by its neighbours. */
    public void displayConnections() {
        if (isEmpty()) {
            System.out.println("The campus graph is empty. No locations added yet.");
            return;
        }

        int roadCount = 0;
        for (CampusLocation location : locations) {
            StringBuilder line = new StringBuilder();
            line.append(String.format("%-18s -> ", location.getName()));

            if (location.getNeighbours().isEmpty()) {
                line.append("(no connections)");
            } else {
                for (int i = 0; i < location.getNeighbours().size(); i++) {
                    if (i > 0) {
                        line.append(", ");
                    }
                    line.append(location.getNeighbours().get(i).getName());
                }
            }
            roadCount += location.getNeighbours().size();
            System.out.println(line);
        }
        // Each road is stored twice (once per direction)
        System.out.println();
        System.out.println("Locations: " + locations.size() + " | Roads: " + (roadCount / 2));
    }

    // ------------------------------------------------------------------- BFS

    /**
     * Breadth-First Search: visits locations level by level using a QUEUE.
     * Level 0 = start, Level 1 = its neighbours, Level 2 = their neighbours, ...
     * @return false if the graph is empty or the start location is missing
     */
    public boolean bfs(String startLocation) {
        CampusLocation start = findLocation(startLocation);
        if (start == null) {
            return false;
        }

        Queue<CampusLocation> queue = new LinkedList<>();
        Set<CampusLocation> visited = new HashSet<>();
        HashMap<CampusLocation, Integer> level = new HashMap<>();
        ArrayList<CampusLocation> visitOrder = new ArrayList<>();

        queue.add(start);
        visited.add(start);
        level.put(start, 0);

        while (!queue.isEmpty()) {
            CampusLocation current = queue.remove();   // take from the FRONT
            visitOrder.add(current);

            for (CampusLocation neighbour : current.getNeighbours()) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);             // mark when queued, so it is queued once
                    level.put(neighbour, level.get(current) + 1);
                    queue.add(neighbour);               // add to the REAR
                }
            }
        }

        System.out.println("Starting Location: " + start.getName());
        System.out.println();
        System.out.println("BFS Traversal:");
        int number = 1;
        for (CampusLocation location : visitOrder) {
            System.out.printf("%2d. %-18s (Level %d)%n", number++, location.getName(), level.get(location));
        }
        System.out.println();
        System.out.println("Order: " + joinNames(visitOrder));
        printUnreachable(visited);
        return true;
    }

    // ------------------------------------------------------------------- DFS

    /**
     * Depth-First Search: goes as deep as possible along one road before
     * backtracking. Implemented with RECURSION (the call stack acts as the stack).
     * @return false if the graph is empty or the start location is missing
     */
    public boolean dfs(String startLocation) {
        CampusLocation start = findLocation(startLocation);
        if (start == null) {
            return false;
        }

        Set<CampusLocation> visited = new HashSet<>();
        ArrayList<CampusLocation> visitOrder = new ArrayList<>();
        dfsRecursive(start, visited, visitOrder);

        System.out.println("Starting Location: " + start.getName());
        System.out.println();
        System.out.println("DFS Traversal:");
        int number = 1;
        for (CampusLocation location : visitOrder) {
            System.out.printf("%2d. %s%n", number++, location.getName());
        }
        System.out.println();
        System.out.println("Order: " + joinNames(visitOrder));
        printUnreachable(visited);
        return true;
    }

    private void dfsRecursive(CampusLocation current, Set<CampusLocation> visited,
                              ArrayList<CampusLocation> visitOrder) {
        visited.add(current);
        visitOrder.add(current);
        for (CampusLocation neighbour : current.getNeighbours()) {
            if (!visited.contains(neighbour)) {
                dfsRecursive(neighbour, visited, visitOrder);
            }
        }
    }

    // --------------------------------------------------------------- helpers

    private String joinNames(ArrayList<CampusLocation> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                result.append(" -> ");
            }
            result.append(list.get(i).getName());
        }
        return result.toString();
    }

    /** Lists locations that could not be reached from the start (disconnected parts). */
    private void printUnreachable(Set<CampusLocation> visited) {
        ArrayList<CampusLocation> unreachable = new ArrayList<>();
        for (CampusLocation location : locations) {
            if (!visited.contains(location)) {
                unreachable.add(location);
            }
        }
        if (!unreachable.isEmpty()) {
            System.out.println("Not reachable from this start: " + joinNames(unreachable).replace(" -> ", ", "));
        }
    }
}
