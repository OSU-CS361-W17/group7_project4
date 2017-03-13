package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MilitaryShipTest {

    @Test
    public void testShip() {
        MilitaryShip ship = new MilitaryShip("Test Ship", 3, false);

        // No specified coordinates should start ship in 0,0 (off-grid)
        assertEquals(0, ship.getStart().getAcross());
        assertEquals(0, ship.getStart().getDown());
        assertEquals(0, ship.getEnd().getAcross());
        assertEquals(0, ship.getEnd().getDown());
    }

    @Test
    public void hitTest() {
        //Test hitting/inking unstealthed ship
        MilitaryShip ship = new MilitaryShip("Test Ship", 3, false);
        ship.updatePosition(5, 5, "vertical");

        assertEquals(false, ship.addHit());
        assertEquals(false, ship.addHit());
        assertEquals(true, ship.addHit());
        assertEquals(true, ship.addHit());

        //Test hitting/sinking stealthed ship
        ship = new MilitaryShip("Test Ship", 3, true);
        ship.updatePosition(5, 5, "vertical");

        assertEquals(false, ship.addHit());
        assertEquals(false, ship.addHit());
        assertEquals(true, ship.addHit());
        assertEquals(true, ship.addHit());
    }

    @Test
    public void testStealthScan() {
        MilitaryShip ship = new MilitaryShip("Test Ship", 3, false);
        ship.updatePosition(5, 5, "vertical");

        assertEquals(true, ship.scan(new Coords(5, 5)));
    }

    @Test
    public void testNonStealthScan() {
        MilitaryShip ship = new MilitaryShip("Test Ship", 3, true);
        ship.updatePosition(5, 5, "vertical");

        assertEquals(false, ship.scan(new Coords(5, 5)));
    }
}
