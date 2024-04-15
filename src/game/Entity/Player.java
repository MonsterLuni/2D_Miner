package game.Entity;

import game.Entity.Blocks.BLK_COAL_ORE;
import game.Entity.Blocks.BLK_INTERACTIVE_FURNACE;
import game.Entity.Blocks.BLK_IRON_ORE;
import game.Entity.Items.ITM_PICKAXE_BEDROCK;
import game.Entity.Items.ITM_PICKAXE_FEATHER;
import game.Entity.Living.Living;
import game.GameManager;
import game.Inventory;
import game.PerlinNoise1D;
import game.UI;
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
    /* true == right, false == left */
    public boolean lookDirection = true;
    public Player(GameManager gm, KeyListener kh){
        this.kh = kh;
        this.offsetY = (int) (((PerlinNoise1D.perlinNoise(((((double) gm.map.worldWidth /2) + ((double) gm.ui.screenWidth /2)) * gm.intervalOfSeed),gm.seed)) * 25) + 950) - (gm.ui.screenHeight/2);
        this.offsetX = -(gm.map.worldWidth/2) + (gm.ui.screenWidth/2);
        this.gm = gm;
        defaultWidth = gm.map.tileSize;
        hotbar = new Inventory(1,5);
        this.color = Color.blue;
        this.maxHealth = 20;
        this.health = maxHealth;
        width = defaultWidth;
        height = defaultHeight;
        X = gm.ui.screenWidth/2;
        Y = gm.ui.screenHeight/2 - defaultHeight;
        hotbar.inventory.put(new ITM_PICKAXE_BEDROCK(),1);
        inv = new Inventory(10,10);
        inv.inventory.put(new ITM_PICKAXE_FEATHER(),1);
        Entity entity = new BLK_COAL_ORE();
        entity.inventoryX = 3;
        inv.inventory.put(entity,1);
        entity = new BLK_IRON_ORE();
        entity.inventoryX = 2;
        inv.inventory.put(entity,1);
        entity = new BLK_INTERACTIVE_FURNACE(gm);
        entity.inventoryX = 1;
        inv.inventory.put(entity,1);
        entity = new BLK_INTERACTIVE_FURNACE(gm);
        entity.inventoryX = 4;
        inv.inventory.put(entity,1);
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
        int spriteY = Y;
        int spriteX = X;
        Entity hotbarElement = hotbar.getKeyFromCoordinates(hotbar.inventorySpaceX,0);
        if(hotbarElement != null){
            if (lookDirection) {
                spriteSelected = gm.map.getPictureForID(hotbarElement.id);
                if (gm.ml.leftButtonPressed) {
                    g2d.rotate(Math.toRadians(35), (double) gm.ui.screenWidth / 2, (double) gm.ui.screenHeight / 2);
                } else {
                    g2d.rotate(Math.toRadians(25), (double) gm.ui.screenWidth / 2, (double) gm.ui.screenHeight / 2);
                }
            } else {
                spriteSelected = flipHorizontal(gm.map.getPictureForID(hotbarElement.id));
                spriteY += 10;
                if (gm.ml.leftButtonPressed) {
                    g2d.rotate(Math.toRadians(-35), (double) gm.ui.screenWidth / 2, (double) gm.ui.screenHeight / 2);
                } else {
                    g2d.rotate(Math.toRadians(-25), (double) gm.ui.screenWidth / 2, (double) gm.ui.screenHeight / 2);
                }
            }
            g2d.drawImage(spriteSelected,spriteX,spriteY,hotbarElement.width,hotbarElement.height,null);
        }
        g2d.setTransform(old);
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
    public void jump(){
        if(jumping){
            Entity index = gm.map.getBlockFromCoordinates((((X - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((Y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - 50));
            if(index != null && index.hitBottom){
                if(Y - offsetY + 1 + height <= index.Y){
                    if(height == defaultHeight){
                        offsetY = gm.map.getBlockFromCoordinates((((X - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((Y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize)).Y - Y - 6;
                    }else {
                        offsetY = gm.map.getBlockFromCoordinates((((X - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((Y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize)).Y - Y - 5;
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
    public void walk(){
        gm.map.updateHitBoxes();
        Entity index = gm.map.getBlockFromCoordinates((((X - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((Y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize));
        if(left){
            Entity index2 = gm.map.getBlockFromCoordinates((((X - offsetX) / gm.map.tileSize) * gm.map.tileSize) - gm.map.tileSize,(((Y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize));
            if(index2 != null && index2.hitRight){
                if(checkOverlapX(index,1,false)){
                    offsetX += walkSpeed;
                }
            }
            else {
                offsetX += walkSpeed;
            }
        } else if (right) {
            Entity index2 = gm.map.getBlockFromCoordinates((((X - offsetX) / gm.map.tileSize) * gm.map.tileSize) + gm.map.tileSize,(((Y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + (height - gm.map.tileSize));
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
        g.fillRect(X,Y,width,height);
    }
    @Override
    public String getName() {
        return "Player";
    }
}