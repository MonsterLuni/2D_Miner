package listener;

import game.Inventory;
import game.UI;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    public UI ui;
    public Inventory primaryInv;
    public Inventory secondaryInv;
    public Inventory[] choosableInventories = new Inventory[4];
    public int chosenOne = 0;
    public KeyListener(UI ui){
        this.ui = ui;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (ui.currentState){
            case UI.menuState -> menuState(e.getKeyCode());
            case UI.gameState -> gameState(e.getKeyCode());
            case UI.inventoryState -> inventoryState(e.getKeyCode());
            case UI.interactState -> interactStateSelector(e.getKeyCode());
        }
    }
    private void menuState(int e) {
        switch (e){
            case KeyEvent.VK_UP -> {
                if(ui.menuSelected - 1 != -1){
                    ui.menuSelected--;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if(ui.menuSelected + 1 != 4){
                    ui.menuSelected++;
                }
            }
            case KeyEvent.VK_SPACE -> menuActivate();
        }
    }
    private void menuActivate() {
        switch (ui.menuSelected){
            case 0 -> ui.startGame();
            case 3 -> ui.running = false;
        }
    }
    private void interactStateSelector(int keyCode) {
        switch(ui.currentInteractState){
            case UI.furnaceInteractState -> interactStateFurnace(keyCode);
        }
    }
    private void interactStateFurnace(int e) {
        primaryInv = ui.p.inv;
        choosableInventories[0] = ui.interactStateFurnace.invTop;
        choosableInventories[1] = ui.interactStateFurnace.invFuel;
        choosableInventories[2] = ui.interactStateFurnace.invOutput;
        choosableInventories[3] = ui.p.hotbar;
        inventoryToInventory(e);
    }
    private void inventoryState(int e) {
        primaryInv = ui.p.inv;
        secondaryInv = ui.p.hotbar;
        inventoryToInventory(e);
    }
    private void inventoryToInventory(int e){
        switch (e){
            case KeyEvent.VK_E -> {
                ui.currentState = UI.gameState;
            }
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
            case KeyEvent.VK_SPACE -> selectInventoryPlace(primaryInv);
            case KeyEvent.VK_ENTER -> selectInventoryPlace(secondaryInv);
            case KeyEvent.VK_F -> primaryInv.swapInventories(secondaryInv);
        }
    }
    private void changeSecondaryInventory() {
        secondaryInv = choosableInventories[chosenOne % choosableInventories.length];
        chosenOne++;
    }
    private void resetActiveInventorySpace(Inventory inv){
        inv.activeInventorySpace = new Point(-1,-1);
        inv.activeInventorySpaceTwo = new Point(-1,-1);
    }
    private void changeInventoryPlace(Inventory inv, int x, int y) {
        if(inv.inventorySpaceX + x >= 0 && inv.inventorySpaceX + x <= (inv.width/25) - 1){
            inv.inventorySpaceX += x;
        }
        if(inv.inventorySpaceY + y >= 0 && inv.inventorySpaceY + y <= (inv.height/25) - 1){
            inv.inventorySpaceY += y;
        }
        ui.p.switchHotbar(ui.p.hotbar.inventorySpaceX);
    }
    private void gameState(int e) {
        primaryInv = ui.p.inv;
        secondaryInv = ui.p.hotbar;
        switch (e){
            case KeyEvent.VK_RIGHT -> {
                if(ui.p.hotbar.inventorySpaceX + 1 <= ui.p.hotbar.maxSize - 1){
                    ui.p.hotbar.inventorySpaceX += 1;
                    ui.p.switchHotbar(ui.p.hotbar.inventorySpaceX);
                }
            }
            case KeyEvent.VK_LEFT -> {
                if(ui.p.hotbar.inventorySpaceX - 1 >= 0){
                    ui.p.hotbar.inventorySpaceX -= 1;
                    ui.p.switchHotbar(ui.p.hotbar.inventorySpaceX);
                }
            }
            case KeyEvent.VK_F2 -> {
                if(ui.debug){
                    ui.map.vertices = !ui.map.vertices;
                }
            }
            case KeyEvent.VK_F6 -> {
                if(ui.debug){
                    ui.p.health -= 1;
                }
            }
            case KeyEvent.VK_F5 -> {
                if(ui.debug){
                    ui.map.specificBlockShown = !ui.map.specificBlockShown;
                }
            }
            case KeyEvent.VK_A -> {
                ui.p.left = true;
                ui.p.lookDirection = false;
            }
            case KeyEvent.VK_S -> {
                if(ui.p.height == ui.p.defaultHeight) {
                    ui.p.Y += 25;
                    ui.p.height = 25;
                }
            }
            case KeyEvent.VK_F8 -> ui.saveGame(1);
            case KeyEvent.VK_F9 -> ui.loadGame(1);
            case KeyEvent.VK_D -> {
                ui.p.right = true;
                ui.p.lookDirection = true;
            }
            case KeyEvent.VK_E -> ui.currentState = UI.inventoryState;
            case KeyEvent.VK_F3 -> ui.debug = !ui.debug;
            case KeyEvent.VK_F11 -> ui.toggleFullscreen();
            case KeyEvent.VK_SPACE -> ui.p.jumping = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (ui.currentState){
            case UI.gameState -> gameStateReleased(e.getKeyCode());
            case UI.inventoryState -> System.out.println("Kommt noch");
        }
    }
    private void gameStateReleased(int e) {
        switch (e){
            case KeyEvent.VK_A -> ui.p.left = false;
            case KeyEvent.VK_D -> ui.p.right = false;
            case KeyEvent.VK_SPACE -> ui.p.jumping = false;
            case KeyEvent.VK_S -> {
                ui.p.Y -= 25;
                ui.p.height = ui.p.defaultHeight;
            }
        }
    }
    private void selectInventoryPlace(Inventory inv) {
        if(inv.activeInventorySpace.x == -1){
            inv.activeInventorySpace = new Point(inv.inventorySpaceX,inv.inventorySpaceY);
        }
        else{
            inv.activeInventorySpaceTwo = new Point(inv.inventorySpaceX,inv.inventorySpaceY);
            inv.changeItemInInventory();
            resetActiveInventorySpace(inv);
        }
    }
}
