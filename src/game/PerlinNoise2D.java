package game;

import java.util.Random;

public class PerlinNoise2D {
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

    private static double grad(int hash, double x, double y) {
        int h = hash & 15; // Convert low 4 bits of hash code into 12 gradient directions
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : h == 12 || h == 14 ? x : 0;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    public static double perlinNoise(double x, double y, long seed) {
        setSeed(seed);
        int X = (int) Math.floor(x) & 255; // Find unit cube that contains point
        int Y = (int) Math.floor(y) & 255;
        x -= Math.floor(x); // Find relative x, y, z of point in cube
        y -= Math.floor(y);
        double u = fade(x); // Compute fade curves for each of x, y, z
        double v = fade(y);
        int A = permutation[X] + Y; // Hash coordinates of the 8 cube corners
        int B = permutation[X + 1] + Y;

        return lerp(v, lerp(u, grad(permutation[A], x, y), grad(permutation[B], x - 1, y)),
                lerp(u, grad(permutation[A + 1], x, y - 1), grad(permutation[B + 1], x - 1, y - 1)));
    }
}
