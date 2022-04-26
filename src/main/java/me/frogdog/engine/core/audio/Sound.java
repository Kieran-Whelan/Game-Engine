package me.frogdog.engine.core.audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.libc.LibCStdlib;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Sound {

    private int bufferId;
    private int sourceId;
    private String path;

    private boolean isPlaying;

    public Sound(String path, boolean isLoop) {
        this.path = path;

        MemoryStack.stackPush();
        IntBuffer channelsBuffer = MemoryStack.stackMallocInt(1);
        MemoryStack.stackPush();
        IntBuffer sampleRateBuffer = MemoryStack.stackMallocInt(1);

        ShortBuffer rawAudioBuffer = STBVorbis.stb_vorbis_decode_filename(path, channelsBuffer, sampleRateBuffer);

        if (rawAudioBuffer == null) {
            MemoryStack.stackPop();
            MemoryStack.stackPop();
            return;
        }

        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();

        MemoryStack.stackPop();
        MemoryStack.stackPop();

        int format = -1;
        if (channels == -1) {
            format = AL10.AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL10.AL_FORMAT_STEREO16;
        }

        bufferId = AL10.alGenBuffers();
        AL10.alBufferData(bufferId, format, rawAudioBuffer, sampleRate);

        sourceId = AL10.alGenSources();

        AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId);
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, isLoop ? 1 : 0);
        AL10.alSourcei(sourceId, AL10.AL_POSITION, 0);
        AL10.alSourcef(sourceId, AL10.AL_GAIN, 1.0f);

        LibCStdlib.free(rawAudioBuffer);
    }

    public void delete() {
        AL10.alDeleteSources(sourceId);
        AL10.alDeleteBuffers(bufferId);
    }

    public void play() {
        AL10.alSourcePlay(sourceId);
        /*int state = AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE);
        if (state == AL10.AL_STOPPED) {
            isPlaying = false;
            AL10.alSourcei(sourceId, AL10.AL_POSITION, 0);
        }

        if (!isPlaying) {
            AL10.alSourcePlay(sourceId);
            isPlaying = true;
        } */
    }

    public void stop() {
        if (isPlaying) {
            AL10.alSourceStop(sourceId);
            isPlaying = false;
        }
    }

    public String getPath() {
        return this.path;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }
}
