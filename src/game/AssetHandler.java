package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AssetHandler {
    public Image player,player_sitting,heart_full, heart_half, heart_empty,oxygen_full,oxygen_half,oxygen_empty,ecken;
    public static Image grass, dirt,stone,barrier,bedrock, air,iron_ore, furnace,coal_ore,iron_bar,pickaxe_bedrock,pickaxe_feather,pickaxe_wood,sand,water,oak_wood,leave;
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
            player_sitting = ImageIO.read(new File("assets/android_sitting.png"));
            player = ImageIO.read(new File("assets/android.png"));
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
            oak_wood = ImageIO.read(new File("assets/tiles/oak_wood.png"));
            leave = ImageIO.read(new File("assets/tiles/leave.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadInventory(){
        try {
            ecken = ImageIO.read(new File("assets/tiles/ecken.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Image getPictureForID(int blockNumber) {
        switch (blockNumber) {
            case -2 -> {
                return player_sitting;
            }
            case -1 -> {
                return player;
            }
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
            case 15 -> {
                return oak_wood;
            }
            case 16 -> {
                return leave;
            }
        }
        return null;
    }
}
