package game.Entity;

import game.UI;
import listener.KeyListener;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity{
    public int offsetX;
    public int offsetY = 900;
    public int walkSpeed = 5;
    public int jumpSpeed = 10;
    public int gravitySpeed = 5;
    public int defaultHeight = 50;
    public int defaultWidth = 25;
    public int miningDamage = 1;
    public int IndexBlockRight, IndexBlockMiddle;
    public KeyListener kh;
    public int grass = 0, dirt = 0, stone = 0, iron_ore = 0;
    public String[] types = {"grass","dirt","stone","iron_ore"};
    UI ui;
    public ArrayList<Entity> inventory;
    public Player(UI ui,KeyListener kh){
        this.kh = kh;
        this.ui = ui;
        width = defaultWidth;
        height = defaultHeight;
        X = ui.screenWidth/2;
        Y = ui.screenHeight/2 - defaultHeight;
        inventory = new ArrayList<>((ui.inventoryWidth/25) * (ui.inventoryHeight/25));
    }
    public void drawPlayer(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(X,Y,width,height);
    }
    public void updateIndex(){
        IndexBlockRight = ui.map.getBlockFromPlayerY((((X - offsetX) / 25) * 25) + 25,(((Y + offsetY) / 25) * 25));
        IndexBlockMiddle = ui.map.getBlockFromPlayerY((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25));
    }
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
    public void jump(){
        if(kh.spacePressed){
            int index = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 50));
            if(ui.blocks.get(index).hitBottom){
                if(Y - offsetY + 1 + height <= ui.blocks.get(index).Y){
                    if(height == defaultHeight){
                        offsetY = ui.blocks.get(ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 25))).Y - Y - 6;
                    }else {
                        offsetY = ui.blocks.get(ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 25))).Y - Y - 5;
                    }
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
            return X - offsetX - overlap + 5 <= ui.blocks.get(index).X;
        }
        else {
            return X - offsetX - overlap >= ui.blocks.get(index).X;
        }
    }
    public void walk(){
        int index = ui.map.getBlockFromCoordinates((((X - offsetX) / 25) * 25),(((Y + offsetY) / 25) * 25) + (height - 25));
        if(kh.aPressed){
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
    public void sortInventory() {
        countInventoryEntities();
        inventory.clear();
        for (String type : types){
            switch (type){
                case "grass" -> {
                    if(grass != 0){
                        inventory.add(new Block(25, 25, ui.map.grass, type));}
                    }
                case "dirt" -> {
                    if(dirt != 0){
                        inventory.add(new Block(25, 25, ui.map.dirt, type));}
                    }
                case "stone" -> {
                    if(stone != 0){
                        inventory.add(new Block(25, 25, ui.map.stone, type));}
                    }
                case "iron_ore" -> {
                    if(iron_ore != 0){
                        inventory.add(new Block(25, 25, ui.map.iron_ore, type));}
                    }
            }

        }
    }
    public void countInventoryEntities(){
        //TODO: Help?
        for (int i = 0; i < ui.p.inventory.size(); i++){
            switch (ui.p.inventory.get(i).getName()){
                case "grass" -> grass++;
                case "dirt" -> dirt++;
                case "stone" -> stone++;
                case "iron_ore" -> iron_ore++;
            }
        }
    }
    @Override
    public String getName() {
        return "Player";
    }
}