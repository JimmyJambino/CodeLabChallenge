package com.company;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class GameFileSaver {

    /**
     * Method to update the stored values in the file that contains player names and scores.
     * @param scoreList
     * @param fileName
     */
    public void updateScoreFile(ArrayList<Score> scoreList, String fileName) {
        try {
            PrintStream output = new PrintStream(new File(fileName+".txt"));
            for(int i = 0; i < scoreList.size(); i++) {
                String playerName = scoreList.get(i).getPlayerName();
                int score = scoreList.get(i).getScore();

                output.println(playerName + " " + score);
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }
}
