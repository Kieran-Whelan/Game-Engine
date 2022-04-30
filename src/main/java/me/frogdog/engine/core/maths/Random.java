package me.frogdog.engine.core.maths;

public class Random {

    public int randRange(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public float randRange(float min, float max) {
        return min + (float)(Math.random() * ((max - min) + 1));
    }

    public double randRange(double min, double max) {
        return min + (double)(Math.random() * ((max - min)));
    }
}
