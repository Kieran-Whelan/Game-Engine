package me.frogdog.engine.core.input.keyboard;

import me.frogdog.engine.game.Main;
import org.lwjgl.glfw.GLFW;

public class Keyboard {

    private static int lastKeyDown;

    public Keyboard() {}

    public void init() {
        GLFW.glfwSetKeyCallback(Main.getWindow().getWindow(), (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
            if (action == GLFW.GLFW_RELEASE) {
                lastKeyDown = key;
            }
        });
    }

    public boolean isKeyDown(int keycode) {
        return GLFW.glfwGetKey(Main.getWindow().getWindow(), keycode) == GLFW.GLFW_PRESS;
    }

    public boolean isKeyReleased(int keycode) {
        return GLFW.glfwGetKey(Main.getWindow().getWindow(), keycode) == GLFW.GLFW_RELEASE;
    }

    public boolean isKeyPressed(int keycode) {
        if (keycode == lastKeyDown) {
            lastKeyDown = 0;
            return true;
        }
        lastKeyDown = 0;
        return false;
    }

}
