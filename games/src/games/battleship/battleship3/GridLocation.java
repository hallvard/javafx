package games.battleship.battleship3;

import java.util.ArrayList;
import java.util.List;

public class GridLocation {
    private final int x, y;

    public GridLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<GridLocation> getNeighbors(int boardSize) {
        List<GridLocation> neighbors = new ArrayList<>();
        if (x < boardSize) neighbors.add(new GridLocation(x+1, y));
        if (x > 0) neighbors.add(new GridLocation(x-1, y));
        if (y < boardSize) neighbors.add(new GridLocation(x, y+1));
        if (y > 0) neighbors.add(new GridLocation(x, y-1));
        return neighbors;

    }

    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    public boolean equals(Object o) {
        GridLocation gl = (GridLocation) o;
        return gl.getX() == getX() && gl.getY() == getY();
    }

}
