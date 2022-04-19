package me.frogdog.core;

public interface ILoigc {

    void init() throws Exception;

    void input();

    void update(float interval, MouseManager mouseManager);

    void render();

    void cleanup();
}
