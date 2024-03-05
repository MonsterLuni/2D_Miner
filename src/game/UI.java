package game;
import game.Entity.Blocks.BLK_INTERACTIVE_FURNACE;
import game.Entity.Entity;
import game.Entity.LIV_ZOMBIE;
import game.Entity.Player;
import listener.KeyListener;
import listener.MouseListener;
import listener.MouseMotionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI extends JFrame {
    private long lastTime;
    public boolean running = true;
    private final int defaultHeight = 720;
    private final int defaultWidth = 1280;
    public int menuSelected = 0;
    public int screenHeight = defaultHeight;
    public int screenWidth = defaultWidth;
    public Player p;
    public Map map;
    public ArrayList<Entity> blocks;
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
    private final ArrayList<String> messages = new ArrayList<>(4);
    private final ArrayList<Integer> liveTime = new ArrayList<>(4);
    public BLK_INTERACTIVE_FURNACE interactStateFurnace;
    public KeyListener kl;
    private Image heart_full, heart_half, heart_empty;
    public final static int menuState = 0;
    public final static int loadingState = 1;
    public final static int gameState = 2;
    public final static int inventoryState = 3;
    public final static int interactState = 4;
    public final static int deathState = 5;
    public int currentState = menuState;
    public final static int furnaceInteractState = 1;
    public int currentInteractState = 0;
    LIV_ZOMBIE zombie;
    String currentText = "";
    String currentPercent = "0%";
    static final Color background = new Color(211, 244, 244);
    public UI(){
        kl  = new KeyListener(this);
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setIconImage(new ImageIcon("assets/logo.png").getImage());
        setVisible(true);
        addKeyListener(kl);
        setFocusTraversalKeysEnabled(false);
        lastTime = System.currentTimeMillis();
        fpsLimiter();
    }
    public void fpsLimiter() {
        long targetTime = System.currentTimeMillis() + (long) (1000 / maxFps);
        while (running) {
            long now = System.currentTimeMillis();
            fps = 1000 / (double)(now - lastTime);
            update();
            lastTime = now;
            long sleepTime = targetTime - System.currentTimeMillis();
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            targetTime += 1000 / maxFps;
        }
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    public void update(){
        switch (currentState){
            case menuState -> drawMenuState();
            case loadingState -> drawLoadingState();
            case gameState -> drawGameState();
            case inventoryState -> drawInventoryState();
            case interactState -> drawInteractiveState();
            case deathState -> drawDeathState();
        }
    }
    private void drawLoadingState() {
        clearWindow(Color.black);
        imageG.setColor(Color.white);
        Font f = new Font("Arial",Font.ITALIC, 60);
        imageG.setFont(f);
        imageG.drawString(currentPercent,calculateCenterX(currentPercent,f),250);
        f = new Font("Arial",Font.ITALIC, 50);
        imageG.setFont(f);
        imageG.drawString(currentText,calculateCenterX(currentText,f),300);
        drawToImage();
    }
    private void drawDeathState() {
        clearWindow(background);
        imageG.setColor(Color.black);
        imageG.drawString("DU BIST TOT",screenWidth/2,screenHeight/2);
        drawToImage();
    }
    public void startGame(){
        currentState = loadingState;
        updateLoading("Loading MouseListener","0%");
        ml  = new MouseListener(this);
        updateLoading("Loading MouseMotionListener","5%");
        mml = new MouseMotionListener(this);
        updateLoading("Loading Player","20%");
        p = new Player(this,kl);
        kl.primaryInv = p.inv;
        kl.secondaryInv = p.hotbar;
        updateLoading("Loading Zombie","40%");
        zombie = new LIV_ZOMBIE(this);
        updateLoading("Loading Heart Images","60%");
        try {
            this.heart_full = ImageIO.read(new File("assets/heart_full.png"));
            this.heart_half = ImageIO.read(new File("assets/heart_half.png"));
            this.heart_empty = ImageIO.read(new File("assets/heart_empty.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateLoading("Loading Map","80%");
        map = new Map(this);
        map.getImages();
        map.loadMap();
        updateLoading("Loading Hotboxes","90%");
        map.loadHitBoxes();
        updateLoading("Setting GameState","100%");
        addMouseListener(ml);
        addMouseMotionListener(mml);
        currentState = gameState;
    }
    public void saveGame(int num){
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
    public void updateLoading(String text, String percent) {
        currentText = text;
        currentPercent = percent;
        drawLoadingState();
    }
    private void drawMenuState(){
        clearWindow(background);
        int i = 0;
        int startingpoint = 250;

        clearWindow(Color.black);
        imageG.setColor(Color.white);

        Font f = new Font("Arial",Font.ITALIC, 50);
        imageG.setFont(f);
        imageG.drawString("2D Miner",calculateCenterX("2D Miner",f),150);
        f = new Font("Arial",Font.ITALIC, 30);
        imageG.setFont(f);

        imageG.drawString("PLAY",calculateCenterX("PLAY",f),startingpoint);

        i++;

        imageG.drawString("SETTINGS",calculateCenterX("SETTINGS",f),startingpoint + (i * 50));

        i++;

        imageG.drawString("MANUAL",calculateCenterX("MANUAL",f),startingpoint + (i * 50));

        i++;

        imageG.drawString("QUIT",calculateCenterX("QUIT",f),startingpoint + (i * 50));

        imageG.drawString(">",calculateCenterX(">",f) - 100,startingpoint + (menuSelected * 50));

        drawToImage();
    }
    private int calculateCenterX(String text, Font font) {
        FontMetrics fontMetrics = imageG.getFontMetrics(font);
        int stringWidth = fontMetrics.stringWidth(text);
        return (screenWidth - stringWidth) / 2;
    }
    public void addMessage(String message, int time){
        if(messages.size() < 5){
            messages.add(message);
            liveTime.add(time);
        }
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
    private void clearWindow(Color col){
        imageG.clearRect(0,0,screenWidth,screenHeight);
        imageG.setColor(col);
        imageG.fillRect(0,0,screenWidth,screenHeight);
    }
    private void drawGameState(){
        clearWindow(background);
        map.drawMap(imageG);
        if(debug){
            drawDebug();
        }
        updatePlayer();
        updateMonsters();
        drawMessage();
        drawAnyInventory(p.hotbar,screenHeight - 50,(screenWidth/2 - (p.hotbar.maxSize/2 * 28)));
        drawToImage();
    }
    private void furnaceInteractiveState() {
        drawAnyInventory(p.inv,(screenHeight - 250)/2,100);
        drawAnyInventory(p.hotbar,screenHeight - 50,(screenWidth/2 - (p.hotbar.maxSize/2 * 28)));
        drawAnyInventory(interactStateFurnace.invTop,(screenHeight - 250)/2,900);
        drawAnyInventory(interactStateFurnace.invFuel,(screenHeight - 250)/2 + 50,900);
        drawAnyInventory(interactStateFurnace.invOutput,(screenHeight - 250)/2 + 25,950);
        drawToImage();
        interactStateFurnace.checkFurnace();
    }
    private void updatePlayer() {
        p.draw(imageG,p);
        p.drawSelected(imageG);
        p.jump();
        p.walk();
        p.gravity();
        drawPlayerHealth();
    }
    private void updateMonsters(){
        zombie.draw(imageG, p);
        zombie.gravity();
        zombie.walk();
    }
    private void drawInteractiveState() {
        clearWindow(background);
        switch (currentInteractState) {
            case furnaceInteractState -> furnaceInteractiveState();
            case 2 -> System.out.println("kommt noch nh");
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
                if(inv == kl.primaryInv || inv == kl.secondaryInv){
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
                    }
                }
                imageG.drawRect(width + (i*28),height + (l*28),25,25);
                for (java.util.Map.Entry<Entity, Integer> entry : inv.inventory.entrySet()) {
                    if(entry.getKey() != null){
                        if(entry.getKey().inventoryX == i && entry.getKey().inventoryY == l){
                            imageG.drawImage(map.getPictureForID(entry.getKey().id),width + (i*28),height + (l*28),null);
                            if(entry.getValue() > 1){
                                imageG.drawString(String.valueOf(entry.getValue()),width + (i*28),height + (l*28) + 25);
                            }
                        }
                    }
                }
            }
        }
    }
    private void drawPlayerHealth(){
        for (double i = 2; i < p.maxHealth; i += 2){
            if(i <= p.health){
                imageG.drawImage(heart_full, (int) (screenWidth - 300 + (i*12.5)),50,null);
            }
            else if(i - 1 == p.health){
                imageG.drawImage(heart_half, (int) (screenWidth - 300 + (i*12.5)),50,null);
            }
            else{
                imageG.drawImage(heart_empty, (int) (screenWidth - 300 + (i*12.5)),50,null);
            }
        }
        if(p.health <= 0){
            currentState = deathState;
        }
    }
    private void drawInventoryState(){
        clearWindow(background);
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
            if(map.ui.p.getOnlyVisibleBlocks(i)){
                number++;
            }
        }
        imageG.drawString("Updated Blocks: " + number,10,100);
        imageG.drawString("Player Coordinates: [X:" + (p.X - p.offsetX) + " Y:" + (p.Y + p.offsetY) + "]" ,10,125);
        imageG.drawString("Draw Grid [F2]: " + map.vertices,10,150);
        imageG.drawString("Specific Block [F4]" + map.specificBlockShown, 10, 175);
    }
}