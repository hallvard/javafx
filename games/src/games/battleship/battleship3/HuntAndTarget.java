package games.battleship.battleship3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 10.04.2015.
 */
public class HuntAndTarget extends Battleship {

    private boolean targetMode = false;
    private List<GridLocation> targetStack = new ArrayList<>();

    @Override
    public GridLocation target() {

        if (! targetMode) {
            int x = (int) (Math.random() * getSize());
            int y = (int) (Math.random() * getSize());
            GridLocation loc = new GridLocation(x, y);
            if (getCell(x, y).isShip()) {
                targetMode = true;
                targetStack.addAll(0, loc.getNeighbors(getSize()));
            }
            return new GridLocation(x, y);
        }

        GridLocation next = targetStack.remove(0);
        for (GridLocation neighbor : next.getNeighbors(getSize())) {
            if (! targetStack.contains(neighbor)) {
                targetStack.add(0, neighbor);
            }
        }
        return next;

    }
}
