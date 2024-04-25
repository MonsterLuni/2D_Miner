package game.Entity.Blocks;

import game.Entity.Entity;
import game.Entity.InventoryItem;
import game.GameManager;
import game.Inventory;

import java.awt.*;

public class BLK_INTERACTIVE_CRAFTING_BENCH extends Entity {
    GameManager gm;
    public Inventory inventory;
    public Inventory output;
    public BLK_INTERACTIVE_CRAFTING_BENCH(GameManager gm){
        this.height = 25;
        this.id = 17;
        this.width = 25;
        this.isInteractive = true;
        this.isBreakable = true;
        this.isPenetrable = false;
        this.hardness = 3;
        this.health = 50;
        this.stackSize = 1;
        this.name = "furnace";
        this.gm = gm;
        inventory = new Inventory(3,3);
        output = new Inventory(1,1);
    }
    @Override
    public void interact(GameManager gm) {
        this.gm = gm;
        gm.currentState = GameManager.interactState;
        gm.currentInteractState = GameManager.craftingTableInteractState;
        gm.interactStateCraftingTable = this;
    }
    public void checkRecipe() {
        Integer[][] recipes = {{2, 2, 2,
                2, -1, 2,
                2, 2, 2, 7}};
        for (Integer[] recipe : recipes) {
            boolean fault = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.println((i * 3) + j);
                    if (!fault) {
                        if (inventory.getEntryFromCoordinates(i, j) == null && recipe[(i * 3) + j] != -1) {
                            fault = true;
                        } else if (inventory.getEntryFromCoordinates(i, j) != null && recipe[(i * 3) + j] != inventory.getEntryFromCoordinates(i, j).getValue().entity.id) {
                            fault = true;
                        }
                    }
                }
            }
            if (!fault) {
                inventory.inventory.clear();
                output.inventory.put(new Point(0,0),new InventoryItem(gm.map.getNewBlockFromID(recipe[9]), 1));
                break;
            }
        }
    }
}
