package algorithmsproject4;

/**
* GameBoard class is a template for GameBoard objects
* It performs all the movement operations on vehicles
*
* @author Nate Stahlnecker & James Osborne
* @version 1.0
* File: GameBoard.java
* Created: Nov 2017
* ©Copyright Cedarville University, its Computer Science faculty, and the
* authors. All rights reserved.
* Summary of Modifications:
*
*/

import java.util.ArrayList;

public class GameBoard {
    private final Vehicle[][] gameBoard; 
    private final ArrayList<Vehicle> vehicles; 
    private final int boardHeight;
    private final int boardWidth;

    public GameBoard(int boardWidth, int boardHeight) {
        this.gameBoard = new Vehicle[boardHeight][boardWidth];
        this.vehicles = new ArrayList<Vehicle>();
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
    }

    public void addVehicle(int x, int y, int height, int width, 
            String color, String orientation) {
        Coord coord = new Coord(x, y);
        Vehicle vehicle = new Vehicle(coord, height, width, color, orientation);
        
        this.vehicles.add(vehicle);
        
        placeVehicle(vehicle);
    }
    
    public boolean canMoveRight(Vehicle vehicle, int moveDistance) {
        if (vehicle.getOrientation().equals("v") 
                || vehicle.getX()
                 + vehicle.getWidth() - 1
                 + moveDistance >= this.getBoardWidth()) {
            return false;
        }
        
        int availableMoves = 0;
        for (int i = 1; i <= moveDistance; ++i) {
            if (!isCoordEmpty(vehicle.getX() + vehicle.getWidth() - 1 + i, vehicle.getY())) {
                break;
            }

            ++availableMoves;
        }

        return moveDistance <= availableMoves;
    }
    
    public boolean canMoveLeft(Vehicle vehicle, int moveDistance) {
        if (vehicle.getOrientation().equals("v") 
                || vehicle.getX() - moveDistance < 0) {
            return false;
        }

        int availableMoves = 0;
        for (int i = 1; i <= moveDistance; ++i) {
            if (!isCoordEmpty(vehicle.getX() - i, vehicle.getY())) {
                break;
            }

            ++availableMoves;
        }
        
        return moveDistance <= availableMoves;
    }

    public boolean canMoveUp(Vehicle vehicle, int moveDistance) {
        if (vehicle.getOrientation().equals("h") 
                || vehicle.getY() - moveDistance < 0) {
            return false;
        }

        int availableMoves = 0;
        for (int i = 1; i <= moveDistance; ++i) {
            if (!isCoordEmpty(vehicle.getX(), vehicle.getY() - i)) {
                break;
            }

            ++availableMoves;
        }
        
        return moveDistance <= availableMoves;
    }

    public boolean canMoveDown(Vehicle vehicle, int moveDistance) {
        if (vehicle.getOrientation().equals("h") 
                || vehicle.getY()
                  + vehicle.getHeight() - 1
                  + moveDistance >= getBoardHeight()) {
            return false;
        }

        int availableMoves = 0;
        for (int i = 1; i <= moveDistance; ++i) {
            if (!isCoordEmpty(vehicle.getX(),
                    vehicle.getY() + vehicle.getHeight() - 1 + i)) {
                break;
            }

            ++availableMoves;
        }
        
        return moveDistance <= availableMoves;
    }
    
    public void moveRight(Vehicle vehicle, int moveDistance) {
        removeVehicle(vehicle);
        
        vehicle.setX(vehicle.getX() + moveDistance);
        
        placeVehicle(vehicle);
    }
    
    public void moveLeft(Vehicle vehicle, int moveDistance) {
        removeVehicle(vehicle);
        
        vehicle.setX(vehicle.getX() - moveDistance);
        
        placeVehicle(vehicle);
    }

    public void moveUp(Vehicle vehicle, int moveDistance) {
        removeVehicle(vehicle);
        
        vehicle.setY(vehicle.getY() - moveDistance);
        
        placeVehicle(vehicle);
    }

    public void moveDown(Vehicle vehicle, int moveDistance) {
        removeVehicle(vehicle);
        
        vehicle.setY(vehicle.getY() + moveDistance);
        
        placeVehicle(vehicle);
    }

    public void placeVehicle(Vehicle vehicle) {
        setVehicle(vehicle, vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        setVehicle(vehicle, null);
    }
    
    private void setVehicle(Vehicle vehicle, Vehicle value) {
        if (vehicle != null) {
            if (vehicle.getOrientation().equals("v")) {
                for (int i = 0; i < vehicle.getHeight(); i++) {
                    this.gameBoard[vehicle.getY() + i][vehicle.getX()] = value;
                }
            } else {
                for (int i = 0; i < vehicle.getWidth(); i++) {
                    this.gameBoard[vehicle.getY()][vehicle.getX() + i] = value;
                }
            }
        }
    }

    public boolean isCoordEmpty(int x, int y) {
        return this.gameBoard[y][x] == null;
    }

    public ArrayList<Vehicle> getVehicleList() {
        return this.vehicles;
    }

    public int getBoardWidth() {
        return this.boardWidth;
    }

    public int getBoardHeight() {
        return this.boardHeight;
    }

    /*Collects vehicle strings into one larger string*/
    public String makeBoardString() {
        String boardString = "";

        for (int i = 0; i < vehicles.size(); i++) {
            boardString += vehicles.get(i).getVehicleString() + ";";
        }

        return boardString;
    }
}
