package game.Entity;

import game.Entity.Blocks.BLK_INTERACTIVE_FURNACE;
import game.Entity.Items.ITM_PICKAXE_BEDROCK;
import game.Entity.Items.ITM_PICKAXE_FEATHER;
import game.Inventory;
import game.UI;
import listener.KeyListener;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Player extends Entity{
    public int offsetX;
    public int offsetY = 900;
    public int walkSpeed = 5;
    public int jumpSpeed = 10;
    public int gravitySpeed = 5;
    public int defaultHeight = 50;
    public int defaultWidth = 25;
    public int hardness = 1;
    public int miningDamage = 2;
    public int currentHardness = hardness;
    public int currentMiningDamage = miningDamage;
    public int IndexBlockRight, IndexBlockMiddle;
    public Inventory inv = new Inventory();
    public Inventory hotbar = new Inventory();
    /* true == right, false == left */
    public boolean lookDirection = true;
    public KeyListener kh;
    UI ui;
    public Player(UI ui,KeyListener kh){
        this.kh = kh;
        this.ui = ui;
        width = defaultWidth;
        height = defaultHeight;
        X = ui.screenWidth/2;
        Y = ui.screenHeight/2 - defaultHeight;
        hotbar.maxSize = 5;
        hotbar.inventory = new LinkedHashMap<>(5);
        Entity pickaxe = new ITM_PICKAXE_BEDROCK();
        pickaxe.hotbarInt = 0;
        hotbar.inventory.put(pickaxe,1);
        inv.inventory = new LinkedHashMap<>((ui.inventoryWidth / 25) * (ui.inventoryHeight / 25));
        inv.inventory.put(new ITM_PICKAXE_FEATHER(),1);
        inv.inventory.put(new BLK_INTERACTIVE_FURNACE(),1);
        //switchHotbar(hotbarSelected);
    }
    public void drawPlayer(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(X,Y,width,height);
    }
    public static BufferedImage flipHorizontal(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = flippedImage.createGraphics();

        // Create an AffineTransform to flip the image horizontally
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-width, 0);

        // Apply the transformation to the graphics context
        g2d.drawImage(image, transform, null);

        g2d.dispose();
        return flippedImage;
    }
    public void drawSelected(Graphics g){
        Image spriteSelected;
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        int spriteY = Y;
        int spriteX = X;
        Entity hotbarElement = hotbar.getKeyFromCoordinates(hotbar.inventorySpaceX,0);
        try {
            if (lookDirection) {
                spriteSelected = hotbarElement.sprite;
                if (ui.ml.leftButtonPressed) {
                    g2d.rotate(Math.toRadians(35), (double) ui.screenWidth / 2, (double) ui.screenHeight / 2);
                } else {
                    g2d.rotate(Math.toRadians(25), (double) ui.screenWidth / 2, (double) ui.screenHeight / 2);
                }
            } else {
                spriteSelected = flipHorizontal(hotbarElement.sprite);
                spriteY += 10;
                if (ui.ml.leftButtonPressed) {
                    g2d.rotate(Math.toRadians(-35), (double) ui.screenWidth / 2, (double) ui.screenHeight / 2);
                } else {
                    g2d.rotate(Math.toRadians(-25), (double) ui.screenWidth / 2, (double) ui.screenHeight / 2);
                }
            }
            g2d.drawImage(spriteSelected,spriteX,spriteY,hotbarElement.width,hotbarElement.height,null);
        }catch (NullPointerException e){}
        g2d.setTransform(old);
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
    public void switchHotbar(int i){
        Entity hotbarElement = hotbar.getKeyFromCoordinates(i,0);
        try{
            currentMiningDamage = hotbarElement.miningDamage;
            currentHardness = hotbarElement.hardness;
        }catch (NullPointerException e){
            currentMiningDamage = miningDamage;
            currentHardness = hardness;
        }
    }
    @Override
    public String getName() {
        return "Player";
    }
    @Override
    public void interact(UI ui) {

    }
}