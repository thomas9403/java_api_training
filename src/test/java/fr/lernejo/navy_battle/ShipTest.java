package fr.lernejo.navy_battle;

public class ShipTest {
    @org.junit.jupiter.api.Test
    void ship_test() {
        Ship ship = new Ship(1, 2, 3, Ship.Orientation.HORIZONTAL);
        assert ship.shootAtShip(new BoardPosition(1, 2));
        assert ship.shootAtShip(new BoardPosition(2, 2));
        assert ship.shootAtShip(new BoardPosition(3, 2));
        assert ship.isShipSunk();
    }
}

