package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;

import static edu.oregonstate.cs361.battleship.Main.GRID_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    public void testShip() {
        Ship ship = new Ship("Test Ship", 3);

        // No specified coordinates should start ship in 0,0 (off-grid)
        assertEquals(0, ship.getStart().getAcross());
        assertEquals(0, ship.getStart().getDown());
        assertEquals(0, ship.getEnd().getAcross());
        assertEquals(0, ship.getEnd().getDown());
    }

    @Test
    public void testNegativeLength() {
        Ship ship = new Ship("Negative Length Ship", -4);

        // Negative length values should replaced with a positive value
        assertTrue(ship.getLength() >= 1);
    }

    @Test
    public void testUpdatePosition() {
        Ship horizontalShip = new Ship("Horizontal Ship", 3);
        horizontalShip.updatePosition(5, 2, "horizontal");
        Coords horizontalStart = horizontalShip.getStart();
        Coords horizontalEnd = horizontalShip.getEnd();

        assertEquals(2, horizontalStart.getAcross());
        assertEquals(5, horizontalStart.getDown());
        assertEquals(2+3, horizontalEnd.getAcross());
        assertEquals(5, horizontalEnd.getDown());

        Ship verticalShip = new Ship("Vertical Ship", 2);
        verticalShip.updatePosition(7, 8, "vertical");
        Coords verticalStart = verticalShip.getStart();
        Coords verticalEnd = verticalShip.getEnd();

        assertEquals(8, verticalStart.getAcross());
        assertEquals(7, verticalStart.getDown());
        assertEquals(8, verticalEnd.getAcross());
        assertEquals(7+2, verticalEnd.getDown());
    }

    @Test
    public void testOffGridUpdatePosition() {
        Ship ship1 = new Ship("Ship with End Off-Grid", 5);
        ship1.updatePosition(GRID_SIZE - 2, GRID_SIZE - 1, "vertical");
        Coords start1 = ship1.getStart();
        Coords end1 = ship1.getEnd();

        assertEquals(0, start1.getAcross());
        assertEquals(0, start1.getDown());
        assertEquals(0, end1.getAcross());
        assertEquals(0, end1.getDown());

        Ship ship2 = new Ship("Ship with End Off-Grid", 4);
        ship2.updatePosition(GRID_SIZE - 3, GRID_SIZE - 2, "horizontal");
        Coords start2 = ship2.getStart();
        Coords end2 = ship2.getEnd();

        assertEquals(0, start2.getAcross());
        assertEquals(0, start2.getDown());
        assertEquals(0, end2.getAcross());
        assertEquals(0, end2.getDown());

        Ship ship3 = new Ship("Ship with Start Off-Grid", 3);
        ship3.updatePosition(GRID_SIZE + 2, GRID_SIZE - 4, "horizontal");
        Coords start3 = ship3.getStart();
        Coords end3 = ship3.getEnd();

        assertEquals(0, start3.getAcross());
        assertEquals(0, start3.getDown());
        assertEquals(0, end3.getAcross());
        assertEquals(0, end3.getDown());

        Ship ship4 = new Ship("Ship with Start Off-Grid", 4);
        ship4.updatePosition(GRID_SIZE - 2, GRID_SIZE + 1, "vertical");
        Coords start4 = ship4.getStart();
        Coords end4 = ship4.getEnd();

        assertEquals(0, start4.getAcross());
        assertEquals(0, start4.getDown());
        assertEquals(0, end4.getAcross());
        assertEquals(0, end4.getDown());
    }

    @Test
    public void testVert() {
        Ship ship = new Ship("Vertical Ship", 3, new Coords(1,1), new Coords(1,4));

        //Vertical Ship should return true
        assertTrue(ship.checkVert());
    }

    @Test
    public void testNotVert() {
        Ship ship = new Ship("Vertical Ship", 3, new Coords(1,1), new Coords(4,1));

        //Horizontal Ship should return false
        assertFalse(ship.checkVert());
    }
}