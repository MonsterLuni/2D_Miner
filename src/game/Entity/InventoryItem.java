package game.Entity;

public class InventoryItem {
    public Entity entity;
    public int amount = 0;
    public InventoryItem(Entity entity, int amount){
        this.amount = amount;
        this.entity = entity;
    }
}
