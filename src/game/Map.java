package game;

import game.Entity.Blocks.*;
import game.Entity.Entity;
import game.Entity.Items.ITM_IRON_BAR;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Map {
    public UI ui;
    public boolean specificBlockShown;
    boolean first;
    public boolean vertices;
    public int worldWidth;
    public int worldHeight;
    Image grass, dirt,stone,barrier,bedrock, air,iron_ore, furnace,coal_ore,iron_bar,pickaxe_bedrock,pickaxe_feather,pickaxe_wood;
    public int blockNumberFromFirstGround;
    int[] mapHeightTest;
    int[] mapHeightPerColumn = {100, 125, 150, 175, 200, 225, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 225, 200, 150, 0, -50, -100, -200, -225, -250, -300, -300, -275, -250, -250, -175, -150, -100, -50, -25, 100, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300,300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300};
    public Map(UI ui) {
        this.ui = ui;
        worldWidth = ui.screenWidth*5;
        worldHeight = ui.screenHeight*3;
        mapHeightTest = new int[worldWidth];
        ui.blocks = new ArrayList<>((worldWidth / 25) * (worldHeight / 25));
    }
    public void loadMap() {
        for (int i = 0; i < worldWidth / 25; i++) {
            ui.updateLoading("Loading Map" + " " + (int)(i * ((double)100/(worldWidth/25))) + "%","80%");
            first = true;
            blockNumberFromFirstGround = 0;
            for (int l = 0; l < worldHeight / 25; l++) {
                blockSelector(blockSelectingMechanism(i,l),i,l);
            }
        }
    }
    public void randomMapHeight(){
        int offset = 0;
        int oneBefore = offset;
        for(int i = 0; i < worldWidth; i++){
            double rand = Math.random();
            if(rand > 0.67){
                if(rand > 0.80){
                    offset = 0;
                }
                else {
                    offset = -25;
                }
            }
            else {
                if(rand > 0.20){
                    offset = 0;
                }
                else {
                    offset = 25;
                }
            }
            mapHeightTest[i] = oneBefore + offset;
            oneBefore = mapHeightTest[i];
        }

    }
    private int blockSelectingMechanism(int i, int l) {
        if(i + 1 == worldWidth / 25 || i == 0){
            return 5; // barrier
        }
        else if (l * 25 >= mapHeightPerColumn[i] + 1000) {
            blockNumberFromFirstGround++;
            if (first) {
                first = false;
                return 0; // grass
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
            else if(l + 1 == worldHeight / 25){
                return 4; // bedrock
            }
            else{
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
        ui.blocks.add(temporaryEntity);
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
                    return air;
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
                return new BLK_INTERACTIVE_FURNACE();
            }
            case 8 -> {
                return new BLK_COAL_ORE();
            }
            case 9 -> {
                return new ITM_IRON_BAR();
            }
        }
        return null;
    }
    public void loadHitBoxes() {
        for (int i = 0; i < ui.blocks.size(); i++){
            checkHitBoxFromBlock(i);
        }
    }
    public void updateHitBoxes(){
        for (int i = 0; i < ui.blocks.size(); i++){
            if(ui.p.getOnlyVisibleBlocks(i)){
                checkHitBoxFromBlock(i);
            }
        }
    }
    public void checkHitBoxFromBlock(int i){
        if(!ui.blocks.get(i).deactivateHitBox && ui.blocks.get(getBlockFromCoordinates(ui.blocks.get(i).X,ui.blocks.get(i).Y - 25)).deactivateHitBox){
            ui.blocks.get(i).hitTop = true;
        }
        if(!ui.blocks.get(i).deactivateHitBox && ui.blocks.get(getBlockFromCoordinates(ui.blocks.get(i).X,ui.blocks.get(i).Y + 25)).deactivateHitBox){
            ui.blocks.get(i).hitBottom = true;
        }
        if(!ui.blocks.get(i).deactivateHitBox && ui.blocks.get(getBlockFromCoordinates(ui.blocks.get(i).X + 25,ui.blocks.get(i).Y)).deactivateHitBox){
            ui.blocks.get(i).hitRight = true;
        }
        if(!ui.blocks.get(i).deactivateHitBox && ui.blocks.get(getBlockFromCoordinates(ui.blocks.get(i).X - 25,ui.blocks.get(i).Y)).deactivateHitBox){
            ui.blocks.get(i).hitLeft = true;
        }
    }
    public int getBlockFromCoordinates(int X, int Y){
        for (int i = 0; i < ui.blocks.size(); i++){
            if (ui.blocks.get(i).X == X && ui.blocks.get(i).Y == Y) {
                return i;
            }
        }
        return 1;
    }
    public void drawMap(Graphics g){
        for(int i = 0; i < ui.blocks.size(); i++) {
            if(ui.p.getOnlyVisibleBlocks(i)){
                g.setColor(Color.blue);
                ui.blocks.get(i).drawBlock(g,ui.p,ui);
                if(ui.debug){
                    g.setColor(Color.red);
                    ui.blocks.get(i).drawHitBox(g,ui.p);
                    g.setColor(Color.blue);
                    if(vertices){
                        ui.blocks.get(i).drawBlockVertices(g,ui.p);
                    }
                    if(specificBlockShown){
                        try {
                            ui.blocks.get(ui.map.getBlockFromCoordinates((((ui.p.X - ui.p.offsetX) / 25) * 25),(((ui.p.Y + ui.p.offsetY) / 25) * 25) + (ui.p.height - 25))).drawBlockSpecial(g,ui.p);
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
        for(int i=0; i< ui.blocks.size(); i++){
            if(ui.p.getOnlyVisibleBlocks(i)){
                if(mouseC.x == ui.blocks.get(i).point.x && mouseC.y == ui.blocks.get(i).point.y){
                    if(ui.blocks.get(i).breakable){
                        if(ui.blocks.get(i).health - ui.p.currentMiningDamage <= 0){
                            if(ui.blocks.get(i).harvestable(ui.p)){
                                ui.addMessage("Harvested " + ui.blocks.get(i).getName(),120);
                                Entity currentEntity = null;
                                switch (ui.blocks.get(i).name){
                                    case "grass" -> currentEntity = new BLK_GRASS();
                                    case "dirt" -> currentEntity = new BLK_DIRT();
                                    case "stone" -> currentEntity = new BLK_STONE();
                                    case "iron_ore" -> currentEntity = new BLK_IRON_ORE();
                                }
                                if(currentEntity != null){
                                    currentEntity.inventoryX = ui.p.inv.getFirstFreeInventorySpace().x;
                                    currentEntity.inventoryY = ui.p.inv.getFirstFreeInventorySpace().y;
                                    ui.p.inv.inventory.put(currentEntity,currentEntity.dropAmount);
                                    ui.p.inv.sortInventory(currentEntity);
                                }
                            }
                            ui.blocks.get(i).breakBlock(this, "air");
                        }
                        else{
                            ui.blocks.get(i).health -= ui.p.currentMiningDamage;
                            ui.addMessage("Hat noch leben: " + ui.blocks.get(i).health,120);
                        }
                    }
                    else{
                        ui.addMessage("You can't break " + ui.blocks.get(i).getName(),60);
                    }
                }
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
        for(int i=0; i< ui.blocks.size(); i++){
            if(ui.p.getOnlyVisibleBlocks(i)) {
                if (mouseC.x - 150 == ui.blocks.get(i).point.x && mouseC.y - 50 == ui.blocks.get(i).point.y) {
                    if(ui.blocks.get(i).interactive){
                        ui.blocks.get(i).interact(ui);
                    }
                }
            }
        }
    }
    public void placeBlock(Point mouseC, Entity entity){
        for(int i=0; i< ui.blocks.size(); i++){
            if(ui.p.getOnlyVisibleBlocks(i)){
                if(mouseC.x - 150 == ui.blocks.get(i).point.x && mouseC.y - 50 == ui.blocks.get(i).point.y){
                    if(Objects.equals(ui.blocks.get(i).getName(), "air")){
                        ui.blocks.remove(i);
                        entity.X = 0;
                        entity.Y = 0;
                        blockSelector(entity.id,(mouseC.x - 150) / 25,(mouseC.y - 50)/25);
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