package game.Entity.Living;

import game.Entity.Entity;
import game.Entity.Player;
import game.GameManager;

import java.awt.*;

public abstract class Living extends Entity {
    public Color color;
    public GameManager gm;
    public int health,maxHealth;
    public int oxygen, maxOxygen;
    public Entity blockRight, blockMiddle;
    public int jumpSpeed = 10;
    int counterSeconds = 0;
    int counterInterval = 0;
    public int gravitySpeed = 5;
    public int offsetX, offsetY;
    public int walkSpeed = 5;
    public int defaultHeight = 50;
    public boolean jumping, left, right;
    private final int renderHeight = 34;
    private final int renderWidth = 52;
    public abstract void draw(Graphics g, Player p);
    public void gravity(){
        updateIndex();
        if(blockMiddle != null || blockRight != null){
            if(blockMiddle != null){
                offsetY = blockMiddle.Y - (height + Y);
            }
            else{
                offsetY = blockRight.Y - (height + Y);
            }
        }
        else{
            offsetY += gravitySpeed;
        }
    }
    public Entity getBlockFromLiving(int X, int Y){
        Entity block = gm.blocks.get(new Point(X,Y + height));
        if(block != null){
            if(block.hitTop) {
                return block;
            }
        }
        return null;
    }
    public void updateIndex(){
        blockRight = gm.p.getBlockFromLiving((((X - offsetX) / gm.map.tileSize) * gm.map.tileSize) + gm.map.tileSize,(((Y + offsetY) / gm.map.tileSize) * gm.map.tileSize));
        blockMiddle = gm.p.getBlockFromLiving((((X - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((Y + offsetY) / gm.map.tileSize) * gm.map.tileSize));
    }
    public Entity[] getOnlyVisibleBlocks(){
        int positionPlayer = Math.round(((X - (float) offsetX)) / gm.map.tileSize) * gm.map.tileSize;
        int positionPlayerY = Math.round(((Y + (float) offsetY)) / gm.map.tileSize) * gm.map.tileSize;
        Entity[] visibleBlockList = new Entity[(renderHeight*renderWidth)];
        for (int i = 0; i < renderWidth; i++){
            for (int l = 0; l < renderHeight; l++){
                Entity block = gm.blocks.get(new Point(((i - (renderWidth/2)) * gm.map.tileSize) + positionPlayer,((l - (renderHeight/2)) * gm.map.tileSize) + positionPlayerY));
                visibleBlockList[(i * renderHeight) + l] = block;
            }
        }
        return visibleBlockList;
    }
    public boolean checkOverlapX(Entity index, int overlap, boolean right){
        if(right){
            return X - offsetX - overlap + 5 <= index.X;
        }
        else {
            return X - offsetX - overlap >= index.X;
        }
    }
    public abstract void walk();
    public boolean waitforSeconds(int seconds){
        int sec = seconds*gm.maxFps;
        if(counterSeconds > sec){
            counterSeconds = 0;
            return true;
        }
        counterSeconds++;
        return false;
    }
    public boolean waitForInterval(int seconds){
        int sec = seconds*gm.maxFps;
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
    public void interact(GameManager gm) {}
}
