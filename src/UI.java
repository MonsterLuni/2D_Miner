import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

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

    public BufferedImage fullscreenBuffer;
    public Graphics imageG = bufferedImage.getGraphics();
    public boolean fullscreen = false;


    public UI(){
        setSize(screenWidth, screenHeight);
        KeyListener kh = new KeyListener(this);
        MouseListener ml = new MouseListener(this);
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
        lastTime = System.currentTimeMillis();
        map.loadMap();
        fpsLimiter(60);
    }

    public void fpsLimiter(int limit){
        long now = System.currentTimeMillis();
        long last = lastTime;
        double fps = 1000 / (double)(now - last);
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
        drawPlayer(imageG);
        updatePlayer();
        map.drawMap(imageG);
        if(fullscreen){
            g.drawImage(fullscreenBuffer,0,0,null);
        }
        else{
            g.drawImage(bufferedImage,0,0,null);
        }
    }
    public void drawPlayer(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(p.X,p.Y,20,p.height);
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
            fullscreen = false;
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
            fullscreen = true;
        }
    }
}