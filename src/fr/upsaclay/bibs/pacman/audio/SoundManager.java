package fr.upsaclay.bibs.pacman.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private Map<String, Clip> soundClips;

    public SoundManager() {
        soundClips = new HashMap<>();
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

    // Add other methods for pause, resume, etc., as needed
}
