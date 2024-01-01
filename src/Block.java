import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {
    public int height, width;
    public int X,Y;
    public Image sprite;
    public Block(int h, int w, int i, int l,BufferedImage sprite){
        this.height = h;
        this.width = w;
        this.X = i*25;
        this.Y = l*25;
        this.sprite = sprite.getScaledInstance(width,height, Image.SCALE_DEFAULT);
    }
    public void drawBlockVertices(Graphics g, Player p){
        g.drawRect(X + p.offsetX, Y - p.offsetY, width, height);
    }
    public void drawBlock(Graphics g, Player p){
        g.drawImage(sprite,X + p.offsetX,Y - p.offsetY,null);
    }
}
