package game;

import java.util.Random;

public class PerlinNoise1D {
    private static final int PERMUTATION_SIZE = 256;
    private static final int[] permutation = new int[PERMUTATION_SIZE * 2];

    public static void setSeed(long seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < PERMUTATION_SIZE; i++) {
            permutation[i] = permutation[i + PERMUTATION_SIZE] = rand.nextInt(PERMUTATION_SIZE);
        }
    }


    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private static double grad(int hash, double x) {
        int h = hash & 15;
        double grad = 1.0 + (h & 7); // Gradient value from 1 to 8
        if ((h & 8) != 0) grad = -grad; // Randomly negate gradient
        return (grad * x);
    }

    public static double perlinNoise(double x, long seed) {
        setSeed(seed);
        int X = (int) Math.floor(x) & 255;
        x -= Math.floor(x);
        double u = fade(x);
        return lerp(u, grad(permutation[X], x), grad(permutation[X + 1], x - 1)) * 2 + 2;
    }
}

