package model;

import java.util.ArrayList;

/**
 * A campus location (a vertex in the campus graph).
 *
 * Each location keeps its own list of directly connected locations.
 * This list is the location's entry in the graph's ADJACENCY LIST.
 */
public class CampusLocation {

    private final String name;
    private final ArrayList<CampusLocation> neighbours;

    public CampusLocation(String name) {
        this.name = name;
        this.neighbours = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<CampusLocation> getNeighbours() {
        return neighbours;
    }

    public boolean isConnectedTo(CampusLocation other) {
        return neighbours.contains(other);
    }

    public void addNeighbour(CampusLocation other) {
        if (!neighbours.contains(other)) {
            neighbours.add(other);
        }
    }

    public void removeNeighbour(CampusLocation other) {
        neighbours.remove(other);
    }

    @Override
    public String toString() {
        return name;
    }
}
