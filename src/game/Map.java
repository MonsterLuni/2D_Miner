package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    public UI ui;
    public boolean first;
    public boolean stoneB = false;
    BufferedImage grass, dirt, stone, air;

    public Map(UI ui) {
        this.ui = ui;
        ui.blocks = new ArrayList<>((ui.screenWidth / 25) * (ui.screenHeight / 25));
        try {
            grass = ImageIO.read(new File("F:\\Java\\_2D_Miner\\assets\\tiles\\grass.png"));
            dirt = ImageIO.read(new File("F:\\Java\\_2D_Miner\\assets\\tiles\\dirt.png"));
            stone = ImageIO.read(new File("F:\\Java\\_2D_Miner\\assets\\tiles\\stone.png"));
            air = ImageIO.read(new File("F:\\Java\\_2D_Miner\\assets\\tiles\\air.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int j = 0;
    public int[] mapHeightPerColumn = {300, 300, 300, 300, 250, 250, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250, 300, 300, 250, 250, 250, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 250, 250, 250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300};

    public void loadMap() {
        for (int i = 0; i < ui.screenWidth / 25; i++) {
            first = true;
            for (int l = 0; l < ui.screenHeight / 25; l++) {
                if (l * 25 >= mapHeightPerColumn[i]) {
                    if (first) {
                        ui.blocks.add(new Block(25, 25, i, l, grass));
                        first = false;
                    } else {
                        double rand = Math.random();
                        if (j < 3) {
                            ui.blocks.add(new Block(25, 25, i, l, dirt));
                            j++;
                        } else {
                            if (!stoneB && j < 5) {
                                if (rand > 0.6) {
                                    ui.blocks.add(new Block(25, 25, i, l, stone));
                                    stoneB = true;
                                } else {
                                    ui.blocks.add(new Block(25, 25, i, l, dirt));
                                    j++;
                                }
                            } else {
                                ui.blocks.add(new Block(25, 25, i, l, stone));
                            }
                        }
                    }
                } else {
                    ui.blocks.add(new Block(25, 25, i, l, air, true));
                }
            }
            stoneB = false;
            j = 0;
        }
    }
    public void loadHitBoxes() {
        for (int i = 0; i < (ui.screenWidth / 25) * (ui.screenHeight / 25); i++){
            if(!ui.blocks.get(i).air && ui.blocks.get(getBlockFromCoordinates(ui.blocks.get(i).X,ui.blocks.get(i).Y - 25)).air){
                ui.blocks.get(i).hitTop = true;
            }
            if(!ui.blocks.get(i).air && ui.blocks.get(getBlockFromCoordinates(ui.blocks.get(i).X,ui.blocks.get(i).Y + 25)).air){
                ui.blocks.get(i).hitBottom = true;
            }
            if(!ui.blocks.get(i).air && ui.blocks.get(getBlockFromCoordinates(ui.blocks.get(i).X + 25,ui.blocks.get(i).Y)).air){
                ui.blocks.get(i).hitRight = true;
            }
            if(!ui.blocks.get(i).air && ui.blocks.get(getBlockFromCoordinates(ui.blocks.get(i).X - 25,ui.blocks.get(i).Y)).air){
                ui.blocks.get(i).hitLeft = true;
            }
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

    public int getBlockFromPlayerX(int X, int Y){
        for (int i = 0; i < ui.blocks.size() * 25 / 25; i++){
            if(ui.blocks.get(i).X == X && ui.blocks.get(i).Y == Y){
                System.out.println("Registriert!");
                if(ui.blocks.get(i).hitRight){
                    System.out.println("Vollendet" + ui.blocks.get(i).X + "  " + ui.blocks.get(i).Y);
                    return i;
                }
            }
        }
        return -10;
    }
    public int getBlockFromPlayerY(int X, int Y){
        for (int i = 0; i < ui.blocks.size() * 25 / 25; i++){
            if(ui.blocks.get(i).X == X && ui.blocks.get(i).Y == Y + ui.p.height){
                if(ui.blocks.get(i).hitTop){
                    return i;
                }
            }
        }
        return -10;
    }
    public void drawMap(Graphics g){
        for(int i = 0; i < ui.blocks.size(); i++) {
            int positionPlayer = Math.round(((ui.p.X - (float) ui.p.offsetX) - (30 * 25)) /25);
            if(ui.blocks.get(i).X / 25 < positionPlayer || ui.blocks.get(i).X / 25 > positionPlayer + 59 ) {
                // Here is nothing left to see. A world that was fully obliterated.
            }
            else{
                g.setColor(Color.blue);
                ui.blocks.get(i).drawBlock(g, ui.p);
                if(ui.debug){
                    g.setColor(Color.red);
                    ui.blocks.get(i).drawHitBox(g, ui.p);
                    g.setColor(Color.blue);
                    ui.blocks.get(i).drawBlockVertices(g, ui.p);
                }
            }
        }
    }
    public void findBlock(Point mouseCoordinates) {
        //Todo: Why?
        int x = Math.round((float) mouseCoordinates.x / 25) * 25 - (ui.p.offsetX/25)*25 - 650;
        int y = Math.round((float) mouseCoordinates.y / 25) * 25 + (ui.p.offsetY/25)*25 - 350;
        Point mouseC = new Point(x,y);
        for(int i=0; i< ui.blocks.size(); i++){
            if(mouseC.x == ui.blocks.get(i).point.x && mouseC.y == ui.blocks.get(i).point.y){
                if(ui.blocks.get(i).breakable()){
                    ui.blocks.get(i).breakBlock(this, air);
                }
                else{
                    //TODO: Add Particle System that shows, that you can't break the current block.
                }
            }
        }
    }
}