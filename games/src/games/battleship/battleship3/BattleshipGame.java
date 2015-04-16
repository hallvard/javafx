package games.battleship.battleship3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BattleshipGame implements IBattleshipGame {

    List<IBattleship> boards;
    IBattleshipPersistence persistence;

    public BattleshipGame() {
        persistence = new DefaultBattleshipPersistence();
        boards = new ArrayList<>(2);
        boards.add(new Battleship());
        boards.add(new Battleship());
    }

    @Override
    public void init(IBattleship board1, IBattleship board2) {
        boards.set(0, board1);
        boards.set(1, board2);
    }

    @Override
    public IBattleship[] getBoards() {
        return boards.toArray(new IBattleship[boards.size()]);
    }

    @Override
    public void load(InputStream inputStream) throws IOException {
        persistence.load(this, inputStream);
    }

    @Override
    public void save(OutputStream outputStream) throws IOException {
        persistence.save(this, outputStream);
    }
}
