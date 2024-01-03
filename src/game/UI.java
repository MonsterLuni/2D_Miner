package game;

import listener.KeyListener;
import listener.MouseListener;
import listener.MouseMotionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI extends JFrame {
    public long lastTime;
    public Player p;
    public Map map;
    public KeyListener kh;
    public boolean running = true;
    public int defaultHeight = 720;
    public int defaultWidth = 1280;
    public int screenHeight = defaultHeight;
    public int screenWidth = defaultWidth;
    public ArrayList<Block> blocks;
    public BufferedImage bufferedImage = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_RGB);
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public BufferedImage fullscreenBuffer;
    public Graphics imageG = bufferedImage.getGraphics();
    public boolean fullscreen = false;
    public boolean debug = false;
    public double fps = 0;
    public MouseListener ml;

    public UI(){
        setSize(screenWidth, screenHeight);
        KeyListener kh = new KeyListener(this);
        ml = new MouseListener(this);
        MouseMotionListener mml = new MouseMotionListener(this);
        this.kh = kh;
        p = new Player(this,kh);
        map = new Map(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        addKeyListener(kh);
        addMouseListener(ml);
        addMouseMotionListener(mml);
        lastTime = System.currentTimeMillis();
        map.loadMap();
        map.loadHitBoxes();
        fpsLimiter(60);
    }

    public void fpsLimiter(int limit){
        long now = System.currentTimeMillis();
        long last = lastTime;
        fps = 1000 / (double)(now - last);
        while (fps >= limit){
            now = System.currentTimeMillis();
            fps = 1000 / (double)(now - last);
        }
        // Here comes the Code that actually gets run
        update(super.getGraphics());
        lastTime = now;
        if(running){
            fpsLimiter(limit);
        }
    }
    public void update(Graphics g){
        imageG.clearRect(0,0,screenWidth,screenHeight);
        imageG.setColor(Color.white);
        imageG.fillRect(0,0,screenWidth,screenHeight);
        p.drawPlayer(imageG);
        updatePlayer();
        map.drawMap(imageG);
        if(debug){
            drawDebug();
        }
        if(fullscreen){
            g.drawImage(fullscreenBuffer,0,0,null);
        }
        else{
            g.drawImage(bufferedImage,0,0,null);
        }
    }
    private void drawDebug(){
        imageG.setColor(Color.black);
        imageG.setFont(getFont().deriveFont(Font.ITALIC,32f));
        imageG.drawString(df.format(fps),10,50);
    }

    private void updatePlayer() {
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