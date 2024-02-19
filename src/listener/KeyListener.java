package listener;

import game.UI;

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
        switch (e.getKeyCode()){
            case KeyEvent.VK_1 -> {ui.p.hotbarSelected = 0;
                switchHotbar(0);
            }
            case KeyEvent.VK_2 -> {ui.p.hotbarSelected = 1;
                switchHotbar(1);
            }
            case KeyEvent.VK_3 -> {ui.p.hotbarSelected = 2;
                switchHotbar(2);
            }
            case KeyEvent.VK_4 -> {ui.p.hotbarSelected = 3;
                switchHotbar(3);
            }
            case KeyEvent.VK_5 -> {ui.p.hotbarSelected = 4;
                switchHotbar(4);
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
                else {
                    ui.p.sortInventory();
                }
            }
            case KeyEvent.VK_A -> {
                aPressed = true;
                ui.p.lookDirection = false;
            }
            case KeyEvent.VK_E -> {
                if(ui.currentState == ui.gameState){
                    ui.currentState = ui.inventoryState;
                }
                else {
                    ui.currentState = ui.gameState;
                }
            }
            case KeyEvent.VK_D -> {
                dPressed = true;
                ui.p.lookDirection = true;
            }
            case KeyEvent.VK_F3 -> ui.debug = !ui.debug;
            case KeyEvent.VK_F11 -> ui.toggleFullscreen();
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_S -> {
                if(ui.p.height == ui.p.defaultHeight) {
                    ui.p.Y += 25;
                    ui.p.height = 25;
                }
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_D -> dPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
            case KeyEvent.VK_S -> {
                ui.p.Y -= 25;
                ui.p.height = ui.p.defaultHeight;
            }
        }
    }
    public void switchHotbar(int i){
        try{
            ui.p.currentMiningDamage = ui.p.hotbar.get(i).miningDamage;
            ui.p.currentHardness = ui.p.hotbar.get(i).hardness;
        }catch (IndexOutOfBoundsException e){
            ui.p.currentMiningDamage = ui.p.miningDamage;
            ui.p.currentHardness = ui.p.hardness;
        }
    }
}
