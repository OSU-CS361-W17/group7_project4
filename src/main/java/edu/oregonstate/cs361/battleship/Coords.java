package edu.oregonstate.cs361.battleship;

import static edu.oregonstate.cs361.battleship.BattleshipModel.GRID_SIZE;

class Coords {

    enum Direction { UP, DOWN, LEFT, RIGHT } // Directions relative to Coords locations on grid

    private int Across;
    private int Down;

    Coords() {
        this(0, 0);
    }

    Coords(int down, int across) {
        setAcross(across);
        setDown(down);
    }

    int getAcross() {
        return Across;
    }

    int getDown() {
        return Down;
    }

    void setAcross(int across) {
        if (across >= 0)
            Across = across;
        else
            Across = 0;
    }

    void setDown(int down) {
        if (down >= 0)
            Down = down;
        else
            Down = 0;
    }

    /* Helper that determines if Coords is on the grid
     * @return true if Coords is on the grid, false otherwise
     */
    boolean onGrid() {
        if (Across < 1 || Across > GRID_SIZE)
            return false;
        if (Down < 1 || Down > GRID_SIZE)
            return false;

        return true;
    }

    /* Helper function that creates an array of the Coords' four adjacent Coords
     * Filled in the order of North, East, South, West
     * @return Coords[4] containing the four adjacent Coords of a Coords object
     */
    Coords[] getAdjacents() {
        Coords[] adjacentCoords = new Coords[4];
        adjacentCoords[0] = getAbove();
        adjacentCoords[1] = getRight();
        adjacentCoords[2] = getBelow();
        adjacentCoords[3] = getLeft();
        return adjacentCoords;
    }


    /* Gets a new Coords some specified distance above this Coords
     * @param distance how many tiles away from this Coords to go
     * @return Coords object some distance above this
     */
    Coords getAbove(int distance) { return new Coords(Down - distance, Across); }
    Coords getAbove() { return getAbove(1); }

    /* Gets a new Coords some specified distance below this Coords
     * @param distance how many tiles away from this Coords to go
     * @return Coords object some distance below this
     */
    Coords getBelow(int distance) { return new Coords(Down + distance, Across); }
    Coords getBelow() { return getBelow(1); }

    /* Gets a new Coords some specified distance to the right of this Coords
     * @param distance how many tiles away from this Coords to go
     * @return Coords object some distance to the right of this
     */
    Coords getRight(int distance) { return new Coords(Down, Across + distance); }
    Coords getRight() { return getRight(1); }

    /* Gets a new Coords some specified distance to the left of this Coords
     * @param distance how many tiles away from this Coords to go
     * @return Coords object some distance to the left of this
     */
    Coords getLeft(int distance) { return new Coords(Down, Across - distance); }
    Coords getLeft() { return getLeft(1); }

    /* Gets a new Coords in a specified direction and distance from this Coords
     * @param direction UP, DOWN, LEFT, or RIGHT, specifying which direction to go in
     * @param distance how many tiles away from this Coords to go
     */
    Coords getInDirection(Direction direction, int distance) {
        Coords coords = null;
        switch (direction) {
            case UP: coords = getAbove(distance); break;
            case DOWN: coords = getBelow(distance); break;
            case LEFT: coords = getLeft(distance); break;
            case RIGHT: coords = getRight(distance); break;
        }
        return coords;
    }

    Coords getInDirection(Direction direction) {
        return getInDirection(direction, 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Same object is equal
        if (!(obj instanceof Coords)) return false; // Must be Coords object to be equal

        Coords coords = (Coords) obj; // Cast obj to Coords for comparison
        return this.Across == coords.Across && this.Down == coords.Down;
    }
}
