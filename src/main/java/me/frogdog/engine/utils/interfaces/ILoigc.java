package me.frogdog.engine.utils.interfaces;

public interface ILoigc {

    void init() throws Exception;

    void input();

    void update(float interval);

    void render();

    void cleanup();
}
