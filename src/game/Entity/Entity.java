package game.Entity;

import java.awt.*;

public abstract class Entity {
    public int X;
    public int Y;
    public int width;
    public int height;
    public int miningDamage;
    public int hardness;
    public boolean breakable;
    public boolean deactivateHitBox;
    public int health;
    public Image sprite;
    public abstract String getName();
}
