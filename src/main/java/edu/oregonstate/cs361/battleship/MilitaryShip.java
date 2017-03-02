package edu.oregonstate.cs361.battleship;

public class MilitaryShip extends Ship {

    private int hitCounter = 0;
    private boolean isStealth;

    public MilitaryShip(String name, int length, boolean stealth) {
        // If no coordinates specified, default to (0,0) (which is off-grid)
        this(name, length, new Coords(0,0), new Coords(0,0), stealth);
    }

    public MilitaryShip(String name, int length, Coords start, Coords end, boolean stealth) {
        super(name, length, start, end);
        this.isStealth = stealth;
        this.type = "MilitaryShip";
    }


    @Override
    public boolean scan(Coords coord) {
        if(this.isStealth)
            return false;
        if (this.checkCollision(coord))
            return true;
        if (this.checkCollision(new Coords(coord.getAcross()-1, coord.getDown())))
            return true;
        if (this.checkCollision(new Coords(coord.getAcross()+1, coord.getDown())))
            return true;
        if (this.checkCollision(new Coords(coord.getAcross(), coord.getDown()-1)))
            return true;
        if (this.checkCollision(new Coords(coord.getAcross(), coord.getDown()+1)))
            return true;

        return false;
    }

    @Override
    public boolean addHit(){
        if(this.isSunk)
            return isSunk;

        this.hitCounter++;
        if(this.hitCounter == this.length)
            this.isSunk = true;

        return this.isSunk;
    }
}
