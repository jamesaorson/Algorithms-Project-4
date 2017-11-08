package algorithmsproject4;

/**
* Solver class performs breadth first search on the gameboard
*
* @author Nate Stahlnecker & James Osborne
* @version 1.0
* File: Solver.java
* Created: Nov 2017
* ©Copyright Cedarville University, its Computer Science faculty, and the
* authors. All rights reserved.
* Summary of Modifications:
*
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solver {
    public String gameBoardfile;
    public GameBoard gameBoard;
    private final Queue<String> queue;
    private final HashMap<String, String> records;
    private final HashMap<String, String> visitedBoards;
    private final ArrayList<String> solution;

    public Solver(String file) throws FileNotFoundException {
        this.gameBoardfile = file;
        this.gameBoard = parseInputFile(file);
        this.queue = new LinkedList<String>();
        this.records = new HashMap<String, String>();
        this.visitedBoards = new HashMap<String, String>();
        this.solution = new ArrayList<String>();
    }

    public GameBoard parseInputFile(String input) throws FileNotFoundException {
        File file = new File(input);
        Scanner scan = new Scanner(file);
        GameBoard initialBoard = new GameBoard(6, 6);
        
        int numVehicles = scan.nextInt();
        
        for (int i = 0; i < numVehicles; i++) {
            int size = scan.next().equals("car") ? 2 : 3;
            
            String color = scan.next();
            String orientation = scan.next();
            
            int y = scan.nextInt() - 1;
            int x = scan.nextInt() - 1;
            
            int width = orientation.equals("h") ? size : 1;
            int height = orientation.equals("v") ? size : 1;
            
            initialBoard.addVehicle(x, y, height, width, color, orientation);
        }

        return initialBoard;
    }
    
    public boolean solve() {
        String currBoardString = this.gameBoard.makeBoardString();
        //Add first visit to the queue, which is the initial board
        addVisits(null, currBoardString, null, null, 0);

        while (!queue.isEmpty()) {
            //Initialize current board from boardString
            currBoardString = queue.remove();
            GameBoard currBoard = makeBoardFromBoardString(currBoardString);
            ArrayList<Vehicle> currVehicles = currBoard.getVehicleList();

            for (int i = 0; i < currVehicles.size(); i++) {
                Vehicle currVehicle = currVehicles.get(i);
                String color = currVehicle.getColor();
                int moveDistance = 1;
                //Tracks if a move exists in right or up direction
                boolean rightOrUpMoveExists = true;
                //Tracks if a move exists in left or down direction
                boolean leftOrDownMoveExists = true;

                while (rightOrUpMoveExists || leftOrDownMoveExists) {
                    if (currVehicle.getOrientation().equals("h")) {
                        if (currBoard.canMoveRight(currVehicle, moveDistance)) {
                            //Only a right move will solve the problem
                            if (performRightMove(
                                    currBoard, 
                                    currBoardString,
                                    currVehicle,
                                    color, 
                                    moveDistance)) {
                                //Maze is solvable
                                return true;
                            }
                        } else {
                            rightOrUpMoveExists = false;
                        }
                        if (currBoard.canMoveLeft(
                                currVehicle, 
                                moveDistance)) {
                            performLeftMove(
                                    currBoard, 
                                    currBoardString, 
                                    currVehicle, 
                                    color, 
                                    moveDistance);
                        }  else {
                            leftOrDownMoveExists = false;
                        }
                    } else { //if vertical
                        if (currBoard.canMoveUp(currVehicle, moveDistance)) {
                            performUpMove(
                                    currBoard, 
                                    currBoardString, 
                                    currVehicle, 
                                    color, 
                                    moveDistance);
                        } else {
                            rightOrUpMoveExists = false;
                        }
                        if (currBoard.canMoveDown(
                                currVehicle, 
                                moveDistance)) {
                            performDownMove(
                                    currBoard, 
                                    currBoardString, 
                                    currVehicle, 
                                    color, 
                                    moveDistance);
                        } else {
                            leftOrDownMoveExists = false;
                        }
                    }

                    ++moveDistance;
                }
            }
        }
        
        System.out.println("Unsolvable rush hour problem");
        return false;
    }
    
    private boolean performRightMove(
            GameBoard currBoard, 
            String currBoardString, 
            Vehicle currVehicle, 
            String color, 
            int moveDistance) {
        currBoard.moveRight(currVehicle, moveDistance);

        String nextState = currBoard.makeBoardString();
        addVisits(currBoardString, nextState, color, "right", moveDistance);

        //The puzzle can only be solved on a right move,
        //for that is when the red car will have moved
        //to the exit
        if (isSolved(currVehicle)) {
            backtrack(
                    this.solution, 
                    this.visitedBoards, 
                    this.records, 
                    nextState);
            printSolution();

            return true;
        } else {
            //Reverse move
            currBoard.moveLeft(currVehicle, moveDistance);
        }
        
        return false;
    }
    
    private void performLeftMove(
            GameBoard currBoard, 
            String currBoardString, 
            Vehicle currVehicle, 
            String color, 
            int moveDistance) {
        currBoard.moveLeft(currVehicle, moveDistance);
        
        String nextState = currBoard.makeBoardString();
        addVisits(currBoardString, nextState, color, "up", moveDistance);

        //Reverse move
        currBoard.moveRight(currVehicle, moveDistance);
    }
    
    private void performUpMove(
            GameBoard currBoard, 
            String currBoardString, 
            Vehicle currVehicle, 
            String color, 
            int moveDistance) {
        currBoard.moveUp(currVehicle, moveDistance);
        
        String nextState = currBoard.makeBoardString();
        addVisits(currBoardString, nextState, color, "up", moveDistance);

        //Reverse move
        currBoard.moveDown(currVehicle, moveDistance);
    }
    
    private void performDownMove(
            GameBoard currBoard, 
            String currBoardString, 
            Vehicle currVehicle, 
            String color, 
            int moveDistance) {
        currBoard.moveDown(currVehicle, moveDistance);
        
        String nextState = currBoard.makeBoardString();
        addVisits(currBoardString, nextState, color, "down", moveDistance);

        //Reverse move
        currBoard.moveUp(currVehicle, moveDistance);
    }

    public void addVisits(
            String curr, 
            String next, 
            String color,
            String direction, 
            int moveDistance) {
        //Only insert next visit if the visit has not been visited before
        if (!this.visitedBoards.containsKey(next)) {
            this.visitedBoards.put(next, curr);
            
            //Place movement string into records for printing
            this.records.put(
                    next, 
                    color + "\t"
                    + moveDistance + "\t"
                    + direction);
            
            //Add next unique move to the queue
            this.queue.add(next);
        }
    }

    public GameBoard makeBoardFromBoardString(String boardString) {
        GameBoard currBoard =  new GameBoard(
                this.gameBoard.getBoardWidth(),
                this.gameBoard.getBoardHeight());
        
        String[] splitBoardString = boardString.split(";");

        for (int i = 0; i < splitBoardString.length; ++i) {
            String[] vehicleString = splitBoardString[i].split(",");
            
            String color = vehicleString[0];
                    
            int height = Integer.parseInt(vehicleString[1]);
            int width = Integer.parseInt(vehicleString[2]);
            
            int x = Integer.parseInt(vehicleString[3]);
            int y = Integer.parseInt(vehicleString[4]);
            
            String orientation = vehicleString[5];
            
            currBoard.addVehicle(x, y, height, width, color, orientation);
        }

        return currBoard;
    }

    public boolean isSolved(Vehicle vehicle) {
        if (vehicle.getColor().toLowerCase().equals("red")) {
            //If red car is at exit, the rush hour is solved
            return (vehicle.getX() + vehicle.getWidth()
                    == this.gameBoard.getBoardWidth());
        } else {
            return false;
        }
    }

    /*Recursively create the solution string arrayList*/
    private void backtrack(
            ArrayList<String> solution,
            HashMap<String,
            String> visits,
            HashMap<String, String> records, 
            String next) {
        String parent = visits.get(next);
        
        if (parent != null) {
            backtrack(solution, visits, records, parent);
            solution.add(records.get(next));
        }
    }
    
    public void printSolution() {
        System.out.println(this.solution.size() + " moves");
        for (int i = 0; i < this.solution.size(); ++i) {
            System.out.println(this.solution.get(i));
        }
    }
}
