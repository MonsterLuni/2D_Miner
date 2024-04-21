package game;

import game.Entity.Blocks.*;
import game.Entity.Entity;
import game.Entity.Items.ITM_IRON_BAR;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class Map {
    public GameManager gm;
    public boolean specificBlockShown;
    boolean first, waterCalculate;
    public boolean vertices;
    public int worldWidth;
    public int worldHeight;
    public int tileSize = 25;
    public int blockNumberFromFirstGround;
    public Map(GameManager gm) {
        this.gm = gm;
        worldWidth = gm.ui.screenWidth*30;
        worldHeight = gm.ui.screenHeight*10;
        gm.blocks = new HashMap<>((worldWidth / tileSize) * (worldHeight / tileSize));
    }
    public void loadMap() {
        for (int i = 0; i < worldWidth / tileSize; i++) {
            gm.updateLoading("Loading Map" + " " + (int)(i * ((double)100/(worldWidth/tileSize))) + "%","80%");
            first = true;
            waterCalculate = true;
            blockNumberFromFirstGround = 0;
            for (int l = 0; l < worldHeight / tileSize; l++) {
                blockSelector(blockSelectingMechanism(i,l),i,l);
            }
        }
    }
    private int blockSelectingMechanism(int i, int l) {
        double Biome = (PerlinNoise1D.perlinNoise((i * gm.intervalOfSeed / 10),gm.seed));
        double height = (PerlinNoise1D.perlinNoise((i * gm.intervalOfSeed),gm.seed));
        double cave = (PerlinNoise2D.perlinNoise(i * gm.intervalOfSeed/3,l * gm.intervalOfSeed,gm.seed));
        double ore = (PerlinNoise2D.perlinNoise(i * gm.intervalOfSeed,l * gm.intervalOfSeed/3,gm.seed));
        if(Biome > 5){
            if(i + 1 == worldWidth / 25 || i == 0){
                return 5; // barrier
            }
            else if (l * 25 >= (height * 25) + 1000) {
                blockNumberFromFirstGround++;
                if(l + 1 == worldHeight / 25){
                    return 4; // bedrock
                }
                else if(cave > 0.22 && blockNumberFromFirstGround > 16) {
                    return 3; // air
                }
                else if (blockNumberFromFirstGround > 12){
                    if(ore > 0.4){
                        return 6; // iron
                    }else{
                        return 2; // stone
                    }
                }
                else{
                    return 13; // sand
                }
            }
        }
        else{
            if(i + 1 == worldWidth / 25 || i == 0){
                return 5; // barrier
            }
            else if (l * 25 >= (height * 25) + 1000) {
                blockNumberFromFirstGround++;
                if (first) {
                    first = false;
                    if((height * 25) + 1000 < 200 + 1000){
                        return 0; // grass
                    }
                    else{
                        return 13; // sand
                    }
                }
                else if(l + 1 == worldHeight / 25){
                    return 4; // bedrock
                }
                else if(cave > 0.22 && blockNumberFromFirstGround > 10) {
                    return 3; // air
                }
                else if (blockNumberFromFirstGround > 8){
                    if(blockNumberFromFirstGround < 16){
                        if(Math.random() > 0.6){
                            return 2; // stone
                        }
                        else {
                            return 1; // dirt
                        }
                    }
                    else{
                        if(ore > 0.4){
                            return 8; // iron
                        }else{
                            return 2; // stone
                        }
                    }
                }
                else{
                    if(waterCalculate){
                        waterCalculate = false;
                        for (int j = 0; gm.blocks.get(new Point(i*25,(l - 1)*25)).Y + (j * 25) > 200 + 1000; j--){
                            if(!Objects.equals(gm.blocks.get(new Point(i*25,(l + j - 1)*25)).getName(), "sand")){
                                blockSelector(14,i,l + j - 1); // water
                            }
                        }
                    }
                    return 1; // dirt
                }
            }
        }
        return 3;
    }
    public void blockSelector(int blockNumber, int i, int l){
        Entity temporaryEntity = getNewBlockFromID(blockNumber);
        temporaryEntity.X = i*25;
        temporaryEntity.Y = l*25;
        temporaryEntity.point = new Point(temporaryEntity.X,temporaryEntity.Y);
        gm.blocks.put(temporaryEntity.point,temporaryEntity);
    }
    public Image getPictureForID(int blockNumber) {
            switch (blockNumber) {
                case 0 -> {
                    return AssetHandler.grass;
                }
                case 1 -> {
                    return AssetHandler.dirt;
                }
                case 2 -> {
                    return AssetHandler.stone;
                }
                case 3 -> {
                    return null;
                }
                case 4 -> {
                    return AssetHandler.bedrock;
                }
                case 5 -> {
                    return AssetHandler.barrier;
                }
                case 6 -> {
                    return AssetHandler.iron_ore;
                }
                case 7 -> {
                    return AssetHandler.furnace;
                }
                case 8 -> {
                    return AssetHandler.coal_ore;
                }
                case 9 -> {
                    return AssetHandler.iron_bar;
                }
                case 10 -> {
                    return AssetHandler.pickaxe_bedrock;
                }
                case 11 -> {
                    return AssetHandler.pickaxe_feather;
                }
                case 12 -> {
                    return AssetHandler.pickaxe_wood;
                }
                case 13 -> {
                    return AssetHandler.sand;
                }
                case 14 -> {
                    return AssetHandler.water;
                }
            }
        return null;
    }
    public Entity getNewBlockFromID(int blockNumber){
        switch (blockNumber){
            case 0 -> {
                return new BLK_GRASS();
            }
            case 1 -> {
                return new BLK_DIRT();
            }
            case 2 -> {
                return new BLK_STONE();
            }
            case 3 -> {
                return new BLK_AIR();
            }
            case 4 -> {
                return new BLK_BEDROCK();
            }
            case 5 -> {
                return new BLK_BARRIER();
            }
            case 6 -> {
                return new BLK_IRON_ORE();
            }
            case 7 -> {
                return new BLK_INTERACTIVE_FURNACE(gm);
            }
            case 8 -> {
                return new BLK_COAL_ORE();
            }
            case 9 -> {
                return new ITM_IRON_BAR();
            }
            case 13 -> {
                return new BLK_SAND();
            }
            case 14 -> {
                return new BLK_WATER();
            }
        }
        return null;
    }
    public void updateHitBoxes(){
        for (Entity block: gm.p.getOnlyVisibleBlocks()) {
            if(block != null){
                if(!block.penetrable){
                    checkHitBoxFromBlock(block.point);
                }
            }
        }
    }
    public void checkHitBoxFromBlock(Point i){
        if(gm.getBlock(gm.blocks.get(i).X,gm.blocks.get(i).Y - 25) != null){
            if(gm.getBlock(gm.blocks.get(i).X,gm.blocks.get(i).Y - 25).penetrable){
                gm.blocks.get(i).hitTop = true;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).X,gm.blocks.get(i).Y + 25) != null){
            if(gm.getBlock(gm.blocks.get(i).X,gm.blocks.get(i).Y + 25).penetrable){
                gm.blocks.get(i).hitBottom = true;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).X + 25,gm.blocks.get(i).Y) != null){
            if(gm.getBlock(gm.blocks.get(i).X + 25,gm.blocks.get(i).Y).penetrable){
                gm.blocks.get(i).hitRight = true;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).X - 25,gm.blocks.get(i).Y) != null){
            if(gm.getBlock(gm.blocks.get(i).X - 25,gm.blocks.get(i).Y).penetrable){
                gm.blocks.get(i).hitLeft = true;
            }
        }
    }
    public void drawMap(Graphics g){
        for (Entity block: gm.p.getOnlyVisibleBlocks()) {
            if(block != null){
                g.setColor(Color.blue);
                block.drawBlock(g,gm.p,gm);
                if(gm.debug){
                    g.setColor(Color.red);
                    block.drawHitBox(g,gm.p);
                    g.setColor(Color.blue);
                    if(vertices){
                        block.drawBlockVertices(g,gm.p);
                    }
                    if(specificBlockShown){
                        try {
                            gm.getBlock((((gm.p.X - gm.p.offsetX) / 25) * 25),(((gm.p.Y + gm.p.offsetY) / 25) * 25) + (gm.p.height - 25)).drawBlockSpecial(g,gm.p);
                        }
                        catch (Exception e){
                            System.out.println("Out of Bounds");
                        }
                    }
                }
            }
        }
    }
    public void findBlock(Point mouseCoordinates) {
        Point mouseC = gm.mml.getMouseBlockHover(mouseCoordinates);
        Entity block = gm.blocks.get(mouseC);
        if(block != null){
            if (block.breakable) {
                if (block.health - gm.p.currentMiningDamage <= 0) {
                    if (block.harvestable(gm.p)) {
                        gm.addMessage("Harvested " + block.getName(), 120);
                        Entity currentEntity = getNewBlockFromID(block.id);
                        if (currentEntity != null) {
                            currentEntity.inventoryX = gm.p.inv.getFirstFreeInventorySpace().x;
                            currentEntity.inventoryY = gm.p.inv.getFirstFreeInventorySpace().y;
                            gm.p.inv.inventory.put(currentEntity, currentEntity.dropAmount);
                            gm.p.inv.sortInventory(currentEntity);
                        }
                    }
                    gm.playSound("attackBlock.wav");
                    block.breakBlock(this, "air");
                } else {
                    block.health -= gm.p.currentMiningDamage;
                    gm.playSound("click.wav");
                    gm.addMessage("Hat noch leben: " + block.health, 120);
                }
            } else {
                gm.addMessage("You can't break " + block.getName(), 60);
            }
        }
    }
    public void interactWorld(Point mouseCoordinates, Entity entity){
        Point mouseC = gm.mml.getMouseBlockHover(mouseCoordinates);
        if(gm.p.hotbar.getKeyFromCoordinates(gm.p.hotbar.inventorySpaceX,0) != null){
            placeBlock(mouseC,entity);
            gm.playSound("place.wav");
        }
        else{
            interactBlock(mouseC);
        }
    }
    public void interactBlock(Point mouseC){
        Entity block = gm.blocks.get(mouseC);
        if(block != null){
            if(block.interactive){
                block.interact(gm);
            }
        }
    }
    public void placeBlock(Point mouseC, Entity entity){
        updateHitBoxes();
        for (Entity block: gm.p.getOnlyVisibleBlocks()) {
            if(block != null){
                if(mouseC.x == block.point.x && mouseC.y == block.point.y){
                    if(Objects.equals(block.getName(), "air")){
                        gm.blocks.remove(block.point);
                        entity.X = 0;
                        entity.Y = 0;
                        blockSelector(entity.id,(mouseC.x) / 25,(mouseC.y)/25);
                        for (java.util.Map.Entry<Entity, Integer> entry : gm.p.hotbar.inventory.entrySet()) {
                            if(entry.getKey().inventoryX == gm.p.hotbar.inventorySpaceX){
                                if(entry.getValue() - 1 <= 0){
                                    gm.p.hotbar.inventory.remove(entry.getKey());
                                }
                                else{
                                    entry.setValue(entry.getValue() - 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}