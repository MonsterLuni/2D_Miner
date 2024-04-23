package game;

import game.Entity.Blocks.BLK_INTERACTIVE_CRAFTING_BENCH;
import game.Entity.Blocks.BLK_INTERACTIVE_FURNACE;
import game.Entity.Entity;
import game.Entity.LIV_ZOMBIE;
import game.Entity.Player;
import listener.KeyListener;
import listener.MouseListener;
import listener.MouseMotionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameManager {

    public UI ui;

    private long lastTime;
    public int maxFps = 60;
    public double fps = 0;
    public boolean isRunning = true;
    public boolean isDebug = false;
    public final static int menuState = 0;
    public final static int loadingState = 1;
    public final static int gameState = 2;
    public final static int inventoryState = 3;
    public final static int interactState = 4;
    public final static int deathState = 5;
    public int currentState = menuState;
    public KeyListener kl;
    public MouseListener ml;
    public MouseMotionListener mml;
    public AssetHandler ah;
    public Player p;
    public Map map;
    LIV_ZOMBIE zombie;
    public String currentText = "";
    public String currentPercent = "0%";
    public long seed = 99999970;
    public double intervalOfSeed = 0.07;
    public int menuSelected = 0;
    public BLK_INTERACTIVE_FURNACE interactStateFurnace;
    public BLK_INTERACTIVE_CRAFTING_BENCH interactStateCraftingTable;
    public final static int furnaceInteractState = 1;
    public final static int craftingTableInteractState = 2;
    public int currentInteractState = 0;

    public HashMap<Point, Entity> blocks;
    public final ArrayList<String> messages = new ArrayList<>(4);
    public final ArrayList<Integer> liveTime = new ArrayList<>(4);
    Clip sound;
    Clip effect;
    public GameManager(){
        kl  = new KeyListener(this);
        this.ui = new UI(this);
        lastTime = System.currentTimeMillis();
        ah = new AssetHandler();
        ah.loadImages();
        ah.loadInventory();
        ah.loadHearts();
        ah.loadOxygen();
        loopSound("HomeScreen.wav");
        fpsLimiter();
    }
    public Entity getBlock(int x, int y){
        return blocks.get(new Point(x,y));
    }
    public void addMessage(String message, int time){
        if(messages.size() < 5){
            messages.add(message);
            liveTime.add(time);
        }
    }
    public void fpsLimiter() {
        long targetTime = System.currentTimeMillis() + (long) (1000 / maxFps);
        while (isRunning) {
            long now = System.currentTimeMillis();
            fps = 1000 / (double)(now - lastTime);
            update();
            lastTime = now;
            long sleepTime = targetTime - System.currentTimeMillis();
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            }
            targetTime += 1000 / maxFps;
        }
        ui.dispatchEvent(new WindowEvent(ui, WindowEvent.WINDOW_CLOSING));
    }
    public void startGame(){
        currentState = loadingState;
        updateLoading("Loading MouseListener","0%");
        ml  = new MouseListener(this);
        updateLoading("Loading MouseMotionListener","20%");
        mml = new MouseMotionListener(this);
        updateLoading("Loading Map","60%");
        map = new Map(this);
        map.loadMap();
        updateLoading("Loading Zombie","65%");
        zombie = new LIV_ZOMBIE(this);
        updateLoading("Loading Player","80%");
        p = new Player(this,kl);
        kl.primaryInv = p.inv;
        kl.secondaryInv = p.hotbar;
        updateLoading("Loading Hitboxes","90%");
        map.updateHitBoxes();
        updateLoading("Setting GameState","100%");
        ui.addMouseListener(ml);
        ui.addMouseMotionListener(mml);
        stopSound();
        //loopSound("main.wav"); //TODO: Activate again
        currentState = gameState;
        ui.clearWindow(ui.gameBackground);
    }
    public void update(){
        switch (currentState){
            case menuState -> ui.drawMenuState();
            case loadingState -> ui.drawLoadingState();
            case gameState -> ui.drawGameState();
            case inventoryState -> ui.drawInventoryState();
            case interactState -> ui.drawInteractiveState();
            case deathState -> ui.drawDeathState();
        }
    }
    public void updateLoading(String text, String percent) {
        currentText = text;
        currentPercent = percent;
    }
    public void saveGame(int num) {
        SaveGame sg = new SaveGame();
        sg.offsetX = p.offsetX;
        sg.offsetY = p.offsetY;
        sg.hardness = p.hardness;
        sg.miningDamage = p.miningDamage;
        sg.currentHardness = p.currentHardness;
        sg.currentMiningDamage = p .currentMiningDamage;
        sg.maxHealth = p .maxHealth;
        sg.health = p.health;
        sg.inv = p.inv;
        sg.hotbar = p.hotbar;
        sg.blocks = blocks;
        try{
            FileOutputStream fos = new FileOutputStream("Game"+ num +".sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sg);
            oos.flush();
            oos.close();
            System.out.println("Game Saved");
        }catch (Exception e){
            System.out.println("Serialization Error! Can't save data\n"
                    +e.getClass() + ": " + e.getMessage() + "\n");
        }
    }
    public void loadGame(int num){
        try{
            FileInputStream fis = new FileInputStream("Game"+ num +".sav");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SaveGame sg = (SaveGame) ois.readObject();
            p.offsetX = sg.offsetX;
            p.offsetY = sg.offsetY;
            p.hardness = sg.hardness;
            p.miningDamage = sg.miningDamage;
            p.currentHardness = sg.currentHardness;
            p .currentMiningDamage = sg.currentMiningDamage;
            p.maxHealth = sg.maxHealth;
            p.health = sg.health;
            p.inv = sg.inv;
            p.hotbar = sg.hotbar;
            blocks =  sg.blocks;
            ois.close();
            System.out.println("Game Loaded");
        }catch (Exception e){
            System.out.println("Serialization Error! Can't save data\n"
                    +e.getClass() + ": " + e.getMessage() + "\n");
        }
    }
    public void playSound(String soundFile) {
        try {
            File f = new File("assets/sounds/" + soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            effect = AudioSystem.getClip();
            effect.open(audioIn);
            effect.start();
        }
        catch (Exception e){
            System.out.println(Arrays.toString(e.getMessage().toCharArray()));
        }
    }
    public void loopSound(String soundFile) {
        try {
            File f = new File("assets/sounds/" + soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            sound = AudioSystem.getClip();
            sound.open(audioIn);
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        }
        catch (Exception e){
            System.out.println(Arrays.toString(e.getMessage().toCharArray()));
        }
    }
    public void stopSound() {
        System.out.println(sound);
        if (sound != null && sound.isRunning()) {
            sound.stop();
        }
    }
    public void stopEffect() {
        if (effect != null && effect.isRunning()) {
            effect.stop();
        }
    }
}
