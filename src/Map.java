import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
    public UI ui;
    public boolean first;
    BufferedImage grass, dirt;
    public Map(UI ui){
        this.ui = ui;
        ui.blocks = new ArrayList<>((ui.screenWidth / 25)*(ui.screenHeight / 25));
        try {
            grass = ImageIO.read(new File("F:\\Java\\_2D_Miner\\assets\\tiles\\grass.png"));
            dirt = ImageIO.read(new File("F:\\Java\\_2D_Miner\\assets\\tiles\\dirt.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int[] mapHeightPerColumn = {300,300,300,300,250,250,200,200,200,250,250,250,250,250,250,250,300,300,350,350,350,300,300,300,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,300,300,300,300,300,300,300,250,250,250,250,250,250,250,300,300,300,300,300,300,300,300,300,300};
    public void loadMap() {
        for(int i = 0; i < ui.screenWidth / 25; i++) {
            first = true;
            for (int l = 0; l < ui.screenHeight / 25; l++) {
                if (l * 25 >= mapHeightPerColumn[i]) {
                    if(first){
                        ui.blocks.add(new Block(25,25,i,l,grass));
                        first = false;
                    }
                    else{
                        ui.blocks.add(new Block(25,25,i,l,dirt));
                    }
                }
            }
        }
    }
    public void drawmapHeightPerColumn(Graphics g){
        int i = 1;
        for (int height : mapHeightPerColumn){
            g.setColor(Color.red);
            g.drawLine((i - 1) * 25 + ui.p.offsetX,height - ui.p.offsetY,i*25 + ui.p.offsetX,height - ui.p.offsetY);
            i++;
        }
    }
    public void drawMap(Graphics g){
        for (Block block : ui.blocks) {
            int positionPlayer = Math.round(((ui.p.X - (float) ui.p.offsetX) - (30 * 25)) /25);
            if(block.X / 25 < positionPlayer || block.X / 25 > positionPlayer + 59 ) {
               // hier gibt es nichts zu sehens
            }
            else{
                g.setColor(Color.blue);
                block.drawBlock(g, ui.p);
                if(ui.debug){
                    block.drawBlockVertices(g, ui.p);
                }
            }
        }
        if(ui.debug){
            drawmapHeightPerColumn(g);
        }
    }
}