package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameFileReader {

    /**
     * Method to read a file containing player name and scores.
     * @param fileName
     * @return ArrayList<Score>
     */
    public ArrayList<Score> readFile(String fileName) {
        ArrayList<Score> scoreList = new ArrayList<>();
        try {
            Scanner load = new Scanner(new File(fileName+".txt"));
            while (load.hasNextLine()) {
                String line = load.nextLine();
                Scanner lineScan = new Scanner(line);
                scoreList.add(new Score(lineScan.next(),lineScan.nextInt())); // no need to verify here as they are verified earlier.
            }
        } catch (IOException io) {
            System.out.println("File not found");
        }
        return scoreList;
    }
}
