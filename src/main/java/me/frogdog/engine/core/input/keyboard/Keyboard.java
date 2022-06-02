package me.frogdog.engine.core.input.keyboard;

import me.frogdog.engine.game.Main;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    private List<Integer> keysDown;

    public Keyboard() {
        keysDown = new ArrayList<>();
    }

    public void init() {
        GLFW.glfwSetKeyCallback(Main.getWindow().getWindow(), (window, key, scancode, action, mods) -> {
            /*
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
             */
            if (action == GLFW.GLFW_RELEASE) {
                keysDown.add(key);
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
        if (keysDown.contains(keycode)) {
            return true;
        }
        return false;
    }

    public void clearKeys() {
        keysDown.clear();
    }

}
