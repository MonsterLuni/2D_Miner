package game;

import game.Entity.Block;
import game.Entity.Entity;
import game.Entity.Player;
import listener.KeyListener;
import listener.MouseListener;
import listener.MouseMotionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI extends JFrame {
    long lastTime;
    boolean running = true;
    int defaultHeight = 720;
    int defaultWidth = 1280;
    public int screenHeight = defaultHeight;
    public int screenWidth = defaultWidth;
    public Player p;
    public Map map;
    public ArrayList<Block> blocks;
    public BufferedImage bufferedImage = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_RGB);
    public BufferedImage fullscreenBuffer;
    public Graphics imageG = bufferedImage.getGraphics();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public boolean fullscreen = false;
    public boolean debug = false;
    public double fps = 0;
    public int maxFps = 60;
    public MouseListener ml;
    public MouseMotionListener mml;
    public ArrayList<String> messages = new ArrayList<>(4);
    public ArrayList<Integer> liveTime = new ArrayList<>(4);
    public KeyListener kl;
    public final static int gameState = 0;
    public final static int inventoryState = 1;
    public final static int interactState = 2;
    public Inventory interactStateInventory;
    public Inventory interactStateInventory2;
    public int currentInteractState = 0;
    public final static int furnaceInteractState = 1;
    public int currentState = gameState;
    Color background = new Color(211, 244, 244);
    public UI(){
        kl  = new KeyListener(this);
        ml  = new MouseListener(this);
        mml = new MouseMotionListener(this);
        map = new Map(this);
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        addKeyListener(kl);
        addMouseListener(ml);
        addMouseMotionListener(mml);
        setFocusTraversalKeysEnabled(false);
        p = new Player(this,kl);
        map.loadMap();
        map.loadHitBoxes();
        lastTime = System.currentTimeMillis();
        fpsLimiter();
    }
    public void fpsLimiter(){
        long now = System.currentTimeMillis();
        long last = lastTime;
        fps = 1000 / (double)(now - last);
        while (fps >= maxFps){
            now = System.currentTimeMillis();
            fps = 1000 / (double)(now - last);
        }
        // Here comes the Code that actually gets run
        update();
        lastTime = now;
        if(running){
            fpsLimiter();
        }
    }
    public void update(){
        clearWindow(background);
        switch (currentState){
            case gameState -> drawGameState();
            case inventoryState -> drawInventoryState();
            case interactState -> drawInteractiveState();
        }
    }
    private void clearWindow(Color col){
        imageG.clearRect(0,0,screenWidth,screenHeight);
        imageG.setColor(col);
        imageG.fillRect(0,0,screenWidth,screenHeight);
    }
    private void drawGameState(){
        map.drawMap(imageG);
        if(debug){
            drawDebug();
        }
        updatePlayer();
        drawMessage();
        drawAnyInventory(p.hotbar,screenHeight - 50,(screenWidth/2 - (p.hotbar.maxSize/2 * 28)));
        drawToImage();
    }
    private void drawInteractiveState() {
        switch (currentInteractState) {
            case furnaceInteractState -> furnaceInteractiveState();
            case 2 -> System.out.println("kommt noch nh");
        }
    }
    private void furnaceInteractiveState() {
        drawAnyInventory(p.inv,(screenHeight - 250)/2,100);
        drawAnyInventory(p.hotbar,screenHeight - 50,(screenWidth/2 - (p.hotbar.maxSize/2 * 28)));
        drawAnyInventory(interactStateInventory,(screenHeight - 250)/2,900);
        drawAnyInventory(interactStateInventory2,(screenHeight - 250)/2 + 50,900);
        drawToImage();
    }
    public void addMessage(String message, int time){
        if(messages.size() < 5){
            messages.add(message);
            liveTime.add(time);
        }
    }
    private void drawMessage(){
        imageG.setFont(getFont().deriveFont(Font.ITALIC,20));
        imageG.setColor(Color.black);
        for(int i = 0; i < messages.size(); i++){
            if(messages.get(i) != null){
                imageG.drawString(messages.get(i),50,screenHeight-100 + (i*20));
                if(liveTime.get(i) - 1 == 0){
                    liveTime.remove(i);
                    messages.remove(i);
                }
                else {
                    liveTime.set(i,liveTime.get(i) - 1);
                }
            }
        }
    }
    private void drawAnyInventory(Inventory inv, int height, int width){
        imageG.setColor(Color.gray);
        imageG.fillRect(width,height,(28 * inv.width/25),(28 * inv.height/25));
        imageG.setColor(Color.black);
        for (int i = 0; i < inv.width/25; i++){
            for (int l = 0; l < inv.height/25; l++) {
                if(inv.activeInventorySpace.x == i && inv.activeInventorySpace.y == l){
                    imageG.setColor(Color.yellow);
                    imageG.fillRect(width + (i*28),height + (l*28),25,25);
                    imageG.drawRect(width + (i*28),height + (l*28),25,25);
                }
                else{
                    if(inv.inventorySpaceX == i && inv.inventorySpaceY == l){
                        imageG.setColor(Color.yellow);
                    }
                    else{
                        imageG.setColor(Color.black);
                    }
                    imageG.drawRect(width + (i*28),height + (l*28),25,25);
                }
                for (java.util.Map.Entry<Entity, Integer> entry : inv.inventory.entrySet()) {
                    if(entry.getKey().inventoryX == i && entry.getKey().inventoryY == l){
                        imageG.drawImage(entry.getKey().sprite,width + (i*28),height + (l*28),null);
                        if(entry.getValue() > 1){
                            imageG.drawString(String.valueOf(entry.getValue()),width + (i*28),height + (l*28) + 25);
                        }
                    }
                }
            }
        }
    }
    private void drawInventoryState(){
        drawAnyInventory(p.inv,(screenHeight - 250)/2,100);
        drawAnyInventory(p.hotbar,screenHeight - 50,(screenWidth/2 - (p.hotbar.maxSize/2 * 28)));
        drawToImage();
    }
    private void drawToImage(){
        if(fullscreen){
            super.getGraphics().drawImage(fullscreenBuffer,0,0,null);
        }
        else{
            super.getGraphics().drawImage(bufferedImage,0,0,null);
        }
    }
    private void drawDebug(){
        imageG.setColor(Color.black);
        imageG.setFont(getFont().deriveFont(Font.ITALIC,20f));
        imageG.drawString("FPS: " + df.format(fps),10,50);
        imageG.drawString("Loaded Blocks: " + blocks.size(),10,75);
        int number = 0;
        for (int i = 0 ; i < blocks.size(); i++){
            if(map.getOnlyVisibleBlocks(i)){
                number++;
            }
        }
        imageG.drawString("Updated Blocks: " + number,10,100);
        imageG.drawString("Player Coordinates: [X:" + (p.X - p.offsetX) + " Y:" + (p.Y + p.offsetY) + "]" ,10,125);
        imageG.drawString("Draw Grid [F2]: " + map.vertices,10,150);
        imageG.drawString("Specific Block [F4]" + map.specificBlockShown, 10, 175);
    }
    private void updatePlayer() {
        p.drawPlayer(imageG);
        p.drawSelected(imageG);
        p.jump();
        p.walk();
        p.gravity();
    }
    public void toggleFullscreen() {
        if(fullscreen){
            screenHeight = defaultHeight;
            screenWidth = defaultWidth;
            setSize(screenWidth, screenHeight);
            setExtendedState(JFrame.NORMAL);
            setLocationRelativeTo(null);
            imageG = bufferedImage.getGraphics();
        }
        else{
            GraphicsDevice currentMonitor = MouseInfo.getPointerInfo().getDevice();
            screenWidth = currentMonitor.getDisplayMode().getWidth();
            screenHeight = currentMonitor.getDisplayMode().getHeight();
            fullscreenBuffer = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_RGB);
            imageG = fullscreenBuffer.getGraphics();
            setSize(screenWidth, screenHeight);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            map.loadMap();
        }
        fullscreen = !fullscreen;
    }
}