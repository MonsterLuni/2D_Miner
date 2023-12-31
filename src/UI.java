import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI extends JFrame {
    public long lastTime;
    public Player p;
    public Map map;
    public KeyListener kh;
    public boolean running = true;
    public int screenHeight = 1000;
    public int screenWidth = 1000;
    public ArrayList<Block> blocks;
    public BufferedImage bufferedImage = new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_RGB);
    public Graphics imageG = bufferedImage.getGraphics();


    public UI(){
        setSize(screenWidth, screenHeight);
        KeyListener kh = new KeyListener(this);
        MouseListener ml = new MouseListener(this);
        this.kh = kh;
        p = new Player(this,kh);
        map = new Map(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        g.drawImage(bufferedImage,0,0,null);
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
}
