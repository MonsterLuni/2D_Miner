package game.Entity.Blocks;

import game.Entity.Entity;
import game.Inventory;
import game.UI;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BLK_INTERACTIVE_FURNACE extends Entity {
    public Inventory invTop = new Inventory(1,1);
    public Inventory invFuel = new Inventory(1,1);
    public BLK_INTERACTIVE_FURNACE(){
        this.height = 25;
        this.id = 7;
        this.width = 25;
        this.interactive = true;
        this.breakable = true;
        this.deactivateHitBox = false;
        this.hardness = 3;
        this.health = 50;
        this.stackSize = 1;
        invFuel.maxSize = 1;
        invTop.maxSize = 1;
        try {
            this.sprite = ImageIO.read(new File("assets/tiles/furnace.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String getName() {
        return "furnace";
    }
    @Override
    public void interact(UI ui) {
        ui.currentState = UI.interactState;
        ui.currentInteractState = UI.furnaceInteractState;
        ui.interactStateInventory = this.invTop;
        ui.interactStateInventory2 = this.invFuel;
    }
}
