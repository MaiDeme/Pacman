package fr.upsaclay.bibs.pacman.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SpriteLoader {
    public List<String[]> loadSprites(String filename) {
        List<String[]> frames = new ArrayList<String[]>();

        //Loading frames from text file
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Splitting line into array of 0 and 1
                String[] frame =line.split("");
                frames.add(frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return frames;
    }
}

