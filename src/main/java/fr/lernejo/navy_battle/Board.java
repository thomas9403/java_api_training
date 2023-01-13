package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Board {
    public BoardPosition[] getHitPositions() {
        BoardPosition[] hitPositions = new BoardPosition[0];
        return hitPositions;
    }

    enum State {FREE, FIRED, HIT}
    enum FireResult {MISS, HIT, SUNK}

    private final State[][] cells = new State[10][10];
    private final List<Ship> ships = new ArrayList<>();

    Board() {
        for (State[] row : cells) {
            Arrays.fill(row, State.FREE);
        }
        ships.add(new Ship(0, 0, 5, Ship.Orientation.VERTICAL));
        ships.add(new Ship(6, 9, 4, Ship.Orientation.HORIZONTAL));
        ships.add(new Ship(3,5, 3, Ship.Orientation.HORIZONTAL));
        ships.add(new Ship(6, 6, 3, Ship.Orientation.VERTICAL));
        ships.add(new Ship(1, 1, 2, Ship.Orientation.VERTICAL));
    }

    FireResult takeFireFromEnemy(BoardPosition p) {
        for (Ship s : ships) {
            if (s.shootAtShip(p)) {
                if (s.isShipSunk()) {
                    ships.remove(s);
                    return FireResult.SUNK;
                }
                return FireResult.HIT;
            }
        }
        return FireResult.MISS;
    }

    boolean shipLeft() {
        return !ships.isEmpty();
    }

    void updateCell(int x, int y, boolean enemy) {
        cells[x][y] = enemy ? State.HIT : State.FIRED;
    }

    State getCellState(int x, int y) {
        return cells[x][y];
    }
}
