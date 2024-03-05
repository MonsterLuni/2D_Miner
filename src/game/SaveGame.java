package game;

import java.io.Serializable;

public class SaveGame implements Serializable {
    public int offsetX;
    public int offsetY;
    public int hardness;
    public int miningDamage;
    public int currentHardness;
    public int currentMiningDamage;
    public int maxHealth;
    public int health;
    public Inventory inv;
    //public Inventory hotbar;
}
