package listener;

import game.GameManager;
import game.Inventory;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyListener implements java.awt.event.KeyListener {
    public GameManager gm;
    public Inventory primaryInv;
    public Inventory secondaryInv;
    public Inventory[] choosableInventories;
    public int chosenOne = 0;
    public KeyListener(GameManager gm){
        this.gm = gm;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (gm.currentState){
            case GameManager.menuState -> menuState(e.getKeyCode());
            case GameManager.gameState -> gameState(e.getKeyCode());
            case GameManager.inventoryState -> inventoryState(e.getKeyCode());
            case GameManager.interactState -> interactStateSelector(e.getKeyCode());
        }
    }
    private void menuState(int e) {
        switch (e){
            case KeyEvent.VK_UP -> {
                if(gm.menuSelected - 1 != -1){
                    gm.menuSelected--;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if(gm.menuSelected + 1 != 4){
                    gm.menuSelected++;
                }
            }
            case KeyEvent.VK_SPACE -> menuActivate();
        }
    }
    private void menuActivate() {
        switch (gm.menuSelected){
            case 0 -> gm.startGame();
            case 3 -> gm.isRunning = false;
        }
    }
    private void interactStateSelector(int keyCode) {
        switch(gm.currentInteractState){
            case GameManager.furnaceInteractState -> interactStateFurnace(keyCode);
            case GameManager.craftingTableInteractState -> interactStateCraftingTable(keyCode);
        }
    }
    private void interactStateCraftingTable(int e) {
        choosableInventories = new Inventory[3];
        primaryInv = gm.p.inv;
        choosableInventories[0] = gm.interactStateCraftingTable.inventory;
        choosableInventories[1] = gm.interactStateCraftingTable.output;
        choosableInventories[2] = gm.p.hotbar;
        inventoryToInventory(e);
    }
    private void interactStateFurnace(int e) {
        choosableInventories = new Inventory[4];
        primaryInv = gm.p.inv;
        choosableInventories[0] = gm.interactStateFurnace.invTop;
        choosableInventories[1] = gm.interactStateFurnace.invFuel;
        choosableInventories[2] = gm.interactStateFurnace.invOutput;
        choosableInventories[3] = gm.p.hotbar;
        inventoryToInventory(e);
    }
    private void inventoryState(int e) {
        primaryInv = gm.p.inv;
        secondaryInv = gm.p.hotbar;
        inventoryToInventory(e);
    }
    private void inventoryToInventory(int e){
        switch (e){
            case KeyEvent.VK_E -> gm.currentState = GameManager.gameState;
            // --------------
            case KeyEvent.VK_W -> changeInventoryPlace(primaryInv,0,-1);
            case KeyEvent.VK_A -> changeInventoryPlace(primaryInv,-1,0);
            case KeyEvent.VK_S -> changeInventoryPlace(primaryInv,0,1);
            case KeyEvent.VK_D -> changeInventoryPlace(primaryInv,1,0);
            // --------------
            case KeyEvent.VK_UP -> changeInventoryPlace(secondaryInv,0,-1);
            case KeyEvent.VK_LEFT -> changeInventoryPlace(secondaryInv,-1,0);
            case KeyEvent.VK_DOWN -> changeInventoryPlace(secondaryInv,0,1);
            case KeyEvent.VK_RIGHT -> changeInventoryPlace(secondaryInv,1,0);
            // --------------
            case KeyEvent.VK_TAB -> changeSecondaryInventory();
            case KeyEvent.VK_SPACE -> selectInventoryPlace(primaryInv,true);
            case KeyEvent.VK_ENTER -> selectInventoryPlace(secondaryInv,true);
            case KeyEvent.VK_SHIFT -> selectInventoryPlace(primaryInv,false);
            case KeyEvent.VK_F -> primaryInv.swapInventories(secondaryInv);
        }
    }
    private void changeSecondaryInventory() {
        secondaryInv = choosableInventories[chosenOne % choosableInventories.length];
        chosenOne++;
    }
    private void resetActiveInventorySpace(Inventory inv){
        inv.activeInventorySpace.x = -1;
        inv.activeInventorySpace.y = -1;
        inv.activeInventorySpaceTwo.x = -1;
        inv.activeInventorySpaceTwo.y = -1;
    }
    private void changeInventoryPlace(Inventory inv, int x, int y) {
        if(inv.inventorySpaceX + x >= 0 && inv.inventorySpaceX + x <= (inv.width/25) - 1){
            inv.inventorySpaceX += x;
        }
        if(inv.inventorySpaceY + y >= 0 && inv.inventorySpaceY + y <= (inv.height/25) - 1){
            inv.inventorySpaceY += y;
        }
        gm.p.switchHotbar(gm.p.hotbar.inventorySpaceX);
    }
    private void gameState(int e) {
        primaryInv = gm.p.inv;
        secondaryInv = gm.p.hotbar;
        switch (e){
            case KeyEvent.VK_RIGHT -> {
                if(gm.p.hotbar.inventorySpaceX + 1 <= gm.p.hotbar.maxSize - 1){
                    gm.p.hotbar.inventorySpaceX += 1;
                    gm.p.switchHotbar(gm.p.hotbar.inventorySpaceX);
                }
            }
            case KeyEvent.VK_LEFT -> {
                if(gm.p.hotbar.inventorySpaceX - 1 >= 0){
                    gm.p.hotbar.inventorySpaceX -= 1;
                    gm.p.switchHotbar(gm.p.hotbar.inventorySpaceX);
                }
            }
            case KeyEvent.VK_F2 -> {
                if(gm.isDebug){
                    gm.map.vertices = !gm.map.vertices;
                }
            }
            case KeyEvent.VK_F6 -> {
                if(gm.isDebug){
                    gm.p.health -= 1;
                }
            }
            case KeyEvent.VK_F5 -> {
                if(gm.isDebug){
                    gm.map.specificBlockShown = !gm.map.specificBlockShown;
                }
            }
            case KeyEvent.VK_A -> {
                gm.p.left = true;
                gm.p.isLookingRight = false;
            }
            case KeyEvent.VK_S -> {
                if(gm.p.height == gm.p.defaultHeight) {
                    gm.p.Y += 25;
                    gm.p.height = 25;
                }
            }
            case KeyEvent.VK_F8 -> gm.saveGame(1);
            case KeyEvent.VK_F9 -> gm.loadGame(1);
            case KeyEvent.VK_D -> {
                gm.p.right = true;
                gm.p.isLookingRight = true;
            }
            case KeyEvent.VK_E -> gm.currentState = GameManager.inventoryState;
            case KeyEvent.VK_F3 -> gm.isDebug = !gm.isDebug;
            case KeyEvent.VK_F11 -> gm.ui.toggleFullscreen();
            case KeyEvent.VK_SPACE -> gm.p.isJumping = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (gm.currentState){
            case GameManager.gameState -> gameStateReleased(e.getKeyCode());
            //case GameManager.inventoryState -> System.out.println("Kommt noch");
        }
    }
    private void gameStateReleased(int e) {
        switch (e){
            case KeyEvent.VK_A -> gm.p.left = false;
            case KeyEvent.VK_D -> gm.p.right = false;
            case KeyEvent.VK_SPACE -> gm.p.isJumping = false;
            case KeyEvent.VK_S -> {
                gm.p.Y -= 25;
                gm.p.height = gm.p.defaultHeight;
            }
        }
    }
    private void selectInventoryPlace(Inventory inv,boolean swap) {
        if(inv.activeInventorySpace.x == -1){
            inv.activeInventorySpace.x = inv.inventorySpaceX;
            inv.activeInventorySpace.y = inv.inventorySpaceY;
        }
        else{
            inv.activeInventorySpaceTwo.x = inv.inventorySpaceX;
            inv.activeInventorySpaceTwo.y = inv.inventorySpaceY;
            if(swap){
                inv.changeItemInInventory();
            }else{
                inv.splitItemInInventory();
            }
            resetActiveInventorySpace(inv);
        }
    }
}
