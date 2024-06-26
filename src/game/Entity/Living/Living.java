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
    public int gravitySpeed = 5;
    public int offsetX, offsetY;
    public int walkSpeed = 5;
    public int defaultHeight = 50;
    public boolean isJumping, left, right;
    public abstract void draw(Graphics g, Player p);
    public void gravity(){
        updateIndex();
        if(blockMiddle != null && blockMiddle.hitTop || blockRight != null && blockRight.hitTop){
            if(blockMiddle != null){
                offsetY = blockMiddle.point.y - (height + point.y);
            }
            else{
                offsetY = blockRight.point.y - (height + point.y);
            }
        }
        else{
            offsetY += gravitySpeed;
        }
    }
    public void updateIndex(){
        blockRight = gm.getBlock((((point.x - offsetX) / gm.map.tileSize) * gm.map.tileSize) + gm.map.tileSize,(((point.y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + height);
        blockMiddle = gm.getBlock((((point.x - offsetX) / gm.map.tileSize) * gm.map.tileSize),(((point.y + offsetY) / gm.map.tileSize) * gm.map.tileSize) + height);
    }
    public Entity[] getOnlyVisibleBlocks(){
        int positionPlayer = Math.round(((point.x - (float) offsetX)) / gm.map.tileSize) * gm.map.tileSize;
        int positionPlayerY = Math.round(((point.y + (float) offsetY)) / gm.map.tileSize) * gm.map.tileSize;
        int renderHeight = 34;
        int renderWidth = 52;
        Entity[] visibleBlockList = new Entity[(renderHeight * renderWidth)];
        for (int i = 0; i < renderWidth; i++){
            for (int l = 0; l < renderHeight; l++){
                Entity block = gm.blocks.get(new Point(((i - (renderWidth /2)) * gm.map.tileSize) + positionPlayer,((l - (renderHeight /2)) * gm.map.tileSize) + positionPlayerY));
                visibleBlockList[(i * renderHeight) + l] = block;
            }
        }
        return visibleBlockList;
    }
    public boolean checkOverlapX(Entity index, int overlap, boolean right){
        if(right){
            return point.x - offsetX - overlap + 5 <= index.point.x;
        }
        else {
            return point.x - offsetX - overlap >= index.point.x;
        }
    }
    public abstract void walk();
    @Override
    public String getName() {
        return null;
    }
}
