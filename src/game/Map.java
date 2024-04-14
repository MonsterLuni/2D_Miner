package game;

import game.Entity.Blocks.*;
import game.Entity.Entity;
import game.Entity.Items.ITM_IRON_BAR;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Map {
    public UI ui;
    public boolean specificBlockShown;
    boolean first, waterCalculate;
    public boolean vertices;
    public int worldWidth;
    public int worldHeight;
    public int tileSize = 25;
    Image grass, dirt,stone,barrier,bedrock, air,iron_ore, furnace,coal_ore,iron_bar,pickaxe_bedrock,pickaxe_feather,pickaxe_wood,sand,water;
    public int blockNumberFromFirstGround;
    public Map(UI ui) {
        this.ui = ui;
        worldWidth = ui.screenWidth*20;
        worldHeight = ui.screenHeight*10;
        ui.blocks = new HashMap<>((worldWidth / tileSize) * (worldHeight / tileSize));
    }
    public void loadMap() {
        for (int i = 0; i < worldWidth / tileSize; i++) {
            ui.updateLoading("Loading Map" + " " + (int)(i * ((double)100/(worldWidth/tileSize))) + "%","80%");
            first = true;
            waterCalculate = true;
            blockNumberFromFirstGround = 0;
            for (int l = 0; l < worldHeight / tileSize; l++) {
                blockSelector(blockSelectingMechanism(i,l),i,l);
            }
        }
    }
    private int blockSelectingMechanism(int i, int l) {
        double height = (PerlinNoise1D.perlinNoise((i * ui.intervalOfSeed),ui.seed));
        if(i + 1 == worldWidth / 25 || i == 0){
            return 5; // barrier
        }
        else if (l * 25 >= (height * 25) + 1000) {
            blockNumberFromFirstGround++;
            if (first) {
                if((height * 25) + 1000 < 200 + 1000){
                    first = false;
                    return 0; // grass
                }
                else{
                    first = false;
                    return 13; // sand
                }
            }
            else if(l + 1 == worldHeight / 25){
                return 4; // bedrock
            }
            else if (blockNumberFromFirstGround > 3){
                if(blockNumberFromFirstGround < 8){
                    if(Math.random() > 0.6){
                        return 2; // stone
                    }
                    else {
                        return 1; // dirt
                    }
                }
                else{
                    return 2; // stone
                }
            }
            else{
                if(waterCalculate){
                    waterCalculate = false;
                    for (int j = 0; ui.blocks.get(new Point(i*25,(l - 1)*25)).Y + (j * 25) > 200 + 1000; j--){
                        if(!Objects.equals(ui.blocks.get(new Point(i*25,(l + j - 1)*25)).getName(), "sand")){
                            blockSelector(14,i,l + j - 1); // water
                        }
                    }
                }
                return 1; // dirt
            }
        }
        else {
            return 3; // air
        }
    }
    public void blockSelector(int blockNumber, int i, int l){
        Entity temporaryEntity = getNewBlockFromID(blockNumber);
        temporaryEntity.X = i*25;
        temporaryEntity.Y = l*25;
        temporaryEntity.point = new Point(temporaryEntity.X,temporaryEntity.Y);
        ui.blocks.put(temporaryEntity.point,temporaryEntity);
    }
    public void getImages(){
        try {
            grass = ImageIO.read(new File("assets/tiles/grass.png"));
            dirt = ImageIO.read(new File("assets/tiles/dirt.png"));
            stone = ImageIO.read(new File("assets/tiles/stone.png"));
            air = ImageIO.read(new File("assets/tiles/air.png"));
            bedrock = ImageIO.read(new File("assets/tiles/bedrock.png"));
            barrier = ImageIO.read(new File("assets/tiles/barrier.png"));
            iron_ore = ImageIO.read(new File("assets/tiles/iron_ore.png"));
            furnace = ImageIO.read(new File("assets/tiles/furnace.png"));
            coal_ore = ImageIO.read(new File("assets/tiles/coal_ore.png"));
            iron_bar = ImageIO.read(new File("assets/items/iron_bar.png"));
            pickaxe_bedrock = ImageIO.read(new File("assets/items/pickaxe_bedrock.png"));
            pickaxe_feather = ImageIO.read(new File("assets/items/pickaxe_feather.png"));
            pickaxe_wood = ImageIO.read(new File("assets/items/pickaxe_wood.png"));
            sand = ImageIO.read(new File("assets/tiles/sand.png"));
            water = ImageIO.read(new File("assets/tiles/water.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Image getPictureForID(int blockNumber) {
            switch (blockNumber) {
                case 0 -> {
                    return grass;
                }
                case 1 -> {
                    return dirt;
                }
                case 2 -> {
                    return stone;
                }
                case 3 -> {
                    return null;
                }
                case 4 -> {
                    return bedrock;
                }
                case 5 -> {
                    return barrier;
                }
                case 6 -> {
                    return iron_ore;
                }
                case 7 -> {
                    return furnace;
                }
                case 8 -> {
                    return coal_ore;
                }
                case 9 -> {
                    return iron_bar;
                }
                case 10 -> {
                    return pickaxe_bedrock;
                }
                case 11 -> {
                    return pickaxe_feather;
                }
                case 12 -> {
                    return pickaxe_wood;
                }
                case 13 -> {
                    return sand;
                }
                case 14 -> {
                    return water;
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
                return new BLK_INTERACTIVE_FURNACE(ui);
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
        for (Entity block: ui.p.getOnlyVisibleBlocks()) {
            if(block != null){
                if(!block.deactivateHitBox){
                    checkHitBoxFromBlock(block.point);
                }
            }
        }
    }
    public void checkHitBoxFromBlock(Point i){
        if(getBlockFromCoordinates(ui.blocks.get(i).X,ui.blocks.get(i).Y - 25) != null){
            if(getBlockFromCoordinates(ui.blocks.get(i).X,ui.blocks.get(i).Y - 25).deactivateHitBox){
                ui.blocks.get(i).hitTop = true;
            }
        }
        if(getBlockFromCoordinates(ui.blocks.get(i).X,ui.blocks.get(i).Y + 25) != null){
            if(getBlockFromCoordinates(ui.blocks.get(i).X,ui.blocks.get(i).Y + 25).deactivateHitBox){
                ui.blocks.get(i).hitBottom = true;
            }
        }
        if(getBlockFromCoordinates(ui.blocks.get(i).X + 25,ui.blocks.get(i).Y) != null){
            if(getBlockFromCoordinates(ui.blocks.get(i).X + 25,ui.blocks.get(i).Y).deactivateHitBox){
                ui.blocks.get(i).hitRight = true;
            }
        }
        if(getBlockFromCoordinates(ui.blocks.get(i).X - 25,ui.blocks.get(i).Y) != null){
            if(getBlockFromCoordinates(ui.blocks.get(i).X - 25,ui.blocks.get(i).Y).deactivateHitBox){
                ui.blocks.get(i).hitLeft = true;
            }
        }
    }
    public Entity getBlockFromCoordinates(int X, int Y){
        if (ui.blocks.get(new Point(X,Y)) != null) {
            return ui.blocks.get(new Point(X,Y));
        }
        return null;
    }
    public void drawMap(Graphics g){
        for (Entity block: ui.p.getOnlyVisibleBlocks()) {
            if(block != null){
                g.setColor(Color.blue);
                block.drawBlock(g,ui.p,ui);
                if(ui.debug){
                    g.setColor(Color.red);
                    block.drawHitBox(g,ui.p);
                    g.setColor(Color.blue);
                    if(vertices){
                        block.drawBlockVertices(g,ui.p);
                    }
                    if(specificBlockShown){
                        try {
                            ui.map.getBlockFromCoordinates((((ui.p.X - ui.p.offsetX) / 25) * 25),(((ui.p.Y + ui.p.offsetY) / 25) * 25) + (ui.p.height - 25)).drawBlockSpecial(g,ui.p);
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
        Point mouseC = ui.mml.getMouseBlockHover(mouseCoordinates);
        Entity block = ui.blocks.get(mouseC);
        if(block != null){
            if (block.breakable) {
                if (block.health - ui.p.currentMiningDamage <= 0) {
                    if (block.harvestable(ui.p)) {
                        ui.addMessage("Harvested " + block.getName(), 120);
                        Entity currentEntity = getNewBlockFromID(block.id);
                        if (currentEntity != null) {
                            currentEntity.inventoryX = ui.p.inv.getFirstFreeInventorySpace().x;
                            currentEntity.inventoryY = ui.p.inv.getFirstFreeInventorySpace().y;
                            ui.p.inv.inventory.put(currentEntity, currentEntity.dropAmount);
                            ui.p.inv.sortInventory(currentEntity);
                        }
                    }
                    block.breakBlock(this, "air");
                } else {
                    block.health -= ui.p.currentMiningDamage;
                    ui.addMessage("Hat noch leben: " + block.health, 120);
                }
            } else {
                ui.addMessage("You can't break " + block.getName(), 60);
            }
        }
    }
    public void interactWorld(Point mouseCoordinates, Entity entity){
        Point mouseC = ui.mml.getMouseBlockHover(mouseCoordinates);
        if(ui.p.hotbar.getKeyFromCoordinates(ui.p.hotbar.inventorySpaceX,0) != null){
            placeBlock(mouseC,entity);
        }
        else{
            interactBlock(mouseC);
        }
    }
    public void interactBlock(Point mouseC){
        Entity block = ui.blocks.get(mouseC);
        if(block != null){
            if(block.interactive){
                block.interact(ui);
            }
        }
    }
    public void placeBlock(Point mouseC, Entity entity){
        for (Entity block: ui.p.getOnlyVisibleBlocks()) {
            if(block != null){
                if(mouseC.x == block.point.x && mouseC.y == block.point.y){
                    if(Objects.equals(block.getName(), "air")){
                        ui.blocks.remove(block.point);
                        entity.X = 0;
                        entity.Y = 0;
                        blockSelector(entity.id,(mouseC.x) / 25,(mouseC.y)/25);
                        updateHitBoxes();
                        for (java.util.Map.Entry<Entity, Integer> entry : ui.p.hotbar.inventory.entrySet()) {
                            if(entry.getKey().inventoryX == ui.p.hotbar.inventorySpaceX){
                                if(entry.getValue() - 1 <= 0){
                                    ui.p.hotbar.inventory.remove(entry.getKey());
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