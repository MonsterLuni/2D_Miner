package game.Entity;

import game.Entity.Blocks.BLK_INTERACTIVE_CRAFTING_BENCH;
import game.Entity.Items.ITM_PICKAXE_BEDROCK;
import game.Entity.Items.ITM_TORCH;
import game.Entity.Living.Living;
import game.GameManager;
import game.Inventory;
import game.PerlinNoise1D;
import game.Wait;
import listener.KeyListener;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Living {
    public KeyListener kh;
    public Inventory inv;
    public Inventory hotbar;
    public int defaultWidth;
    public int hardness = 1;
    public int miningDamage = 2;
    public int currentHardness = hardness;
    public int currentMiningDamage = miningDamage;
    public boolean isLookingRight = true;
    Wait jumpTimer = new Wait();
    public Player(GameManager gm, KeyListener kh){
        this.kh = kh;
        this.offsetY = (int) (((PerlinNoise1D.perlinNoise(((((double) gm.map.worldWidth /2) + ((double) gm.ui.defaultWidth /2)) * gm.intervalOfSeed),gm.seed)) * 25) + 950) - (gm.ui.defaultHeight /2);
        this.offsetX = -(gm.map.worldWidth/2) + (gm.ui.defaultWidth /2);
        this.gm = gm;
        defaultWidth = gm.map.tileSize;
        hotbar = new Inventory(1,5);
        this.color = Color.blue;
        this.maxHealth = 20;
        this.health = maxHealth;
        this.maxOxygen = 10;
        this.oxygen = maxOxygen;
        width = defaultWidth;
        height = defaultHeight;
        point.x = gm.ui.defaultWidth /2;
        point.y = gm.ui.defaultHeight /2 - defaultHeight;
        hotbar.inventory.put(new Point(0,0),new InventoryItem(new ITM_PICKAXE_BEDROCK(),1));
        hotbar.inventory.put(new Point(1,0),new InventoryItem(new ITM_TORCH(),5));
        inv = new Inventory(10,10);
        inv.inventory.put(new Point(3,4),new InventoryItem(new BLK_INTERACTIVE_CRAFTING_BENCH(gm),1));
        switchHotbar(hotbar.inventorySpaceX);
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
        int spriteY = point.y;
        int spriteX = point.x;
        try{
            Entity hotbarElement = hotbar.getEntryFromCoordinates(hotbar.inventorySpaceX,0).getValue().entity;
            if(hotbarElement != null){
                if (isLookingRight) {
                    spriteSelected = gm.ah.getPictureForID(hotbarElement.id);
                    if (gm.ml.leftButtonPressed) {
                        g2d.rotate(Math.toRadians(35), (double) gm.ui.defaultWidth / 2, (double) gm.ui.defaultHeight / 2);
                    } else {
                        g2d.rotate(Math.toRadians(25), (double) gm.ui.defaultWidth / 2, (double) gm.ui.defaultHeight / 2);
                    }
                } else {
                    spriteSelected = flipHorizontal(gm.ah.getPictureForID(hotbarElement.id));
                    spriteY += 10;
                    if (gm.ml.leftButtonPressed) {
                        g2d.rotate(Math.toRadians(-35), (double) gm.ui.defaultWidth / 2, (double) gm.ui.defaultHeight / 2);
                    } else {
                        g2d.rotate(Math.toRadians(-25), (double) gm.ui.defaultWidth / 2, (double) gm.ui.defaultHeight / 2);
                    }
                }
                g2d.drawImage(spriteSelected,spriteX,spriteY,hotbarElement.width,hotbarElement.height,null);
            }
            g2d.setTransform(old);
        }catch (NullPointerException ignored){}
    }
    public void switchHotbar(int i){
        try{
            Entity hotbarElement = hotbar.getEntryFromCoordinates(i,0).getValue().entity;
            currentMiningDamage = hotbarElement.miningDamage;
            currentHardness = hotbarElement.hardness;
        }catch (NullPointerException e){
            currentMiningDamage = miningDamage;
            currentHardness = hardness;
        }
    }
    public void jump(){
        if(isJumping){
            if(jumpTimer.stopAfterFrames(10)){
                Entity index = gm.getBlock((((point.x - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((point.y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - 50));
                if(index != null && index.hitBottom){
                    if(point.y - offsetY + 1 + height <= index.point.y){
                        if(height == defaultHeight){
                            offsetY = gm.getBlock((((point.x - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((point.y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize)).point.y - point.y - 6;
                        }else {
                            offsetY = gm.getBlock((((point.x - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((point.y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize)).point.y - point.y - 5;
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
    }
    public void walk(){
        gm.map.updateHitBoxes();
        Entity index = gm.getBlock((((gm.p.point.x - gm.p.offsetX) / gm.map.tileSize) * gm.map.tileSize),(((gm.p.point.y + gm.p.offsetY) / gm.map.tileSize) * gm.map.tileSize));
        if(left){
            Entity index2 = gm.getBlock((((point.x - offsetX) / gm.map.tileSize) * gm.map.tileSize) - gm.map.tileSize,(((point.y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize));
            if(index2 != null && index2.hitRight){
                if(checkOverlapX(index,1,false)){
                    offsetX += walkSpeed;
                }
            }
            else {
                offsetX += walkSpeed;
            }
        } else if (right) {
            Entity index2 = gm.getBlock((((point.x - offsetX) / gm.map.tileSize) * gm.map.tileSize) + gm.map.tileSize,(((point.y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize));
            if(index != null && index2.hitLeft){
                if(checkOverlapX(index,0,true)){
                    offsetX -= walkSpeed;
                }
            }
            else {
                offsetX -= walkSpeed;
            }
        }
    }
    public void draw(Graphics g, Player p){
        g.setColor(color);
        if(height < defaultHeight){
            g.drawImage(gm.ah.getPictureForID(-2),point.x,point.y,null);
        }
        else{
            g.drawImage(gm.ah.getPictureForID(-1),point.x,point.y,null);
        }
    }
    @Override
    public String getName() {
        return "Player";
    }
    @Override
    public void gravity(){
        updateIndex();
        if(blockMiddle != null && blockMiddle.hitTop || blockRight != null && blockRight.hitTop){
            if(blockMiddle != null){
                offsetY = blockMiddle.point.y - (height + point.y);
            }
            else{
                offsetY = blockRight.point.y - (height + point.y);
            }
            jumpTimer = new Wait();
        }
        else{
            offsetY += gravitySpeed;
        }
    }
}