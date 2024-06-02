package game;

import game.Entity.Blocks.*;
import game.Entity.Entity;
import game.Entity.InventoryItem;
import game.Entity.Items.ITM_IRON_BAR;
import game.Entity.Items.ITM_TORCH;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    List<Integer[]> List = new ArrayList<>();
    public Map(GameManager gm) {
        this.gm = gm;
        worldWidth = gm.ui.screenWidth*20;
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
        addPostWorldCreation();
    }
    private int blockSelectingMechanism(int i, int l) {
        double Biome = (PerlinNoise1D.perlinNoise((i * gm.intervalOfSeed / 10),gm.seed));
        double height = (PerlinNoise1D.perlinNoise((i * gm.intervalOfSeed),gm.seed));
        double cave = (PerlinNoise2D.perlinNoise(i * gm.intervalOfSeed/3,l * gm.intervalOfSeed,gm.seed));
        double ore = (PerlinNoise2D.perlinNoise(i * gm.intervalOfSeed,l * gm.intervalOfSeed/3,gm.seed));
        if(Biome > 5){ // DESERT
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
        else{ // PLAINS
            double tree = (PerlinNoise1D.perlinNoise((i * gm.intervalOfSeed * 5),gm.seed));
            if(i + 1 == worldWidth / 25 || i == 0){
                return 5; // barrier
            }
            else if (l * 25 >= (height * 25) + 1000) {
                blockNumberFromFirstGround++;
                if (first) {
                    first = false;
                    if((height * 25) + 1000 < 200 + 1000){
                        if(tree > 3){
                            boolean toNear = false;
                            for (Integer[] ints: List){
                                if (i < ints[1] + 4 && i > ints[1] - 4) {
                                    toNear = true;
                                    break;
                                }
                            }
                            if(!toNear){
                                List.add(new Integer[]{0,i,l});
                            }
                        }
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
                            return 8; // coal
                        }else{
                            return 2; // stone
                        }
                    }
                }
                else{
                    if(waterCalculate){
                        waterCalculate = false;
                        for (int j = 0; gm.blocks.get(new Point(i*25,(l - 1)*25)).point.y + (j * 25) > 200 + 1000; j--){
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
        temporaryEntity.point.x = i*25;
        temporaryEntity.point.y = l*25;
        temporaryEntity.point = new Point(temporaryEntity.point.x,temporaryEntity.point.y);
        gm.blocks.put(temporaryEntity.point,temporaryEntity);
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
            case 15 -> {
                return new BLK_OAK_WOOD();
            }
            case 16 -> {
                return new BLK_LEAVE();
            }
            case 17 -> {
                return new BLK_INTERACTIVE_CRAFTING_BENCH(gm);
            }
            case 18 -> {
                return new ITM_TORCH();
            }
        }
        return null;
    }
    public void addPostWorldCreation(){
        for (Integer[] ints: List){
            switch (ints[0]){
                case 0 -> buildTree(ints[1],ints[2]);
                case 1 -> buildMineshaft();
            }
        }
    }
    private void buildMineshaft() {
    }
    public void buildTree(int x, int y){
        blockSelector(15,x,y - 1);
        blockSelector(15,x,y - 2);
        blockSelector(15,x,y - 3);
        blockSelector(16,x,y - 4);
        blockSelector(16,x,y - 5);
        blockSelector(16,x - 1,y - 4);
        blockSelector(16,x + 1,y - 4);
    }
    public void updateHitBoxes(){
        for (Entity block: gm.p.getOnlyVisibleBlocks()) {
            if(block != null){
                block.hitTop = false;
                block.hitLeft = false;
                block.hitRight = false;
                block.hitBottom = false;
                if(!block.isPenetrable){
                    checkHitBoxFromBlock(block.point);
                }
                if(!block.lightEmission){
                        block.lightLevel = updateLightLevelFromBlock(block.point);
                        if(block.lightLevel > 15){
                            block.lightLevel = 15;
                        }
                        else if(block.lightLevel < 0){
                            block.lightLevel = 0;
                        }
                }else{
                    block.lightLevel = updateLightLevelFromBlock(block.point) + 1;
                    if(block.lightLevel > 15){
                        block.lightLevel = 15;
                    }
                    else if(block.lightLevel < block.minLightLevel){
                        block.lightLevel = block.minLightLevel;
                    }
                }
                if(Objects.equals(block.getName(), "air") && block.lightLevel < gm.daytime){
                    block.lightLevel = gm.daytime;
                }
            }
        }
    }
    public int updateLightLevelFromBlock(Point i) {
        int highest = 0;
        if(gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y - 25) != null){
            if(highest < gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y - 25).lightLevel){
                highest = gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y - 25).lightLevel - gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y - 25).lightDampness;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y + 25) != null){
            if(highest < gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y + 25).lightLevel){
                highest = gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y + 25).lightLevel - gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y + 25).lightDampness;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).point.x + 25,gm.blocks.get(i).point.y) != null){
            if(highest < gm.getBlock(gm.blocks.get(i).point.x + 25,gm.blocks.get(i).point.y).lightLevel){
                highest = gm.getBlock(gm.blocks.get(i).point.x + 25,gm.blocks.get(i).point.y).lightLevel - gm.getBlock(gm.blocks.get(i).point.x + 25,gm.blocks.get(i).point.y).lightDampness;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).point.x - 25,gm.blocks.get(i).point.y) != null){
            if(highest < gm.getBlock(gm.blocks.get(i).point.x - 25,gm.blocks.get(i).point.y).lightLevel){
                highest = gm.getBlock(gm.blocks.get(i).point.x - 25,gm.blocks.get(i).point.y).lightLevel - gm.getBlock(gm.blocks.get(i).point.x - 25,gm.blocks.get(i).point.y).lightDampness;
            }
        }
        return highest;
    }
    public void checkHitBoxFromBlock(Point i){
        if(gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y - 25) != null){
            if(gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y - 25).isPenetrable){
                gm.blocks.get(i).hitTop = true;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y + 25) != null){
            if(gm.getBlock(gm.blocks.get(i).point.x,gm.blocks.get(i).point.y + 25).isPenetrable){
                gm.blocks.get(i).hitBottom = true;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).point.x + 25,gm.blocks.get(i).point.y) != null){
            if(gm.getBlock(gm.blocks.get(i).point.x + 25,gm.blocks.get(i).point.y).isPenetrable){
                gm.blocks.get(i).hitRight = true;
            }
        }
        if(gm.getBlock(gm.blocks.get(i).point.x - 25,gm.blocks.get(i).point.y) != null){
            if(gm.getBlock(gm.blocks.get(i).point.x - 25,gm.blocks.get(i).point.y).isPenetrable){
                gm.blocks.get(i).hitLeft = true;
            }
        }
    }
    public void drawMap(Graphics g){
        for (Entity block: gm.p.getOnlyVisibleBlocks()) {
            if(block != null){
                g.setColor(Color.blue);
                block.drawBlock(g,gm.p,gm);
                if(gm.isDebug){
                    g.setColor(Color.red);
                    block.drawHitBox(g,gm.p);
                    g.setColor(Color.blue);
                    if(vertices){
                        block.drawBlockVertices(g,gm.p);
                    }
                    if(specificBlockShown){
                        try {
                            gm.getBlock((((gm.p.point.x - gm.p.offsetX) / 25) * 25),(((gm.p.point.y + gm.p.offsetY) / 25) * 25) + (gm.p.height - 25)).drawBlockSpecial(g,gm.p);
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
            if (block.isBreakable) {
                if (block.health - gm.p.currentMiningDamage <= 0) {
                    if (block.harvestable(gm.p)) {
                        gm.addMessage("Harvested " + block.getName(), 120);
                        Entity currentEntity = getNewBlockFromID(block.id);
                        if (currentEntity != null) {
                            // nimm point und amch halt sachen bitte geh nachher bitte niifgzvbwuioevfziufvudf9wefvwez
                            Point point = gm.p.inv.getFirstFreeInventorySpace();
                            gm.p.inv.inventory.put(point,new InventoryItem(currentEntity, currentEntity.dropAmount));
                            gm.p.inv.sortInventory(point);
                        }
                    }
                    gm.playSound("attackBlock.wav");
                    Entity newBlock = getNewBlockFromID(3);
                    newBlock.point.x = block.point.x;
                    newBlock.point.y = block.point.y;
                    newBlock.point = block.point;
                    gm.blocks.replace(block.point,newBlock);
                    gm.map.updateHitBoxes();
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
    public void interactWorld(Point mouseCoordinates){
        Point mouseC = gm.mml.getMouseBlockHover(mouseCoordinates);
        if(gm.p.hotbar.getEntryFromCoordinates(gm.p.hotbar.inventorySpaceX,0) != null){
            if(gm.p.hotbar.getEntryFromCoordinates(gm.p.hotbar.inventorySpaceX,0).getValue().entity.isPlacable){
                placeBlock(mouseC,gm.p.hotbar.getEntryFromCoordinates(gm.p.hotbar.inventorySpaceX,0).getValue().entity);
            }
            else{
                interactBlock(mouseC);
            }

        }
        else{
            interactBlock(mouseC);
        }
    }
    public void interactBlock(Point mouseC){
        Entity block = gm.blocks.get(mouseC);
        if(block != null){
            if(block.isInteractive){
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
                            entity.point.x = 0;
                            entity.point.y = 0;
                            blockSelector(entity.id,(mouseC.x) / 25,(mouseC.y)/25);
                            gm.playSound("place.wav");
                            for (java.util.Map.Entry<Point, InventoryItem> entry : gm.p.hotbar.inventory.entrySet()) {
                                if(entry.getKey().x == gm.p.hotbar.inventorySpaceX){
                                    if(entry.getValue().amount - 1 <= 0){
                                        gm.p.hotbar.inventory.remove(entry.getKey());
                                    }
                                    else{
                                        entry.getValue().amount = entry.getValue().amount - 1;
                                        entry.setValue(new InventoryItem(entry.getValue().entity, entry.getValue().amount));
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }
}