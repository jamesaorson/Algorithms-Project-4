package algorithmsproject4;

/**
* Vehicle class is a template for vehicle objects, which are then used to fill
* the board
*
* @author Nate Stahlnecker & James Osborne
* @version 1.0
* File: Vehicle.java
* Created: Nov 2017
* ©Copyright Cedarville University, its Computer Science faculty, and the
* authors. All rights reserved.
* Summary of Modifications:
*
*/

public final class Vehicle {
    private final String color;
    private final int height;
    private final int width;
    private final Coord coord;
    private final String orientation;
    private String blockString;
    
    public Vehicle(Coord c, int h, int w, String col, String o) {
        this.coord = c;
        this.height = h;
        this.width = w;
        this.color  = col;
        this.orientation = o;
        this.blockString = getVehicleString();
    }

    public String getColor() {
        return this.color;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getX() {
        return this.coord.getX();
    }

    public int getY() {
        return this.coord.getY();
    }

    public String getOrientation() {
        return this.orientation;
    }
    
    public String getVehicleString() {
        this.blockString = makeVehicleString();
        
        return this.blockString;
    } 

    public void setX(int xLoc) {
        this.coord.setX(xLoc);
        this.blockString = getVehicleString();
    }
    
    public void setY(int yLoc) {
        this.coord.setY(yLoc);
        this.blockString = getVehicleString ();
    }
    
    /*A string that represents all of the fields of a Vehicle*/
    private String makeVehicleString() {
        return this.color + ","
                + this.height + ","
                + this.width + ","
                + this.coord.getX() + ","
                + this.coord.getY() + ","
                + this.orientation;
    }
}
