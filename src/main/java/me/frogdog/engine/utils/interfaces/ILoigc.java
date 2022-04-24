package me.frogdog.engine.utils.interfaces;

import me.frogdog.engine.core.input.Mouse;

public interface ILoigc {

    void init() throws Exception;

    void input();

    void update(float interval, Mouse mouseManager);

    void render();

    void cleanup();
}
