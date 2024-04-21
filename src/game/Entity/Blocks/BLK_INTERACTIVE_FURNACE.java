package game.Entity.Blocks;

import game.Entity.Entity;
import game.GameManager;
import game.Inventory;
import game.Wait;

public class BLK_INTERACTIVE_FURNACE extends Entity {
    GameManager gm;
    @Override
    public void interact(GameManager gm) {
        this.gm = gm;
        gm.interactStateFurnace = this;
        gm.currentState = GameManager.interactState;
        gm.currentInteractState = GameManager.furnaceInteractState;
    }
    public Inventory invTop;
    public Inventory invFuel;
    public Inventory invOutput;
    Wait oven = new Wait();
    public BLK_INTERACTIVE_FURNACE(GameManager gm){
        this.height = 25;
        this.id = 7;
        this.width = 25;
        this.interactive = true;
        this.breakable = true;
        this.penetrable = false;
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
    public void checkFurnace() {
        if(invTop.getKeyFromCoordinates(0,0) != null && invFuel.getKeyFromCoordinates(0,0) != null) {
            if (invTop.getKeyFromCoordinates(0, 0).smeltable && invFuel.getKeyFromCoordinates(0, 0).fuel) {
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
            if(recipe[0] == invTop.getKeyFromCoordinates(0, 0).id){
                if(invTop.inventory.get(invTop.getKeyFromCoordinates(0,0)) - 1 > 0){
                    invTop.inventory.replace(invTop.getKeyFromCoordinates(0,0), invTop.inventory.get(invTop.getKeyFromCoordinates(0,0)) - 1);
                }
                else{
                    invTop.inventory.remove(invTop.getKeyFromCoordinates(0, 0));
                }
                invOutput.inventory.put(gm.map.getNewBlockFromID(recipe[1]),2);
            }
        }
    }
}
