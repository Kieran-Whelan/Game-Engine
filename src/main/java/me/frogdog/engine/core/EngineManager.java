package me.frogdog.engine.core;

import me.frogdog.engine.core.audio.AudioManager;
import me.frogdog.engine.core.input.Keyboard;
import me.frogdog.engine.core.input.Mouse;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.game.Main;
import me.frogdog.engine.utils.interfaces.ILoigc;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class EngineManager {

    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    private static int fps;
    private static float frametime = 1.0f / FRAMERATE;

    private boolean isRunning;

    private WindowManager window;
    private GLFWErrorCallback errorCallback;
    private ILoigc gameLogic;
    private Mouse mouse;
    private Keyboard keyboard;
    private AudioManager audio;

    public void init() throws Exception {
        GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        window = Main.getWindow();
        gameLogic = Main.getGame();
        mouse = new Mouse();
        keyboard = new Keyboard();
        audio = new AudioManager();
        window.init();
        audio.init();
        gameLogic.init();
        mouse.init();
        keyboard.init();
    }

    public void start() throws Exception {
        init();
        if (isRunning) {
            return;
        }
        run();
    }

    public void run() {
        this.isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();

            while (unprocessedTime > frametime) {
                render = true;
                unprocessedTime -= frametime;

                if (window.windowShouldClose()) {
                    stop();
                }

                if (frameCounter >= NANOSECOND) {
                    setFps(frames);
                    window.setTitle(Consts.TITLE + " FPS: " + getFps());
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if (render) {
                update(frametime);
                render();
                frames++;
            }

        }
        cleanup();
    }

    private void stop() {
        if (!isRunning) {
            return;
        }

        isRunning = false;
    }

    private void input() {
        mouse.input();
        gameLogic.input();
    }

    private void render() {
        gameLogic.render();
        window.update();
    }

    private void update(float interval) {
        gameLogic.update(interval, mouse);
    }

    private void cleanup() {
        window.cleanup();
        audio.cleanup();
        gameLogic.cleanup();
        errorCallback.free();
        GLFW.glfwTerminate();
    }

    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        EngineManager.fps = fps;
    }
}
