package fr.lernejo.navy_battle;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    enum Orientation {VERTICAL, HORIZONTAL}
    private final List<BoardPosition> occupied = new ArrayList<>();

    public Ship(int x, int y, int size, Orientation o) {
        for (int i = 0; i < size; i++) {
            BoardPosition position;
            if (o == Orientation.HORIZONTAL) {
                position = new BoardPosition(x + i, y);
            } else {
                position = new BoardPosition(x, y + i);
            }
            this.occupied.add(position);
        }
    }

    public boolean shootAtShip(BoardPosition b) {
        if (this.occupied.contains(b)) {
            this.occupied.remove(b);
            return true;
        }
        return false;
    }

    public boolean isShipSunk() {
        return this.occupied.isEmpty();
    }
}
