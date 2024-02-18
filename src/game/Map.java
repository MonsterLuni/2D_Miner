package game;

import game.Entity.Block;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    public UI ui;
    public boolean specificBlockShown;
    boolean first;
    public boolean vertices;
    public int worldWidth;
    public int worldHeight;
    public int blockNumberFromFirstGround;
    public BufferedImage grass, dirt, stone, air, bedrock, barrier, iron_ore;
    int[] mapHeightTest;
    int[] mapHeightPerColumn = {100, 125, 150, 175, 200, 225, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 225, 200, 150, 0, -50, -100, -200, -225, -250, -300, -300, -275, -250, -250, -175, -150, -100, -50, -25, 100, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300,300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300};
    public Map(UI ui) {
        this.ui = ui;
        worldWidth = ui.screenWidth*5;
        worldHeight = ui.screenHeight*3;
        mapHeightTest = new int[worldWidth];
        ui.blocks = new ArrayList<>((worldWidth / 25) * (worldHeight / 25));
        try {
            grass = ImageIO.read(new File("assets/tiles/grass.png"));
            dirt = ImageIO.read(new File("assets/tiles/dirt.png"));
            stone = ImageIO.read(new File("assets/tiles/stone.png"));
            air = ImageIO.read(new File("assets/tiles/air.png"));
            bedrock = ImageIO.read(new File("assets/tiles/bedrock.png"));
            barrier = ImageIO.read(new File("assets/tiles/barrier.png"));
            iron_ore = ImageIO.read(new File("assets/tiles/iron_ore.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadMap() {
        randomMapHeight();
        for (int i = 0; i < worldWidth / 25; i++) {
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
        switch (blockNumber){
            case 0 -> addBlock(25,25, i, l, grass,false,true, "grass",1,5);
            case 1 -> addBlock(25, 25, i, l, dirt, false, true, "dirt",1,10);
            case 2 -> addBlock(25, 25, i, l, stone, false, true, "stone",2,30);
            case 3 -> addBlock(25, 25, i, l, air, true, false, "air",0,0);
            case 4 -> addBlock(25, 25, i, l, bedrock, false, false, "bedrock",10,1000);
            case 5 -> addBlock(25, 25, i, l, barrier, false, false, "barrier",10,1000);
            case 6 -> addBlock(25, 25, i, l, iron_ore, false, true, "iron_ore",3,50);
            case 7 -> {}
        }
    }
    public void addBlock(int height, int width, int i, int l, BufferedImage sprite, boolean deactivateHitBox, boolean breakable, String name, int hardness, int health){
        ui.blocks.add(new Block(height, width, i, l, sprite, deactivateHitBox, breakable, name, hardness, health));
    }
    public void loadHitBoxes() {
        for (int i = 0; i < ui.blocks.size(); i++){
            checkHitBoxFromBlock(i);
        }
    }
    public void updateHitBoxes(){
        for (int i = 0; i < ui.blocks.size(); i++){
            if(getOnlyVisibleBlocks(i)){
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
            if(ui.blocks.get(i).X == X && ui.blocks.get(i).Y == Y){
                return i;
            }
        }
        return 1;
    }
    public int getBlockFromPlayerY(int X, int Y){
        for (int i = 0; i < ui.blocks.size(); i++){
            if(getOnlyVisibleBlocks(i)){
                if(ui.blocks.get(i).X == X && ui.blocks.get(i).Y == Y + ui.p.height){
                    if(ui.blocks.get(i).hitTop){
                        return i;
                    }
                }
            }
        }
        return -10;
    }
    public void drawMap(Graphics g){
        for(int i = 0; i < ui.blocks.size(); i++) {
            if(getOnlyVisibleBlocks(i)){
                g.setColor(Color.blue);
                ui.blocks.get(i).drawBlock(g, ui.p);
                if(ui.debug){
                    g.setColor(Color.red);
                    ui.blocks.get(i).drawHitBox(g, ui.p);
                    g.setColor(Color.blue);
                    if(vertices){
                        ui.blocks.get(i).drawBlockVertices(g, ui.p);
                    }
                    if(specificBlockShown){
                        try {
                            ui.blocks.get(ui.map.getBlockFromCoordinates((((ui.p.X - ui.p.offsetX) / 25) * 25),(((ui.p.Y + ui.p.offsetY) / 25) * 25) + (ui.p.height - 25))).drawBlockSpecial(g, ui.p);
                        }
                        catch (Exception e){
                            System.out.println("Out of Bounds");
                        }
                    }
                }
            }
        }
    }
    public boolean getOnlyVisibleBlocks(int index){
            int positionPlayer = Math.round(((ui.p.X - (float) ui.p.offsetX) - (30 * 25)) /25);
            int positionPlayerY = Math.round(((ui.p.Y + (float) ui.p.offsetY) - (30 * 25)) /25);
        return ui.blocks.get(index).X / 25 >= positionPlayer && ui.blocks.get(index).X / 25 <= positionPlayer + 59 && ui.blocks.get(index).Y / 25 <= positionPlayerY + 54 && ui.blocks.get(index).Y / 25 >= positionPlayerY + 15;
    }
    public void findBlock(Point mouseCoordinates) {
        Point mouseC = ui.mml.getMouseBlockHover(mouseCoordinates);
        for(int i=0; i< ui.blocks.size(); i++){
            if(getOnlyVisibleBlocks(i)){
                if(mouseC.x == ui.blocks.get(i).point.x && mouseC.y == ui.blocks.get(i).point.y){
                    if(ui.blocks.get(i).breakable){
                        if(ui.blocks.get(i).health - ui.p.miningDamage <= 0){
                            if(ui.blocks.get(i).harvestable(ui.p)){
                                ui.addMessage("Harvested " + ui.blocks.get(i).getName(),120);
                                ui.p.inventory.add(new Block(ui.blocks.get(i).height,ui.blocks.get(i).width,ui.blocks.get(i).image,ui.blocks.get(i).getName()));
                            }
                            ui.blocks.get(i).breakBlock(this, air, "air");
                        }
                        else{
                            ui.blocks.get(i).health -= ui.p.miningDamage;
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
}