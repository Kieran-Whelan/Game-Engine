package me.frogdog.engine.core.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private Clip clip;
    private AudioInputStream audioInputStream;
    private SoundStatus soundStatus;

    public Sound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        floatControl.setValue(-50.0f);
    }

    public void play() {
        clip.start();
    }

}
