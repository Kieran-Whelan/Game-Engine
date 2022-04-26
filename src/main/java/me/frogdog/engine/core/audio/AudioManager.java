package me.frogdog.engine.core.audio;

import org.lwjgl.openal.*;

public class AudioManager {

    private long audioContext;
    private long audioDevice;

    public AudioManager() {}

    public void init() {
        String defaultDevice = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = ALC10.alcOpenDevice(defaultDevice);

        int[] attributes = {0};
        audioContext = ALC10.alcCreateContext(audioDevice, attributes);
        ALC10.alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            assert false : "Audio library not supported on this device!";
        }
    }

    public void cleanup() {
        ALC10.alcDestroyContext(audioContext);
        ALC10.alcCloseDevice(audioDevice);
    }
}
