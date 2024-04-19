package game;

import game.Entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

public class UI extends JFrame {
    GameManager gm;
    private final int defaultHeight = 720;
    private final int defaultWidth = 1280;
    private int waterUpdateCount = 0;
    public int screenHeight = defaultHeight;
    public int screenWidth = defaultWidth;
    public BufferedImage bufferedImage = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_RGB);
    public BufferedImage fullscreenBuffer;
    public Graphics imageG = bufferedImage.getGraphics();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public boolean fullscreen = false;
    public final Color gameBackground = new Color(173, 240, 240);
    public final Color menuBackground = new Color(0,0,0);
    private final Wait wfFOne = new Wait();
    private final Wait wfFTwo = new Wait();
    private final Wait wfFThree = new Wait();
    private final Wait wfFFour = new Wait();
    public UI(GameManager gm){
        this.gm = gm;
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setIconImage(new ImageIcon("assets/logo.png").getImage());
        setVisible(true);
        addKeyListener(gm.kl);
        setFocusTraversalKeysEnabled(false);
    }
    public void drawLoadingState() {
            clearWindow(menuBackground); //wtf
            imageG.setColor(Color.white);
            Font f = new Font("Arial",Font.ITALIC, 60);
            imageG.setFont(f);
            imageG.drawString(gm.currentPercent,calculateCenterX(gm.currentPercent,f),250);
            f = new Font("Arial",Font.ITALIC, 50);
            imageG.setFont(f);
            imageG.drawString(gm.currentText,calculateCenterX(gm.currentText,f),300);
            drawToImage();
    }
    public void drawDeathState() {
        clearWindow(gameBackground);
        imageG.setColor(Color.black);
        imageG.drawString("DU BIST TOT",screenWidth/2,screenHeight/2);
        drawToImage();
    }
    public void drawMenuState(){
        clearWindow(menuBackground);
        int i = 0;
        int startingpoint = 250;
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

        imageG.drawString(">",calculateCenterX(">",f) - 100,startingpoint + (gm.menuSelected * 50));

        drawToImage();
    }
    public void drawGameState(){
        clearWindow(gameBackground);
        gm.map.drawMap(imageG);
        if(gm.debug){
            drawDebug();
        }
        updateGravity();
        updatePlayer();
        updateMonsters();
        drawMessage();
        drawAnyInventory(gm.p.hotbar,screenHeight - 50,(screenWidth/2 - (gm.p.hotbar.maxSize/2 * 28)));
        drawToImage();
    }
    private int calculateCenterX(String text, Font font) {
        FontMetrics fontMetrics = imageG.getFontMetrics(font);
        int stringWidth = fontMetrics.stringWidth(text);
        return (screenWidth - stringWidth) / 2;
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
            gm.map.loadMap();
        }
        fullscreen = !fullscreen;
    }
    void clearWindow(Color col){
        imageG.clearRect(0,0,screenWidth,screenHeight);
        imageG.setColor(col);
        imageG.fillRect(0,0,screenWidth,screenHeight);
    }
    private void furnaceInteractiveState() {
        drawAnyInventory(gm.p.inv,(screenHeight - 250)/2,100);
        drawAnyInventory(gm.p.hotbar,screenHeight - 50,(screenWidth/2 - (gm.p.hotbar.maxSize/2 * 28)));
        drawAnyInventory(gm.interactStateFurnace.invTop,(screenHeight - 250)/2,900);
        drawAnyInventory(gm.interactStateFurnace.invFuel,(screenHeight - 250)/2 + 50,900);
        drawAnyInventory(gm.interactStateFurnace.invOutput,(screenHeight - 250)/2 + 25,950);
        drawToImage();
        gm.interactStateFurnace.checkFurnace();
    }
    public boolean soundPlayed = false;
    private void updatePlayer() {
        gm.p.draw(imageG,gm.p);
        gm.p.drawSelected(imageG);
        gm.p.jump();
        gm.p.walk();
        gm.p.gravity();
        drawPlayerHealth();

        if(gm.getBlock((((gm.p.X - gm.p.offsetX) / gm.map.tileSize) * gm.map.tileSize),(((gm.p.Y + gm.p.offsetY) / gm.map.tileSize) * gm.map.tileSize)) != null){
            if(gm.getBlock((((gm.p.X - gm.p.offsetX) / gm.map.tileSize) * gm.map.tileSize),(((gm.p.Y + gm.p.offsetY) / gm.map.tileSize) * gm.map.tileSize)).isLiquid){
                if(!soundPlayed){
                    gm.playSound("water_entry.wav");
                    soundPlayed = true;
                }
                drawPlayerBreath();
                if(wfFTwo.waitForFrames(30)){
                    gm.p.oxygen--;
                }
            }else if (gm.p.oxygen < gm.p.maxOxygen){
                soundPlayed = false;
                drawPlayerBreath();
                if(wfFThree.waitForFrames(15)){
                    gm.p.oxygen++;
                }
            }
        }
    }
    private void updateMonsters(){
        gm.zombie.draw(imageG, gm.p);
        gm.zombie.gravity();
        gm.zombie.walk();
    }
    public void updateGravity(){
        List<Integer[]> updateList = new ArrayList<>();
        if(wfFOne.waitForFrames(5)){
            for (Entity block: gm.p.getOnlyVisibleBlocks()){
                    if (block != null && block.isLiquid) {
                        if (gm.getBlock(block.X, block.Y + 25) != null && !gm.getBlock(block.X, block.Y + 25).isLiquid && gm.getBlock(block.X, block.Y + 25).penetrable) {
                            updateList.add(new Integer[]{block.id, block.X / 25, (block.Y / 25) + 1, 3, block.X / 25, block.Y / 25});
                        }
                        else if(gm.getBlock(block.X + 25, block.Y) != null
                                && gm.getBlock(block.X - 25, block.Y) != null
                                && gm.getBlock(block.X + 25, block.Y).penetrable
                                && !gm.getBlock(block.X+ 25, block.Y).isLiquid
                                && gm.getBlock(block.X - 25, block.Y).penetrable
                                && !gm.getBlock(block.X - 25, block.Y).isLiquid){
                                double random = Math.random();
                                if(random < 0.5){
                                    updateList.add(new Integer[]{block.id, (block.X / 25) + 1, block.Y / 25, 3, block.X / 25, block.Y / 25});
                                }
                                else{
                                    updateList.add(new Integer[]{block.id, (block.X / 25) - 1, block.Y / 25, 3, block.X / 25, block.Y / 25});
                                }
                        }
                        else if (gm.getBlock(block.X + 25, block.Y) != null && gm.getBlock(block.X + 25, block.Y).penetrable && !gm.getBlock(block.X+ 25, block.Y).isLiquid) {
                            updateList.add(new Integer[]{block.id, (block.X / 25) + 1, block.Y / 25, 3, block.X / 25, block.Y / 25});
                        }
                        else if (gm.getBlock(block.X - 25, block.Y) != null && gm.getBlock(block.X - 25, block.Y).penetrable && !gm.getBlock(block.X - 25, block.Y).isLiquid) {
                            updateList.add(new Integer[]{block.id, (block.X / 25) - 1, block.Y / 25, 3, block.X / 25, block.Y / 25});
                        }
                    } else if (block != null && block.isFalling) {
                        if (gm.getBlock(block.X, block.Y + 25) != null) {
                            if (gm.getBlock(block.X, block.Y + 25).penetrable) {
                                updateList.add(new Integer[]{block.id, block.X / 25, (block.Y / 25) + 1, 3, block.X / 25, block.Y / 25});
                            }
                        }
                    }
            }
            // 1,2,3 -> BLOCK ZU DEM ES GEHT, mit ID von block der dort sein soll; 4,5,6 -> BLOCK VON DEM ES IST;
            List<Integer[]> uniqueUpdateList = new ArrayList<>();
            for (Integer[] ints : updateList) {
                boolean foundDuplicate = false;
                for (Integer[] existing : uniqueUpdateList) {
                    if (ints[1].equals(existing[1]) && ints[2].equals(existing[2])) {
                        uniqueUpdateList.add(new Integer[]{existing[0],existing[1],existing[2]});
                        existing[0] = -1;
                        foundDuplicate = true;
                        break;
                    }
                }
                if (!foundDuplicate) {
                    uniqueUpdateList.add(ints);
                }
            }
            waterUpdateCount = uniqueUpdateList.size();
            for (Integer[] ints: uniqueUpdateList){
                if(ints[0] != -1){
                    gm.map.blockSelector(ints[0],ints[1],ints[2]);
                    if(ints.length > 3){
                        gm.map.blockSelector(ints[3],ints[4],ints[5]);
                    }
                }
            }
        }
    }
    public void drawInteractiveState() {
        clearWindow(gameBackground);
        switch (gm.currentInteractState) {
            case GameManager.furnaceInteractState -> furnaceInteractiveState();
            case 2 -> System.out.println("kommt noch nh");
        }

    }
    private void drawMessage(){
        imageG.setFont(getFont().deriveFont(Font.ITALIC,20));
        imageG.setColor(Color.black);
        for(int i = 0; i < gm.messages.size(); i++){
            if(gm.messages.get(i) != null){
                imageG.drawString(gm.messages.get(i),50,screenHeight-100 + (i*20));
                if(gm.liveTime.get(i) - 1 == 0){
                    gm.liveTime.remove(i);
                    gm.messages.remove(i);
                }
                else {
                    gm.liveTime.set(i,gm.liveTime.get(i) - 1);
                }
            }
        }
    }
    private void drawAnyInventory(Inventory inv, int height, int width){
        int spacing = 3;
        imageG.setColor(Color.gray);
        imageG.fillRect(width,height,((gm.map.tileSize + spacing) * inv.width/gm.map.tileSize),((gm.map.tileSize + spacing) * inv.height/gm.map.tileSize));
        imageG.setColor(Color.black);
        for (int i = 0; i < inv.width/gm.map.tileSize; i++){
            for (int l = 0; l < inv.height/gm.map.tileSize; l++) {
                if(inv == gm.kl.primaryInv || inv == gm.kl.secondaryInv){
                    if(inv.activeInventorySpace != null && inv.activeInventorySpace.x == i && inv.activeInventorySpace.y == l){
                        imageG.setColor(Color.yellow);
                        imageG.fillRect(width + (i*(gm.map.tileSize + spacing)),height + (l*(gm.map.tileSize + spacing)),gm.map.tileSize,gm.map.tileSize);
                        imageG.drawRect(width + (i*(gm.map.tileSize + spacing)),height + (l*(gm.map.tileSize + spacing)),gm.map.tileSize,gm.map.tileSize);
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
                imageG.drawRect(width + (i*(gm.map.tileSize + spacing)),height + (l*(gm.map.tileSize + spacing)),gm.map.tileSize,gm.map.tileSize);
                for (java.util.Map.Entry<Entity, Integer> entry : inv.inventory.entrySet()) {
                    if(entry.getKey() != null){
                        if(entry.getKey().inventoryX == i && entry.getKey().inventoryY == l){
                            imageG.drawImage(gm.map.getPictureForID(entry.getKey().id),width + (i*(gm.map.tileSize + spacing)),height + (l*(gm.map.tileSize + spacing)),null);
                            if(entry.getValue() > 1){
                                imageG.drawString(String.valueOf(entry.getValue()),width + (i*(gm.map.tileSize + spacing)),height + (l*(gm.map.tileSize + spacing)) + gm.map.tileSize);
                            }
                        }
                    }
                }
            }
        }
    }
    private void drawPlayerHealth(){
        for (double i = 2; i <= gm.p.maxHealth; i += 2){
            if(i <= gm.p.health){
                imageG.drawImage(gm.ah.heart_full, (int) (screenWidth - 300 + (i*12.5)),50,new Color(0,0,0,0),null);
            }
            else if(i - 1 == gm.p.health){
                imageG.drawImage(gm.ah.heart_half, (int) (screenWidth - 300 + (i*12.5)),50,new Color(0,0,0,0),null);
            }
            else{
                imageG.drawImage(gm.ah.heart_empty, (int) (screenWidth - 300 + (i*12.5)),50,new Color(0,0,0,0),null);
            }
        }
        if(gm.p.health <= 0){
            gm.currentState = GameManager.deathState;
        }
    }
    private void drawPlayerBreath(){
        for (double i = 2; i <= gm.p.maxOxygen; i += 2){
            if(i <= gm.p.oxygen){
                imageG.drawImage(gm.ah.oxygen_full, (int) (screenWidth - 300 + (i*12.5)),100,new Color(0,0,0,0),null);
            }
            else if(i - 1 == gm.p.oxygen){
                imageG.drawImage(gm.ah.oxygen_half, (int) (screenWidth - 300 + (i*12.5)),100,new Color(0,0,0,0),null);
            }
            else{
                imageG.drawImage(gm.ah.oxygen_empty, (int) (screenWidth - 300 + (i*12.5)),100,new Color(0,0,0,0),null);
            }
        }
        if(gm.p.oxygen <= 0){
            if(wfFFour.waitForFrames(30)){
                gm.p.health--;
                gm.playSound("damage.wav");
            }
        }
    }
    public void drawInventoryState(){
        clearWindow(gameBackground);
        drawAnyInventory(gm.p.inv,(screenHeight - 250)/2,100);
        drawAnyInventory(gm.p.hotbar,screenHeight - 50,(screenWidth/2 - (gm.p.hotbar.maxSize/2 * 28)));
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
        imageG.drawString("FPS: " + df.format(gm.fps),10,50);
        imageG.drawString("Loaded Blocks: " + gm.blocks.size(),10,75);
        imageG.drawString("Updated Blocks: " + gm.p.getOnlyVisibleBlocks().length,10,100);
        imageG.drawString("Updated [Flow & Gravity] Blocks: " +  waterUpdateCount ,10,125);
        imageG.drawString("Player Coordinates: [X:" + (gm.p.X - gm.p.offsetX) + " Y:" + (gm.p.Y + gm.p.offsetY) + "]" ,10,150);
        imageG.drawString("Draw Grid [F2]: " + gm.map.vertices,10,175);
        imageG.drawString("Specific Block [F4]" + gm.map.specificBlockShown, 10, 200);
    }
}