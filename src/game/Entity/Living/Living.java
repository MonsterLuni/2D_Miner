package game.Entity.Living;

import game.Entity.Entity;
import game.Entity.Player;
import game.UI;

import java.awt.*;

public abstract class Living extends Entity {
    public Color color;
    public UI ui;
    public int health,maxHealth;
    public int IndexBlockRight, IndexBlockMiddle;
    public int jumpSpeed = 10;
    int counterSeconds = 0;
    int counterInterval = 0;
    public int gravitySpeed = 5;
    public int offsetX, offsetY;
    public int walkSpeed = 5;
    public int defaultHeight = 50;
    public boolean jumping, left, right;
    public abstract void draw(Graphics g, Player p);
    public void gravity(){
        updateIndex();
        if(IndexBlockMiddle != -10 || IndexBlockRight != -10){
            if(IndexBlockMiddle != -10){
                offsetY = ui.blocks.get(IndexBlockMiddle).Y - (height + Y);
            }
            else{
                offsetY = ui.blocks.get(IndexBlockRight).Y - (height + Y);
            }
        }
        else{
            offsetY += gravitySpeed;
        }
    }
    public int getBlockFromLiving(int X, int Y){
        for (int i = 0; i < ui.blocks.size(); i++){
            if(getOnlyVisibleBlocks(i)){
                if(ui.blocks.get(i).X == X && ui.blocks.get(i).Y == Y + height){
                    if(ui.blocks.get(i).hitTop){
                        return i;
                    }
                }
            }
        }
        return -10;
    }
    public void updateIndex(){
        IndexBlockRight = ui.p.getBlockFromLiving((((X - offsetX) / 25) * 25) + 25,(((Y + offsetY) / 25) * 25));
        IndexBlockMiddle = ui.p.getBlockFromLiving((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25));
    }
    public boolean getOnlyVisibleBlocks(int index){
        int positionPlayer = Math.round(((X - (float) offsetX) - (30 * 25)) /25);
        int positionPlayerY = Math.round(((Y + (float) offsetY) - (30 * 25)) /25);
        if(ui.blocks.get(index) != null){
            return ui.blocks.get(index).X / 25 >= positionPlayer && ui.blocks.get(index).X / 25 <= positionPlayer + 59 && ui.blocks.get(index).Y / 25 <= positionPlayerY + 54 && ui.blocks.get(index).Y / 25 >= positionPlayerY + 15;
        }
        else{
            return false;
        }
    }
    public boolean checkOverlapX(int index, int overlap, boolean right){
        if(right){
            return X - offsetX - overlap + 5 <= ui.blocks.get(index).X;
        }
        else {
            return X - offsetX - overlap >= ui.blocks.get(index).X;
        }
    }
    public abstract void walk();
    public boolean waitforSeconds(int seconds){
        int sec = seconds*ui.maxFps;
        if(counterSeconds > sec){
            counterSeconds = 0;
            return true;
        }
        counterSeconds++;
        return false;
    }
    public boolean waitForInterval(int seconds){
        int sec = seconds*ui.maxFps;
        if(counterInterval > sec){
            if(counterInterval > sec*2){
                counterInterval = 0;
            }
            counterInterval++;
            return true;
        }
        counterInterval++;
        return false;
    }
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void interact(UI ui) {

    }
}
