package games.battleship.battleship3;

import java.util.ArrayList;
import java.util.List;

public class HuntAndTargetEnemy extends Battleship implements IEnemy {

    private boolean targetMode = false;
    private List<GridLocation> targetStack = new ArrayList<>();

    @Override
    public GridLocation target() {

        if (! targetMode) {
            GridLocation loc = targetRandomly();
            if (getCell(loc.getX(), loc.getY()).isShip()) {
                targetMode = true;
                targetStack.addAll(0, loc.getNeighbors(getSize()));
            }
            return loc;
        }

        else {
            if (! targetStack.isEmpty())  {
                GridLocation next = targetStack.remove(0);
                if (getCell(next.getX(), next.getY()).isShip()) {
                    next.getNeighbors(getSize()).stream()
                            .filter(n -> (!targetStack.contains(n)) && !getCell(n.getX(), n.getY()).isHit())
                            .forEach(n ->
                                targetStack.add(0, n)
                    );
                }
                return next;
            }
            else {
                targetMode = false;
                return targetRandomly();
            }
        }

    }

    private GridLocation targetRandomly() {
        int x = (int) (Math.random() * getSize());
        int y = (int) (Math.random() * getSize());
        while (getCell(x, y).isHit()) {
            x = (int) (Math.random() * getSize());
            y = (int) (Math.random() * getSize());
        }
        return new GridLocation(x, y);
    }

}
