package fr.upsaclay.bibs.pacman.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SpriteLoader {
    public String[][] loadSprites(String filename) {
        List<String[]> framesList = new ArrayList<String[]>();

        //Loading frames from text file
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Splitting line into array of 0 and 1
                String[] frame =line.split(" ");
                framesList.add(frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Converting the list in a 2D array

        String[][] framesArray = new String[framesList.size()][];
        for (int i = 0; i < framesList.size(); i++) {
            framesArray[i] = framesList.get(i);
    }
        return framesArray;
    }
}

