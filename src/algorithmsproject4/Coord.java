package algorithmsproject4;

/**
* Coord class is a template for coordinate objects
*
* @author Nate Stahlnecker & James Osborne
* @version 1.0
* File: Coord.java
* Created: Nov 2017
* ©Copyright Cedarville University, its Computer Science faculty, and the
* authors. All rights reserved.
* Summary of Modifications:
*
*/

public class Coord {
    private int x;
    private int y;

    public Coord(int xLoc, int yLoc) {
        this.x = xLoc;
        this.y = yLoc;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int xLoc)  {
        this.x = xLoc;
    }

    public void setY(int yLoc)  {
        this.y = yLoc;
    }
}
