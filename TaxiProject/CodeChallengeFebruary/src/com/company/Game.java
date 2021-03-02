package com.company;

import com.company.Objects.Goal;
import com.company.Objects.Lake;
import com.company.Objects.Player;
import com.company.Objects.Tree;
import com.company.Tool.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Game class that contains all the information necessary to run the game.
 */
public class Game {

    // ##############################################
    // ########## - Variables - #####################
    // ##############################################
    private ArrayList<Score> scoreListBeginner = new ArrayList<>();
    private ArrayList<Score> scoreListIntermediate = new ArrayList<>();
    private ArrayList<Score> scoreListExpert = new ArrayList<>();
    static Player player = new Player();
    static Goal goal = new Goal();
    private Grid grid = new Grid();
    private String playerName;
    private boolean gameRunning;
    private boolean programRunning;
    private boolean playerPosition = true;
    private boolean goalPosition = true;
    private GameFileSaver fileSaver = new GameFileSaver();
    private GameFileReader fileReader = new GameFileReader();
    private int mapLevel = 1;
    private ArrayList<String> menuList = new ArrayList<>();

    /**
     * Initializes a game. Chooses player name, map, hazards etc, shows a menu
     */
    public void startTheGame() {
        scoreListBeginner = fileReader.readFile("highScoreBeginner");
        scoreListIntermediate = fileReader.readFile("highScoreIntermediate");
        scoreListExpert = fileReader.readFile("highScoreExpert");

        programRunning = true;
        setupMenu();
        setPlayerName();
        while(programRunning) {
            printMenu();
            while(grid.getMapRunning()) {
                grid.drawPlayer();
                if(playerPosition) {
                    grid.printPlayerPosition();
                }
                if(goalPosition) {
                    grid.printGoalPosition();
                }
                grid.movePlayer();
                if(!grid.getMapRunning()) {
                    grid.drawRoute();
                    grid.drawGrid();
                    if(!grid.getCrash()) {
                        addToScoreList();
                        grid.resetRoute();
                        System.out.println("GOAL!!!");
                        typeAnythingToContinue();
                    } else {
                        System.out.println("You have crashed. Game over.");
                        grid.resetRoute();
                        typeAnythingToContinue();
                    }

                }
            }
        }
    }

    /**
     * Checks which map is currently active and adds the score to the correct arraylist.
     */
    public void addToScoreList() {
        switch (mapLevel) {
            case 1:
                scoreListBeginner.add(new Score(playerName, grid.getNumberOfMoves()));
                fileSaver.updateScoreFile(scoreListBeginner, "highScoreBeginner");
                break;
            case 2:
                scoreListIntermediate.add(new Score(playerName, grid.getNumberOfMoves()));
                fileSaver.updateScoreFile(scoreListIntermediate, "highScoreIntermediate");
                break;
            case 3:
                scoreListExpert.add(new Score(playerName, grid.getNumberOfMoves()));
                fileSaver.updateScoreFile(scoreListExpert, "highScoreExpert");
                break;
            default:
                System.out.println("Incorrect score list.");
                break;
        }
    }

    /**
     * Settings for the beginner map.
     */
    public void mapBeginner() {
        grid.initializeMap(15); //
        grid.addWallsToEdges();

        // Objects
        //grid.addPlayerToSpace(3,3,player);
        //grid.addObjectToSpace(14,14, goal);


        grid.addObjectPatternToSpace(10,10,new Lake(), "plus");
        grid.addObjectPatternToSpace(12,4,new Lake(), "plus");
        grid.addObjectPatternToSpace(13,13, new Tree(), "lineY");
        grid.addObjectPatternToSpace(14,3, new Tree(), "lineY");
        grid.addObjectPatternToSpace(7,10, new Tree(), "lineY");
        grid.addObjectPatternToSpace(7,5, new Tree(), "lineY");
        grid.addObjectPatternToSpace(5,4, new Tree(), "lineX");
        grid.addObjectPatternToSpace(3,8, new Tree(), "lineX");
        grid.addObjectPatternToSpace(3,9, new Tree(), "lineX");
        grid.addObjectPatternToSpace(10,14, new Tree(), "lineX");
        grid.addObjectPatternToSpace(4,13, new Lake(), "bigPlus");

        grid.addPlayerToSpace(grid.randomizePosition()[0],grid.randomizePosition()[1],player);
        grid.addObjectToSpace(grid.randomizePosition()[0],grid.randomizePosition()[1], goal);


    }

    /**
     * Settings for the intermediate map.
     */
    public void mapIntermediate() {
        grid.initializeMap(20);
        grid.addWallsToEdges();

        // Objects
        grid.addPlayerToSpace(10,10,player);
        grid.addObjectToSpace(17,17, goal);

        grid.addObjectPatternToSpace(10,12,new Tree(),"lineX");
        grid.addObjectPatternToSpace(6,16, new Lake(), "bigPlus");
        grid.addObjectPatternToSpace(3,11, new Tree(), "lineX");
        grid.addObjectPatternToSpace(10,13, new Tree(),"lineX");
        grid.addObjectPatternToSpace(11,15, new Tree(),"lineX");
        grid.addObjectPatternToSpace(12,9,new Tree(),"lineY");
        grid.addObjectPatternToSpace(10,6, new Lake(),"lineX");
        grid.addObjectPatternToSpace(15,16, new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(19,16, new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(5,4,new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(18,5,new Lake(),"lineX");
        grid.addObjectToSpace(8,3,new Tree());
        grid.addObjectToSpace(8,2,new Tree());
        grid.addObjectToSpace(8,1,new Tree());
        grid.addObjectToSpace(15,13,new Tree());
        grid.addObjectToSpace(16,13,new Tree());
        grid.addObjectToSpace(17,13,new Tree());
        grid.addObjectToSpace(18,12,new Tree());
        grid.addObjectToSpace(18,11,new Tree());
        grid.addObjectToSpace(18,10,new Tree());
        grid.addObjectToSpace(17,9,new Tree());
        grid.addObjectToSpace(16,9,new Tree());
        grid.addObjectToSpace(15,9,new Tree());
    }

    /**
     * Settings for the expert map.
     */
    public void mapExpert() {
        grid.initializeMap(25);
        grid.addWallsToEdges();

        // Objects
        grid.addPlayerToSpace(23,9,player);
        grid.addObjectToSpace(5,22, goal);

        grid.addObjectPatternToSpace(4,22,new Tree(),"lineY");
        grid.addObjectPatternToSpace(6,22,new Tree(),"lineY");
        grid.addObjectPatternToSpace(12,24,new Tree(),"lineX");
        grid.addObjectPatternToSpace(13,19,new Tree(),"lineX");
        grid.addObjectPatternToSpace(13,17,new Tree(),"lineX");
        grid.addObjectPatternToSpace(19,19,new Tree(),"lineX");
        grid.addObjectPatternToSpace(19,17,new Tree(),"lineX");
        grid.addObjectPatternToSpace(7,14,new Tree(),"lineY");
        grid.addObjectPatternToSpace(9,16,new Tree(),"lineY");
        grid.addObjectPatternToSpace(12,10,new Tree(),"lineX");
        grid.addObjectPatternToSpace(18,14,new Tree(),"lineY");
        grid.addObjectPatternToSpace(21,12,new Tree(),"lineY");
        grid.addObjectPatternToSpace(22,9,new Tree(),"lineY");
        grid.addObjectPatternToSpace(20,1,new Tree(),"lineX");
        grid.addObjectPatternToSpace(7,5,new Tree(),"lineX");
        grid.addObjectPatternToSpace(7,2,new Tree(),"lineX");
        grid.addObjectPatternToSpace(20,23,new Tree(),"bigPlus");
        grid.addObjectPatternToSpace(9,21,new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(7,8,new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(14,13,new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(12,5,new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(24,13,new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(23,5,new Lake(),"bigPlus");
        grid.addObjectPatternToSpace(2,13,new Lake(),"lineY");
        grid.addObjectPatternToSpace(2,7,new Lake(),"lineY");
        grid.addObjectPatternToSpace(4,13,new Lake(),"lineY");
        grid.addObjectPatternToSpace(4,7,new Lake(),"lineY");
        grid.addObjectPatternToSpace(23,17,new Lake(),"lineY");
        grid.addObjectPatternToSpace(13,18,new Lake(),"lineX");
        grid.addObjectPatternToSpace(19,18,new Lake(),"lineX");
        grid.addObjectPatternToSpace(18,7,new Lake(),"lineX");
        grid.addObjectPatternToSpace(17,4,new Lake(),"lineX");

        grid.addObjectToSpace(2,10,new Tree());
        grid.addObjectToSpace(4,10,new Tree());
        grid.addObjectToSpace(8,19,new Tree());
        grid.addObjectToSpace(16,8,new Tree());
        grid.addObjectToSpace(19,8,new Tree());
        grid.addObjectToSpace(24,7,new Tree());

        grid.addObjectToSpace(5,18,new Lake());
        grid.addObjectToSpace(6,18,new Lake());
    }

    /**
     * Sets the player name and makes sure it isn't empty or contains any spaces.
     */
    public void setPlayerName() {
        Scanner scanner = new Scanner(System.in);
        String playerName;

        System.out.println("Please type in your player name. No spaces allowed.");
        playerName = scanner.nextLine();
        while(playerName.contains(" ") || playerName.length() == 0) {
            System.out.println("Error: Player name may not contain any spaces or be empty.");
            playerName = scanner.next();
        }
        this.playerName = playerName;
    }

    // MENU
    /**
     * Method to easily add new lines to the menu without having to write a number in front. See method printMenu() for usage.
     */
    private void setupMenu() {
        menuList.add("Start game - " );
        menuList.add("Change map");
        menuList.add("Preview maps");
        menuList.add("High-scores / Statistics");
        menuList.add("Settings");
        menuList.add("Exit program");
    }

    /**
     * Prints the start menu that displays all the options including starting the game.
     */
    public void printMenu() {
        System.out.println();
        System.out.println("Welcome to the taxi game " + playerName + ": please choose an option from the menu ");
        menuList.set(0,"Start game - " + mapChoice(mapLevel));
        for(int i = 0; i < menuList.size(); i++) {
            System.out.println(i+1 + " - " + menuList.get(i));
        }

        switch (Input.inputInt(menuList.size())) {
            case 1:
                playMap();
                break;
            case 2:
                changeMap();
                break;
            case 3:
                showMaps();
                break;
            case 4:
                printHighScores();
                break;
            case 5:
                settings();
                break;
            case 6:
                programRunning = false;
                System.out.println("Exiting program. Goodbye!");
                break;
            default:
                break;
        }
    }

    /**
     * Starts the map that is currently active by looking at the integer mapLevel.
     */
    public void playMap() {
        switch (mapLevel) {
            case 1:
                mapBeginner();
                gameRunning = true;
                grid.setMapRunning(true);
                break;
            case 2:
                mapIntermediate();
                gameRunning = true;
                grid.setMapRunning(true);
                break;
            case 3:
                mapExpert();
                gameRunning = true;
                grid.setMapRunning(true);
                break;
            default:
                System.out.println("Map not available.");
                break;
        }
    }

    /**
     * Allows the player to change the active map by assigning the integer mapLevel a new value.
     */
    public void changeMap() {
        System.out.println("Choose between following map options: " +
                "\n1 - Beginner map: 20 by 20 grid" +
                "\n2 - Intermediate Map: 30 by 30 grid" +
                "\n3 - Expert map: 40 by 40 grid");
        mapLevel = Input.inputInt(4);
    }

    /**
     * This method is used to print out the chosen map name in the start menu and to save the map name for high scores.
     * @param choice
     * @return
     */
    public String mapChoice(int choice) {
        switch (choice) {
            case 1:
                return "Beginner";
            case 2:
                return "Intermediate";
            case 3:
                return "Expert";
            default:
                return "Beginner";
        }
    }

    /**
     * Allows the player to preview the playable maps before playing them.
     */
    public void showMaps() {
        int choice;

        System.out.println("Preview which map? " +
                "\n1 - Beginner map: 20 by 20 grid" +
                "\n2 - Intermediate Map: 30 by 30 grid" +
                "\n3 - Expert map: 40 by 40 grid" +
                "\n4 - Go back to previous menu");
        choice = Input.inputInt(4);
        switch (choice) {
            case 1:
                mapBeginner();
                grid.drawGrid();
                typeAnythingToContinue();
                break;
            case 2:
                mapIntermediate();
                grid.drawGrid();
                typeAnythingToContinue();
                break;
            case 3:
                mapExpert();
                grid.drawGrid();
                typeAnythingToContinue();
                break;
            default:
                break;
        }
    }

    /**
     * Allows the player to change settings such as displaying the player's or the goal's position during the game.
     * In addition, the player can also toggle safe crash on/off, which gives the player 1 fail safe in case they crash.
     */
    public void settings() {
        System.out.println("1 - Player position On/Off " +
                "\n2 - Display goal position On/Off" +
                "\n3 - Safe crash On/Off" +
                "\n4 - Previous menu.");
        int choice = Input.inputInt(3);
        switch (choice) {
            case 1:
                setSettings("player position");
                break;
            case 2:
                setSettings("goal position");
                break;
            case 3:
                setSettings("safe crash");
            default:
                break;
        }
    }

    /**
     * Method used to change settings in conjunction with the method settings() above.
     * @param option
     */
    public void setSettings(String option) {
        System.out.println("1 - "+option+" on." +
                "\n2 - "+option+" off." +
                "\n3 - Previous menu.");
        int choice = Input.inputInt(3);
        switch (option) {
            case "player position":
                switch (choice) {
                    case 1:
                        playerPosition = true;
                        break;
                    case 2:
                        playerPosition = false;
                        break;
                    default:
                        break;
                }
            case "goal position":
                switch (choice) {
                    case 1:
                        goalPosition = true;
                        break;
                    case 2:
                        goalPosition = false;
                        break;
                    default:
                        break;
                }
            case "safe crash":
                switch (choice) {
                    case 1:
                        grid.setSafeCrash(true);
                        break;
                    case 2:
                        grid.setSafeCrash(false);
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
    }

    /**
     * A simple method to prevent the menu from displaying right after any action.
     */
    public void typeAnythingToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type anything to continue: ");
        scanner.nextLine();
    }

    /**
     * Prints the top 3 players from each category.
     */
    public void printHighScores() {
        ScoreComparator scoreComparator = new ScoreComparator();
        Collections.sort(scoreListBeginner, scoreComparator);
        Collections.sort(scoreListIntermediate, scoreComparator);
        Collections.sort(scoreListExpert, scoreComparator);

        System.out.println("\nTop 3 Beginners: ");
        for(int i = 0; i < 3; i++) {
            System.out.println((i+1)+". " + scoreListBeginner.get(i).getPlayerName() + " " + scoreListBeginner.get(i).getScore());
        }
        System.out.println("\nTop 3 Intermediates: ");
        for(int i = 0; i < 3; i++) {
            System.out.println((i+1)+". " + scoreListIntermediate.get(i).getPlayerName() + " " + scoreListIntermediate.get(i).getScore());
        }
        System.out.println("\nTop 3 Expert: ");
        for(int i = 0; i < 3; i++) {
            System.out.println((i+1)+". " + scoreListExpert.get(i).getPlayerName() + " " + scoreListExpert.get(i).getScore());
        }
        typeAnythingToContinue();
    }
}
