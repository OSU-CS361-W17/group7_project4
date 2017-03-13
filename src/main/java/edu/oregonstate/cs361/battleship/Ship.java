package edu.oregonstate.cs361.battleship;

import static edu.oregonstate.cs361.battleship.BattleshipModel.GRID_SIZE;

public class Ship {

    private String name;
    private int length;
    private Coords start;
    private Coords end;
    private boolean isVert = false;
    private boolean isSunk = false;
    protected String type;

    public Ship(String name, int length) {
        // If no coordinates specified, default to (0,0) (which is off-grid)
        this(name, length, new Coords(0, 0), new Coords(0, 0));
    }

    public Ship(String name, int length, Coords start, Coords end) {
        this.name = name;

        if (length >= 1 && length <= GRID_SIZE)
            this.length = length;
        else
            this.length = 1;

        this.start = start;
        this.end = end;

        if(start.getDown() != end.getDown())
            isVert = true;

        type = "Ship";
    }

    /* Creates an array of Coords containing all of the tiles the Ship overlaps with
     * @return Array of Coords the Ship overlaps with
     */
    Coords[] getCoordsArray() {
        Coords[] coords = new Coords[length];

        for (int i = 0; i < length; i++) {
            if (isVert)
                coords[i] = start.getBelow(i);
            else
                coords[i] = start.getRight(i);
        }
        return coords;
    }


    public final String getName() { return name; }

    public final int getLength() { return length; }

    protected final Coords getStart() { return start; }

    protected final Coords getEnd() { return end; }

    public final boolean checkVert() { return isVert; }

    protected final void setVert(String orientation) {
        if (orientation.equals("vertical"))
            isVert = true;
        else if (orientation.equals("horizontal"))
            isVert = false;
    }

    /* Checks if specified coordinates overlap with the ship
     * @param coordinates The specified location to check for overlap
     * @return true if there is overlap, false otherwise
     */
    public final boolean checkCollision(Coords target) {
        Coords[] shipCoords = getCoordsArray();

        for (Coords coords : shipCoords) {
            if (coords.equals(target))
                return true;
        }

        return false;
    }

    public final boolean updatePosition(int row, int column, String orientation) {
        // Specified start position cannot be off-grid
        if (row <= 0 || row > GRID_SIZE || column <= 0 || column > GRID_SIZE)
            return false;

        Coords start = getStart();
        Coords end = getEnd();

        if (orientation.equals("horizontal") && column + getLength() - 1 <= GRID_SIZE) {
            end.setAcross(column + getLength() - 1);
            end.setDown(row);
        }
        else if (orientation.equals("vertical") && row + getLength() - 1 <= GRID_SIZE) {
            end.setAcross(column);
            end.setDown(row + getLength() - 1);
        }
        else {
            return false;
        }

        start.setDown(row);
        start.setAcross(column);


        setVert(orientation);
        return true;
    }

    /* Scan for the ship in a plus shape surrounding coord
     * @param coord the target of the scan (the center of the plus)
     * @return true if ship is inside of scan region, false otherwise
     */
    public boolean scan(Coords coord) {
        if (checkCollision(coord))
            return true;
        if (checkCollision(new Coords(coord.getDown(), coord.getAcross()-1)))
            return true;
        if (checkCollision(new Coords(coord.getDown(), coord.getAcross()+1)))
            return true;
        if (checkCollision(new Coords(coord.getDown()-1, coord.getAcross())))
            return true;
        if (checkCollision(new Coords(coord.getDown()+1, coord.getAcross())))
            return true;

        return false;
    }

    /* Return's a Boolean describing whether the ship has been sunk or not
     * @return true if ship is sunk, false otherwise
     */
    public final boolean checkSunk() { return isSunk; }

    final void setSunk(boolean sunk) { isSunk = sunk; }

    /* Increments the number of hits on the ship, and checks it against the number of possible hits
     * Sets isSunk to true if the ship has taken the maximum number of hits
     * @return isSunk
     */
    public boolean addHit(){
        if(isSunk)
            return isSunk;

        isSunk = true;
        return isSunk;
    }
}