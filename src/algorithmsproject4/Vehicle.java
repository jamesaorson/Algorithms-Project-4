package algorithmsproject4;

public class Vehicle {
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
        this.blockString = getBlockString();
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
    
    public String getBlockString() {
        this.blockString = makeBlockString();
        
        return this.blockString;
    } 

    public void setX(int xLoc) {
        this.coord.setX(xLoc);
        this.blockString = getBlockString();
    }
    
    public void setY(int yLoc) {
        this.coord.setY(yLoc);
        this.blockString = getBlockString();
    }
    
    private String makeBlockString() {
        return this.coord.getX() + "," + this.coord.getY() + "," + this.width + "," + this.height + "," + this.color + "," + this.orientation;
    }
}
