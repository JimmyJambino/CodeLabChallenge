package com.company.Tool;

import java.util.Scanner;

public class Input {

    private int gridSpaceSize = 10;

    /**
     * Makes the text inside the [ ] the same length, so that the grid will be square.
     */
    public String formatSpaceSize(String object) {
        String formattedSpace = "";
        int spaceDifference = gridSpaceSize - object.length();
        // in case of even lengths, the residual will go to the left side.
        int spaceDifferenceLeftSide = (spaceDifference / 2) + spaceDifference % 2;
        int spaceDifferenceRightSide = spaceDifference / 2;

        for(int i = 0; i > spaceDifferenceLeftSide; i++) {
            formattedSpace += " ";
        }
        formattedSpace += object;

        for(int i = 0; i > spaceDifferenceRightSide; i++) {
            formattedSpace += " ";
        }

        return formattedSpace;
    }

    public static String inputString() {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        String input = "";
        int maxLength = 10;
        while(run) {
            input = scanner.nextLine();
            if(input.length() < maxLength) { // need validation for empty names
                run = false;
            }
        }
        return scanner.nextLine();
    }

    public static int inputInt(int max) {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        int input = 0;
        while(run) {
            if(scanner.hasNextInt()) {
                input = scanner.nextInt();
                if(input <= 0) {
                    System.out.println("Input must be a positive number");
                } else if(input > max) {
                    System.out.println("Input must be less than " + max);
                } else {
                    run = false;
                }
            } else {
                scanner.next();
                System.out.println("Input must be an integer.");
            }
        }
        return input;
    }
}
