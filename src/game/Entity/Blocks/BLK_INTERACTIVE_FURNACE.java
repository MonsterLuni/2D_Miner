package game.Entity.Blocks;

import game.Entity.Entity;
import game.Inventory;
import game.UI;

public class BLK_INTERACTIVE_FURNACE extends Entity {
    UI ui;
    @Override
    public void interact(UI ui) {
        this.ui = ui;
        ui.interactStateFurnace = this;
        ui.currentState = UI.interactState;
        ui.currentInteractState = UI.furnaceInteractState;
    }
    public Inventory invTop;
    public Inventory invFuel;
    public Inventory invOutput;
    public BLK_INTERACTIVE_FURNACE(UI ui){
        this.height = 25;
        this.id = 7;
        this.width = 25;
        this.interactive = true;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 3;
        this.health = 50;
        this.stackSize = 1;
        this.name = "furnace";
        this.ui = ui;
        invTop = new Inventory(1,1);
        invFuel = new Inventory(1,1);
        invOutput = new Inventory(1,1);
        invTop.maxSize = 1;
    }
    public void checkFurnace() {
        if(invTop.getKeyFromCoordinates(0,0) != null && invFuel.getKeyFromCoordinates(0,0) != null) {
            if (invTop.getKeyFromCoordinates(0, 0).smeltable && invFuel.getKeyFromCoordinates(0, 0).fuel) {
                System.out.println("AM SCHMELZEN");
                checkRecipe();
            }
        }
    }
    private void checkRecipe() {
        Integer[][] recipes = {{6,9}};
        for (Integer[] recipe : recipes){
            if(recipe[0] == invTop.getKeyFromCoordinates(0, 0).id){
                invTop.inventory.remove(invTop.getKeyFromCoordinates(0, 0));
                invOutput.inventory.put(ui.map.getNewBlockFromID(recipe[1]),2);
            }
        }
    }
}
