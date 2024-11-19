package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioLoader {
    private static final Map<String, Clip> audioClips = new HashMap<>();

    // Load an audio file and store it in the clips map
    public static void loadAudio(String name, String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            audioClips.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading audio file: " + e.getMessage());
        }
    }

    // Play the specified audio clip
    public static void playAudio(String name) {
        Clip clip = audioClips.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Stop if already playing
            }
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        } else {
            System.out.println("Audio clip not found: " + name);
        }
    }

    // Stop the specified audio clip
    public static void stopAudio(String name) {
        Clip clip = audioClips.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Stop all audio clips
    public static void stopAll() {
        for (Clip clip : audioClips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
}
