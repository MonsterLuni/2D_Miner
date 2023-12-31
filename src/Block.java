import java.awt.*;

public class Block {
    public int height, width;
    public int X,Y;
    public Block(int h, int w, int i, int l){
        this.height = h;
        this.width = w;
        this.X = i*25;
        this.Y = l*25;
    }
    public void drawBlock(Graphics g){
        g.drawRect(X, Y, width, height);
    }
}
