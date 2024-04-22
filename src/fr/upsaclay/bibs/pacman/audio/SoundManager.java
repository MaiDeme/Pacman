package fr.upsaclay.bibs.pacman.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SoundManager {
    private Map<String, Clip> soundClips;
    private Map<String, Integer> soundPlayCounts;

    public SoundManager() {
        soundClips = new HashMap<>();
        soundPlayCounts = new HashMap<>();
        loadSounds();
    }

    private void loadSound(String name, String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            soundClips.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void loadSounds() {
        loadSound("PACMAN_CHOMP", "resources/sounds/pacman_chomp.wav");
        loadSound("PACMAN_BEGINNING", "resources/sounds/pacman_beginning.wav");
        loadSound("PACMAN_EAT_FRUIT", "resources/sounds/pacman_eatfruit.wav");
        loadSound("PACMAN_DEATH", "resources/sounds/pacman_death.wav");
        loadSound("GHOST_FREAKED", "resources/sounds/pacman_intermission.wav");
        loadSound("GHOST_EATEN", "resources/sounds/8d82b5_Pacman_Eating_Ghost_Sound_Effect.wav");

        // Load other sounds as needed
    }

    public void play(String soundName) {
        Clip clip = soundClips.get(soundName);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stop(String soundName) {
        Clip clip = soundClips.get(soundName);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public boolean isPlaying(String soundName) {
        Clip clip = soundClips.get(soundName);
        return clip != null && clip.isRunning();
    }

    public void playAndWait(String soundName) {
        Clip clip = soundClips.get(soundName);
        if (clip != null) {
            Thread soundThread = new Thread(() -> {
                if (clip.isRunning()) {
                    clip.stop();  // Stop the clip if it is already playing
                }
                clip.setFramePosition(0);  // Rewind to the beginning
                clip.start();  // Start playing
                try {
                    while (clip.isActive()) {
                        Thread.sleep(100);  // Wait a short while and check again
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            soundThread.start();
            try {
                soundThread.join();  // Wait for the sound thread to finish playing
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    
    
    // Add other methods for pause, resume, etc., as needed
}

