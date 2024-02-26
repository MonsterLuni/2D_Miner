package listener;

import game.UI;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {
    public UI ui;
    public boolean aPressed,dPressed, spacePressed;
    public KeyListener(UI ui){
        this.ui = ui;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (ui.currentState){
            case UI.gameState -> gameState(e.getKeyCode());
            case UI.inventoryState -> inventoryState(e.getKeyCode());
        }
    }
    private void inventoryState(int e) {
        switch (e){
            /*case KeyEvent.VK_1 -> {ui.p.hotbarSelected = 0;
                ui.p.switchHotbar(0);
            }
            case KeyEvent.VK_2 -> {ui.p.hotbarSelected = 1;
                ui.p.switchHotbar(1);
            }
            case KeyEvent.VK_3 -> {ui.p.hotbarSelected = 2;
                ui.p.switchHotbar(2);
            }
            case KeyEvent.VK_4 -> {ui.p.hotbarSelected = 3;
                ui.p.switchHotbar(3);
            }
            case KeyEvent.VK_5 -> {ui.p.hotbarSelected = 4;
                ui.p.switchHotbar(4);
            }*/
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
            case KeyEvent.VK_E -> ui.currentState = UI.gameState;
            case KeyEvent.VK_W -> changeInventoryPlace(0,-1);
            case KeyEvent.VK_A -> changeInventoryPlace(-1,0);
            case KeyEvent.VK_S -> changeInventoryPlace(0,1);
            case KeyEvent.VK_D -> changeInventoryPlace(1,0);
            case KeyEvent.VK_SPACE -> selectInventoryPlace();
            case KeyEvent.VK_ESCAPE -> resetActiveInventorySpace();
            case KeyEvent.VK_F -> ui.p.inv.swapInventories(ui.p.hotbar);
        }
    }
    private void selectInventoryPlace() {
        if(ui.p.inv.activeInventorySpace.x == -1){
            ui.p.inv.activeInventorySpace = new Point(ui.p.inv.inventorySpaceX,ui.p.inv.inventorySpaceY);
        }
        else{
            ui.p.inv.activeInventorySpaceTwo = new Point(ui.p.inv.inventorySpaceX,ui.p.inv.inventorySpaceY);
            ui.p.inv.changeItemInInventory();
            resetActiveInventorySpace();
        }
    }
    private void resetActiveInventorySpace(){
        ui.p.inv.activeInventorySpace = new Point(-1,-1);
        ui.p.inv.activeInventorySpaceTwo = new Point(-1,-1);
    }
    private void changeInventoryPlace(int x, int y) {
        if(ui.p.inv.inventorySpaceX + x >= 0 && ui.p.inv.inventorySpaceX + x <= 9){
            ui.p.inv.inventorySpaceX += x;
        }
        if(ui.p.inv.inventorySpaceY + y >= 0 && ui.p.inv.inventorySpaceY + y <= 9){
            ui.p.inv.inventorySpaceY += y;
        }
    }
    private void gameState(int e) {
        switch (e){
            /*case KeyEvent.VK_1 -> {ui.p.hotbarSelected = 0;
                ui.p.switchHotbar(0);
            }
            case KeyEvent.VK_2 -> {ui.p.hotbarSelected = 1;
                ui.p.switchHotbar(1);
            }
            case KeyEvent.VK_3 -> {ui.p.hotbarSelected = 2;
                ui.p.switchHotbar(2);
            }
            case KeyEvent.VK_4 -> {ui.p.hotbarSelected = 3;
                ui.p.switchHotbar(3);
            }
            case KeyEvent.VK_5 -> {ui.p.hotbarSelected = 4;
                ui.p.switchHotbar(4);
            }*/
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
            case KeyEvent.VK_F5 -> {
                if(ui.debug){
                    ui.map.specificBlockShown = !ui.map.specificBlockShown;
                }
            }
            case KeyEvent.VK_A -> {
                aPressed = true;
                ui.p.lookDirection = false;
            }
            case KeyEvent.VK_S -> {
                if(ui.p.height == ui.p.defaultHeight) {
                    ui.p.Y += 25;
                    ui.p.height = 25;
                }
            }
            case KeyEvent.VK_D -> {
                dPressed = true;
                ui.p.lookDirection = true;
            }
            case KeyEvent.VK_E -> ui.currentState = UI.inventoryState;
            case KeyEvent.VK_F3 -> ui.debug = !ui.debug;
            case KeyEvent.VK_F11 -> ui.toggleFullscreen();
            case KeyEvent.VK_SPACE -> spacePressed = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (ui.currentState){
            case UI.gameState -> gameStateReleased(e.getKeyCode());
        }
    }
    private void gameStateReleased(int e) {
        switch (e){
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_D -> dPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
            case KeyEvent.VK_S -> {
                ui.p.Y -= 25;
                ui.p.height = ui.p.defaultHeight;
            }
        }
    }
}
