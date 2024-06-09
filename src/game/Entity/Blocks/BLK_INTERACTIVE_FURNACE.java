package game.Entity.Blocks;

import game.Entity.Entity;
import game.Entity.InventoryItem;
import game.GameManager;
import game.Inventory;
import game.Wait;

import java.awt.*;

public class BLK_INTERACTIVE_FURNACE extends Entity {
    GameManager gm;
    public Inventory invTop;
    public Inventory invFuel;
    public Inventory invOutput;
    Wait oven = new Wait();
    public BLK_INTERACTIVE_FURNACE(GameManager gm){
        this.height = 25;
        this.id = 7;
        this.width = 25;
        this.isInteractive = true;
        this.isBreakable = true;
        this.isPenetrable = false;
        this.hardness = 3;
        this.health = 50;
        this.stackSize = 1;
        this.name = "furnace";
        this.gm = gm;
        invTop = new Inventory(1,1);
        invFuel = new Inventory(1,1);
        invOutput = new Inventory(1,1);
        invTop.maxSize = 1;
    }
    @Override
    public void interact(GameManager gm) {
        this.gm = gm;
        gm.interactStateFurnace = this;
        gm.currentState = GameManager.interactState;
        gm.currentInteractState = GameManager.furnaceInteractState;
    }
    public void checkFurnace() {
        if(invTop.getEntryFromCoordinates(0,0) != null && invFuel.getEntryFromCoordinates(0,0) != null) {
            if (invTop.getEntryFromCoordinates(0, 0).getValue().entity.isSmeltable && invFuel.getEntryFromCoordinates(0, 0).getValue().entity.isFuel) {
                System.out.println("AM SCHMELZEN");
                if(oven.waitForFrames(60)){
                    checkRecipe();
                }
            }
        }
    }
    private void checkRecipe() {
        Integer[][] recipes = {{6,9}};
        for (Integer[] recipe : recipes){
            if(recipe[0] == invTop.getEntryFromCoordinates(0, 0).getValue().entity.id){
                if(invTop.getEntryFromCoordinates(0,0).getValue().amount - 1 > 0){
                    invTop.inventory.replace(invTop.getEntryFromCoordinates(0,0).getKey(), new InventoryItem(invTop.getEntryFromCoordinates(0,0).getValue().entity,invTop.getEntryFromCoordinates(0,0).getValue().amount - 1));
                }
                else{
                    invTop.inventory.remove(invTop.getEntryFromCoordinates(0, 0).getKey());
                }
                invOutput.inventory.put(new Point(0,0),new InventoryItem(gm.ah.getNewBlockFromID(recipe[1],gm),2));
            }
        }
    }
}
