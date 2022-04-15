package me.frogdog.test;

import me.frogdog.core.WindowManager;
import org.lwjgl.Version;

public class Launcher {

    public static void main(String[] args) {
        WindowManager window = new WindowManager("Frog Engine", 0, 0, false);
        window.init();

        while (!window.windowShouldClose()) {
            window.update();
        }

        window.cleanup();
    }
}
