package com.company;

import com.company.Objects.*;
import com.company.Tool.Color;
import com.company.Tool.Input;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Grid {

    // ##############################################
    // ########## - Variables - #####################
    // ##############################################
    private ArrayList<ArrayList<GameObject>> map = new ArrayList<>();
    private ArrayList<int[]> route = new ArrayList<>();
    private int numberOfMoves = 0;
    private int[] startPosition;
    private boolean mapRunning;
    private boolean crash = false;
    private boolean safeCrash = true;

    // ##############################################
    // ########## - Grid Setup - ####################
    // ##############################################

    /**
     * Method to initialize the length of the map that corresponds to the x-axis.
     * @param x
     * @return ArrayList<GameObject>
     */
    public ArrayList<GameObject> addMapCoordinateX(int x) {
        ArrayList<GameObject> mapSpaceX = new ArrayList<>();
        int walls = 2;
        for(int i = 0; i < x+walls; i++) {
            mapSpaceX.add(new Road());
        }
        return mapSpaceX;
    }

    /**
     * Uses the addMapCoordinateX() method to make
     * @param gridSize
     */
    public void initializeMap(int gridSize) {
        ArrayList<ArrayList<GameObject>> arrayOfArray = new ArrayList<>();
        int walls = 2;

        for(int i = 0; i < gridSize+walls; i++) {
            arrayOfArray.add(addMapCoordinateX(gridSize));
        }
        map = arrayOfArray;
    }

    // ##############################################
    // ########## - Grid Display - ##################
    // ##############################################

    /**
     * Method to display the actual grid. It takes each GameObject object and prints the corresponding toString().
     */
    public void drawGrid() {
        for(int i = map.size()-1; i >= 0; i--) {
            for(GameObject gameObject: map.get(i)) {
                System.out.print(gameObject.toString());
            }
            System.out.println();
        }
    }

    /**
     * Draws the player's "vision" which is a 3 by 3 grid.
     */
    public void drawPlayer() {
        int size = 3;
        int topY = findPlayerPosition()[1]+1;
        int x;
        for(int i = topY; topY+size > i && topY >= 0; topY--) {
            x = findPlayerPosition()[0]-1;
            for(int j = x; x+size > j; j++) {
                System.out.print(map.get(topY).get(j));
            }
            System.out.println();
        }
    }

    /**
     * Draws the route taken by the player at the end of the game.
     */
    public void drawRoute() {
        for(int[] routePosition : route) {
            int routeY = routePosition[1];
            int routeX = routePosition[0];
            map.get(routeY).set(routeX, new Path());
        }
        map.get(startPosition[1]).set(startPosition[0], new Player());
    }

    /**
     * Adds the player's position to the ArrayList<int[]> route so that it can be drawn with the drawRoute() method.
     * @param x coordinate
     * @param y coordinate
     */
    public void addToRoute(int x, int y) {
        int[] routePositions = new int[2];
        routePositions[0] = x;
        routePositions[1] = y;
        route.add(routePositions);
    }

    /**
     * Informs the player how many moves it took to reach the goal and resets the numberOfMoves int so it can be used for a new game.
     * It also clears the int[] route.
     */
    public void resetRoute() {
        if(!crash) {
            System.out.println("It took you " + numberOfMoves + " moves to reach the goal.");
        }
        crash = false;
        numberOfMoves = 0;
        route.clear();
    }

    /**
     * Adds a single GameObject object to an (x,y)-coordinate on the map.
     * @param x
     * @param y
     * @param gameObject
     */
    public void addObjectToSpace(int x, int y, GameObject gameObject) {
        map.get(y).set(x, gameObject);
    }

    /**
     * Adds a pattern of GameObject objects to multiple (x,y)-coordinates on the map, given a String.
     * @param x
     * @param y
     * @param gameObject
     * @param pattern
     */
    public void addObjectPatternToSpace(int x, int y, GameObject gameObject, String pattern) {

        switch (pattern) {
            case "plus":
                plusPattern(x,y,gameObject);
                break;
            case "lineY":
                lineYPattern(x,y,gameObject);
                break;
            case "lineX":
                lineXPattern(x,y,gameObject);
                break;
            case "bigPlus":
                bigPlusPattern(x,y,gameObject);
                break;
            default:
                break;
        }
    }

    /**
     * A pattern drawn like a plus-symbol.
     * @param x
     * @param y
     * @param gameObject
     */
    public void plusPattern(int x, int y, GameObject gameObject) {
        // Plus pattern
        map.get(y).set(x, gameObject); // center
        map.get(y+1).set(x, gameObject); // above
        map.get(y-1).set(x, gameObject); // below
        map.get(y).set(x+1, gameObject); // right
        map.get(y).set(x-1, gameObject); // left
    }

    /**
     * A pattern drawn like a bigger plus-symbol.
     * @param x
     * @param y
     * @param gameObject
     */
    public void bigPlusPattern(int x, int y, GameObject gameObject) {
        // Big plus pattern
        map.get(y).set(x, gameObject);
        map.get(y+2).set(x, gameObject);
        map.get(y+1).set(x, gameObject);
        map.get(y+1).set(x-1, gameObject);
        map.get(y+1).set(x+1, gameObject);
        map.get(y-1).set(x, gameObject);
        map.get(y-1).set(x-1, gameObject);
        map.get(y-1).set(x+1, gameObject);
        map.get(y-2).set(x, gameObject);
        map.get(y).set(x+1, gameObject);
        map.get(y).set(x-1, gameObject);
    }

    /**
     * A pattern drawn like a vertical line.
     * @param x
     * @param y
     * @param gameObject
     */
    public void lineYPattern(int x, int y, GameObject gameObject) {
        // Line Y pattern
        map.get(y).set(x, gameObject);
        map.get(y+1).set(x, gameObject);
        map.get(y+2).set(x, gameObject);
        map.get(y-1).set(x, gameObject);
        map.get(y-2).set(x, gameObject);

    }

    /**
     * A pattern drawn like a horizontal line.
     * @param x
     * @param y
     * @param gameObject
     */
    public void lineXPattern(int x, int y, GameObject gameObject) {
        // Line X pattern
        map.get(y).set(x, gameObject);
        map.get(y).set(x+1, gameObject);
        map.get(y).set(x+2, gameObject);
        map.get(y).set(x-1, gameObject);
        map.get(y).set(x-2, gameObject);
    }

    /**
     * Adds the player to an (x,y)-coordinate.
     * @param x
     * @param y
     * @param player
     */
    public void addPlayerToSpace(int x, int y, Player player) {
        map.get(y).set(x, player);
        setStartPosition(x,y);
    }

    /**
     * Method used to find the goal position on the map. It can then be displayed to the player with printGoalPosition().
     * @return
     */
    public int[] findGoalPosition() {
        int[] position = {-1, -1};
        for (int i = 0; i < map.size(); i++) {
            if(map.get(i).contains(Game.goal)) { // if an arraylist contains player
                position[1] = i; // y = the found array
                position[0] = map.get(i).indexOf(Game.goal);
            }
        }
        return position;
    }

    /**
     * Method used to find the player position on the map. It can then be displayed to the player with printPlayerPosition().
     * @return
     */
    public int[] findPlayerPosition() {
        int[] position = {-1, -1};
        for (int i = 0; i < map.size(); i++) {
            if(map.get(i).contains(Game.player)) { // if an arraylist contains player
                position[1] = i; // y = the found array
                position[0] = map.get(i).indexOf(Game.player);
            }
        }
        return position;
    }

    /**
     * Displays the player's position in (x,y)-coordinates.
     */
    public void printPlayerPosition() {
        int[] position = findPlayerPosition();
        System.out.println("Player coordinates: (" + position[0] + "," + position[1] + ")");
    }
    /**
     * Displays the goal's position in (x,y)-coordinates.
     */
    public void printGoalPosition() {
        int[] position = findGoalPosition();
        System.out.println("Goal coordinates: (" + position[0] + "," + position[1] + ")");
    }

    /**
     * Moves the player depending on the direction chosen.
     */
    public void movePlayer() {
        Scanner scanner = new Scanner(System.in);
        int[] playerPosition = findPlayerPosition();
        int positionX = playerPosition[0]; // +1
        int positionY = playerPosition[1]; // +1
        System.out.println("Type a direction N, W, E, S or a combination for diagonal movement: ");

        String move = scanner.next(); // no need to validate here, method will be called again as long as you haven't crashed or finished the level.
        move = move.toUpperCase();

        // Allows the player to write the order of letters both ways.
        switch (move) {
            case "WN":
                move = "NW";
                break;
            case "EN":
                move = "NE";
                break;
            case "WS":
                move = "SW";
                break;
            case "ES":
                move = "SE";
                break;
            default:
        }

        switch (move) {
            case "E": movePlayerPosition(positionX+1,positionY); //
                break;
            case "N": movePlayerPosition(positionX,positionY+1);
                break;
            case "W": movePlayerPosition(positionX-1,positionY);
                break;
            case "S": movePlayerPosition(positionX,positionY-1);
                break;
            case "NW": movePlayerPosition(positionX-1,positionY+1);
                break;
            case "NE": movePlayerPosition(positionX+1,positionY+1);
                break;
            case "SE": movePlayerPosition(positionX+1,positionY-1);
                break;
            case "SW": movePlayerPosition(positionX-1,positionY-1);
                break;
            default: System.out.println("Invalid choice, please type a direction N, W, E, S or a combination for diagonal movement: ");
        }
    }

    /**
     * Checks if the chosen direction is valid, whether it's the goal position and displays error messages if the direction is invalid.
     * @param x
     * @param y
     */
    public void movePlayerPosition(int x, int y) {
            boolean emptySpace = checkSpaceForRoad(x,y); // Game.grid.checkSpaceForRoad(x,y) before
            boolean inGoal = checkSpaceForGoal(x,y);
            GameObject check = checkSpace(x,y);
            boolean crashAble = check.toString().equals(new Tree().toString()) || check.toString().equals(new Lake().toString());

            if(crashAble) {
                if(safeCrash) {
                    int choice;
                    System.out.println("Safe crash option is on. Do you still wish to crash into " + check.toString()+"?");
                    System.out.println("1 - Yes" +
                            "\n2 - No");
                    choice = Input.inputInt(2);
                    if(choice == 1) {
                        crash = true;
                        mapRunning = false;
                    }
                } else {
                    crash = true;
                    mapRunning = false;
                }
            } else {
                // In cases you aren't crashing, which is most cases.
                if(emptySpace || inGoal) {
                    numberOfMoves++;
                    int currentY = findPlayerPosition()[1];
                    int currentX = findPlayerPosition()[0];
                    if(!inGoal) {
                        addToRoute(x, y);
                        map.get(y).set(x, Game.player);
                        map.get(currentY).set(currentX, new Road());
                    } else {
                        setMapRunning(false);
                        map.get(currentY).set(currentX, new Road());
                    }
                } else {
                    System.out.println("Invalid position. There is a " + checkSpace(x,y).toString());
                }
            }
    }

    /**
     * Checks if the (x,y)-coordinate is of GameObject type Road.
     * @param x
     * @param y
     * @return
     */
    public boolean checkSpaceForRoad(int x, int y) {
        return map.get(y).get(x) instanceof Road;
    }

    /**
     * Checks if the (x,y)-coordinate is of GameObject type Goal.
     * @param x
     * @param y
     * @return
     */
    public boolean checkSpaceForGoal(int x, int y) {
        return map.get(y).get(x) instanceof Goal;
    }

    /**
     * Returns the GameObject at the given (x,y)-coordinate.
     * @param x
     * @param y
     * @return GameObject
     */
    public GameObject checkSpace(int x, int y) {
        return map.get(y).get(x);
    }

    /**
     * Adds GameObject type Wall to the edges of the map.
     */
    public void addWallsToEdges() {
        //find topY and botX and add walls across them
        for(int i = 0; i < map.size(); i++) {
            map.get(0).set(i,new Wall());
        }
        for(int i = map.size()-1; i >= 0; i--) {
            map.get(map.size()-1).set(i,new Wall());
        }
        for(ArrayList<GameObject> list : map) {
            list.set(0,new Wall());
            list.set(map.size()-1,new Wall());
        }
    }

    /**
     * Picks a random available position on the map.
     */
    public int[] randomizePosition() {
        Random random = new Random();

        int x;
        int y;
        boolean available = false;
        while (!available) {
            x = random.nextInt(map.size()-2)+1; // -2 for walls and +1 for possible position (1,1)
            y = random.nextInt(map.size()-2)+1; // -2 for walls and +1 for possible position (1,1)
            if(checkSpaceForRoad(x,y)) {
                return new int[] {x,y};
            }
        }
        return new int[] {0,0};
    }

    public void setMapRunning(boolean mapRunning) {
        this.mapRunning = mapRunning;
    }

    public boolean getMapRunning() {
        return mapRunning;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public boolean getCrash() {
        return crash;
    }

    public void setSafeCrash(boolean safeCrash) {
        this.safeCrash = safeCrash;
    }

    /**
     * Sets the int[] startPosition so that it can be used to draw the start position on the route later.
     * @param x
     * @param y
     */
    public void setStartPosition(int x, int y) {
        int[] startPosition = {x,y};
        this.startPosition = startPosition;
    }
}
