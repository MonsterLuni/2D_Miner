package game.Entity;

import game.UI;
import listener.KeyListener;

import java.awt.*;

public class Player {
    public int X;
    public int Y;
    public int offsetX = 0;
    public int offsetY = 0;
    public int walkSpeed = 5;
    public int jumpSpeed = 10;
    public int gravitySpeed = 5;
    public int defaultHeight = 50;
    public int defaultWidth = 20;
    public int width = defaultWidth;
    public int height = defaultHeight;
    KeyListener kh;
    UI ui;
    public Player(UI ui,KeyListener kh){
        this.kh = kh;
        this.ui = ui;
        X = ui.screenWidth/2;
        Y = 250 - defaultHeight;
    }
    public void drawPlayer(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(X,Y,width,height);
    }
    public void gravity(){
        int index = ui.map.getBlockFromPlayerY((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25));
        if (index == -10 || Y + offsetY + 5 < ui.blocks.get(index).Y - (height + Y) ) {
            offsetY += gravitySpeed;
        }
        else {
            offsetY = ui.blocks.get(ui.map.getBlockFromPlayerY((((X - offsetX) / 25) * 25), (((Y + offsetY) / 25) * 25))).Y - (height + Y);
        }
    }
    public void jump(){
        if(kh.spacePressed){
            int index = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 50));
            int index2 = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 25));
            if(ui.blocks.get(index2).hitBottom){
                if(Y - offsetY + 5 <= ui.blocks.get(index).Y){
                    System.out.println("left");
                }
                else {
                    offsetY -= jumpSpeed;
                }
            }
            else {
                offsetY -= jumpSpeed;
            }
        }
    }
    public boolean checkOverlapX(int index, int overlap, boolean right){
        if(right){
            return X - offsetX - overlap <= ui.blocks.get(index).X;
        }
        else {
            return X - offsetX - overlap >= ui.blocks.get(index).X;
        }

    }
    public void walk(){
        if(kh.aPressed){
            int index = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 25));
            int index2 = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25) - 25,(((Y + offsetY) / 25) * 25) + (height - 25));
            if(ui.blocks.get(index2).hitRight){
                if(checkOverlapX(index,1,false)){
                    offsetX += walkSpeed;
                }
            }
            else {
                offsetX += walkSpeed;
            }
        } else if (kh.dPressed) {
            int index = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 25));
            int index2 = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25) + 25,(((Y + offsetY) / 25) * 25) + (height - 25));
            if(ui.blocks.get(index2).hitLeft){
                if(checkOverlapX(index,0,true)){
                    offsetX -= walkSpeed;
                }
            }
            else {
                offsetX -= walkSpeed;
            }
        }
    }
}
