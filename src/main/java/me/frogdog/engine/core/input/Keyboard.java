package me.frogdog.engine.core.input;

import me.frogdog.engine.game.Main;
import org.lwjgl.glfw.GLFW;

public class Keyboard {

    public Keyboard() {}

    public void init() {
        GLFW.glfwSetKeyCallback(Main.getWindow().getWindow(), (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        });
    }

    public boolean isKeyPressed(int keycode) {
        return GLFW.glfwGetKey(Main.getWindow().getWindow(), keycode) == GLFW.GLFW_PRESS;
    }

    public boolean isKeyReleased(int keycode) {
        return GLFW.glfwGetKey(Main.getWindow().getWindow(), keycode) == GLFW.GLFW_RELEASE;
    }

}
