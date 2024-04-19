package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AssetHandler {
    public Image heart_full, heart_half, heart_empty,oxygen_full,oxygen_half,oxygen_empty;
    public static Image grass, dirt,stone,barrier,bedrock, air,iron_ore, furnace,coal_ore,iron_bar,pickaxe_bedrock,pickaxe_feather,pickaxe_wood,sand,water;
    public void loadHearts(){
        try {
            this.heart_full = ImageIO.read(new File("assets/heart_full.png"));
            this.heart_half = ImageIO.read(new File("assets/heart_half.png"));
            this.heart_empty = ImageIO.read(new File("assets/heart_empty.png"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadOxygen(){
        try {
            this.oxygen_full = ImageIO.read(new File("assets/oxygen_full.png"));
            this.oxygen_half = ImageIO.read(new File("assets/oxygen_half.png"));
            this.oxygen_empty = ImageIO.read(new File("assets/oxygen_empty.png"));
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadImages(){
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
}
