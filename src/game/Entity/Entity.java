package game.Entity;

import game.UI;

import java.awt.*;

public abstract class Entity {
    public int X;
    public int Y;
    public int inventoryX;
    public int inventoryY;
    public boolean interactive;
    public boolean smeltable;
    public boolean fuel;
    public int id;
    public int width;
    public int height;
    public int dropAmount = 1;
    public int miningDamage = 0;
    public int hardness = 0;
    public int stackSize = 64;
    public boolean breakable;
    public boolean deactivateHitBox;
    public int health;
    public Image sprite;
    public abstract String getName();
    public abstract void interact(UI ui);
}
