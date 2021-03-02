package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private ArrayList<String> menuList = new ArrayList<>();

    private void setupMenu() {
        menuList.add("Start game");
        menuList.add("Exit game");
    }
    public void printMenu() {
        setupMenu();
        System.out.println();
        System.out.println("Welcome to the taxi game: please choose an option from the menu ");
        System.out.println("1 - Start game" +
                "\n2 - Exit game");

        switch (1) {

        }
    }

    /**
     *
     * validate input
     * make comments more explanatory ie. can't use negative numbers
     */
    public int inputInteger(Menu menu) {
        int input = 0;
        boolean integer = false;
        Scanner scanner = new Scanner(System.in);

        while(integer) {
            if(scanner.hasNextInt()) {
                input = scanner.nextInt();
                if(input <= 0 || input > menuList.size()) {
                    System.out.println(input + " is not valid");
                } else {
                    integer = true;
                }
            } else {
                String word = scanner.nextLine();
                System.out.println(word + " is not valid");
            }
        }

        return input;
    }

}
