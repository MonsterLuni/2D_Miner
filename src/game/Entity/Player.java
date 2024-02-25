package game.Entity;

import game.Entity.Items.ITM_PICKAXE_BEDROCK;
import game.Entity.Items.ITM_PICKAXE_FEATHER;
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
    public int hotbarSize = 5;
    public int hotbarSelected = 0;
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
    public int inventorySpaceX = 0;
    public int inventorySpaceY = 0;
    public Point activeInventorySpace = new Point(-1,-1);
    public Point activeInventorySpaceTwo = new Point(-1,-1);
    /* true == right, false == left */
    public boolean lookDirection = true;
    public KeyListener kh;
    UI ui;
    public LinkedHashMap<Entity, Integer> inventoryPlus;
    public LinkedHashMap<Entity, Integer> hotbarPlus;
    public Player(UI ui,KeyListener kh){
        this.kh = kh;
        this.ui = ui;
        width = defaultWidth;
        height = defaultHeight;
        X = ui.screenWidth/2;
        Y = ui.screenHeight/2 - defaultHeight;
        hotbarPlus = new LinkedHashMap<>(5);
        Entity pickaxe = new ITM_PICKAXE_BEDROCK();
        pickaxe.hotbarInt = 0;
        hotbarPlus.put(pickaxe,1);
        inventoryPlus = new LinkedHashMap<>((ui.inventoryWidth / 25) * (ui.inventoryHeight / 25));
        inventoryPlus.put(new ITM_PICKAXE_FEATHER(),1);
        switchHotbar(hotbarSelected);
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
        Entity hotbar = getHotbarItem(hotbarSelected);
        try {
            if (lookDirection) {
                spriteSelected = hotbar.sprite;
                if (ui.ml.leftButtonPressed) {
                    g2d.rotate(Math.toRadians(35), (double) ui.screenWidth / 2, (double) ui.screenHeight / 2);
                } else {
                    g2d.rotate(Math.toRadians(25), (double) ui.screenWidth / 2, (double) ui.screenHeight / 2);
                }
            } else {
                spriteSelected = flipHorizontal(hotbar.sprite);
                spriteY += 10;
                if (ui.ml.leftButtonPressed) {
                    g2d.rotate(Math.toRadians(-35), (double) ui.screenWidth / 2, (double) ui.screenHeight / 2);
                } else {
                    g2d.rotate(Math.toRadians(-25), (double) ui.screenWidth / 2, (double) ui.screenHeight / 2);
                }
            }
            g2d.drawImage(spriteSelected,spriteX,spriteY,hotbar.width,hotbar.height,null);
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
    public void sortInventory(Entity current) {
        for (java.util.Map.Entry<Entity, Integer> entry : inventoryPlus.entrySet()) {
            if(Objects.equals(entry.getKey().getName(), current.getName()) && entry.getKey() != current){
                if(entry.getValue() + inventoryPlus.get(current) <= entry.getKey().stackSize){
                    entry.setValue(entry.getValue() + inventoryPlus.get(current));
                    inventoryPlus.remove(current);
                    break;
                }
                else{
                    inventoryPlus.replace(current, (entry.getValue() - entry.getKey().stackSize) + inventoryPlus.get(current));
                    entry.setValue(entry.getKey().stackSize);
                }
            }
        }
    }
    public void updateInventory(Entity current){
        sortInventory(current);
    }
    public Point getFirstFreeInventorySpace(){
        int lastX = 0;
        int lastY = 0;
        Point point = new Point(lastX,lastY);
        for (java.util.Map.Entry<Entity, Integer> entry : inventoryPlus.entrySet()) {
            if(entry.getKey().inventoryX == lastX && entry.getKey().inventoryY == lastY){
                lastX++;
            }
            if(lastX >= 10){
                lastY++;
                lastX = 0;
            }
            point = new Point(lastX,lastY);
        }
        return point;
    }
    public void changeItemInInventory(){
        Entity firstEntry = null;
        Entity secondEntry = null;
        for (java.util.Map.Entry<Entity, Integer> entry : inventoryPlus.entrySet()) {
            if(entry.getKey().inventoryX == activeInventorySpace.x && entry.getKey().inventoryY == activeInventorySpace.y){
                firstEntry = entry.getKey();
            } else if (entry.getKey().inventoryX == activeInventorySpaceTwo.x && entry.getKey().inventoryY == activeInventorySpaceTwo.y) {
                secondEntry = entry.getKey();
            }
        }
        if(firstEntry != null && secondEntry != null){
            int swapX = firstEntry.inventoryX;
            int swapY = firstEntry.inventoryY;
            firstEntry.inventoryX = secondEntry.inventoryX;
            firstEntry.inventoryY = secondEntry.inventoryY;
            secondEntry.inventoryX = swapX;
            secondEntry.inventoryY= swapY;
        }
        else if (firstEntry != null){
            firstEntry.inventoryX = activeInventorySpaceTwo.x;
            firstEntry.inventoryY = activeInventorySpaceTwo.y;
        }
    }
    public void swapInventoryToHotbar(){
        Entity inventory = null;
        Entity hotbar = getHotbarItem(hotbarSelected);
        int hotbarInt = getHotbarValue(hotbarSelected);
        int inventoryInt = 0;
        for (java.util.Map.Entry<Entity, Integer> entry : inventoryPlus.entrySet()) {
            if(entry.getKey().inventoryX == inventorySpaceX && entry.getKey().inventoryY == inventorySpaceY){
                inventory = entry.getKey();
                inventoryInt = entry.getValue();
            }
        }
        if(inventory != null && hotbar != null){
            int invX = inventory.inventoryX;
            int invY = inventory.inventoryY;
            hotbar.inventoryX = invX;
            hotbar.inventoryY = invY;
            inventory.hotbarInt = hotbar.hotbarInt;
            inventoryPlus.put(hotbar,hotbarInt);
            hotbarPlus.put(inventory,inventoryInt);
            hotbarPlus.remove(hotbar);
            inventoryPlus.remove(inventory);
        }
        else if (inventory != null){
            inventory.hotbarInt = hotbarSelected;
            hotbarPlus.put(inventory,inventoryInt);
            inventoryPlus.remove(inventory);
        }
        else if(hotbar != null){
            hotbar.inventoryX = inventorySpaceX;
            hotbar.inventoryY = inventorySpaceY;
            inventoryPlus.put(hotbar,hotbarInt);
            hotbarPlus.remove(hotbar);
        }
        switchHotbar(hotbarSelected);
    }
    public Entity getHotbarItem(int selected){
        Entity entity = null;
        for (java.util.Map.Entry<Entity, Integer> entry : hotbarPlus.entrySet()) {
            if(entry.getKey().hotbarInt == selected){
                entity = entry.getKey();
            }
        }
        return entity;
    }
    public int getHotbarValue(int selected){
        int entity = 0;
        for (java.util.Map.Entry<Entity, Integer> entry : hotbarPlus.entrySet()) {
            if(entry.getKey().hotbarInt == selected){
                entity = entry.getValue();
            }
        }
        return entity;
    }
    public void switchHotbar(int i){
        Entity hotbar = getHotbarItem(i);
        try{
            currentMiningDamage = hotbar.miningDamage;
            currentHardness = hotbar.hardness;
        }catch (NullPointerException e){
            currentMiningDamage = miningDamage;
            currentHardness = hardness;
        }
    }
    @Override
    public String getName() {
        return "Player";
    }
}